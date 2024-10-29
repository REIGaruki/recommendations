package pro.sky.team2.bank_service.service;

import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.request.SendMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pro.sky.team2.bank_service.dto.RecommendationForUserDTO;
import pro.sky.team2.bank_service.repository.TransactionsRepository;

import java.util.List;
import java.util.Set;
import java.util.UUID;

@Service
public class TelegramBotUpdatesManager {

    @Autowired
    RecommendationService recommendationService;

    @Autowired
    TransactionsRepository transactionsRepository;

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
        String messageText = "";
        String userName = message.chat().firstName() + " " +  message.chat().lastName();
        List<UUID> listOfCorrespondentUsers = transactionsRepository.getUserIdByName(message.chat().firstName(), message.chat().lastName());
        if (listOfCorrespondentUsers.size() != 1) {
            messageText = "Пользователь не найден";
        } else {
            UUID userId = listOfCorrespondentUsers.get(0);
            Set<RecommendationForUserDTO> recommendations = recommendationService.recommend(userId);
            messageText = "Здравствуйте " + userName + "\nНовые продукты для вас:\n" + recommendations.toString();
        }
        return new SendMessage(message.chat().id(), messageText);
    }
}
