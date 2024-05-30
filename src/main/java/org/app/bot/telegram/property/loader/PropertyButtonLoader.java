package org.app.bot.telegram.property.loader;

import org.app.bot.telegram.util.ClassUtils;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.Set;
import java.util.concurrent.Flow;

import static java.util.Objects.nonNull;

@Component
public class PropertyButtonLoader extends PropertyLoader {

    public PropertyButtonLoader(ClassUtils classUtils) {
        super(classUtils);
    }

    public List<Flow.Subscriber> loadSubscribers(String fileName) {
        List<Flow.Subscriber> publishers = new ArrayList<>();
        Properties properties = load(fileName);
        Set<String> propertyNames = properties.stringPropertyNames();

        for (String key : propertyNames) {
            String className = properties.getProperty(key);
            Flow.Subscriber publisher = getClassUtils().getBean(className, Flow.Subscriber.class);

            if (nonNull(publisher)) {
                publishers.add(publisher);
            }
        }
        return publishers;
    }
}
