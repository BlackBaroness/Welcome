package ru.baronessdev.welcome.events.global;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;
import ru.baronessdev.welcome.Core;
import ru.baronessdev.welcome.util.BaseWelcomeEvent;

import java.util.List;

public class LeaveEventGlobal extends BaseWelcomeEvent implements Listener {

    @Override
    public String getOption() {
        return "broadcast.leave";
    }

    @EventHandler
    private void onJoinGlobal(PlayerQuitEvent e) {
        if (Core.getConfigCenter().isEnabled(getOption())) {
            List<String> messages = format(e.getPlayer());
            e.setQuitMessage(messages.get(0));
            messages.remove(0);
            for (String s : messages) {
                Bukkit.broadcastMessage(s);
            }
        } else e.setQuitMessage(null);
    }
}
