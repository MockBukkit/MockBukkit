package be.seeseemelk.mockbukkit;

import org.bukkit.BanList;

/**
 * Replica of the Bukkit internal PlayerList and CraftPlayerList implementation
 */
public class PlayerList {

    private int maxPlayers = Integer.MAX_VALUE;
    private BanList ipBans = new MockBanList();
    private BanList profileBans = new MockBanList();

    public void setMaxPlayers(int maxPlayers)
    {
        this.maxPlayers = maxPlayers;
    }

    public int getMaxPlayers()
    {
        return this.maxPlayers;
    }

    public BanList getIPBans()
    {
        return this.ipBans;
    }

    public BanList getProfileBans()
    {
        return this.profileBans;
    }
}
