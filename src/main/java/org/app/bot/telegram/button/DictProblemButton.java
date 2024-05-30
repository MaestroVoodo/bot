package org.app.bot.telegram.button;

import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;

/**
 * Кнопка {@value #DICT_NAME}
 */
@Component
public class DictProblemButton extends SubscriberButton {
    public static final String DICT_NAME = "Ошибки загрузки справочников";

    @PostConstruct
    public void setButtonName() {
        setText(DictProblemButton.DICT_NAME);
    }
}
