package org.app.bot.telegram.button;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.app.bot.telegram.service.button.ButtonDictProblemService;
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
public class DictProblemButton extends BaseButton {

    public static final String BUTTON_NAME = "Ошибки загрузки справочников";
    public static final String ENTER_INPUT_ENV_NAME = "Введите имя стенда:";

    private final Session session;
    private final ButtonDictProblemService service;

    @PostConstruct
    public void setButtonText() {
        setText(DictProblemButton.BUTTON_NAME);
    }

    @Override
    public void click(Update update) {
        boolean isClicked = BUTTON_NAME.equals(getMessageText(update));
        boolean isLastClicked = BUTTON_NAME.equals(session.getLastButtonNameClicked());

        if (isClicked) {
            session.update(update);
            service.call(ENTER_INPUT_ENV_NAME);
        } else if (isLastClicked) {
            service.call(getMessageText(update));
        }
    }
}
