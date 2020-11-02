package brouse13.expbank.configuration.objects;

import org.bukkit.OfflinePlayer;

public interface Configuration {

    Configuration getType();

    boolean accountPlayerExist(OfflinePlayer player);
    void accountPlayerCreate(OfflinePlayer player);
    void accountPlayerDelete(OfflinePlayer player);

    void accountPlayerInsert(OfflinePlayer player, float amount);
    void accountPlayerRemove(OfflinePlayer player, float amount);

    float accountPlayerBalance(OfflinePlayer player);

    String getName(OfflinePlayer player);
    void updateName(OfflinePlayer player);
}
