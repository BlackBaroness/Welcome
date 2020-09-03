package plugins.baroness;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;

public final class Core extends JavaPlugin {

    @Override
    public void onEnable() {
        File config = new File(getDataFolder() + File.separator + "config.yml");
        if (!config.exists()) saveDefaultConfig();

        ConfigCenter configCenter = new ConfigCenter(this);
        Events events = new Events(configCenter);
        Commands commands = new Commands(configCenter);

        Bukkit.getPluginManager().registerEvents(events, this);
        this.getCommand("welcome").setExecutor(commands);
        log("Enabled!");

    }

    public void log(String msg) {
        System.out.println(ChatColor.GREEN + "[Welcome] " + ChatColor.WHITE + msg);
    }
}
