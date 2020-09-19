package plugins.baroness;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.ArrayList;
import java.util.List;

public class Events implements Listener {

    private final ConfigCenter config;

    Events(ConfigCenter config) {
        this.config = config;
    }

    @EventHandler
    private void onDeathLocal(PlayerDeathEvent e) {
        initLocalEvent("local.death", e.getEntity());
    }

    @EventHandler
    private void onJoinLocal(PlayerJoinEvent e) {
        initLocalEvent("local.join", e.getPlayer());
    }

    @EventHandler
    private void onDeathGlobal(PlayerDeathEvent e) {
        if (config.isDefault("broadcast.death")) return;
        if (config.isEnabled("broadcast.death")) {
            List<String> var = format("broadcast.death", e.getEntity());
            e.setDeathMessage(var.get(0));
            var.remove(0);
            for (String string : var) {
                Bukkit.broadcastMessage(string);
            }
        } else e.setDeathMessage(null);
    }

    @EventHandler
    private void onJoinGlobal(PlayerJoinEvent e) {
        if (config.isDefault("broadcast.join")) return;
        if (config.isEnabled("broadcast.join")) {
            List<String> var = format("broadcast.join", e.getPlayer());
            e.setJoinMessage(var.get(0));
            var.remove(0);
            for (String string : var) {
                Bukkit.broadcastMessage(string);
            }
        } else e.setJoinMessage(null);
    }

    @EventHandler
    private void onLeaveGlobal(PlayerQuitEvent e) {
        if (config.isDefault("broadcast.leave")) return;
        if (config.isEnabled("broadcast.leave")) {
            List<String> var = format("broadcast.leave", e.getPlayer());
            e.setQuitMessage(var.get(0));
            var.remove(0);
            for (String string : var) {
                Bukkit.broadcastMessage(string);
            }
        } else  e.setQuitMessage(null);
    }

    private void initLocalEvent(String path, Player p) {
        if (config.isEnabled(path)) {
            for (String string : format(path, p)) {
                p.sendMessage(string);
            }
        }
    }

    private List<String> format(String path, Player p) {
        List<String> var = config.getMessages(path);
        List<String> var1 = new ArrayList<>();

        for (String message : var) {
            message = message.replace("&", "\u00a7")
                    .replace("{online}", Bukkit.getOnlinePlayers().size() + "")
                    .replace("{nick}", p.getName());
            var1.add(message);
        }
        return var1;
    }
}
