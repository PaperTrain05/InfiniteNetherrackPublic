package io.paper.infinitenetherrack.managers;

import io.paper.infinitenetherrack.Heart;
import io.paper.infinitenetherrack.templates.AbstractManager;
import java.util.Collection;
import java.util.UUID;

import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class MessageManager extends AbstractManager {
    public MessageManager(Heart heart) {
        super(heart);
    }

    public String color(String s) {
        return ChatColor.translateAlternateColorCodes('&', s);
    }

    public String papi(CommandSender sender, String s) {
        if (!this.getHeart().isUsingPapi() || !(sender instanceof Player)) {
            return s;
        }
        return PlaceholderAPI.setPlaceholders((Player)sender, s);
    }

    public String pair(String msg, Pair<String, String> ... pairs) {
        for (Pair<String, String> pair : pairs) {
            msg = msg.replace(pair.getKey(), pair.getValue());
        }
        return msg;
    }

    @SafeVarargs
    public final void msg(CommandSender sender, String msg, Pair<String, String> ... pairs) {
        sender.sendMessage(this.color(this.papi(sender, this.pair(msg, pairs))));
    }

    @SafeVarargs
    public final void msg(CommandSender sender, Collection<String> msg, Pair<String, String> ... pairs) {
        msg.forEach(s -> sender.sendMessage(this.color(this.papi(sender, this.pair((String)s, pairs)))));
    }

    public Player getPlayer(String s) {
        if (s.length() > 20) {
            return Bukkit.getPlayer(UUID.fromString(s));
        }
        return Bukkit.getPlayer(s);
    }

    public static class Pair<K, V> {
        private final K key;
        private final V value;

        public String toString() {
            return this.key + "~" + this.value;
        }

        public K getKey() {
            return this.key;
        }

        public V getValue() {
            return this.value;
        }

        public Pair(K key, V value) {
            this.key = key;
            this.value = value;
        }
    }
}
