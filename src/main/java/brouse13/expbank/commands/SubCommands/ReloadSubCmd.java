package brouse13.expbank.commands.SubCommands;

import brouse13.expbank.ExpBank;
import brouse13.expbank.commands.IPermissible;
import brouse13.expbank.commands.ISubCommand;
import brouse13.expbank.configuration.ConfigurationManager;
import brouse13.expbank.configuration.objects.YML;
import brouse13.expbank.sign.ExpSignManager;
import brouse13.expbank.translations.Messages;
import brouse13.expbank.translations.MessagesManager;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Arrays;
import java.util.List;

public class ReloadSubCmd implements ISubCommand, IPermissible {
    ExpBank plugin;

    public ReloadSubCmd(ExpBank plugin) {
        this.plugin = plugin;
    }

    @Override
    public List<String> getPermission() {
        return Arrays.asList("expbank.admin.reload");
    }

    @Override
    public String getName() {
        return "reload";
    }

    @Override
    public String getUsage() {
        return Messages.command_reload;
    }

    @Override
    public boolean execute(CommandSender sender, Command command, String label, String[] arguments) {
        ConfigurationManager.reloadConfiguration();
        MessagesManager.reloadMessages();
        ExpSignManager.reloadSigns();

        sender.sendMessage(Messages.reload_config);
        return true;
    }
}
