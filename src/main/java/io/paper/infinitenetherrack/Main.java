package io.paper.infinitenetherrack;

import io.paper.infinitenetherrack.commands.CmdInfiniteNetherrack;
import io.paper.infinitenetherrack.config.GeneralConfig;
import io.paper.infinitenetherrack.config.MessageConfig;
import io.paper.infinitenetherrack.handlers.InfiniteNetherrackHandler;
import io.paper.infinitenetherrack.managers.MessageManager;
import io.paper.infinitenetherrack.templates.AbstractCommand;
import io.paper.infinitenetherrack.templates.AbstractConfiguration;
import io.paper.infinitenetherrack.templates.AbstractListener;
import io.paper.infinitenetherrack.templates.AbstractManager;
import io.paper.infinitenetherrack.utility.ConfigUtil;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.event.HandlerList;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

public class Main
        extends JavaPlugin {
    List<AbstractManager> managers = new ArrayList<AbstractManager>();
    List<AbstractConfiguration> configs = new ArrayList<AbstractConfiguration>();
    boolean usingPapi;
    boolean usingMCoreFactions;
    Economy economy = null;

    public void onEnable() {
        this.usingPapi = this.getServer().getPluginManager().isPluginEnabled("PlaceholderAPI");
        Plugin factions = this.getServer().getPluginManager().getPlugin("Factions");
        this.usingMCoreFactions = factions != null && factions.getDescription().getVersion().startsWith("2.");
        this.managers.addAll(Arrays.asList(new InfiniteNetherrackHandler(this), new MessageManager(this), new CmdInfiniteNetherrack(this, "infiniteNetherrack")));
        this.configs.addAll(Arrays.asList(new GeneralConfig(this, this.getDataFolder() + "/General.json"), new MessageConfig(this, this.getDataFolder() + "/Messages.json")));
        this.reload();
        this.registerManagers();
        this.setupEconomy();
        System.out.println(this.getDescription().getName() + " by: " + (String)this.getDescription().getAuthors().get(0) + " has been enabled.");
    }

    public void reload() {
        ArrayList<MessageManager.Pair> toReplace = new ArrayList<MessageManager.Pair>();
        this.configs.forEach(abstractConfiguration -> toReplace.add(new MessageManager.Pair<AbstractConfiguration, AbstractConfiguration>((AbstractConfiguration)abstractConfiguration, ConfigUtil.load(abstractConfiguration, this))));
        toReplace.forEach(pairs -> {
            this.configs.remove(pairs.getKey());
            this.configs.add((AbstractConfiguration)pairs.getValue());
        });
    }

    public void onDisable() {
        this.configs.clear();
        this.managers.forEach(AbstractManager::deinitialize);
        HandlerList.unregisterAll(this);
        this.managers.clear();
        Bukkit.getScheduler().cancelTasks(this);
        System.out.println(this.getDescription().getName() + " by: " + (String)this.getDescription().getAuthors().get(0) + " has been disabled.");
    }

    public <T> T getManager(Class<T> clazz) {
        return (T) this.managers.stream().filter(manager -> manager.getClass().equals(clazz)).findFirst().orElse(null);
    }

    public <T> T getConfig(Class<T> clazz) {
        return (T) this.configs.stream().filter(config -> config.getClass().equals(clazz)).findFirst().orElse(null);
    }

    void registerManagers() {
        this.managers.forEach(abstractManager -> {
            if (abstractManager.isLoaded()) {
                abstractManager.deinitialize();
            }
            if (abstractManager instanceof AbstractListener) {
                this.getServer().getPluginManager().registerEvents((AbstractListener)abstractManager, this);
            } else if (abstractManager instanceof AbstractCommand) {
                this.getCommand(((AbstractCommand)abstractManager).getCommand()).setExecutor((AbstractCommand)abstractManager);
                this.getCommand(((AbstractCommand)abstractManager).getCommand()).setTabCompleter((AbstractCommand)abstractManager);
            }
            abstractManager.initialize();
            abstractManager.setLoaded(true);
        });
    }

    boolean setupEconomy() {
        if (this.getServer().getPluginManager().getPlugin("Vault") == null) {
            return false;
        }
        RegisteredServiceProvider rsp = this.getServer().getServicesManager().getRegistration(Economy.class);
        if (rsp == null) {
            return false;
        }
        this.economy = (Economy)rsp.getProvider();
        return this.economy != null;
    }

    public List<AbstractManager> getManagers() {
        return this.managers;
    }

    public List<AbstractConfiguration> getConfigs() {
        return this.configs;
    }

    public boolean isUsingPapi() {
        return this.usingPapi;
    }

    public boolean isUsingMCoreFactions() {
        return this.usingMCoreFactions;
    }

    public Economy getEconomy() {
        return this.economy;
    }
}
