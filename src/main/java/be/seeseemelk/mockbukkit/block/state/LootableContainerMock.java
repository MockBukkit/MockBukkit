package be.seeseemelk.mockbukkit.block.state;

import be.seeseemelk.mockbukkit.MockBukkit;
import be.seeseemelk.mockbukkit.UnimplementedOperationException;
import be.seeseemelk.mockbukkit.scheduler.BukkitSchedulerMock;
import com.destroystokyo.paper.loottable.LootableBlockInventory;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.loot.LootTable;
import org.bukkit.scheduler.BukkitRunnable;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

public abstract class LootableContainerMock extends ContainerMock implements LootableBlockInventory
{

	private boolean refillEnabled = false;
	private long lastFilled = -1;
	private long nextRefill = -1;

	private Map<UUID, Long> lootedPlayers;

	/**
	 * Constructs a new {@link LootableContainerMock} for the provided {@link Material}.
	 *
	 * @param material The material this state is for.
	 */
	protected LootableContainerMock(@NotNull Material material)
	{
		super(material);
	}

	/**
	 * Constructs a new {@link LootableContainerMock} for the provided {@link Block}.
	 *
	 * @param block The block this state is for.
	 */
	protected LootableContainerMock(@NotNull Block block)
	{
		super(block);
	}

	/**
	 * Constructs a new {@link ContainerMock} by cloning the data from an existing one.
	 *
	 * @param state The state to clone.
	 */
	protected LootableContainerMock(@NotNull ContainerMock state)
	{
		super(state);
	}

	@Override
	public boolean isRefillEnabled()
	{
		return this.refillEnabled;
	}

	/**
	 * Sets whether this container should be refilled.
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
		return getLastFilled() != -1;
	}

	@Override
	public boolean canPlayerLoot(@NotNull UUID player)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public boolean hasPlayerLooted(@NotNull UUID player)
	{
		return this.lootedPlayers != null && this.lootedPlayers.containsKey(player);
	}

	@Override
	public @Nullable Long getLastLooted(@NotNull UUID player)
	{
		return lootedPlayers != null ? lootedPlayers.get(player) : null;
	}

	@Override
	public boolean setHasPlayerLooted(@NotNull UUID player, boolean looted)
	{
		final boolean hasLooted = hasPlayerLooted(player);

		if (this.lootedPlayers == null)
		{
			this.lootedPlayers = new HashMap<>();
		}

		if (hasLooted != looted)
		{
			if (looted)
			{
				this.lootedPlayers.computeIfAbsent(player, p -> System.currentTimeMillis());
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
		return nextRefill != -1 && nextRefill > lastFilled;
	}

	@Override
	public long getLastFilled()
	{
		return this.lastFilled;
	}

	@Override
	public long getNextRefill()
	{
		return this.nextRefill;
	}


	/**
	 * Sets the next refill time. This uses the Bukkit Scheduler,
	 * therefore you have to tick it with {@link BukkitSchedulerMock#performTicks(long)}.
	 * @param refillAt timestamp in milliseconds. -1 to clear next refill
	 * @return The previous refill time
	 */
	@Override
	public long setNextRefill(long refillAt)
	{
		final long oldRefill = this.nextRefill;

		if (refillAt < -1)
		{
			refillAt = -1;
		}
		this.nextRefill = refillAt;

		new BukkitRunnable()
		{
			@Override
			public void run()
			{
				if (nextRefill == MockBukkit.getMock().getScheduler().getCurrentTick())
				{
					nextRefill = -1;
					lastFilled = MockBukkit.getMock().getScheduler().getCurrentTick();
				}

			}
		}.runTaskTimer(null, 0, 1);

		return oldRefill;
	}

	@Override
	public void setLootTable(LootTable table)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public LootTable getLootTable()
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public void setSeed(long seed)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public long getSeed()
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public boolean equals(Object o)
	{
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		if (!super.equals(o)) return false;
		LootableContainerMock that = (LootableContainerMock) o;
		return refillEnabled == that.refillEnabled && lastFilled == that.lastFilled && nextRefill == that.nextRefill &&
				Objects.equals(lootedPlayers, that.lootedPlayers);
	}

	@Override
	public int hashCode()
	{
		return Objects.hash(super.hashCode(), refillEnabled, lastFilled, nextRefill, lootedPlayers);
	}

}
