package org.app.bot.telegram;

import lombok.RequiredArgsConstructor;
import org.app.bot.telegram.config.TelegramBotConfig;
import org.app.bot.telegram.service.UserService;
import org.app.bot.telegram.service.button.ButtonService;
import org.app.bot.telegram.session.Session;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import static java.util.Objects.nonNull;

@Component
@RequiredArgsConstructor
public class TelegramBot extends TelegramLongPollingBot {

    private final UserService userService;
    private final ButtonService buttonService;
    private final TelegramBotConfig config;
    private final Session session;

    @Override
    public String getBotUsername() {
        return config.getBotUsername();
    }

    @Override
    public String getBotToken() {
        return config.getBotToken();
    }

    @Override
    public void onUpdateReceived(Update update) {
        userService.checkRegistration(update);
        buttonService.click(update);

        sendMessage(session.getSendMessage());
    }

    private void sendMessage(SendMessage sendMessage) {
        if (nonNull(sendMessage)) {
            try {
                execute(sendMessage);
            } catch (TelegramApiException e) {
                throw new RuntimeException("Failed to send message", e);
            }
        }
    }
}
