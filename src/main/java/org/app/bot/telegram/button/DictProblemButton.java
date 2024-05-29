package org.app.bot.telegram.button;

import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

import java.util.List;

/**
 * Кнопка {@value #DICT_NAME}
 */
@Component
public class DictProblemButton extends KeyboardButton {
    public static final String DICT_NAME = "Ошибки загрузки справочников";

    @PostConstruct
    public void setButtonName() {
        setText(DictProblemButton.DICT_NAME);
    }

    public ReplyKeyboardMarkup getReplyMarkup(MainMenuButton mainMenuButton) {
        ReplyKeyboardMarkup keyboardMarkup = new ReplyKeyboardMarkup();
        keyboardMarkup.setKeyboard(List.of(new KeyboardRow(List.of(mainMenuButton))));
        return keyboardMarkup;
    }
}
