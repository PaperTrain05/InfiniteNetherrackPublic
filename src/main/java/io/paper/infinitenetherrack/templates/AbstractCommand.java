package io.paper.infinitenetherrack.templates;


import io.paper.infinitenetherrack.Main;

import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;

public abstract class AbstractCommand
        extends AbstractManager
        implements CommandExecutor,
        TabExecutor {
    private final String command;

    public AbstractCommand(Main main, String command) {
        super(main);
        this.command = command;
    }

    public AbstractCommand(Main main) {
        super(main);
        String name = this.getClass().getSimpleName().toLowerCase();
        this.command = name.substring(2);
    }

    public abstract void execute(CommandSender var1, String[] var2);

    public List<String> tabComplete(CommandSender sender, String[] args) {
        return null;
    }

    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!command.getName().equals(this.command)) {
            return false;
        }
        this.execute(sender, args);
        return true;
    }

    public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {
        if (!command.getName().equals(this.command)) {
            return null;
        }
        return this.tabComplete(sender, args);
    }

    public String getCommand() {
        return this.command;
    }
}
