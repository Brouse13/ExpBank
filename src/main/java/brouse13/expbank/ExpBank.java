package brouse13.expbank;

import brouse13.expbank.commands.ExpBankCmd;
import brouse13.expbank.configuration.ConfigurationManager;
import brouse13.expbank.conversation.ConversationManager;
import brouse13.expbank.listeners.*;
import brouse13.expbank.sign.ExpSignManager;
import brouse13.expbank.translations.MessagesManager;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;

public class ExpBank extends JavaPlugin {

    @Override
    public void onEnable() {
        createConfig();
        MessagesManager.loadMessages(this);
        new ConversationManager(this);
        ConfigurationManager.loadConfiguration(this);
        ExpSignManager.loadSigns(this);
        registerListeners();
        registerCommands();
    }

    @Override
    public void onDisable() {
        ExpSignManager.saveSigns();
        Bukkit.getConsoleSender().sendMessage("§aPlugin disabled successfully");

    }

    private void registerCommands() {
        this.getCommand("expbank").setExecutor(new ExpBankCmd(this));
    }

    private void registerListeners() {
        this.getServer().getPluginManager().registerEvents(new RegisterPlayerListener(), this);
        this.getServer().getPluginManager().registerEvents(new SignListeners(this), this);
    }

    private void createConfig() {
        if (!new File(getDataFolder(), "config.yml").exists()) {
            getConfig().options().copyDefaults(true);
            saveConfig();
        }
    }
}
