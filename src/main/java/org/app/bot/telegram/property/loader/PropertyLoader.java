package org.app.bot.telegram.property.loader;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.app.bot.telegram.util.ClassUtils;
import org.springframework.stereotype.Component;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

@Getter
@RequiredArgsConstructor
@Component
public class PropertyLoader {

    private final ClassUtils classUtils;

    public Properties load(String fileName) {
        Properties properties = new Properties();
        try (FileInputStream fis = new FileInputStream(fileName)) {
            properties.load(fis);
            return properties;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
