package org.app.bot.telegram.service;

import org.app.bot.telegram.property.PropertyLoader;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.Map;

import static org.app.bot.telegram.property.PropertyLoader.USER_PROPERTY_FILE_PATH;

@Service
public class UserService {

    private final Map<String, String> registeredUsers;

    public UserService(PropertyLoader propertyLoader) {
        this.registeredUsers = propertyLoader.loadProperty(USER_PROPERTY_FILE_PATH); // TODO: заменить на LoadProperties;
    }

    public void checkRegistration(Update update) {
        String chatId = update.getMessage().getChatId().toString();
        if (!registeredUsers.containsKey(chatId)) {
            System.out.println("Вы не зарегистрированы");
        }
    }
}
