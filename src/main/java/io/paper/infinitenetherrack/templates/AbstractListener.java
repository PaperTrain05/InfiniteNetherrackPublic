package io.paper.infinitenetherrack.templates;

import io.paper.infinitenetherrack.Heart;
import org.bukkit.event.Listener;

public abstract class AbstractListener
        extends AbstractManager
        implements Listener {
    public AbstractListener(Heart heart) {
        super(heart);
    }
}
