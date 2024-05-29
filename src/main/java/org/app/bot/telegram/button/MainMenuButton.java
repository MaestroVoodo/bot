package org.app.bot.telegram.button;

import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

import java.util.List;

/**
 * {@value #DICT_NAME}
 * Кнопка "Проверить тип справочника (Mock/НСИ)"
 */
@Component
public class DictMockButton extends KeyboardButton {

    public static final String DICT_NAME = "Тип справочника (Mock/НСИ)";

    @PostConstruct
    public void setButtonName() {
        setText(DictMockButton.DICT_NAME);
    }

    public getButtonMarkup() {
        ReplyKeyboardMarkup keyboardMarkup = new ReplyKeyboardMarkup();
        keyboardMarkup.setKeyboard(List.of(new KeyboardRow(List.of(dictMockButton, dictProblemButton))));
        return keyboardMarkup;
    }
}
