package be.seeseemelk.mockbukkit.boss;

import org.bukkit.NamespacedKey;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarFlag;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.KeyedBossBar;
import org.jetbrains.annotations.NotNull;

/**
 * Mock implementation of {@link KeyedBossBar}.
 */
public class KeyedBossBarMock extends BossBarMock implements KeyedBossBar
{

	private final @NotNull NamespacedKey key;

	/**
	 * Constructs a new {@link KeyedBossBarMock} with the provided parameters.
	 *
	 * @param key   The bossbar's namespaced key.
	 * @param title The title of the bossbar.
	 * @param color The color of the bossbar.
	 * @param style The style of the bossbar.
	 * @param flags The flags to set on the bossbar.
	 */
	public KeyedBossBarMock(@NotNull NamespacedKey key, @NotNull String title, @NotNull BarColor color,
			@NotNull BarStyle style, BarFlag... flags)
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
