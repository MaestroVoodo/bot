package org.app.bot.telegram.button;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.app.bot.telegram.service.button.ButtonMainMenuService;
import org.app.bot.telegram.session.Session;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.Optional;

/**
 * Кнопка {@value #BUTTON_NAME}
 */
@Setter
@Component
@RequiredArgsConstructor
public class MainMenuButton extends BaseButton {

    public static final String BUTTON_NAME = "Вернуться в меню";
    private final Session session;
    private final ButtonMainMenuService service;

    @PostConstruct
    public void setButtonText() {
        setText(MainMenuButton.BUTTON_NAME);
    }

    @Override
    public void click(Update update) {
        String input = Optional.ofNullable(update)
                .map(Update::getMessage)
                .map(Message::getText)
                .orElse(null);

        boolean isClicked = BUTTON_NAME.equals(input);

        if (isClicked) {
            session.update(update);
            service.sendMenu();
        }
    }
}
