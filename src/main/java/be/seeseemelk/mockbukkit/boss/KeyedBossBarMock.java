package be.seeseemelk.mockbukkit.boss;

import org.bukkit.NamespacedKey;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarFlag;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.KeyedBossBar;

import org.jetbrains.annotations.NotNull;

public class KeyedBossBarMock extends BossBarMock implements KeyedBossBar
{
	private final NamespacedKey key;

	public KeyedBossBarMock(NamespacedKey key, String title, BarColor color, BarStyle style, BarFlag... flags)
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
