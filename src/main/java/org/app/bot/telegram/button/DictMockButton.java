package org.app.bot.telegram.button;

import jakarta.annotation.PostConstruct;
import lombok.AllArgsConstructor;
import org.app.bot.telegram.session.Session;
import org.springframework.stereotype.Component;

/**
 * Кнопка {@value #DICT_NAME}
 */
@AllArgsConstructor
@Component
public class DictMockButton extends SubscriberButton {
    public static final String DICT_NAME = "Тип справочника (Mock/НСИ)";

    private final Session session;

    @PostConstruct
    public void setButtonName() {
        setText(DictMockButton.DICT_NAME);
    }
}
