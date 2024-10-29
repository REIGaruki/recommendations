package pro.sky.team2.bank_service.service;

import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.request.SendMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pro.sky.team2.bank_service.dto.RecommendationDTO;
import pro.sky.team2.bank_service.dto.RecommendationForUserDTO;
import pro.sky.team2.bank_service.repository.TransactionsRepository;

import java.util.ArrayList;
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
        } else if (message.text().startsWith("/recommend")) {
            return sendRecommendationMessage(message);
        } else {
            return sendNotAvailableMessage(message);
        }
    }

    private SendMessage sendStartMessage(Message message) {
        String name = message.chat().firstName() + " " + message.chat().lastName();
        String messageText = "Здравствуйте, уважаемый " + name + "!\n" +
                "Тут должна быть какая-то справка";
        return new SendMessage(message.chat().id(), messageText);
    }

    private SendMessage sendNotAvailableMessage(Message message) {
        return new SendMessage(message.chat().id(), "Неверная команда");
    }

    private SendMessage sendRecommendationMessage(Message message) {
        String messageText;
        String userName = message.text().substring("/recommend ".length());
        List<UUID> listOfCorrespondentUsers = transactionsRepository.getUserIdByName(userName);
        if (listOfCorrespondentUsers.size() != 1) {
            messageText = "Пользователь не найден";
        } else {
            UUID userId = listOfCorrespondentUsers.get(0);
            System.out.println(userId);
           // List<String> names = transactionsRepository.getNamesById(userId);
            List<String> names = new ArrayList<>();
            names.add(transactionsRepository.getF(userId));
            names.add(transactionsRepository.getL(userId));
            Set<RecommendationForUserDTO> recommendations = recommendationService.recommend(userId);
            StringBuilder newProducts = new StringBuilder();
            if (recommendations.isEmpty()) {
                newProducts.append("Новых продуктов нет");
            } else {
                for (RecommendationForUserDTO recommendation : recommendations) {
                    newProducts.append("\n");
                    newProducts.append(recommendation.getName());
                    newProducts.append("\n");
                    newProducts.append(recommendation.getText());
                    newProducts.append("\n");
                }
            }
            messageText = "Здравствуйте, " + names.get(0) +
                    " " + names.get(1) +
                    "\n\nНовые продукты для вас:\n" + newProducts;
        }
        return new SendMessage(message.chat().id(), messageText);
    }
}
