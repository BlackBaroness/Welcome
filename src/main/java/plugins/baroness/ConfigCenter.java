package plugins.baroness;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ConfigCenter {

    private final JavaPlugin core;
    private final List<String> enabled = new ArrayList<>();
    private final List<String> def = new ArrayList<>();
    private final HashMap<String, List<String>> messages = new HashMap<>();

    ConfigCenter(JavaPlugin javaPlugin) {
        core = javaPlugin;
        loadConfig();
    }

    private List<String> format(String path) {
        FileConfiguration cfg = core.getConfig();
        path = path + ".message";
        List<String> var = cfg.getStringList(path);
        List<String> var1 = new ArrayList<>();
        var.forEach(message -> {
            var1.add(message.replace("&", "\u00a7"));
        });
        return var1;
    }

    void loadConfig() {
        core.reloadConfig();
        enabled.clear();
        messages.clear();
        def.clear();
        FileConfiguration config = core.getConfig();

        String[] options = {
                "local.join", "local.death",
                "broadcast.join", "broadcast.death",
                "broadcast.leave", "broadcast.join",
                "broadcast.death", "broadcast.leave"
        };

        for (String option : options) {
            if (config.getBoolean(option + ".default")) {
                def.add(option);
            }

            if (config.getBoolean(option + ".enabled") && !def.contains(option)) {
                enabled.add(option);
                messages.put(option, format(option));
            }
        }
    }

    boolean isEnabled(String path) {
        return enabled.contains(path);
    }

    public List<String> getMessages(String path) {
        return messages.get(path);
    }

    public boolean isDefault(String path) {
        return def.contains(path);
    }
}
