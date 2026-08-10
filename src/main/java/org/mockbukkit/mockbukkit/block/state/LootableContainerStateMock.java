package org.mockbukkit.mockbukkit.block.state;

import com.destroystokyo.paper.loottable.LootableBlockInventory;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.Nullable;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

/**
 * Mock implementation of a {@link LootableBlockInventory}.
 * <p>
 * Unlike a real server, this mock never refills the container by itself: a block state
 * is a snapshot and does not change over time. The refill timestamps are plain data
 * that can be controlled with {@link #setNextRefill(long)} and {@link #setLastFilled(long)}.
 *
 * @see LootableStateMock
 */
@NullMarked
public abstract class LootableContainerStateMock extends LootableStateMock implements LootableBlockInventory
{

	private boolean refillEnabled = false;
	private long lastFilled = -1;
	private long nextRefill = -1;
	private final Map<UUID, Long> lootedPlayers = new HashMap<>();

	protected LootableContainerStateMock(Material material)
	{
		super(material);
	}

	protected LootableContainerStateMock(Block block)
	{
		super(block);
	}

	protected LootableContainerStateMock(LootableContainerStateMock state)
	{
		super(state);
		this.refillEnabled = state.refillEnabled;
		this.lastFilled = state.lastFilled;
		this.nextRefill = state.nextRefill;
		this.lootedPlayers.putAll(state.lootedPlayers);
	}

	@Override
	public boolean isRefillEnabled()
	{
		return this.refillEnabled;
	}

	/**
	 * Sets whether this container should be refilled.
	 * On a real server this is controlled by the {@code lootables.auto-replenish}
	 * setting in the Paper world configuration, which defaults to {@code false}.
	 *
	 * @param refillEnabled Whether this container should be refilled.
	 */
	public void setRefillEnabled(boolean refillEnabled)
	{
		this.refillEnabled = refillEnabled;
	}

	@Override
	public boolean hasBeenFilled()
	{
		return this.lastFilled != -1;
	}

	/**
	 * Checks if this player can loot this block.
	 * This mirrors the default Paper world configuration, where
	 * {@code lootables.restrict-player-reloot} is enabled and no reloot
	 * time is set: a player can only loot a container they have never looted.
	 *
	 * @param player the player to check
	 * @return Whether this player can loot this block
	 */
	@Override
	public boolean canPlayerLoot(UUID player)
	{
		return !hasPlayerLooted(player);
	}

	@Override
	public boolean hasPlayerLooted(UUID player)
	{
		return this.lootedPlayers.containsKey(player);
	}

	@Override
	public @Nullable Long getLastLooted(UUID player)
	{
		return this.lootedPlayers.get(player);
	}

	@Override
	public boolean setHasPlayerLooted(UUID player, boolean looted)
	{
		final boolean hasLooted = hasPlayerLooted(player);

		if (hasLooted != looted)
		{
			if (looted)
			{
				this.lootedPlayers.put(player, System.currentTimeMillis());
			}
			else
			{
				this.lootedPlayers.remove(player);
			}
		}
		return hasLooted;
	}

	@Override
	public boolean hasPendingRefill()
	{
		return this.nextRefill != -1 && this.nextRefill > this.lastFilled;
	}

	@Override
	public long getLastFilled()
	{
		return this.lastFilled;
	}

	/**
	 * Sets the timestamp in milliseconds that this container was last filled at,
	 * as if a player had opened it and its loot table had been rolled.
	 *
	 * @param lastFilled timestamp in milliseconds. -1 to mark this container as never filled
	 */
	public void setLastFilled(long lastFilled)
	{
		this.lastFilled = Math.max(lastFilled, -1);
	}

	@Override
	public long getNextRefill()
	{
		return this.nextRefill;
	}

	@Override
	public long setNextRefill(long refillAt)
	{
		final long oldRefill = this.nextRefill;
		this.nextRefill = Math.max(refillAt, -1);
		return oldRefill;
	}

	@Override
	public boolean equals(Object o)
	{
		if (!(o instanceof LootableContainerStateMock that))
		{
			return false;
		}
		if (!super.equals(o))
		{
			return false;
		}
		return refillEnabled == that.refillEnabled && lastFilled == that.lastFilled
				&& nextRefill == that.nextRefill && lootedPlayers.equals(that.lootedPlayers);
	}

	@Override
	public int hashCode()
	{
		return Objects.hash(super.hashCode(), refillEnabled, lastFilled, nextRefill, lootedPlayers);
	}

}
