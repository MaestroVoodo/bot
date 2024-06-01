package org.app.bot.telegram.button;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.app.bot.telegram.service.button.ButtonDictMockService;
import org.app.bot.telegram.session.Session;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;

import static org.app.bot.telegram.service.UpdateService.getMessageText;

/**
 * Кнопка {@value #BUTTON_NAME}
 */
@Setter
@Component
@RequiredArgsConstructor
public class DictMockButton extends BaseButton {

    public static final String BUTTON_NAME = "Тип справочника (Mock/НСИ)";
    public static final String ENTER_DICT_NAME = "Введите имя справочника";

    private final Session session;
    private final ButtonDictMockService service;

    @PostConstruct
    public void setButtonText() {
        setText(DictMockButton.BUTTON_NAME);
    }

    @Override
    public void click(Update update) {
        boolean isClicked = BUTTON_NAME.equals(getMessageText(update));
        boolean isLastClicked = BUTTON_NAME.equals(session.getLastButtonNameClicked());

        if (isClicked) {
            session.update(update);
            service.call(ENTER_DICT_NAME);
        } else if (isLastClicked) {
            service.call(getMessageText(update));
        }
    }
}
