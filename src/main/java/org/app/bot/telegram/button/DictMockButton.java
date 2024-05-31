package org.app.bot.telegram.button;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.app.bot.telegram.property.loader.Subscriber;
import org.app.bot.telegram.service.DictMockService;
import org.app.bot.telegram.session.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;

import java.util.concurrent.Flow;

import static org.app.bot.telegram.property.loader.Subscriber.TURN_CLICKED_ANOTHER_BUTTONS_OFF;

/**
 * Кнопка {@value #BUTTON_NAME}
 */
@Setter
@Component
@RequiredArgsConstructor
public class DictMockButton extends KeyboardButton implements Flow.Subscriber {

    public static final String BUTTON_NAME = "Тип справочника (Mock/НСИ)";
    public static final String INPUT_DICT_NAME = "Введите имя справочника";
    private final Session session;
    private final DictMockService service;
    private Flow.Subscription subscription;

    @Autowired
    private Subscriber subscriber;
    boolean clicked;

    @PostConstruct
    public void setButtonText() {
        setText(DictMockButton.BUTTON_NAME);
    }

    @Override
    public void onSubscribe(Flow.Subscription subscription) {
        this.subscription = subscription;
        subscription.request(1);
    }

    @Override
    public void onNext(Object item) {
        String inputMessage = String.valueOf(item);

        if (TURN_CLICKED_ANOTHER_BUTTONS_OFF.equals(inputMessage) && !session.getLastButtonNameClicked().equals(BUTTON_NAME)) {
            clicked = false;
        }

        if (clicked && !TURN_CLICKED_ANOTHER_BUTTONS_OFF.equals(inputMessage)) {
            service.call(inputMessage);
        } else if (BUTTON_NAME.equals(inputMessage)) {
            clicked = true;
            session.setLastButtonNameClicked(BUTTON_NAME);
            subscriber.getPublisher().submit(TURN_CLICKED_ANOTHER_BUTTONS_OFF);
            service.call(INPUT_DICT_NAME);
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
