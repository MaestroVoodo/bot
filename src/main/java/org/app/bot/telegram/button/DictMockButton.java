package org.app.bot.telegram.button.dict;

import jakarta.annotation.PostConstruct;
import lombok.NonNull;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;

/**
 * {@value #DICT_NAME}
 * Кнопка "Проверить тип справочника (Mock/НСИ)"
 */
@Component
public class DictMockButton extends KeyboardButton {

    public static final String DICT_NAME = "Проверить тип справочника (Mock/НСИ)";

    @PostConstruct
    public void setButtonName() {
        setText(DictMockButton.DICT_NAME);
    }
}
