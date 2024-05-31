package org.app.bot.telegram.service;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.app.bot.telegram.button.MainMenuButton;
import org.app.bot.telegram.session.Session;
import org.app.bot.telegram.util.ClassUtils;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

import java.util.List;

import static org.app.bot.telegram.button.DictMockButton.BUTTON_NAME;
import static org.app.bot.telegram.button.DictMockButton.INPUT_DICT_NAME;
import static org.app.bot.telegram.property.loader.Subscriber.TURN_CLICKED_ANOTHER_BUTTONS_OFF;

@Service
@RequiredArgsConstructor
public class DictMockService {

    private final Session session;
    private final ClassUtils classUtils;

    private static final String IS_MOCK = "Мок";
    private static final String REAL = "НСИ";


    public void call(String message) {

        SendMessage response = null;

        if (BUTTON_NAME.equals(message)) {
            response = createResponse(
                    session.getUpdate(),
                    getReplyMarkup(classUtils.getBean(MainMenuButton.class.getSimpleName(), MainMenuButton.class)),
                    INPUT_DICT_NAME);

        } else if (!TURN_CLICKED_ANOTHER_BUTTONS_OFF.equals(message)) {
            response = createResponse(
                    session.getUpdate(),
                    getReplyMarkup(classUtils.getBean(MainMenuButton.class.getSimpleName(), MainMenuButton.class)),
                    "Введено и обработано: " + message);
        }

        session.setSendMessage(response);
    }

    private static SendMessage createResponse(Update update, ReplyKeyboardMarkup keyboardMarkup, String message) {
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
