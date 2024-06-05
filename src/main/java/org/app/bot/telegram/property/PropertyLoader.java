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

    public static final String BUTTON_PROPERTY_FILE_PATH = "/buttons.properties";
    public static final String USER_PROPERTY_FILE_PATH = "/users.properties";

    public Map<String, String> loadProperty(String propertyFilePath) {
        Map<String, String> buttonConfigMap = new HashMap<>();

        try (InputStream input = getClass().getResourceAsStream(propertyFilePath)) {
            if (input == null) {
                throw new RuntimeException("Не найден файл конфигурации: " + propertyFilePath);
            }

            Properties properties = new Properties();
            properties.load(new InputStreamReader(input, StandardCharsets.UTF_8));

            for (String key : properties.stringPropertyNames()) {
                String value = properties.getProperty(key);
                buttonConfigMap.put(key, value);
            }
        } catch (IOException ex) {
            throw new RuntimeException("Ошибка чтения файла конфигурации", ex);
        }

        return buttonConfigMap;
    }
}
