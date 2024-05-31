package org.app.bot.telegram.button;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.app.bot.telegram.service.DictMockService;
import org.app.bot.telegram.session.Session;
import org.app.bot.telegram.subscriber.LatchableSubscriber;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;

import java.util.Optional;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Flow;

/**
 * Кнопка {@value #BUTTON_NAME}
 */
@Setter
@Component
@RequiredArgsConstructor
public class DictMockButton extends KeyboardButton implements LatchableSubscriber<Update> {

    public static final String BUTTON_NAME = "Тип справочника (Mock/НСИ)";
    public static final String INPUT_DICT_NAME = "Введите имя справочника";
    private final Session session;
    private final DictMockService service;
    private Flow.Subscription subscription;
    private CountDownLatch latch;

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
    public void onNext(Update update) {

        String input = Optional.ofNullable(update)
                .map(Update::getMessage)
                .map(Message::getText)
                .orElse(null);

        boolean isClicked = BUTTON_NAME.equals(input);
        boolean isLastClicked = BUTTON_NAME.equals(session.getLastButtonNameClicked());

        if (isClicked) {
            session.update(update);
            service.call(INPUT_DICT_NAME);
        } else if (isLastClicked) {
            service.call(input);
        }

        latch.countDown();
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
