package org.app.bot.telegram.session;

import lombok.Data;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;

@Data
@Component
public class Session {

    private String lastButtonClicked;

    public void update(Update update) {
        lastButtonClicked = update.getMessage().getText();
    }
}
