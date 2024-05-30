package org.app.bot.telegram.button;

import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;

/**
 * Кнопка {@value #DICT_NAME}
 */
@Component
public class MainMenuButton extends SubscriberButton {
    public static final String DICT_NAME = "Вернуться в меню";

    @PostConstruct
    public void setButtonName() {
        setText(MainMenuButton.DICT_NAME);
    }
}
