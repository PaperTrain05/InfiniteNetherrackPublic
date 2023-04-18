package io.paper.infinitenetherrack.handlers;

import io.paper.infinitenetherrack.Main;
import io.paper.infinitenetherrack.config.GeneralConfig;
import io.paper.infinitenetherrack.config.MessageConfig;
import io.paper.infinitenetherrack.hooks.MCoreFactionsHook;
import io.paper.infinitenetherrack.managers.MessageManager;
import io.paper.infinitenetherrack.templates.AbstractListener;
import de.tr7zw.changeme.nbtapi.NBTItem;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.inventory.ItemStack;

public class InfiniteNetherrackHandler
        extends AbstractListener {
    GeneralConfig cnf;
    MessageManager msgManager;
    MessageConfig msg;
    List<MessageObj> messageObjList = new ArrayList<MessageObj>();
    public Runnable TaskSendMessage = () -> {
        ArrayList toRemove = new ArrayList();
        this.messageObjList.stream().filter(obj -> System.currentTimeMillis() >= obj.getTime() + this.cnf.getMessageEveryXSeconds() * 1000L).forEach(messageObj -> {
            Player player = Bukkit.getPlayer(messageObj.getUuid());
            if (player != null) {
                this.msgManager.msg((CommandSender)player, this.msg.getCharged(), new MessageManager.Pair<String, String>("{price}", messageObj.getAmountSpent() + ""), new MessageManager.Pair<String, String>("{sec}", this.cnf.getMessageEveryXSeconds() + ""));
                toRemove.add(messageObj);
            }
        });
        toRemove.forEach(messageObj -> this.messageObjList.remove(messageObj));
    };

    public InfiniteNetherrackHandler(Main main) {
        super(main);
    }

    @Override
    public void initialize() {
        this.msgManager = this.getHeart().getManager(MessageManager.class);
        this.cnf = this.getHeart().getConfig(GeneralConfig.class);
        this.msg = this.getHeart().getConfig(MessageConfig.class);
    }

    @Override
    public void deinitialize() {
        this.msgManager = null;
        this.cnf = null;
        this.msg = null;
    }

    @EventHandler
    public void onInfiniteNetherrackPlace(BlockPlaceEvent e) {
        if (e.isCancelled()) {
            return;
        }
        ItemStack hand = e.getPlayer().getItemInHand();
        if (hand == null || Material.AIR.equals(hand.getType())) {
            return;
        }
        NBTItem NBTHand = new NBTItem(hand);
        if (!NBTHand.getBoolean("infiniteNetherrack").booleanValue()) {
            return;
        }
        if (this.cnf.isDisablePlacingInSystemFactions() && MCoreFactionsHook.isSystemFaction(e.getBlock().getLocation())) {
            this.msgManager.msg((CommandSender)e.getPlayer(), this.msg.getCantPlaceInSystemFac(), new MessageManager.Pair[0]);
            e.setCancelled(true);
            return;
        }
        OfflinePlayer oPlayer = Bukkit.getOfflinePlayer(e.getPlayer().getUniqueId());
        Economy economy = this.getHeart().getEconomy();
        if (economy.getBalance(oPlayer) < (double)this.cnf.getPricePer()) {
            this.msgManager.msg((CommandSender)e.getPlayer(), this.msg.getCantAfford(), new MessageManager.Pair[0]);
            e.setCancelled(true);
            return;
        }
        economy.withdrawPlayer(oPlayer, this.cnf.getPricePer());
        e.getBlockPlaced().setType(Material.getMaterial(this.cnf.getInfiniteBlockMaterial()));
        e.getPlayer().getItemInHand().setAmount(64);
        UUID uuid = e.getPlayer().getUniqueId();
        MessageObj obj = this.getObj(uuid);
        if (obj == null) {
            this.messageObjList.add(new MessageObj(uuid, System.currentTimeMillis(), this.cnf.getPricePer()));
        } else {
            obj.setAmountSpent(obj.getAmountSpent() + this.cnf.getPricePer());
        }
    }

    private MessageObj getObj(UUID uuid) {
        return this.messageObjList.stream().filter(messageObj -> messageObj.getUuid().equals(uuid)).findFirst().orElse(null);
    }

    private static class MessageObj {
        UUID uuid;
        long time;
        long amountSpent;

        public long getAmountSpent() {
            return this.amountSpent;
        }

        public long getTime() {
            return this.time;
        }

        public UUID getUuid() {
            return this.uuid;
        }

        public MessageObj(UUID uuid, long time, long amountSpent) {
            this.uuid = uuid;
            this.time = time;
            this.amountSpent = amountSpent;
        }

        public void setAmountSpent(long amountSpent) {
            this.amountSpent = amountSpent;
        }
    }
}
