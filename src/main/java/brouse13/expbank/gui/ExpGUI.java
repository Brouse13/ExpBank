package brouse13.expbank.gui;

import brouse13.expbank.translations.Messages;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class ExpGUI{
    GUI gui;
    public ExpGUI(Player player) {
        this.gui  = new GUI(9, Messages.prefix);
        gui.setUpBackground(new ItemStack(Material.LIGHT_GRAY_STAINED_GLASS_PANE));
        gui.insertItem(0, gui.createItem(Material.GREEN_TERRACOTTA, "§aDeposit", null));
        gui.insertItem(4, gui.createItem(Material.YELLOW_TERRACOTTA, "§eComing soon...", null));
        gui.insertItem(8, gui.createItem(Material.RED_TERRACOTTA, "§cWithdraw", null));
        player.openInventory(gui.getInventory());
    }


}
