package ru.baronessdev.welcome.events.local;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import ru.baronessdev.welcome.util.BaseWelcomeEvent;

public class DeathEventLocal extends BaseWelcomeEvent implements Listener {

    @Override
    public String getOption() {
        return "local.death";
    }

    @EventHandler
    private void onDeathLocal(PlayerDeathEvent e) {
        Player p = e.getEntity();
        for (String string : format(p)) {
            p.sendMessage(string);
        }
    }
}
