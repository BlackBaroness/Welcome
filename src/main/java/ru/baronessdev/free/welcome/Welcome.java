package ru.baronessdev.free.welcome;

import lombok.Getter;
import org.bukkit.plugin.java.JavaPlugin;
import ru.baronessdev.free.welcome.util.Metrics;

public final class Welcome extends JavaPlugin {

    @Getter
    private static JavaPlugin instance;

    @Override
    public void onEnable() {
        new Metrics(this, 9311);
        saveDefaultConfig();
        instance = this;

        getCommand("welcome").setExecutor(new ReloadCommand());
    }
}