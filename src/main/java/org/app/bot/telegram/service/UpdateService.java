package org.app.bot.telegram.service;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;

import static java.util.Optional.ofNullable;

@Component
public class UpdateService {

    protected String getChatId(Update update) {
        return ofNullable(update)
                .map(Update::getMessage)
                .map(Message::getChatId)
                .map(Object::toString)
                .orElse(null);
    }
}
