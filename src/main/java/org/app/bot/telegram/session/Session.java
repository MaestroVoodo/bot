package org.app.bot.telegram.session;

import lombok.Data;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

@Data
@Component
public class Session {

    private Update update;
    private SendMessage sendMessage;
    private String lastButtonNameClicked;

    public void update(Update update) {
        this.update = update;
        lastButtonNameClicked = update.getMessage().getText();
    }
}
