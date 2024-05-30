package org.app.bot.telegram.button;

import jakarta.annotation.PostConstruct;
import lombok.Setter;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;

import java.util.concurrent.Flow;

/**
 * Кнопка {@value #BUTTON_NAME}
 */
@Setter
@Component
public class MainMenuButton extends KeyboardButton implements Flow.Subscriber {

    public static final String BUTTON_NAME = "Вернуться в меню";

    private Flow.Subscription subscription;

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
        String inputMessage = String.valueOf(item);
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
