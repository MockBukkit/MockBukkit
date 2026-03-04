package org.mockbukkit.mockbukkit.entity;

import org.bukkit.entity.EntityType;
import org.bukkit.entity.OminousItemSpawner;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.mockbukkit.mockbukkit.ServerMock;

import java.util.UUID;

/**
 * Mock implementation of an {@link OminousItemSpawner}.
 *
 * @see EntityMock
 */
public class OminousItemSpawnerMock extends EntityMock implements OminousItemSpawner
{

	private long spawnItemAfterTicks = 60;

	private @NotNull ItemStack item = ItemStack.empty();

	/**
	 * Constructs a new {@link OminousItemSpawnerMock} on the provided {@link ServerMock} with a specified {@link UUID}.
	 *
	 * @param server The server to create the entity on.
	 * @param uuid   The UUID of the entity.
	 */
	public OminousItemSpawnerMock(@NotNull ServerMock server, @NotNull UUID uuid)
	{
		super(server, uuid);
	}

	@Override
	public @NotNull ItemStack getItem()
	{
		return this.item.clone();
	}

	@Override
	public void setItem(@Nullable ItemStack item)
	{
		this.item = (item == null ? ItemStack.empty() : item.clone());
	}

	@Override
	public long getSpawnItemAfterTicks()
	{
		return this.spawnItemAfterTicks;
	}

	@Override
	public void setSpawnItemAfterTicks(long ticks)
	{
		this.spawnItemAfterTicks = ticks;
	}

	@Override
	public @NotNull EntityType getType()
	{
		return EntityType.OMINOUS_ITEM_SPAWNER;
	}

}
