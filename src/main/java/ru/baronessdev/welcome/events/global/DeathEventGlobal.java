package ru.baronessdev.welcome.events.global;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import ru.baronessdev.welcome.Core;
import ru.baronessdev.welcome.util.BaseWelcomeEvent;

import java.util.List;

public class DeathEventGlobal extends BaseWelcomeEvent implements Listener {

    @Override
    public String getOption() {
        return "broadcast.death";
    }

    @EventHandler
    private void onDeathGlobal(PlayerDeathEvent e) {
        if (Core.getConfigCenter().isEnabled(getOption())) {
            List<String> messages = format(e.getEntity());
            e.setDeathMessage(messages.get(0));
            messages.remove(0);
            for (String s : messages) {
                Bukkit.broadcastMessage(s);
            }
        } else e.setDeathMessage(null);
    }
}
