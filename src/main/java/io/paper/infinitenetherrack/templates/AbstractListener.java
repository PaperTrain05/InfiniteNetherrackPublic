package io.paper.infinitenetherrack.templates;

import io.paper.infinitenetherrack.Main;
import org.bukkit.event.Listener;

public abstract class AbstractListener
        extends AbstractManager
        implements Listener {
    public AbstractListener(Main main) {
        super(main);
    }
}
