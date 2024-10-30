package pro.sky.team2.bank_service.service;

import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.request.SendMessage;
import org.springframework.stereotype.Service;
import pro.sky.team2.bank_service.dto.RecommendationForUserDTO;
import pro.sky.team2.bank_service.repository.TransactionsRepository;

import java.util.List;
import java.util.Set;
import java.util.UUID;

@Service
public class TelegramBotUpdatesManager {

    private final RecommendationService recommendationService;

    private final TransactionsRepository transactionsRepository;

    public TelegramBotUpdatesManager(RecommendationService recommendationService, TransactionsRepository transactionsRepository) {
        this.recommendationService = recommendationService;
        this.transactionsRepository = transactionsRepository;
    }

    public SendMessage manageUpdateMessage(Message message) {
        if (message.text().equals("/start")) {
            return sendStartMessage(message);
        } else if (message.text().startsWith("/recommend ")) {
            return sendRecommendationMessage(message);
        } else {
            return sendNotAvailableMessage(message);
        }
    }

    private SendMessage sendStartMessage(Message message) {
        String name = message.chat().firstName() + " " + message.chat().lastName();
        String messageText = "Здравствуйте, уважаемый " + name + "!\n" +
                "Отправляйте команду\n/recommend {имя_пользователя}\nдля получения рекомендаций по нашим продуктам";
        return new SendMessage(message.chat().id(), messageText);
    }

    private SendMessage sendNotAvailableMessage(Message message) {
        return new SendMessage(message.chat().id(), "Неверная команда");
    }

    private SendMessage sendRecommendationMessage(Message message) {
        String messageText;
        String userName = message.text().substring("/recommend ".length());
        List<String> listOfUserInfo = transactionsRepository.getUserInfoByName(userName);
        if (listOfUserInfo.size() != 3) {
            messageText = "Пользователь не найден";
        } else {
            UUID userId = UUID.fromString(listOfUserInfo.get(0));
            Set<RecommendationForUserDTO> recommendations = recommendationService.recommend(userId);
            StringBuilder newProducts = new StringBuilder();
            if (recommendations.isEmpty()) {
                newProducts.append("Новых продуктов нет");
            } else {
                for (RecommendationForUserDTO recommendation : recommendations) {
                    newProducts.append(formatRecommendation(recommendation));
                }
            }
            messageText = "Здравствуйте, " + listOfUserInfo.get(1) +
                    " " + listOfUserInfo.get(2) +
                    "\n\nНовые продукты для вас:\n" + newProducts;
        }
        return new SendMessage(message.chat().id(), messageText);
    }

    private String formatRecommendation(RecommendationForUserDTO recommendation) {
        return "\n" +
                recommendation.getName() +
                "\n" +
                recommendation.getText() +
                "\n";
    }
}
