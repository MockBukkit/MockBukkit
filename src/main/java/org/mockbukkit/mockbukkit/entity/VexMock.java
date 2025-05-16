package org.mockbukkit.mockbukkit.entity;

import com.google.common.base.Preconditions;
import org.bukkit.Location;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Mob;
import org.bukkit.entity.Vex;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.mockbukkit.mockbukkit.ServerMock;

import java.util.Objects;
import java.util.UUID;

/**
 * Mock implementation of a {@link Vex}.
 *
 * @see MonsterMock
 */
public class VexMock extends MonsterMock implements Vex
{

	private boolean isCharging = false;
	private boolean hasLimitedLife = false;
	private int limitedLifeTicks = -1;

	private @Nullable Location boundLocation = null;
	private @Nullable Mob summoner = null;

	/**
	 * Constructs a new {@link VexMock} on the provided {@link ServerMock} with a specified {@link UUID}.
	 *
	 * @param server The server to create the entity on.
	 * @param uuid   The UUID of the entity.
	 */
	public VexMock(@NotNull ServerMock server, @NotNull UUID uuid)
	{
		super(server, uuid);
	}

	@Override
	public boolean isCharging()
	{
		return this.isCharging;
	}

	@Override
	public void setCharging(boolean charging)
	{
		this.isCharging = charging;
	}

	@Override
	public @Nullable Location getBound()
	{
		return this.boundLocation == null ? null : boundLocation.clone();
	}

	@Override
	public void setBound(@Nullable Location location)
	{
		if (location == null)
		{
			this.boundLocation = null;
		}
		else
		{
			Preconditions.checkArgument(Objects.equals(this.getWorld(), location.getWorld()), "The bound world cannot be different to the entity's world.");
			this.boundLocation = location.clone();
		}
	}

	@Override
	@Deprecated(forRemoval = true)
	public int getLifeTicks()
	{
		return this.getLimitedLifetimeTicks();
	}

	@Override
	@Deprecated(forRemoval = true)
	public void setLifeTicks(int lifeTicks)
	{
		this.setLimitedLifetime(lifeTicks >= 0);
		this.setLimitedLifetimeTicks(lifeTicks);
	}

	@Override
	@Deprecated(forRemoval = true)
	public boolean hasLimitedLife()
	{
		return this.hasLimitedLifetime();
	}

	@Override
	public @Nullable Mob getSummoner()
	{
		return this.summoner;
	}

	@Override
	public void setSummoner(@Nullable Mob summoner)
	{
		this.summoner = summoner;
	}

	@Override
	public boolean hasLimitedLifetime()
	{
		return this.hasLimitedLife;
	}

	@Override
	public void setLimitedLifetime(boolean hasLimitedLifetime)
	{
		this.hasLimitedLife = hasLimitedLifetime;
	}

	@Override
	public int getLimitedLifetimeTicks()
	{
		return this.limitedLifeTicks;
	}

	@Override
	public void setLimitedLifetimeTicks(int ticks)
	{
		this.limitedLifeTicks = ticks;
	}

	@Override
	public @NotNull EntityType getType()
	{
		return EntityType.VEX;
	}

}
