package ru.baronessdev.welcome.util;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import ru.baronessdev.welcome.Core;

import java.util.ArrayList;
import java.util.List;

public abstract class BaseWelcomeEvent {

    public abstract String getOption();

    protected List<String> format(Player p) {
        List<String> messages = new ArrayList<>(Core.getConfigCenter().getMessages(getOption()));
        for (int i = 0; i < messages.size(); i++) {
            messages.set(i, messages.get(i)
                    .replace("{online}", Bukkit.getOnlinePlayers().size() + "")
                    .replace("{nick}", p.getName()));
        }
        return messages;
    }
}
