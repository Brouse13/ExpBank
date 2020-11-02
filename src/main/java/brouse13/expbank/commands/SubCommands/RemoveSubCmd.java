package brouse13.expbank.commands.SubCommands;

import brouse13.expbank.commands.IPermissible;
import brouse13.expbank.commands.ISubCommand;
import brouse13.expbank.configuration.ConfigurationManager;
import brouse13.expbank.translations.Messages;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Arrays;
import java.util.List;


public class RemoveSubCmd implements ISubCommand, IPermissible {
    @Override
    public List<String> getPermission() {
        return Arrays.asList("expbank.admin.withdraw");
    }

    @Override
    public String getName() {
        return "remove";
    }

    @Override
    public String getUsage() {
        return Messages.command_remove;
    }
    @Override
    public boolean execute(CommandSender sender, Command command, String label, String[] arguments) {
        int number;
        Player player;
        if (arguments.length >= 3) {
            try {
                player = Bukkit.getPlayer(arguments[1]);
                if (!player.hasPlayedBefore())
                    return false;
            }catch (NullPointerException exception) {
                sender.sendMessage(Messages.player_not_exist.replace("{0}", arguments[1]));
                return false;
            }

            try {
                number = Integer.parseInt(arguments[2]);
            }catch (NumberFormatException exception) {
                sender.sendMessage(Messages.not_number.replace("{0}", arguments[2]));
                return false;
            }

            if (ConfigurationManager.getConfiguration().accountPlayerBalance(player) < number) {
                sender.sendMessage(Messages.not_enough_exp.replace("{0}", number+""));
                return false;
            }
            ConfigurationManager.getConfiguration().accountPlayerRemove(player, number);
            sender.sendMessage(Messages.exp_removed_to
                    .replace("{0}", number+"")
                    .replace("{1}", player.getName())) ;
            return true;
        }
        sender.sendMessage("/expbank give <Player> <amount>");
        return true;
    }
}
