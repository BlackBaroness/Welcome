package ru.baronessdev.welcome.events.global;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import ru.baronessdev.welcome.Core;
import ru.baronessdev.welcome.util.BaseWelcomeEvent;

import java.util.List;

public class JoinEventGlobal extends BaseWelcomeEvent implements Listener {

    @Override
    public String getOption() {
        return "broadcast.join";
    }

    @EventHandler (priority = EventPriority.LOWEST)
    private void onJoinGlobal(PlayerJoinEvent e) {
        if (Core.getConfigCenter().isEnabled(getOption())) {
            List<String> messages = format(e.getPlayer());
            e.setJoinMessage(messages.get(0));
            messages.remove(0);
            for (String s : messages) {
                Bukkit.broadcastMessage(s);
            }
        } else e.setJoinMessage(null);
    }
}
