package be.seeseemelk.mockbukkit.entity;

import org.bukkit.entity.Player;

public class PlayerSpigot extends Player.Spigot {
    final Player p;

    public PlayerSpigot(Player p) {
        this.p = p;
    }

    @Override
    public void respawn() {
        this.p.setHealth(20);
    }

}
