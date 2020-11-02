package brouse13.expbank.sign;

import brouse13.expbank.ExpBank;
import brouse13.expbank.configuration.objects.YML;
import brouse13.expbank.translations.Messages;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.*;

public class ExpSignManager {
    private static List<ExpSign> signs = new ArrayList<>();
    private static YML signFile;

    /**
     * Method to charge into the List all sign from the config
     * @param plugin ExpBank instance
     */
    public static void loadSigns(ExpBank plugin) {
        Bukkit.getConsoleSender().sendMessage("§aLoading signs...");
        signFile = new YML(plugin.getDataFolder(), "signs", false);
        FileConfiguration configuration = signFile.getFile();

        if (!configuration.contains("signs")) {
            Bukkit.getConsoleSender().sendMessage("§eNo signs to load found, skyping action");
            return;
        }

        for (String key : configuration.getConfigurationSection("signs").getKeys(false)) {
            Location loc= new Location(
                    Bukkit.getWorld(configuration.getString("signs."+ key+ ".location.world")),
                    configuration.getDouble("signs."+ key+ ".location.x"),
                    configuration.getDouble("signs."+ key+ ".location.y"),
                    configuration.getDouble("signs."+ key+ ".location.z")
            );

            if (!isExpSign(loc)) {
                configuration.set("signs."+ key, null);
                Bukkit.getConsoleSender().sendMessage("Sign hasn't been found. Proceeding to remove it");
            }else {
                addSign(new ExpSign(
                        Bukkit.getOfflinePlayer(UUID.fromString(configuration.getString("signs."+ key+ ".uuid"))),
                        configuration.getLong("signs."+ key+ ".timestamp"),
                        loc,
                        SignType.valueOf(configuration.getString("signs."+ key+ ".type"))
                ));
            }


        }
        Bukkit.getConsoleSender().sendMessage("§aLoaded §6"+ signs.size()+ " §aExpBank signs");
    }

    /**
     * Used to save into the signs.yml all the ExpSign signs
     */
    public static void saveSigns() {
        Bukkit.getConsoleSender().sendMessage("§aSaving signs...");
        FileConfiguration configuration = signFile.getFile();
        configuration.set("signs", null);
        signFile.saveFile();

        try {
            if (!signs.isEmpty()) {
                for (ExpSign sign: signs) {
                    configuration.set("signs."+ sign.getTimestamp()+ ".uuid", sign.getOwner().getUniqueId().toString());
                    configuration.set("signs."+ sign.getTimestamp()+ ".timestamp", sign.getTimestamp());
                    configuration.set("signs."+ sign.getTimestamp()+ ".location.world", sign.getLocation().getWorld().getName());
                    configuration.set("signs."+ sign.getTimestamp()+ ".location.x", sign.getLocation().getX());
                    configuration.set("signs."+ sign.getTimestamp()+ ".location.y", sign.getLocation().getY());
                    configuration.set("signs."+ sign.getTimestamp()+ ".location.z", sign.getLocation().getZ());
                    configuration.set("signs."+ sign.getTimestamp()+ ".type", sign.getSignType().toString());
                    signFile.saveFile();
                }
            }else {
                Bukkit.getConsoleSender().sendMessage("§eNo signs to save found, skyping action");
                return;
            }
        }catch (NullPointerException ignored) {
            //Do nothing
        }
        Bukkit.getConsoleSender().sendMessage("§6"+ signs.size()+ " §asigns were successfully saved");
        signFile.reloadFile();
        signs.clear();
    }

    /**
     * Get listed signs
     * @return Return all ExpSign Objects
     */
    public static List<ExpSign> getSigns() {
        return signs;
    }

    /**
     * Add new ExpSign
     * @param expsign ExpSign to add
     */
    public static void addSign(ExpSign expsign) {
        if (!signs.contains(expsign)) {
            signs.add(expsign);
            return;
        }
        Bukkit.getConsoleSender().sendMessage(Messages.load_duplicate);
    }

    /**
     * Remove existing ExpSign
     * @param expSign ExpSign to remove
     */
    public static void removeSign(ExpSign expSign) {
        if(signs.contains(expSign)) {
            signs.remove(expSign);
        }else {
            System.out.println("NO se encontó la señal");
        }
    }
    /**
     * Get ExpSign from timestamp
     * @param timestamp Timestamp to check
     * @return Return ExpSign object
     */
    public static ExpSign getSign(long timestamp) {
        for (ExpSign expSign : signs) {
            if (expSign.getTimestamp() == timestamp) {
                return expSign;
            }
        }
        return null;
    }

    /**
     * Get ExpSign from location
     * @param location Location to check
     * @return Return ExpSign object
     */
    public static ExpSign getSign(Location location) {
        for (ExpSign expSign : signs) {
            if (expSign.getLocation().equals(location)) {
                return expSign;
            }
        }
        return null;
    }

    /**
     * Check location to search a ExpSign
     * @param location Location to check
     * @return Return true if exist
     */
    public static boolean isExpSign(Location location) {
        for (ExpSign sign : signs) {
            if (sign.getLocation().getX() == location.getX()
                && sign.getLocation().getY() == location.getY()
                && sign.getLocation().getZ() == location.getZ()
                && sign.getLocation().getWorld() == location.getWorld()) {
                return true;
            }
        }
        return false;
    }

    public static void reloadSigns() {
        signFile.reloadFile();
    }
}
