package brouse13.expbank.translations;

import brouse13.expbank.ExpBank;
import brouse13.expbank.configuration.objects.YML;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.jetbrains.annotations.NotNull;

import java.io.File;

public class MessagesManager {

    private static YML messages;

    public static void loadMessages(ExpBank plugin) {
        Bukkit.getConsoleSender().sendMessage("§aLoading messages...");
        if (!new File(plugin.getDataFolder(), "messages.yml").exists()) {
            plugin.saveResource("messages.yml", false);
        }
        messages = new YML(plugin.getDataFolder(), "messages", false);
        new Messages();
        Bukkit.getConsoleSender().sendMessage("§aMessages loaded");
    }

    public static void reloadMessages() {
        messages.reloadFile();
        new Messages();

    }

    @NotNull
    protected static String getFromMessages(@NotNull String message, String defecto) {
        FileConfiguration fileConfiguration = messages.getFile();
        if (fileConfiguration.getString(message) != null) {
            return ChatColor.translateAlternateColorCodes('&', fileConfiguration.getString(message));
        }else {
            return ChatColor.translateAlternateColorCodes('&', defecto);
        }
    }
}
