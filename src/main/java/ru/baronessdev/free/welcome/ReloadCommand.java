package ru.baronessdev.free.welcome;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class ReloadCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        Welcome.getInstance().reloadConfig();
        sender.sendMessage(Welcome.getInstance().getConfig().getString("reloadMessage"));
        return true;
    }
}
