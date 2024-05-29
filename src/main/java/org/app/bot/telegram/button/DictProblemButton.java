package org.app.bot.telegram.button.dict;

import jakarta.annotation.PostConstruct;
import lombok.NonNull;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;

/**
 * {@value #DICT_NAME}
 */
@Component
public class DictProblemButton extends KeyboardButton {
    public static final String DICT_NAME = "Проверить ошибки загрузки справочников";

    @PostConstruct
    public void setButtonName() {
        setText(DictProblemButton.DICT_NAME);
    }
}
