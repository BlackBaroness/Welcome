package ru.baronessdev.free.welcome.util;

import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class PlaceholderAPIHook {

    private static final boolean enabled = Bukkit.getPluginManager().getPlugin("PlaceholderAPI") != null;

    public static String setPlaceholders(Player p, String s) {
        return (enabled) ? PlaceholderAPI.setPlaceholders(p, s) : s;
    }
}
