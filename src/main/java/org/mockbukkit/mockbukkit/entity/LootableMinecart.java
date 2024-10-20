package org.mockbukkit.mockbukkit.entity;

import com.destroystokyo.paper.loottable.LootableInventory;
import org.bukkit.loot.LootTable;
import org.bukkit.scheduler.BukkitRunnable;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.mockbukkit.mockbukkit.ServerMock;
import org.mockbukkit.mockbukkit.exception.UnimplementedOperationException;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public abstract class LootableMinecart extends MinecartMock implements LootableInventory
{

	private long nextRefill = -1;
	private long lastFilled = -1;
	private Map<UUID, Long> lootedPlayers;
	private boolean refillEnabled;

	/**
	 * Constructs a new {@link LootableMinecart} on the provided {@link ServerMock} with a specified {@link UUID}.
	 *
	 * @param server The server to create the entity on.
	 * @param uuid   The UUID of the entity.
	 */
	protected LootableMinecart(@NotNull ServerMock server, @NotNull UUID uuid)
	{
		super(server, uuid);
	}

	@Override
	public boolean isRefillEnabled()
	{
		return this.refillEnabled;
	}

	public void setRefillEnabled(boolean refillEnabled)
	{
		this.refillEnabled = refillEnabled;
	}

	@Override
	public boolean hasBeenFilled()
	{
		return this.lastFilled != -1;
	}

	@Override
	public boolean hasPlayerLooted(@NotNull UUID player)
	{
		return this.lootedPlayers != null && this.lootedPlayers.containsKey(player);
	}

	@Override
	public @Nullable Long getLastLooted(@NotNull UUID player)
	{
		return this.lootedPlayers != null ? this.lootedPlayers.get(player) : null;
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
		return this.nextRefill != -1;
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

	@Override
	public long setNextRefill(long refillAt)
	{
		final long oldRefill = this.nextRefill;
		this.nextRefill = refillAt;

		new BukkitRunnable()
		{
			@Override
			public void run()
			{
				if (nextRefill == server.getScheduler().getCurrentTick())
				{
					nextRefill = -1;
					lastFilled = server.getScheduler().getCurrentTick();
				}

			}
		}.runTaskTimer(null, 1, 1);

		return oldRefill;
	}

	@Override
	public void setLootTable(@Nullable LootTable table)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public @Nullable LootTable getLootTable()
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

}
