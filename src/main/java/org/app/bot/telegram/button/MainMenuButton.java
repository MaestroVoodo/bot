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
public class MainMenuButton extends KeyboardButton {
    public static final String DICT_NAME = "Вернуться в меню";

    @PostConstruct
    public void setButtonName() {
        setText(MainMenuButton.DICT_NAME);
    }
}
