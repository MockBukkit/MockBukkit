package org.mockbukkit.mockbukkit.entity;

import org.mockbukkit.mockbukkit.ServerMock;
import com.google.common.base.Preconditions;
import org.bukkit.Raid;
import org.bukkit.block.Block;
import org.bukkit.entity.Raider;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.UUID;

/**
 * Mock implementation of a {@link Raider}.
 *
 * @see MonsterMock
 */
public abstract class RaiderMock extends MonsterMock implements Raider
{

	private boolean canJoinRaid;
	private boolean celebrating;
	private boolean patrolLeader;
	private int wave;
	private int ticksOutsideRaid;
	private Block patrolTarget;
	private Raid raid;

	/**
	 * Constructs a new {@link RaiderMock} on the provided {@link ServerMock} with a specified {@link UUID}.
	 *
	 * @param server The server to create the entity on.
	 * @param uuid   The UUID of the entity.
	 */
	protected RaiderMock(@NotNull ServerMock server, @NotNull UUID uuid)
	{
		super(server, uuid);
	}

	@Override
	public void setRaid(@Nullable Raid raid)
	{
		this.raid = raid;
	}

	@Override
	public @Nullable Raid getRaid()
	{
		return this.raid;
	}

	@Override
	public int getWave()
	{
		return this.wave;
	}

	@Override
	public void setWave(int wave)
	{
		Preconditions.checkArgument(wave >= 0, "wave must be >= 0");
		this.wave = wave;
	}

	@Override
	public @Nullable Block getPatrolTarget()
	{
		return this.patrolTarget;
	}

	@Override
	public void setPatrolTarget(@Nullable Block block)
	{
		this.patrolTarget = block;
	}

	@Override
	public boolean isPatrolLeader()
	{
		return this.patrolLeader;
	}

	@Override
	public void setPatrolLeader(boolean leader)
	{
		this.patrolLeader = leader;
	}

	@Override
	public boolean isCanJoinRaid()
	{
		return this.canJoinRaid;
	}

	@Override
	public void setCanJoinRaid(boolean join)
	{
		this.canJoinRaid = join;
	}

	@Override
	public int getTicksOutsideRaid()
	{
		return this.ticksOutsideRaid;
	}

	@Override
	public void setTicksOutsideRaid(int ticks)
	{
		Preconditions.checkArgument(ticks >= 0, "ticks must be >= 0");
		this.ticksOutsideRaid = ticks;
	}

	@Override
	public boolean isCelebrating()
	{
		return this.celebrating;
	}

	@Override
	public void setCelebrating(boolean celebrating)
	{
		this.celebrating = celebrating;
	}

}
