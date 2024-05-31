package org.app.bot.telegram.property.loader;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.Flow;
import java.util.concurrent.SubmissionPublisher;

@Getter
@Component
@AllArgsConstructor
public class Subscriber {

    public static final String TURN_CLICKED_ANOTHER_BUTTONS_OFF = "turn_clicked_button_of";

    private final SubscriberButtonLoader propertyButtonLoader;
    private final SubmissionPublisher<String> publisher = new SubmissionPublisher<>();

    public void subscribe() {
        List<Flow.Subscriber> subscribers = propertyButtonLoader.loadSubscribers("src/main/resources/telegram_bot.properties");
        subscribers.forEach(publisher::subscribe);
    }
}
