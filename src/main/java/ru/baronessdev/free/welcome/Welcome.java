package ru.baronessdev.free.welcome;

import lombok.Getter;
import org.bstats.bukkit.Metrics;
import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;
import ru.baronessdev.free.welcome.util.UpdateCheckerUtil;
import ru.baronessdev.free.welcome.util.logging.LogType;
import ru.baronessdev.free.welcome.util.logging.Logger;

public final class Welcome extends JavaPlugin {

    @Getter
    private static JavaPlugin instance;

    @Override
    public void onEnable() {
        new Metrics(this, 9311);
        saveDefaultConfig();
        instance = this;

        getCommand("welcome").setExecutor(new ReloadCommand());
        checkUpdates();
    }

    private void checkUpdates() {
        try {
            int i = UpdateCheckerUtil.check(this);
            if (i != -1) {
                Logger.log(LogType.INFO, "New version found: v" + ChatColor.YELLOW + i + ChatColor.GRAY + " (Current: v" + getDescription().getVersion() + ")");
                Logger.log(LogType.INFO, "Update now: " + ChatColor.AQUA + "market.baronessdev.ru/shop/licenses/");
            }
        } catch (UpdateCheckerUtil.UpdateCheckException e) {
            Logger.log(LogType.ERROR, "Could not check for updates: " + e.getRootCause());
            Logger.log(LogType.ERROR, "Please contact Baroness's Dev if this isn't your mistake.");
        }
    }
}