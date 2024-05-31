package org.app.bot.telegram;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.app.bot.telegram.config.TelegramBotConfig;
import org.app.bot.telegram.property.loader.Subscriber;
import org.app.bot.telegram.service.MainMenuService;
import org.app.bot.telegram.session.Session;
import org.app.bot.telegram.subscriber.LatchableSubscriber;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.concurrent.CountDownLatch;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

@Component
@RequiredArgsConstructor
public class TelegramBot extends TelegramLongPollingBot {

    private final MainMenuService mainMenuService;
    private final TelegramBotConfig config;
    private final Session session;
    private final Subscriber subscriber;
    private CountDownLatch latch;

    @PostConstruct
    private void init() {

        // Подписыаемся на события (Подписываем кнопки определенные в telegram.bot.properties)
        subscriber.subscribe();

        // Количество подписчиков
        int subscribersCount = subscriber.getSubscribers().size();

        // Инициализация наблюдателя за подписчиками
        latch = new CountDownLatch(subscribersCount);

        // Поставка наблюдателя в подписчики для определения завершения их работы
        subscriber.getSubscribers().stream()
                .filter(LatchableSubscriber.class::isInstance)
                .map(LatchableSubscriber.class::cast)
                .forEach(sub -> sub.setLatch(latch));
    }

    @Override
    public String getBotUsername() {
        return config.getBotUsername();
    }

    @Override
    public String getBotToken() {
        return config.getBotToken();
    }

    @Override
    public void onUpdateReceived(Update update) {
        if (isNull(session.getSendMessage())) {
            handleNewUpdate(update);
        } else {
            handleSubscriberUpdate(update);
        }
    }

    private void handleNewUpdate(Update update) {
        session.update(update);
        mainMenuService.sendMenu();
        sendMessage(session.getSendMessage());
    }

    private void handleSubscriberUpdate(Update update) {
        subscriber.getPublisher().submit(update);

        try {
            latch.await();
            sendMessage(session.getSendMessage());
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new RuntimeException("Thread was interrupted", e);
        }
    }

    private void sendMessage(SendMessage sendMessage) {
        if (nonNull(sendMessage)) {
            try {
                execute(sendMessage);
            } catch (TelegramApiException e) {
                throw new RuntimeException("Failed to send message", e);
            }
        }
    }
}
