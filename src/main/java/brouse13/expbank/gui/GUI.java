package brouse13.expbank.gui;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class GUI {

    private Inventory inventory;
    private int size;
    private String name;
    private ItemStack itemStack;

    public GUI(int size, String title) {
        this.size = size;
        this.name = title;
        inventory = Bukkit.createInventory(null, size, name);
    }

    public void setUpBackground(@Nullable ItemStack itemStack) {
        this.itemStack = itemStack;
        if (itemStack != null) {
            for (int i = 0; i < size; i++) {
                inventory.setItem(i, itemStack);
            }
        }
    }

    public ItemStack createItem(@NotNull Material material, @Nullable String name, @Nullable List<String> lore) {
        ItemStack itemStack = new ItemStack(material);
        ItemMeta itemMeta = itemStack.getItemMeta();

        if (name != null) {
            itemMeta.setDisplayName(name);
        }

        if (lore != null) {
            itemMeta.setLore(lore);
        }
        itemStack.setItemMeta(itemMeta);
        return itemStack;
    }

    public ItemStack createSkull(Player owner) {
        ItemStack itemStack = createItem(Material.PLAYER_HEAD, null, null);

        SkullMeta skullMeta = (SkullMeta) itemStack.getItemMeta();
        skullMeta.setOwningPlayer(owner);
        itemStack.setItemMeta(skullMeta);

        return itemStack;
    }

    public void insertItem(int pos, ItemStack item) {
        getInventory().setItem(pos, item);
    }

    public void reloadInventory(){
        setUpBackground(this.itemStack);
    }

    public void openInventory(Player player) {
        player.openInventory(getInventory());
    }

    public Inventory getInventory() {
        return inventory;
    }
}
