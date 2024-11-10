package org.mockbukkit.mockbukkit.entity;

import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;
import org.mockbukkit.mockbukkit.ServerMock;
import org.bukkit.boss.BossBar;
import org.bukkit.entity.Boss;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.mockbukkit.mockbukkit.boss.BossBarMock;

import java.util.UUID;

abstract class AbstractBossMock extends MonsterMock implements Boss
{

	protected BossBarMock bossBarMock;
	/**
	 * Constructs a new {@link MonsterMock} on the provided {@link ServerMock} with a specified {@link UUID}.
	 *
	 * @param server The server to create the entity on.
	 * @param uuid   The UUID of the entity.
	 */
	protected AbstractBossMock(@NotNull ServerMock server, @NotNull UUID uuid, String title)
	{
		super(server, uuid);
		bossBarMock = new BossBarMock(title, BarColor.PURPLE, BarStyle.SOLID);
	}

	@Override
	public @Nullable BossBar getBossBar()
	{
		return bossBarMock;
	}
}
