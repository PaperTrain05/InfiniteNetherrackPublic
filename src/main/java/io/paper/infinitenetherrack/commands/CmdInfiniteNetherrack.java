package io.paper.infinitenetherrack.commands;

import io.paper.infinitenetherrack.Main;
import io.paper.infinitenetherrack.config.GeneralConfig;
import io.paper.infinitenetherrack.config.MessageConfig;
import io.paper.infinitenetherrack.managers.MessageManager;
import io.paper.infinitenetherrack.templates.AbstractCommand;
import io.paper.infinitenetherrack.utility.Utility;
import de.tr7zw.changeme.nbtapi.NBTItem;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class CmdInfiniteNetherrack
        extends AbstractCommand {
    MessageConfig msg;
    MessageManager msgManager;
    GeneralConfig cnf;
    List<String> blank = new ArrayList<String>();

    public CmdInfiniteNetherrack(Main main, String command) {
        super(main, command);
    }

    @Override
    public void initialize() {
        this.msgManager = this.getHeart().getManager(MessageManager.class);
        this.msg = this.getHeart().getConfig(MessageConfig.class);
        this.cnf = this.getHeart().getConfig(GeneralConfig.class);
    }

    @Override
    public void deinitialize() {
        this.msgManager = null;
        this.msg = null;
        this.cnf = null;
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if (args.length == 0) {
            this.msgManager.msg(sender, this.msg.getInfiniteObsidianUsage(), new MessageManager.Pair[0]);
            this.msgManager.msg(sender, this.msg.getReloadUsage(), new MessageManager.Pair[0]);
            return;
        }
        if (args[0].equalsIgnoreCase("reload")) {
            if (!sender.hasPermission("infiniteNetherrack.reload")) {
                this.msgManager.msg(sender, this.msg.getNoPermission(), new MessageManager.Pair[0]);
                return;
            }
            this.getHeart().reload();
            this.msgManager.msg(sender, this.msg.getReloadedConfig(), new MessageManager.Pair[0]);
            return;
        }
        if (args[0].equalsIgnoreCase("give")) {
            if (!sender.hasPermission("infiniteNetherrack.give")) {
                this.msgManager.msg(sender, this.msg.getNoPermission(), new MessageManager.Pair[0]);
                return;
            }
            if (args.length < 2) {
                this.msgManager.msg(sender, this.msg.getInfiniteObsidianUsage(), new MessageManager.Pair[0]);
                return;
            }
            Player player = this.msgManager.getPlayer(args[1]);
            if (player == null) {
                this.msgManager.msg(sender, this.msg.getCantFindPlayer(), new MessageManager.Pair<String, String>("{player}", args[1]));
                return;
            }
            int amt = 1;
            if (args.length == 3) {
                Integer amt1 = Utility.getInt(args[2]);
                if (amt1 == null) {
                    this.msgManager.msg(sender, this.msg.getInvalidNumber(), new MessageManager.Pair<String, String>("{num}", args[2]));
                    return;
                }
                amt = amt1;
            }
            ArrayList<String> newLore = new ArrayList<String>();
            this.cnf.getLore().forEach(s -> newLore.add(this.msgManager.pair((String)s, new MessageManager.Pair<String, String>("{price}", this.cnf.getPricePer() + ""))));
            ItemStack obby = Utility.makeItem(Material.getMaterial(this.cnf.getMaterial()), amt, (short)0, this.cnf.getDisplayName(), newLore, this.cnf.isGlow());
            NBTItem nbtItem = new NBTItem(obby);
            nbtItem.setBoolean("infiniteNetherrack", true);
            obby = nbtItem.getItem();
            player.getInventory().addItem(new ItemStack[]{obby});
            this.msgManager.msg((CommandSender)player, this.msg.getReceived(), new MessageManager.Pair<String, String>("{amt}", amt + ""), new MessageManager.Pair<String, String>("{player}", sender.getName()));
            this.msgManager.msg(sender, this.msg.getGave(), new MessageManager.Pair<String, String>("{amt}", amt + ""), new MessageManager.Pair<String, String>("{player}", player.getName()));
        }
    }

    @Override
    public List<String> tabComplete(CommandSender sender, String[] args) {
        if (args.length == 1) {
            return Arrays.asList("give", "reload");
        }
        if (args[0].equalsIgnoreCase("give")) {
            if (!sender.hasPermission("infiniteNetherrack.give")) {
                return this.blank;
            }
            if (args.length == 2) {
                return null;
            }
            if (args.length == 3) {
                return Arrays.asList("1", "2", "3", "4", "5", "6", "7", "8", "9", "10");
            }
        }
        return this.blank;
    }
}
