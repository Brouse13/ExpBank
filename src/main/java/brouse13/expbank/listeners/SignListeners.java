package brouse13.expbank.listeners;

import brouse13.expbank.ExpBank;
import brouse13.expbank.conversation.ConversationManager;
import brouse13.expbank.gui.ExpGUI;
import brouse13.expbank.sign.ExpSign;
import brouse13.expbank.sign.ExpSignManager;
import brouse13.expbank.sign.SignType;
import brouse13.expbank.translations.Messages;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.SignChangeEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;

public class SignListeners implements Listener {
    ExpBank main;

    public SignListeners(ExpBank main) {
        this.main = main;
    }

    @EventHandler
    public void SignPlaceEvent(SignChangeEvent event) {
        if (event.getLine(0).equalsIgnoreCase("[ExpBank]")) {//The sign is from the plugin ->1st line
            if (event.getPlayer().hasPermission("expbank.admin.create")) {//Check player permission
                ExpSign sign = new ExpSign(event.getPlayer(), System.currentTimeMillis(), event.getBlock().getLocation(), event.getLines());
                if (sign.getSignType() != null) {//Methods above are used to create the sign
                    ExpSignManager.addSign(sign);

                    event.setLine(0, Messages.prefix);
                    event.setLine(1, sign.getSignType().toString().toLowerCase());
                    event.getPlayer().sendMessage(Messages.sign_created);
                }else {
                    event.setCancelled(true);
                    event.getPlayer().sendMessage(Messages.sign_invalid);
                }
            }else {
                event.setCancelled(true);
                event.getPlayer().sendMessage(Messages.permission_denied);
            }
        }
    }

    @EventHandler
    public void SignClick(PlayerInteractEvent event) {
        if (event.getAction().equals(Action.RIGHT_CLICK_BLOCK) && event.getClickedBlock().getType().toString().toLowerCase().contains("sign")) {
            Block sign = event.getClickedBlock();
            if (ExpSignManager.isExpSign(sign.getLocation())) {
                ExpSign expSign = ExpSignManager.getSign(sign.getLocation());

                if (event.getPlayer().hasPermission("expbank.sign.use")) {
                    if (expSign.getSignType().equals(SignType.Deposit)) {
                        ConversationManager.convDeposit(event.getPlayer());
                    }else if (expSign.getSignType().equals(SignType.Withdraw)) {
                        ConversationManager.convWithdraw(event.getPlayer());
                    }else if(expSign.getSignType().equals(SignType.All)) {
                        new ExpGUI(event.getPlayer());
                    }
                }else {
                    event.getPlayer().sendMessage(Messages.permission_denied);
                }
            }
        }
    }

    @EventHandler
    public void SignRemove(BlockBreakEvent event) {
        if (event.getBlock().getState() instanceof Sign) {
            if (ExpSignManager.isExpSign(event.getBlock().getLocation())) {
                if (event.getPlayer().hasPermission("expbank.admin.remove") || event.getPlayer().hasPermission("expbank.admin.*")){
                    Location blocation = event.getBlock().getLocation();

                    ExpSignManager.removeSign(ExpSignManager.getSign(
                            new Location(blocation.getWorld(), blocation.getX(), blocation.getY(), blocation.getZ())));
                    event.getPlayer().sendMessage(Messages.sign_removed);
                }else {
                    event.setCancelled(true);
                    event.getPlayer().sendMessage(Messages.permission_denied);

                }
            }
        }
    }


    @EventHandler
    public void InventoryClick(InventoryClickEvent event) {
        if (event.getView().getTitle().equals(Messages.prefix)) {
            if(event.getCurrentItem() == null) {
                return;
            }
            if (event.getCurrentItem().getType().equals(Material.GREEN_TERRACOTTA)) {
                ConversationManager.convDeposit((Player) event.getWhoClicked());
            }else if(event.getCurrentItem().getType().equals(Material.RED_TERRACOTTA)) {
                ConversationManager.convWithdraw((Player) event.getWhoClicked());
            }else if(event.getCurrentItem().getType().equals(Material.YELLOW_TERRACOTTA)) {
                event.getWhoClicked().sendMessage("Coming soon...");
            }
            event.setCancelled(true);
        }
    }
}
