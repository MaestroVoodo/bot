package org.app.bot.telegram.aop;

import lombok.RequiredArgsConstructor;
import org.app.bot.telegram.property.PropertyLoader;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.util.Map;

@Aspect
@Component
@RequiredArgsConstructor
public class LoadPropertiesAspect {

    private final PropertyLoader propertyLoader;

    @Before("@annotation(loadProperties)")
    public void loadProperties(JoinPoint joinPoint, LoadProperties loadProperties) throws Throwable {
        String propertyFilePath = loadProperties.value();
        Map<String, String> propertiesMap = propertyLoader.loadProperty(propertyFilePath);

        Object target = joinPoint.getTarget();
        Class<?> targetClass = target.getClass();

        for (Field field : targetClass.getDeclaredFields()) {
            if (field.isAnnotationPresent(LoadProperties.class)) {
                field.setAccessible(true);
                field.set(target, propertiesMap);
            }
        }
    }
}
