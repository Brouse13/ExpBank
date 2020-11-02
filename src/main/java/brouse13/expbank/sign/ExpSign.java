package brouse13.expbank.sign;

import org.bukkit.Location;
import org.bukkit.OfflinePlayer;

public class ExpSign {

    private OfflinePlayer owner;
    private Long timestamp;
    private Location location;
    private SignType type;

    public ExpSign(OfflinePlayer owner, Long timestamp, Location location, String[] lines) {
        this.owner = owner;
        this.timestamp = timestamp;
        this.location = location;
        this.type = linesToType(lines);
    }

    public ExpSign(OfflinePlayer owner, Long timestamp, Location location, SignType signType) {
        this.owner = owner;
        this.timestamp = timestamp;
        this.location = location;
        this.type = signType;
    }


    public Long getTimestamp() {
        return timestamp;
    }

    public OfflinePlayer getOwner() {
        return owner;
    }

    public SignType getSignType() {
        return type;
    }

    public Location getLocation() {
        return location;
    }

    private SignType linesToType(String[] lines) {
        if (lines[1].equalsIgnoreCase("admin")) {
            return SignType.Admin;
        }else if (lines[1].equalsIgnoreCase("withdraw")) {
            return SignType.Withdraw;
        }else if (lines[1].equalsIgnoreCase("deposit")) {
            return SignType.Deposit;
        }else if (lines[1].equalsIgnoreCase("give")) {
            return SignType.Give;
        }else if (lines[1].equalsIgnoreCase("all")) {
            return SignType.All;
        }
        return null;
    }

    @Override
    public String toString() {
        return super.toString();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof ExpSign) {
            ExpSign sign = (ExpSign) obj;
            return sign.getLocation().getX() == this.location.getX()
                    && sign.getLocation().getY() == this.location.getY()
                    && sign.getLocation().getZ() == this.location.getZ()
                    && sign.getLocation().getWorld().toString().equals(this.getLocation().getWorld().toString())
                    && sign.getTimestamp().equals(this.timestamp);
        }
        return false;
    }
}
