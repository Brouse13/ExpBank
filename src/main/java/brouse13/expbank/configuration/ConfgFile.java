package brouse13.expbank.configuration;

import brouse13.expbank.configuration.objects.Configuration;
import brouse13.expbank.configuration.objects.YML;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.io.File;

public class ConfgFile extends YML implements Configuration {
    private static YML configFile;

    public ConfgFile(@NotNull File dir, String fileName, boolean overwrite) {
        super(dir, fileName, overwrite);
        configFile = this;
    }

    @Override
    public Configuration getType() {
        return this;
    }

    @Override
    public boolean accountPlayerExist(OfflinePlayer player) {
        if (this.getFile().contains("players."+ player.getUniqueId())) {
            return true;
        }
        return false;
    }

    @Override
    public void accountPlayerCreate(OfflinePlayer player) {
        getFile().set("players."+ player.getUniqueId()+ ".name", player.getName());
        getFile().set("players."+ player.getUniqueId()+ ".xp", 0);
        saveFile();
        reloadFile();
    }

    @Override
    public void accountPlayerDelete(OfflinePlayer player) {
        getFile().set("players."+ player.getUniqueId(), null);
    }

    @Override
    public void accountPlayerInsert(OfflinePlayer player, float amount) {
        getFile().set("players."+ player.getUniqueId()+ ".xp", (accountPlayerBalance(player) + amount));
        saveFile();
        reloadFile();
    }

    @Override
    public void accountPlayerRemove(OfflinePlayer player, float amount) {
        getFile().set("players."+ player.getUniqueId()+ ".xp", (accountPlayerBalance(player) - amount));
        saveFile();
        reloadFile();
    }

    @Override
    public float accountPlayerBalance(OfflinePlayer player) {
        return getFile().getInt("players."+ player.getUniqueId()+ ".xp", 0);
    }

    @Override
    public String getName(OfflinePlayer player) {
        return getFile().getString("players."+ player.getUniqueId()+ ".name", "");
    }

    @Override
    public void updateName(OfflinePlayer player) {
        getFile().set("players."+ player.getUniqueId()+ ".name", player.getName());
        saveFile();
        reloadFile();
    }
}
