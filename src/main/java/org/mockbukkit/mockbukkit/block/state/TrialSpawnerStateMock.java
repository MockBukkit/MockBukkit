package org.mockbukkit.mockbukkit.block.state;

import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableSet;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.TrialSpawner;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.spawner.TrialSpawnerConfiguration;
import org.jspecify.annotations.NullMarked;
import org.mockbukkit.mockbukkit.exception.UnimplementedOperationException;

import java.util.Collection;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;

/**
 * Mock implementation of a {@link TrialSpawner}.
 *
 * @see TileStateMock
 */
@NullMarked
@SuppressWarnings("UnstableApiUsage")
public class TrialSpawnerStateMock extends TileStateMock implements TrialSpawner
{
	private final Set<UUID> trackedPlayers = new HashSet<>();
	private final Set<UUID> trackedEntities = new HashSet<>();

	private long cooldownEnd = 0;
	private long nextSpawnAttempt = 0;
	private int requiredPlayerRange = 16;
	private boolean ominous = false;

	protected TrialSpawnerStateMock(Material material)
	{
		super(material);
	}

	protected TrialSpawnerStateMock(Block block)
	{
		super(block);
	}

	protected TrialSpawnerStateMock(TrialSpawnerStateMock state)
	{
		super(state);

		this.trackedPlayers.addAll(state.trackedPlayers);
		this.trackedEntities.addAll(state.trackedEntities);
		this.cooldownEnd = state.cooldownEnd;
		this.nextSpawnAttempt = state.nextSpawnAttempt;
		this.requiredPlayerRange = state.requiredPlayerRange;
		this.ominous = state.ominous;
	}

	@Override
	public long getCooldownEnd()
	{
		return this.cooldownEnd;
	}

	@Override
	public void setCooldownEnd(long ticks)
	{
		this.cooldownEnd = ticks;
	}

	@Override
	public long getNextSpawnAttempt()
	{
		return this.nextSpawnAttempt;
	}

	@Override
	public void setNextSpawnAttempt(long ticks)
	{
		this.nextSpawnAttempt = ticks;
	}

	@Override
	public int getCooldownLength()
	{
		//TODO: Auto-generated method stub
		throw UnimplementedOperationException.exception();
	}

	@Override
	public void setCooldownLength(int ticks)
	{
		//TODO: Auto-generated method stub
		throw UnimplementedOperationException.exception();
	}

	@Override
	public int getRequiredPlayerRange()
	{
		return this.requiredPlayerRange;
	}

	@Override
	public void setRequiredPlayerRange(int requiredPlayerRange)
	{
		this.requiredPlayerRange = requiredPlayerRange;
	}

	@Override
	public Collection<Player> getTrackedPlayers()
	{
		ImmutableSet.Builder<Player> entities = ImmutableSet.builder();

		for(UUID uuid : this.trackedPlayers)
		{
			Player player = Bukkit.getPlayer(uuid);
			if (player != null)
			{
				entities.add(player);
			}
		}

		return entities.build();
	}

	@Override
	public boolean isTrackingPlayer(Player player)
	{
		Preconditions.checkArgument(player != null, "Entity cannot be null");
		return this.trackedPlayers.contains(player.getUniqueId());
	}

	@Override
	public void startTrackingPlayer(Player player)
	{
		Preconditions.checkArgument(player != null, "Player cannot be null");
		this.trackedPlayers.add(player.getUniqueId());
	}

	@Override
	public void stopTrackingPlayer(Player player)
	{
		Preconditions.checkArgument(player != null, "Player cannot be null");
		this.trackedPlayers.remove(player.getUniqueId());
	}

	@Override
	public Collection<Entity> getTrackedEntities()
	{
		ImmutableSet.Builder<Entity> entities = ImmutableSet.builder();

		for(UUID uuid : this.trackedEntities)
		{
			Entity entity = Bukkit.getEntity(uuid);
			if (entity != null)
			{
				entities.add(entity);
			}
		}

		return entities.build();
	}

	@Override
	public boolean isTrackingEntity(Entity entity)
	{
		Preconditions.checkArgument(entity != null, "Entity cannot be null");
		return this.trackedEntities.contains(entity.getUniqueId());
	}

	@Override
	public void startTrackingEntity(Entity entity)
	{
		Preconditions.checkArgument(entity != null, "Entity cannot be null");
		this.trackedEntities.add(entity.getUniqueId());
	}

	@Override
	public void stopTrackingEntity(Entity entity)
	{
		Preconditions.checkArgument(entity != null, "Entity cannot be null");
		this.trackedEntities.remove(entity.getUniqueId());
	}

	@Override
	public boolean isOminous()
	{
		return this.ominous;
	}

	@Override
	public void setOminous(boolean ominous)
	{
		this.ominous = ominous;
	}

	@Override
	public TrialSpawnerConfiguration getNormalConfiguration()
	{
		// TODO: Auto generated stub
		throw UnimplementedOperationException.exception();
	}

	@Override
	public TrialSpawnerConfiguration getOminousConfiguration()
	{
		// TODO: Auto generated stub
		throw UnimplementedOperationException.exception();
	}

	@Override
	public TrialSpawnerStateMock getSnapshot()
	{
		return new TrialSpawnerStateMock(this);
	}

	@Override
	public boolean equals(Object o)
	{
		if (!(o instanceof TrialSpawnerStateMock that))
		{
			return false;
		}
		if (!super.equals(o))
		{
			return false;
		}
		return cooldownEnd == that.cooldownEnd && nextSpawnAttempt == that.nextSpawnAttempt
				&& requiredPlayerRange == that.requiredPlayerRange
				&& ominous == that.ominous && Objects.equals(trackedPlayers, that.trackedPlayers)
				&& Objects.equals(trackedEntities, that.trackedEntities);
	}

	@Override
	public int hashCode()
	{
		return Objects.hash(super.hashCode(), trackedPlayers, trackedEntities, cooldownEnd, nextSpawnAttempt, requiredPlayerRange, ominous);
	}

}
