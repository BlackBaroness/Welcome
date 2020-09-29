package ru.baronessdev.welcome;

import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;

public final class Core extends JavaPlugin {

    private static ConfigCenter configCenter;
    private static JavaPlugin plugin;

    @Override
    public void onEnable() {
        saveDefaultConfig();
        plugin = this;

        configCenter = new ConfigCenter(this);
        ReloadCommand reloadCommand = new ReloadCommand(configCenter);
        this.getCommand("welcome").setExecutor(reloadCommand);
        log("Enabled!");

    }

    public static void log(String msg) {
        System.out.println(ChatColor.GREEN + "[Welcome] " + ChatColor.WHITE + msg);
    }

    public static ConfigCenter getConfigCenter() {
        return configCenter;
    }

    public static JavaPlugin getPlugin() {
        return plugin;
    }
}
