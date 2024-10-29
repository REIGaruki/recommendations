package pro.sky.team2.bank_service.listener;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pro.sky.team2.bank_service.service.TelegramBotUpdatesManager;

import java.util.List;

@Service
public class TelegramBotUpdatesListener implements UpdatesListener{

    private final Logger logger = LoggerFactory.getLogger(TelegramBotUpdatesListener.class);

    @Autowired
    private TelegramBot telegramBot;

    @Autowired
    private TelegramBotUpdatesManager telegramBotUpdatesManager;

    @PostConstruct
    public void init() {
        telegramBot.setUpdatesListener(this);
    }

    @Override
    public int process(List<Update> updates) {
        try {
            updates.forEach(update -> {
                logger.info("Processing update succeeded");
                Message message = update.message();
                SendMessage sendMessage = telegramBotUpdatesManager.manageUpdateMessage(message);
                telegramBot.execute(sendMessage);
                logger.info("Processing update succeeded");
            });
        } catch (Exception e) {
            logger.error("Processing update failed: {?}", e);
        } finally {
            return UpdatesListener.CONFIRMED_UPDATES_ALL;
        }
    }
}
