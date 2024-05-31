package org.app.bot.telegram.property.loader;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.List;
import java.util.concurrent.Flow;
import java.util.concurrent.SubmissionPublisher;

@Getter
@Component
@RequiredArgsConstructor
public class Subscriber {

    private List<Flow.Subscriber> subscribers;
    private final SubscriberButtonLoader propertyButtonLoader;
    private final SubmissionPublisher<Update> publisher = new SubmissionPublisher<>();

    public void subscribe() {
        subscribers = propertyButtonLoader.loadSubscribers("src/main/resources/telegram_bot.properties");
        subscribers.forEach(publisher::subscribe);
    }
}
