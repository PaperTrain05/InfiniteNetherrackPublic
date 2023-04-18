package io.paper.infinitenetherrack.config;

import io.paper.infinitenetherrack.Main;
import io.paper.infinitenetherrack.templates.AbstractConfiguration;

import java.util.Arrays;
import java.util.List;

public class GeneralConfig
        extends AbstractConfiguration {
    String displayName = "&c&lInfinite Netherrack";
    List<String> lore = Arrays.asList("", "&7Place this block to never run out of netherrack", "&7It will charge &a${price} &7each time its placed");
    String material = "BEDROCK";
    boolean glow = true;
    long pricePer = 1000L;
    String infiniteBlockMaterial = "NETHERRACK";
    long messageEveryXSeconds = 45L;
    boolean disablePlacingInSystemFactions = true;

    public GeneralConfig(Main main, String path) {
        this.path = path;
        this.main = main;
    }

    public String getDisplayName() {
        return this.displayName;
    }

    public List<String> getLore() {
        return this.lore;
    }

    public String getMaterial() {
        return this.material;
    }

    public boolean isGlow() {
        return this.glow;
    }

    public long getPricePer() {
        return this.pricePer;
    }

    public String getInfiniteBlockMaterial() {
        return this.infiniteBlockMaterial;
    }

    public long getMessageEveryXSeconds() {
        return this.messageEveryXSeconds;
    }

    public boolean isDisablePlacingInSystemFactions() {
        return this.disablePlacingInSystemFactions;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public void setLore(List<String> lore) {
        this.lore = lore;
    }

    public void setMaterial(String material) {
        this.material = material;
    }

    public void setGlow(boolean glow) {
        this.glow = glow;
    }

    public void setPricePer(long pricePer) {
        this.pricePer = pricePer;
    }

    public void setInfiniteBlockMaterial(String infiniteBlockMaterial) {
        this.infiniteBlockMaterial = infiniteBlockMaterial;
    }

    public void setMessageEveryXSeconds(long messageEveryXSeconds) {
        this.messageEveryXSeconds = messageEveryXSeconds;
    }

    public void setDisablePlacingInSystemFactions(boolean disablePlacingInSystemFactions) {
        this.disablePlacingInSystemFactions = disablePlacingInSystemFactions;
    }

    public GeneralConfig() {
    }

    public String toString() {
        return "GeneralConfig(displayName=" + this.getDisplayName() + ", lore=" + this.getLore() + ", material=" + this.getMaterial() + ", glow=" + this.isGlow() + ", pricePer=" + this.getPricePer() + ", infiniteBlockMaterial=" + this.getInfiniteBlockMaterial() + ", messageEveryXSeconds=" + this.getMessageEveryXSeconds() + ", disablePlacingInSystemFactions=" + this.isDisablePlacingInSystemFactions() + ")";
    }
}
