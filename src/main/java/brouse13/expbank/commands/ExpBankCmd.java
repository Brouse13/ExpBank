package brouse13.expbank.commands;

import brouse13.expbank.ExpBank;
import brouse13.expbank.commands.SubCommands.*;
import brouse13.expbank.translations.Messages;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;

public class ExpBankCmd implements CommandExecutor {
    ExpBank plugin;
    HashMap<String, ISubCommand> subcommands = new HashMap<>();

    public ExpBankCmd(ExpBank plugin) {
        this.plugin = plugin;
        regiserSubcommand(new InfoSubCmd(plugin));
        regiserSubcommand(new RemoveSubCmd());
        regiserSubcommand(new GiveSubCmd());
        regiserSubcommand(new BalanceSubCmd());
        regiserSubcommand(new ReloadSubCmd(plugin));
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (sender instanceof Player) {
            if (args.length > 0) {
                if (!subcommands.isEmpty()) {
                    for (String name : subcommands.keySet()) {
                        if (name.equals(args[0])) {
                            ISubCommand subCommand = subcommands.get(args[0]);
                            if (subCommand instanceof IPermissible) {
                                if (!sender.hasPermission(((IPermissible) subCommand).getPermission().get(0))) {
                                    sender.sendMessage(Messages.permission_denied);
                                    return false;
                                }
                            }
                            subCommand.execute(sender, command, label, args);
                            return true;
                        }
                    }
                }else {
                    Bukkit.getConsoleSender().sendMessage("§cNo subcommand found for command §6"+ command.getName()+ "§cplease, checkout API to add a or remove subcommands");
                }
            }
        }else {
            sender.sendMessage(Messages.only_players);
            return false;
        }
        sender.sendMessage(this.getHelp());
        return true;
    }

    private void regiserSubcommand(ISubCommand subCommand) {
        if (subCommand.getName() == null)
            throw new IllegalArgumentException("You must supply subcommand name to register it");
        if (subcommands.containsKey(subCommand.getName())) {
            throw new IllegalArgumentException("You can't register two subcommands with the same name");
        }else {
            subcommands.put(subCommand.getName().toLowerCase(), subCommand);
        }
    }

    private String getHelp() {
        StringBuilder helpmessage = new StringBuilder(Messages.prefix.concat("\n"));
        if (subcommands.isEmpty()) {
            return helpmessage.toString().concat("No subcommands available");
        }else {
            for (ISubCommand subCommand : subcommands.values()) {
                helpmessage.append("§6/expbank ")
                        .append(subCommand.getName())
                        .append("§f: ");
                if (subCommand.getUsage() == null) {
                    helpmessage.append("No info available \n");
                }else {
                    helpmessage.append(subCommand.getUsage()).append("\n");
                }
            }
            return helpmessage.toString();
        }
    }
}
