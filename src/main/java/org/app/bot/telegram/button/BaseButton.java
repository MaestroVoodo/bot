package org.app.bot.telegram.button;

import lombok.Getter;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;

@Getter
public abstract class BaseButton extends KeyboardButton {
    public abstract void click(Update update);
}
