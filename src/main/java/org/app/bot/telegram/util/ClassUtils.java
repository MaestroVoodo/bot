package org.app.bot.telegram.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import static java.util.Objects.nonNull;

@Configuration
@Component
public class ClassUtils {

    @Autowired
    private ApplicationContext context;

    public static <T> T getInstance(String className, Class<T> classType) {
        try {
            Class<?> clazz = Class.forName(className);
            return classType.cast(clazz.getDeclaredConstructor().newInstance());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public <T> T getBean(String beanName, Class<T> targetType) {
        String bn = firstSymbolToLowerCase(beanName);
        if (nonNull(context) && context.containsBean(bn)) {
            Object bean = context.getBean(bn);
            if (targetType.isInstance(bean)) {
                return targetType.cast(bean);
            } else {
                throw new IllegalArgumentException("Bean '" + beanName + "' is not of type " + targetType);
            }
        } else {
            throw new IllegalStateException("ApplicationContext is not initialized or bean '" + beanName + "' not found.");
        }
    }

    public <T> T getBean(String beanName) {
        String bn = firstSymbolToLowerCase(beanName);
        if (context != null && context.containsBean(bn)) {
            Object bean = context.getBean(bn);
            @SuppressWarnings("unchecked")
            T castedBean = (T) bean;
            return castedBean;
        } else {
            throw new IllegalStateException("ApplicationContext is not initialized or bean '" + beanName + "' not found.");
        }
    }

    public String firstSymbolToLowerCase(String str) {
        if (str.isEmpty()) {
            return str;
        }
        char[] chars = str.toCharArray();
        chars[0] = Character.toLowerCase(chars[0]);
        return new String(chars);
    }
}
