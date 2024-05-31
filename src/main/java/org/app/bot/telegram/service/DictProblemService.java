package org.app.bot.telegram.service;

import org.springframework.stereotype.Service;

@Service
public class DictMockService {

    private static final String IS_MOCK = "Мок";
    private static final String REAL = "НСИ";

    public String call(String inputMessage) {

//        validate(inputMessage); // TODO:

        boolean isMock = false;

        // Вызов shared сервиса - ответ / мок или не мок

        String responseMessage;

        if (isMock) {
            responseMessage = IS_MOCK;
        } else {
            responseMessage = REAL;
        }

        return responseMessage;
    }
}
