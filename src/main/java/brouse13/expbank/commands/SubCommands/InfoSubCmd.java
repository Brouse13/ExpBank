package brouse13.expbank.commands.SubCommands;

import brouse13.expbank.ExpBank;
import brouse13.expbank.commands.ISubCommand;
import brouse13.expbank.translations.Messages;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

public class InfoSubCmd implements ISubCommand {
    ExpBank plugin;

    public InfoSubCmd(ExpBank plugin) {
        this.plugin = plugin;
    }

    @Override
    public String getUsage() {
        return Messages.command_info;
    }

    @Override
    public String getName() {
        return "info";
    }

    @Override
    public boolean execute(CommandSender sender, Command command, String label, String[] arguments) {
        sender.sendMessage("----------"+ Messages.prefix+ "----------"+"\n" +
                            "Version: "+ plugin.getDescription().getVersion()+ "\n" +
                            "Author: "+ plugin.getDescription().getAuthors().get(0)+ "\n" +
                            "Dependencies: "+ "Not yet");
        return false;
    }
}
