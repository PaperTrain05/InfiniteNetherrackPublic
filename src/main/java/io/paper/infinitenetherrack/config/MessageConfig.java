package io.paper.infinitenetherrack.config;

import io.paper.infinitenetherrack.Heart;
import io.paper.infinitenetherrack.templates.AbstractConfiguration;

public class MessageConfig
        extends AbstractConfiguration {
    String reloadUsage = "&cUsage: &7/infiniteObsidian reload";
    String infiniteObsidianUsage = "&cUsage: &7/infiniteObsidian give <player> [amount]";
    String received = "&7You have received &fx{amt}&7 Infinite Obsidian from &c{player}";
    String gave = "&7You have given &c{player}&f x{amt} &7Infinite Obsidian.";
    String cantAfford = "&cYou do not have a sufficient balance to place an Infinite Obsidian block";
    String charged = "&7You we're charged &a${price} &7for your recent Infinite Obsidian placements in the last {sec} seconds.";
    String cantPlaceInSystemFac = "&cYou cannot place Infinite Obsidian in system factions.";
    String reloadedConfig = "&7You have&a reloaded&7 the configuration.";
    String noPermission = "&cYou don not have permission to run that command.";
    String cantFindPlayer = "&cThe player: &f{player}&c cannot be found!";
    String invalidNumber = "&cThe number &f{num} &cis invalid! Try again.";

    public MessageConfig(Heart heart, String path) {
        this.path = path;
        this.heart = heart;
    }

    public String getReloadUsage() {
        return this.reloadUsage;
    }

    public String getInfiniteObsidianUsage() {
        return this.infiniteObsidianUsage;
    }

    public String getReceived() {
        return this.received;
    }

    public String getGave() {
        return this.gave;
    }

    public String getCantAfford() {
        return this.cantAfford;
    }

    public String getCharged() {
        return this.charged;
    }

    public String getCantPlaceInSystemFac() {
        return this.cantPlaceInSystemFac;
    }

    public String getReloadedConfig() {
        return this.reloadedConfig;
    }

    public String getNoPermission() {
        return this.noPermission;
    }

    public String getCantFindPlayer() {
        return this.cantFindPlayer;
    }

    public String getInvalidNumber() {
        return this.invalidNumber;
    }

    public void setReloadUsage(String reloadUsage) {
        this.reloadUsage = reloadUsage;
    }

    public void setInfiniteObsidianUsage(String infiniteObsidianUsage) {
        this.infiniteObsidianUsage = infiniteObsidianUsage;
    }

    public void setReceived(String received) {
        this.received = received;
    }

    public void setGave(String gave) {
        this.gave = gave;
    }

    public void setCantAfford(String cantAfford) {
        this.cantAfford = cantAfford;
    }

    public void setCharged(String charged) {
        this.charged = charged;
    }

    public void setCantPlaceInSystemFac(String cantPlaceInSystemFac) {
        this.cantPlaceInSystemFac = cantPlaceInSystemFac;
    }

    public void setReloadedConfig(String reloadedConfig) {
        this.reloadedConfig = reloadedConfig;
    }

    public void setNoPermission(String noPermission) {
        this.noPermission = noPermission;
    }

    public void setCantFindPlayer(String cantFindPlayer) {
        this.cantFindPlayer = cantFindPlayer;
    }

    public void setInvalidNumber(String invalidNumber) {
        this.invalidNumber = invalidNumber;
    }

    public MessageConfig() {
    }

    public String toString() {
        return "MessageConfig(reloadUsage=" + this.getReloadUsage() + ", infiniteObsidianUsage=" + this.getInfiniteObsidianUsage() + ", received=" + this.getReceived() + ", gave=" + this.getGave() + ", cantAfford=" + this.getCantAfford() + ", charged=" + this.getCharged() + ", cantPlaceInSystemFac=" + this.getCantPlaceInSystemFac() + ", reloadedConfig=" + this.getReloadedConfig() + ", noPermission=" + this.getNoPermission() + ", cantFindPlayer=" + this.getCantFindPlayer() + ", invalidNumber=" + this.getInvalidNumber() + ")";
    }
}
