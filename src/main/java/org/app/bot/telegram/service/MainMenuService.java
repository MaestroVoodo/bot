package org.app.bot.telegram.service;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.app.bot.telegram.button.DictMockButton;
import org.app.bot.telegram.button.DictProblemButton;
import org.app.bot.telegram.session.Session;
import org.app.bot.telegram.util.ClassUtils;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MainMenuService {

    private static final String CHOISE_BUTTON = "Выберите одну из кнопок:";
    private final Session session;
    private final ClassUtils classUtils;

    public void call(String inputMessage) {
        DictMockButton dictMockButton = classUtils.getBean(DictMockButton.class.getSimpleName(), DictMockButton.class);
        DictProblemButton dictProblemButton = classUtils.getBean(DictProblemButton.class.getSimpleName(), DictProblemButton.class);
        ReplyKeyboardMarkup replyMarkup = getReplyMarkup(dictMockButton, dictProblemButton);

        SendMessage response = createResponse(
                session.getUpdate(),
                replyMarkup,
                CHOISE_BUTTON);

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
