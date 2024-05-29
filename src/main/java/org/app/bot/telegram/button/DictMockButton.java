package org.app.bot.telegram.button;

import jakarta.annotation.PostConstruct;
import lombok.AllArgsConstructor;
import org.app.bot.telegram.session.Session;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

import java.util.List;

/**
 * Кнопка {@value #DICT_NAME}
 */
@AllArgsConstructor
@Component
public class DictMockButton extends KeyboardButton {
    public static final String DICT_NAME = "Тип справочника (Mock/НСИ)";

    private final Session session;

    @PostConstruct
    public void setButtonName() {
        setText(DictMockButton.DICT_NAME);
    }

    public ReplyKeyboardMarkup getReplyMarkup(MainMenuButton mainMenuButton) {
        ReplyKeyboardMarkup keyboardMarkup = new ReplyKeyboardMarkup();
        keyboardMarkup.setKeyboard(List.of(new KeyboardRow(List.of(mainMenuButton))));
        return keyboardMarkup;
    }
}
