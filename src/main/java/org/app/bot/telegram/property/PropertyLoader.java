package org.app.bot.telegram.property;

import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

@Component
public class PropertyLoader {

    public static final String BUTTON_PROPERTY_FILE_PATH = "/telegram_bot.properties";

    public Map<String, String> loadButtonConfig() {
        Map<String, String> buttonConfigMap = new HashMap<>();

        try (InputStream input = getClass().getResourceAsStream(BUTTON_PROPERTY_FILE_PATH)) {
            if (input == null) {
                throw new RuntimeException("Не найден файл конфигурации: " + BUTTON_PROPERTY_FILE_PATH);
            }

            Properties properties = new Properties();
            properties.load(new InputStreamReader(input, StandardCharsets.UTF_8));

            for (String key : properties.stringPropertyNames()) {
                String value = properties.getProperty(key);
                buttonConfigMap.put(key, value);
            }
        } catch (IOException ex) {
            throw new RuntimeException("Ошибка чтения файла конфигцрации", ex);
        }

        return buttonConfigMap;
    }
}
