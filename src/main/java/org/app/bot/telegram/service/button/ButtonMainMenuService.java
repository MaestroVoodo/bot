package org.app.bot.telegram.service.button;

import lombok.RequiredArgsConstructor;
import org.app.bot.telegram.button.DictMockButton;
import org.app.bot.telegram.button.DictProblemButton;
import org.app.bot.telegram.service.MessageService;
import org.app.bot.telegram.session.Session;
import org.app.bot.telegram.util.ClassUtils;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;

@Service
@RequiredArgsConstructor
public class ButtonMainMenuService extends MessageService {

    private static final String CHOISE_BUTTON = "Выберите одну из кнопок:";
    private final Session session;
    private final ClassUtils classUtils;

    public void sendMenu() {
        DictMockButton dictMockButton = classUtils.getBean(DictMockButton.class.getSimpleName(), DictMockButton.class);
        DictProblemButton dictProblemButton = classUtils.getBean(DictProblemButton.class.getSimpleName(), DictProblemButton.class);
        ReplyKeyboardMarkup replyMarkup = getReplyMarkup(dictMockButton, dictProblemButton);

        SendMessage response = createResponse(
                session.getUpdate(),
                replyMarkup,
                CHOISE_BUTTON);

        session.setSendMessage(response);
    }
}
