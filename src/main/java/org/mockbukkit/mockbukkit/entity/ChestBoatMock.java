package org.mockbukkit.mockbukkit.entity;

import org.bukkit.Material;
import org.bukkit.entity.ChestBoat;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.inventory.Inventory;
import org.bukkit.loot.LootTable;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.mockbukkit.mockbukkit.ServerMock;
import org.mockbukkit.mockbukkit.exception.UnimplementedOperationException;
import org.mockbukkit.mockbukkit.inventory.ChestInventoryMock;

import java.util.UUID;

public class ChestBoatMock extends BoatMock implements ChestBoat
{

	private final Inventory inventory;

	/**
	 * Constructs a new {@link ChestBoatMock} on the provided {@link ServerMock} with a specified {@link UUID}.
	 *
	 * @param server The server to create the entity on.
	 * @param uuid   The UUID of the entity.
	 */
	public ChestBoatMock(@NotNull ServerMock server, @NotNull UUID uuid)
	{
		super(server, uuid);
		this.inventory = new ChestInventoryMock(this, 3 * 9);
	}

	@Override
	public boolean isRefillEnabled()
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	public void setRefillEnabled(boolean refillEnabled)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public boolean hasBeenFilled()
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
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
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public @Nullable Long getLastLooted(@NotNull UUID player)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public boolean setHasPlayerLooted(@NotNull UUID player, boolean looted)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public boolean hasPendingRefill()
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public long getLastFilled()
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public long getNextRefill()
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public long setNextRefill(long refillAt)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public @NotNull Inventory getInventory()
	{
		return this.inventory;
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
	public void setLootTable(@Nullable LootTable lootTable, long l)
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
	public @NotNull Material getBoatMaterial()
	{
		return switch (getBoatType())
		{
			case OAK -> Material.OAK_CHEST_BOAT;
			case SPRUCE -> Material.SPRUCE_CHEST_BOAT;
			case BIRCH -> Material.BIRCH_CHEST_BOAT;
			case JUNGLE -> Material.JUNGLE_CHEST_BOAT;
			case ACACIA -> Material.ACACIA_CHEST_BOAT;
			case CHERRY -> Material.CHERRY_CHEST_BOAT;
			case DARK_OAK -> Material.DARK_OAK_CHEST_BOAT;
			case MANGROVE -> Material.MANGROVE_CHEST_BOAT;
			case BAMBOO -> Material.BAMBOO_CHEST_RAFT;
		};
	}

	@Override
	public @NotNull Entity getEntity()
	{
		return this;
	}

	@Override
	public @NotNull EntityType getType()
	{
		return EntityType.OAK_CHEST_BOAT;
	}

}
