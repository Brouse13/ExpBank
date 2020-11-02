package brouse13.expbank.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

public interface ISubCommand {
    String getName();
    String getUsage();

    boolean execute(CommandSender sender, Command command, String label, String[] arguments);
}
