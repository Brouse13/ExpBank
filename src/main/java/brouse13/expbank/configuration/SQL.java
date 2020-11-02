package brouse13.expbank.configuration;

import brouse13.expbank.configuration.objects.Configuration;
import brouse13.expbank.configuration.objects.MySql;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.sql.ResultSet;
import java.sql.SQLException;

public class SQL extends MySql implements Configuration {
    private static MySql mySql;


    public SQL(String host, String database, int port, String username, String password) throws SQLException {
        super(host, database, port, username, password);
        mySql = this;
        createTable();
    }

    @Override
    public Configuration getType() {
        return this;
    }

    @Override
    public boolean accountPlayerExist(@NotNull OfflinePlayer player) {
        try {
            ResultSet resultSet = mySql.querySql("SELECT Uuid FROM xp WHERE Uuid='" + player.getUniqueId() + "'");
            return resultSet.getString("Uuid") != null;

        } catch (SQLException throwables) {
            return false;
        }
    }

    @Override
    public void accountPlayerCreate(@NotNull OfflinePlayer player) {
        mySql.updateSql("INSERT INTO xp (Username, Uuid) VALUES ('"+ player.getName()+ "', '"+ player.getUniqueId().toString()+ "'"+ ")");
    }

    @Override
    public void accountPlayerDelete(@NotNull OfflinePlayer player) {
        mySql.updateSql("DELETE * FROM xp WHERE Uuid="+ player.getUniqueId());
    }

    @Override
    public void accountPlayerInsert(OfflinePlayer player, float amount) {
        float saved = accountPlayerBalance(player);
        mySql.updateSql("UPDATE xp SET Xp="+(saved + amount)+ " WHERE Uuid ='"+ player.getUniqueId().toString()+"'");
    }

    @Override
    public void accountPlayerRemove(OfflinePlayer player, float amount) {
        float saved = accountPlayerBalance(player);
        mySql.updateSql("UPDATE xp SET Xp="+(saved - amount)+ " WHERE Uuid ='"+ player.getUniqueId().toString()+"'");
    }

    @Override
    public float accountPlayerBalance(OfflinePlayer player) {
        try {
            return mySql.querySql("SELECT Xp FROM xp WHERE Uuid='"+ player.getUniqueId().toString()+"'")
                    .getInt("Xp");
        } catch (SQLException exception) {
            return 0;

        }
    }

    @Override
    public String getName(OfflinePlayer player) {
        try {
            System.out.println(mySql.querySql("SELECT * FROM xp WHERE Uuid='"+ player.getUniqueId().toString()+ "'")
                    .getString("Username"));
            return mySql.querySql("SELECT * FROM xp WHERE Uuid='"+ player.getUniqueId().toString()+ "'")
                    .getString("Username");
        } catch (SQLException exception) {
            return null;

        }
    }

    @Override
    public void updateName(OfflinePlayer player) {
        mySql.updateSql("UPDATE xp SET Username='"+ player.getName()+ "' WHERE Uuid ='"+ player.getUniqueId().toString()+ "'");
    }

    public void createTable() {
        try{
            mySql.updateSql("CREATE TABLE IF NOT EXISTS `xp` ( `Id` INT NOT NULL AUTO_INCREMENT , `Username` VARCHAR(50) NOT NULL , `Uuid` VARCHAR(50) NOT NULL , `Xp` INT(10) NOT NULL DEFAULT '0', PRIMARY KEY (`Id`)) ENGINE = InnoDB;");
        }catch (Exception exception) {
            Bukkit.getConsoleSender().sendMessage("Error while trying to create the database");
        }
    }
}