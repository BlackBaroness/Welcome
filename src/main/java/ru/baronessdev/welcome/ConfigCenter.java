package ru.baronessdev.welcome;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;
import ru.baronessdev.welcome.events.global.DeathEventGlobal;
import ru.baronessdev.welcome.events.global.JoinEventGlobal;
import ru.baronessdev.welcome.events.global.LeaveEventGlobal;
import ru.baronessdev.welcome.events.local.DeathEventLocal;
import ru.baronessdev.welcome.events.local.JoinEventLocal;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ConfigCenter {

    private final JavaPlugin core;

    private final List<String> enabled = new ArrayList<>();
    private final List<String> def = new ArrayList<>();

    private final List<Listener> listeners = new ArrayList<>();

    private final HashMap<String, List<String>> messages = new HashMap<>();

    ConfigCenter(JavaPlugin core) {
        this.core = core;
        reload();
    }

    public void reload() {
        core.reloadConfig();
        FileConfiguration cfg = core.getConfig();

        unHook();
        enabled.clear();
        def.clear();
        listeners.clear();
        messages.clear();

        String[] options = {
                "local.join", "local.death",
                "broadcast.join", "broadcast.death",
                "broadcast.leave"
        };

        for (String option : options) {
            if (cfg.getBoolean(option + ".default")) {
                def.add(option);
                continue;
            }

            if (cfg.getBoolean(option + ".enabled")) {
                enabled.add(option);
                messages.put(option, format(option));
            }
        }

        // JoinEventLocal
        if (enabled.contains(options[0])) {
            JoinEventLocal eventLocal = new JoinEventLocal();
            Bukkit.getPluginManager().registerEvents(eventLocal, core);
            listeners.add(eventLocal);
        }

        // DeathEventLocal
        if (enabled.contains(options[1])) {
            DeathEventLocal eventLocal = new DeathEventLocal();
            Bukkit.getPluginManager().registerEvents(eventLocal, core);
            listeners.add(eventLocal);
        }

        // JoinEventGlobal
        if (!def.contains(options[2])) {
            JoinEventGlobal eventGlobal = new JoinEventGlobal();
            Bukkit.getPluginManager().registerEvents(eventGlobal, core);
            listeners.add(eventGlobal);
        }

        // DeathEventGlobal
        if (!def.contains(options[3])) {
            DeathEventGlobal eventGlobal = new DeathEventGlobal();
            Bukkit.getPluginManager().registerEvents(eventGlobal, core);
            listeners.add(eventGlobal);
        }

        // LeaveEventGlobal
        if (!def.contains(options[4])) {
            LeaveEventGlobal eventGlobal = new LeaveEventGlobal();
            Bukkit.getPluginManager().registerEvents(eventGlobal, core);
            listeners.add(eventGlobal);
        }
    }

    public boolean isEnabled(String path) {
        return enabled.contains(path);
    }

    public List<String> getMessages(String path) {
        return messages.get(path);
    }

    public boolean isDefault(String path) {
        return def.contains(path);
    }

    private void unHook() {
        for (Listener listener : listeners) {
            HandlerList.unregisterAll(listener);
        }
    }

    private List<String> format(String path) {
        FileConfiguration cfg = core.getConfig();
        path = path + ".message";
        List<String> raw = cfg.getStringList(path);
        List<String> cooked = new ArrayList<>();
        for (String s : raw) {
            cooked.add(s.replace("&", "\u00a7"));
        }
        return cooked;
    }
}
