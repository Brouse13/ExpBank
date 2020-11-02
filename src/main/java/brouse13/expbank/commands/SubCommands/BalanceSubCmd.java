package brouse13.expbank.commands.SubCommands;

import brouse13.expbank.commands.ISubCommand;
import brouse13.expbank.configuration.ConfigurationManager;
import brouse13.expbank.translations.Messages;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

public class BalanceSubCmd implements ISubCommand {


    @Override
    public String getName() {
        return "balance";
    }

    @Override
    public String getUsage() {
        return Messages.command_balance;
    }

    @Override
    public boolean execute(CommandSender sender, Command command, String label, String[] arguments) {
        float balance = ConfigurationManager.getConfiguration().accountPlayerBalance((OfflinePlayer) sender);
        sender.sendMessage(Messages.account_balance
                .replace("{0}", ((int) balance)+ ""));

        return false;
    }
}
