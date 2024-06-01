package org.app.bot.telegram.service.button;

import lombok.RequiredArgsConstructor;
import org.app.bot.telegram.button.MainMenuButton;
import org.app.bot.telegram.service.MessageService;
import org.app.bot.telegram.session.Session;
import org.app.bot.telegram.util.ClassUtils;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

@Service
@RequiredArgsConstructor
public class ButtonDictMockService extends MessageService {

    private final Session session;
    private final ClassUtils classUtils;

    public void call(String message) {
        SendMessage response = createResponse(
                session.getUpdate(),
                getReplyMarkup(classUtils.getBean(MainMenuButton.class.getSimpleName(), MainMenuButton.class)),
                message);

        session.setSendMessage(response);
    }
}
