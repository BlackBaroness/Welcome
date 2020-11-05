package ru.baronessdev.welcome;

import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;
import ru.baronessdev.welcome.util.Metrics;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;

public final class Core extends JavaPlugin {

    private static ConfigCenter configCenter;
    private static JavaPlugin plugin;

    @Override
    public void onEnable() {
        Metrics metrics = new Metrics(this, 9311);
        saveDefaultConfig();
        plugin = this;

        configCenter = new ConfigCenter(this);
        ReloadCommand reloadCommand = new ReloadCommand(configCenter);
        this.getCommand("welcome").setExecutor(reloadCommand);

        checkUpdate();

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

    public void checkUpdate() {
        String name = getDescription().getName();
        double currentVersion = Double.parseDouble(getDescription().getVersion());
        String url = "https://raw.githubusercontent.com/SiriusWhite74/UpdateRepo/main/" + name;
        double actualVersion;

        try {
            URL u = new URL(url);
            BufferedReader readerMain = new BufferedReader(new InputStreamReader(u.openStream()));
            actualVersion = Double.parseDouble(readerMain.readLine());
            readerMain.close();
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }

        if (currentVersion == actualVersion) {
            System.out.println(ChatColor.LIGHT_PURPLE + "[Baroness's Dev] You are using the latest version of " + name);
        } else {
            String newVersion = ChatColor.LIGHT_PURPLE + "[Baroness's Dev] " + ChatColor.GREEN + "New version published: " + actualVersion + ChatColor.YELLOW + " (Current: " + currentVersion + ")";
            String link = ChatColor.LIGHT_PURPLE + "[Baroness's Dev] " + ChatColor.GREEN + "Download link: " + ChatColor.YELLOW + "https://baronessdev.ru/" + name.toLowerCase();
            String line;

            int size = Math.max(newVersion.length(), link.length());
            StringBuilder sb = new StringBuilder(ChatColor.LIGHT_PURPLE + "[Baroness's Dev] ");
            for (int i = 0; i < size; i++) {
                sb.append("=");
            }
            line = sb.toString();

            System.out.println(line);
            System.out.println(newVersion);
            System.out.println(link);
            System.out.println(line);
        }
    }
}
