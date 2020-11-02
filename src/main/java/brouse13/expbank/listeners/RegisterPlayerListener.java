package brouse13.expbank.listeners;

import brouse13.expbank.configuration.ConfigurationManager;
import brouse13.expbank.translations.Messages;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class RegisterPlayerListener implements Listener {

    @EventHandler
    public void onPlayerLoginEvent(PlayerJoinEvent event) {
        if (!ConfigurationManager.getConfiguration().accountPlayerExist(event.getPlayer())) {
            ConfigurationManager.getConfiguration().accountPlayerCreate(event.getPlayer());
            event.getPlayer().sendMessage(Messages.account_created);
        }

        if (!ConfigurationManager.getConfiguration().getName(event.getPlayer()).equals(event.getPlayer().getName())) {
            ConfigurationManager.getConfiguration().updateName(event.getPlayer());
            event.getPlayer().sendMessage(Messages.account_renamed);
        }
    }
}
