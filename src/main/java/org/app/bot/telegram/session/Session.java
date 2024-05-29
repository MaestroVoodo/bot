package org.app.bot.telegram.session;

import lombok.Data;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.SessionScope;

@Data
@Component
@SessionScope
public class UserSession {

    private String lastButtonClicked;
}
