package org.app.bot.telegram.service.button;

import jakarta.annotation.PostConstruct;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.app.bot.telegram.button.BaseButton;
import org.app.bot.telegram.button.MainMenuButton;
import org.app.bot.telegram.property.PropertyLoader;
import org.app.bot.telegram.session.Session;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.HashMap;
import java.util.Map;

import static java.util.Objects.nonNull;
import static java.util.Optional.ofNullable;

@Getter
@Component
@RequiredArgsConstructor
public class ButtonService {

    private final ApplicationContext context;
    private final PropertyLoader propertyLoader;

    private final Map<String, BaseButton> buttons = new HashMap<>();
    private final Session session;

    @PostConstruct
    public void init() {
        Map<String, String> buttonConfigMap = propertyLoader.loadButtonConfig();
        buttonConfigMap.forEach((buttonText, beanName) -> {
            BaseButton buttonBean = (BaseButton) context.getBean(beanName);
            buttons.put(buttonBean.getText(), buttonBean);
        });
    }

    public void click(Update update) {
        String buttonName = update.getMessage().getText();
        BaseButton button = buttons.get(buttonName);

        if (nonNull(button)) {
            button.click(update);
        } else {
            ofNullable(getButton(session.getLastButtonNameClicked()))
                    .ifPresentOrElse(buttonBean -> buttonBean.click(update),
                            () -> clickStart(update));
        }
    }

    public BaseButton getButton(String buttonName) {
        return getButtons().get(buttonName);
    }

    /**
     * Запуск стартового меню
     *
     * @param update входящий объект
     */
    private void clickStart(Update update) {
        update.getMessage().setText(MainMenuButton.BUTTON_NAME);
        getButton(MainMenuButton.BUTTON_NAME).click(update);
    }
}