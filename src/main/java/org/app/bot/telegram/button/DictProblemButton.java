package org.app.bot.telegram.button;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.app.bot.telegram.service.DictProblemService;
import org.app.bot.telegram.session.Session;
import org.app.bot.telegram.subscriber.LatchableSubscriber;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Flow;

/**
 * Кнопка {@value #BUTTON_NAME}
 */
@Setter
@Component
@RequiredArgsConstructor
public class DictProblemButton extends KeyboardButton implements LatchableSubscriber<Update> {

    public static final String BUTTON_NAME = "Ошибки загрузки справочников";
    private final Session session;
    private Flow.Subscription subscription;
    private final DictProblemService service;
    private CountDownLatch latch;

    @PostConstruct
    public void setButtonText() {
        setText(DictProblemButton.BUTTON_NAME);
    }

    @Override
    public void onSubscribe(Flow.Subscription subscription) {
        this.subscription = subscription;
        subscription.request(1);
    }

    @Override
    public void onNext(Update update) {
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
