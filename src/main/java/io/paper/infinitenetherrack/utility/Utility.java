package io.paper.infinitenetherrack.utility;

import java.util.ArrayList;
import java.util.List;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class Utility {
    public static String color(String s) {
        return ChatColor.translateAlternateColorCodes('&', s);
    }

    public static List<String> color(List<String> ss) {
        ArrayList<String> ret = new ArrayList<String>();
        ss.forEach(s -> ret.add(Utility.color(s)));
        return ret;
    }

    public static ItemStack makeItem(Material material, int amount, short data, String name, List<String> lore, boolean glow) {
        ItemStack item = new ItemStack(material, amount, data);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(Utility.color(name));
        meta.setLore(Utility.color(lore));
        if (glow) {
            meta.addEnchant(Enchantment.LUCK, 1, true);
        }
        meta.addItemFlags(new ItemFlag[]{ItemFlag.HIDE_PLACED_ON});
        meta.addItemFlags(new ItemFlag[]{ItemFlag.HIDE_ENCHANTS});
        meta.addItemFlags(new ItemFlag[]{ItemFlag.HIDE_ATTRIBUTES});
        item.setItemMeta(meta);
        return item;
    }

    public static Integer getInt(String s) {
        try {
            return Integer.parseInt(s);
        }
        catch (Exception ignored) {
            return null;
        }
    }
}
