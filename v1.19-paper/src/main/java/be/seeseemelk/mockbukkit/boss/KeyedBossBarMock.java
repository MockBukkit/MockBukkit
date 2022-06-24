package be.seeseemelk.mockbukkit.boss;

import org.bukkit.NamespacedKey;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarFlag;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.KeyedBossBar;
import org.jetbrains.annotations.NotNull;

public class KeyedBossBarMock extends BossBarMock implements KeyedBossBar
{

	private final @NotNull NamespacedKey key;

	public KeyedBossBarMock(@NotNull NamespacedKey key, @NotNull String title, @NotNull BarColor color, @NotNull BarStyle style, BarFlag... flags)
	{
		super(title, color, style, flags);
		this.key = key;
	}

	@Override
	public @NotNull NamespacedKey getKey()
	{
		return key;
	}

}
