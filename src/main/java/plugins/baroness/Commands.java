package plugins.baroness;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Commands implements CommandExecutor {

    private final ConfigCenter center;

    Commands(ConfigCenter center) {
        this.center = center;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            Player p = (Player) sender;
            if (!p.hasPermission("welcome.admin")) {
                p.sendMessage(ChatColor.AQUA + "[Welcome!] " + ChatColor.RED + "Вы не администратор.");
                return true;
            }
            center.loadConfig();
            p.sendMessage(ChatColor.AQUA + "[Welcome!] " + ChatColor.RED + "Конфиг перезагружен.");
            return true;
        } else {
            center.loadConfig();
            System.out.println(ChatColor.AQUA + "[Welcome!] " + ChatColor.WHITE + "Reloaded.");
        }
        return true;
    }
}
