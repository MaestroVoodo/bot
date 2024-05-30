package org.app.bot.telegram.property.loader;

import org.app.bot.telegram.util.ClassUtils;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.concurrent.Flow;

import static java.util.Objects.nonNull;

@Component
public class PropertyButtonLoader extends PropertyLoader {

    public PropertyButtonLoader(ClassUtils classUtils) {
        super(classUtils);
    }

    public Map<String, Flow.Subscriber> loadSubscribers(String fileName) {
        Map<String, Flow.Subscriber> publishers = new HashMap<>();
        Properties properties = load(fileName);
        Set<String> propertyNames = properties.stringPropertyNames();

        for (String key : propertyNames) {
            String className = properties.getProperty(key);
            Flow.Subscriber publisher = getClassUtils().getBean(className, Flow.Subscriber.class);

            if (nonNull(publisher)) {
                publishers.put(key, publisher);
            }
        }
        return publishers;
    }
}
