package ru.baronessdev.welcome.events.local;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import ru.baronessdev.welcome.Core;
import ru.baronessdev.welcome.util.BaseWelcomeEvent;

public class JoinEventLocal extends BaseWelcomeEvent implements Listener {

    @Override
    public String getOption() {
        return "local.join";
    }

    @EventHandler (priority = EventPriority.MONITOR)
    private void onJoinLocal(PlayerJoinEvent e) {
        Player p = e.getPlayer();
        if (Core.getConfigCenter().isEnabled(getOption())) {
            for (String string : format(p)) {
                p.sendMessage(string);
            }
        }
    }
}
