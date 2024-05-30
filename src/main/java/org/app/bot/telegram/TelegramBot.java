package org.app.bot.telegram;

import jakarta.annotation.PostConstruct;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import org.app.bot.telegram.button.DictMockButton;
import org.app.bot.telegram.button.DictProblemButton;
import org.app.bot.telegram.button.MainMenuButton;
import org.app.bot.telegram.config.TelegramBotConfig;
import org.app.bot.telegram.property.loader.PropertyButtonLoader;
import org.app.bot.telegram.session.Session;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.List;
import java.util.Map;
import java.util.concurrent.Flow;

@Component
@AllArgsConstructor
public class TelegramBot extends TelegramLongPollingBot {

    private final TelegramBotConfig config;
    private final Session session;

    private final MainMenuButton mainMenuButton;
    private final DictMockButton dictMockButton;
    private final DictProblemButton dictProblemButton;

    private final PropertyButtonLoader propertyButtonLoader;

    private Map<String, Flow.Subscriber> subscribers;

    @PostConstruct
    private void init() {
        subscribers = propertyButtonLoader.loadSubscribers("src/main/resources/telegram_bot.properties");
    }

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
        session.update(update);

        String messageText = update.getMessage().getText();
        SendMessage response;

        // Обработчик нажатия кнопки
        if (DictMockButton.DICT_NAME.equals(messageText)) {
            response = buildResponse(update, getReplyMarkup(mainMenuButton), "Введите название справочника:");
        } else if (DictProblemButton.DICT_NAME.equals(messageText)) {
            response = buildResponse(update, getReplyMarkup(mainMenuButton), DictProblemButton.DICT_NAME);
        } else {
            response = buildResponse(update, getReplyMarkup(dictMockButton, dictProblemButton), "Выберите одну из кнопок:");
        }

        try {
            execute(response);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    private static SendMessage buildResponse(Update update, ReplyKeyboardMarkup keyboardMarkup, String message) {
        SendMessage response = new SendMessage();
        response.setChatId(update.getMessage().getChatId().toString());
        response.setText(message);
        response.setReplyMarkup(keyboardMarkup);

        return response;
    }

    @NonNull
    public ReplyKeyboardMarkup getReplyMarkup(KeyboardButton... buttons) {
        ReplyKeyboardMarkup keyboardMarkup = new ReplyKeyboardMarkup();
        keyboardMarkup.setKeyboard(List.of(new KeyboardRow(List.of(buttons))));
        return keyboardMarkup;
    }
}
