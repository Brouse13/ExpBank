package brouse13.expbank.configuration;

import brouse13.expbank.ExpBank;
import brouse13.expbank.configuration.objects.Configuration;
import brouse13.expbank.configuration.objects.YML;
import org.bukkit.Bukkit;
import org.jetbrains.annotations.NotNull;

import java.sql.SQLException;

public class ConfigurationManager {
    private static Configuration configuration;
    private static ExpBank plugin;
    private static YML file;

    public static void loadConfiguration(@NotNull ExpBank plugin) {
        ConfigurationManager.plugin = plugin;
        file = new YML(plugin.getDataFolder(), "players", false);

        Bukkit.getConsoleSender().sendMessage("§aLoading configuration...");
        if (plugin.getConfig().getBoolean("config.mysql.enabled")) {
            try {
                SQL sql = new SQL(
                        plugin.getConfig().getString("config.mysql.settings.host"),
                        plugin.getConfig().getString("config.mysql.settings.database"),
                        plugin.getConfig().getInt("config.mysql.settings.port"),
                        plugin.getConfig().getString("config.mysql.settings.username"),
                        plugin.getConfig().getString("config.mysql.settings.password")
                );
                if (sql.existError())
                    throw new SQLException();

                configuration = sql;
            } catch (SQLException exception) {
                configuration = new ConfgFile(plugin.getDataFolder(), "players", false);

                Bukkit.getConsoleSender().sendMessage("§aConfiguration loaded §7(§fYAML§7)");

                Bukkit.getConsoleSender().sendMessage("§cError while enabling MySQL using default configuration");
                return;
            }
            Bukkit.getConsoleSender().sendMessage("§aConfiguration loaded §7(§fMySQL§7)");
        }else {
            configuration = new ConfgFile(plugin.getDataFolder(), "players", false);
            Bukkit.getConsoleSender().sendMessage("§aConfiguration loaded §7(§fYAML§7)");
        }

    }

    public static void switchConfiguration(Configuration configuration) {
        ConfigurationManager.configuration = configuration;
    }

    public static void reloadConfiguration() {
        plugin.reloadConfig();
        file.reloadFile();
    }

    public static Configuration getConfiguration() {
        return configuration;
    }
}
