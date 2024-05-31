package org.app.bot.telegram.button;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.app.bot.telegram.property.loader.Subscriber;
import org.app.bot.telegram.service.MainMenuService;
import org.app.bot.telegram.session.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;

import java.util.Optional;
import java.util.concurrent.Flow;

/**
 * Кнопка {@value #BUTTON_NAME}
 */
@Setter
@Component
@RequiredArgsConstructor
public class MainMenuButton extends KeyboardButton implements Flow.Subscriber {

    public static final String BUTTON_NAME = "Вернуться в меню";
    private final Session session;
    private final MainMenuService service;
    private Flow.Subscription subscription;

    @Autowired
    private Subscriber subscriber;
    boolean clicked;

    @PostConstruct
    public void setButtonText() {
        setText(MainMenuButton.BUTTON_NAME);
    }

    @Override
    public void onSubscribe(Flow.Subscription subscription) {
        this.subscription = subscription;
        subscription.request(1);
    }

    @Override
    public void onNext(Object item) {
        Update update = (Update) (item);

        String input = Optional.ofNullable(update)
                .map(Update::getMessage)
                .map(Message::getText)
                .orElse(null);

        boolean isClicked = BUTTON_NAME.equals(input);

        if (isClicked) {
            session.update(update);
            service.sendMenu();
        }

        subscription.request(1);
    }

    @Override
    public void onError(Throwable throwable) {
        throwable.printStackTrace();
    }

    @Override
    public void onComplete() {
        System.out.println(BUTTON_NAME + " is complete");
    }
}
