package be.seeseemelk.mockbukkit.boss;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import org.bukkit.NamespacedKey;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarFlag;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.KeyedBossBar;
import org.bukkit.entity.Player;

public class KeyedBossBarMock implements KeyedBossBar {
    private final NamespacedKey key;
    private String title = null;
    private BarColor color = null;
    private BarStyle style = null;
    private HashSet<Player> players = new HashSet<>();
    private HashSet<BarFlag> barFlags = new HashSet<>();
    private boolean visible = true;
    private double progress = 1.0;

    public KeyedBossBarMock(NamespacedKey key, String title, BarColor color, BarStyle style, BarFlag... flags) {
        this.key = key;
        this.title = title;
        this.color = color;
        this.style = style;
        for (BarFlag flag : flags) {
            addFlag(flag);
        }
    }

    @Override
    public NamespacedKey getKey() {
        return key;
    }

    @Override
    public String getTitle() {
        return title;
    }

    @Override
    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public BarColor getColor() {
        return color;
    }

    @Override
    public void setColor(BarColor color) {
        this.color = color;
    }

    @Override
    public BarStyle getStyle() {
        return style;
    }

    @Override
    public void setStyle(BarStyle style) {
        this.style = style;
    }

    @Override
    public void removeFlag(BarFlag flag) {
        barFlags.remove(flag);
    }

    @Override
    public void addFlag(BarFlag flag) {
        barFlags.add(flag);
    }

    @Override
    public boolean hasFlag(BarFlag flag) {
        return barFlags.contains(flag);
    }

    @Override
    public void setProgress(double progress) {
        if (progress > 1.0 || progress < 0) {
            throw new IllegalArgumentException("Progress must be between 0.0 and 1.0");
        }
        this.progress = progress;
    }

    @Override
    public double getProgress() {
        return progress;
    }

    @Override
    public void addPlayer(Player player) {
        this.players.add(player);
    }

    @Override
    public void removePlayer(Player player) {
        this.players.remove(player);
    }

    @Override
    public void removeAll() {
        this.players.clear();
    }

    @Override
    public List<Player> getPlayers() {
        return new ArrayList<Player>(players);
    }

    @Override
    public void setVisible(boolean visible) {
        this.visible = visible;
    }

    @Override
    public boolean isVisible() {
        return visible;
    }

    @Deprecated
    @Override
    public void show() {
        setVisible(true);
    }

    @Deprecated
    @Override
    public void hide() {
        setVisible(false);
    }
    
}
