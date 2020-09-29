package ru.baronessdev.welcome;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ReloadCommand implements CommandExecutor {

    private final ConfigCenter center;

    ReloadCommand(ConfigCenter center) {
        this.center = center;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            Player p = (Player) sender;
            if (!p.hasPermission("welcome.admin")) {
                p.sendMessage(ChatColor.GREEN + "[Welcome] " + ChatColor.RED + "Недостаточно прав.");
                return true;
            }
            center.reload();
            p.sendMessage(ChatColor.GREEN + "[Welcome] " + ChatColor.WHITE + "Конфиг перезагружен.");
            return true;
        } else {
            center.reload();
            Core.log("Reloaded.");
        }
        return true;
    }
}
