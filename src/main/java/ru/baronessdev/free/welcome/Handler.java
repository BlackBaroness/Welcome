package ru.baronessdev.free.welcome;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import ru.baronessdev.free.welcome.util.PlaceholderAPIHook;

import java.util.ArrayList;
import java.util.List;

public class Handler implements Listener {

    private final FileConfiguration cfg = Welcome.getInstance().getConfig();

    /* ================================= LOCAL =========================================*/

    @EventHandler(ignoreCancelled = true)
    public void joinLocal(PlayerJoinEvent e) {
        processLocal(e.getPlayer(), "local.join");
    }

    @EventHandler(ignoreCancelled = true)
    public void deathLocal(PlayerDeathEvent e) {
        processLocal(e.getEntity(), "local.death");
    }

    private void processLocal(Player p, String path) {
        if (isEnabled(path)) getMessages(p, path).forEach(p::sendMessage);
    }

    /* ================================= GLOBAL =========================================*/

    @EventHandler(ignoreCancelled = true)
    public void joinGlobal(PlayerJoinEvent e) {
        if (processGlobal(e.getPlayer(), "global.join"))
            e.setJoinMessage(null);
    }

    @EventHandler(ignoreCancelled = true)
    public void leaveGlobal(PlayerQuitEvent e) {
        if (processGlobal(e.getPlayer(), "global.leave"))
            e.setQuitMessage(null);
    }

    @EventHandler(ignoreCancelled = true)
    public void deathGlobal(PlayerDeathEvent e) {
        if (processGlobal(e.getEntity(), "global.death"))
            e.setDeathMessage(null);
    }

    private boolean processGlobal(Player p, String path) {
        if (!isEnabled(path)) return false;
        getMessages(p, path).forEach(Bukkit::broadcastMessage);
        return true;
    }

    /* ================================== UTIL ==========================================*/

    private List<String> getMessages(Player p, String path) {
        List<String> list = new ArrayList<>();
        cfg.getStringList(path + ".message").forEach(message -> list.add(PlaceholderAPIHook.setPlaceholders(p, message
                .replace("&", "§")
                .replace("{nick}", p.getName())
                .replace("{online}", Bukkit.getOnlinePlayers().size() + "")
        )));

        return list;
    }

    @SuppressWarnings("BooleanMethodIsAlwaysInverted")
    private boolean isEnabled(String path) {
        return cfg.getBoolean(path + ".enabled");
    }
}
