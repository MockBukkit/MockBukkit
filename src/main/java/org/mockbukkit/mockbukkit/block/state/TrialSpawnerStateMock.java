package org.mockbukkit.mockbukkit.block.state;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.TrialSpawner;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.spawner.TrialSpawnerConfiguration;
import org.jetbrains.annotations.NotNull;
import org.mockbukkit.mockbukkit.exception.UnimplementedOperationException;

import java.util.Collection;

/**
 * Mock implementation of a {@link TrialSpawner}.
 *
 * @see TileStateMock
 */
public class TrialSpawnerStateMock extends TileStateMock implements TrialSpawner
{

	protected TrialSpawnerStateMock(@NotNull Block block)
	{
		super(block);
	}

	public TrialSpawnerStateMock(@NotNull Material material)
	{
		super(material);
	}

	protected TrialSpawnerStateMock(@NotNull TrialSpawnerStateMock state)
	{
		super(state);
	}

	@Override
	public @NotNull TrialSpawnerStateMock getSnapshot()
	{
		return new TrialSpawnerStateMock(this);
	}

	@Override
	public @NotNull TrialSpawnerStateMock copy()
	{
		return new TrialSpawnerStateMock(this);
	}

	@Override
	public long getCooldownEnd()
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public void setCooldownEnd(long l)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public long getNextSpawnAttempt()
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public void setNextSpawnAttempt(long l)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public int getCooldownLength()
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public void setCooldownLength(int i)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public int getRequiredPlayerRange()
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public void setRequiredPlayerRange(int i)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public @NotNull Collection<Player> getTrackedPlayers()
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public boolean isTrackingPlayer(@NotNull Player player)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public void startTrackingPlayer(@NotNull Player player)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public void stopTrackingPlayer(@NotNull Player player)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public @NotNull Collection<Entity> getTrackedEntities()
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public boolean isTrackingEntity(@NotNull Entity entity)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public void startTrackingEntity(@NotNull Entity entity)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public void stopTrackingEntity(@NotNull Entity entity)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public boolean isOminous()
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public void setOminous(boolean b)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public @NotNull TrialSpawnerConfiguration getNormalConfiguration()
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public @NotNull TrialSpawnerConfiguration getOminousConfiguration()
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

}
