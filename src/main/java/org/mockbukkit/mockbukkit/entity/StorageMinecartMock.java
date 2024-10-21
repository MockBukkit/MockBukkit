package org.mockbukkit.mockbukkit.entity;

import org.mockbukkit.mockbukkit.ServerMock;
import org.mockbukkit.mockbukkit.exception.UnimplementedOperationException;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.minecart.StorageMinecart;
import org.bukkit.inventory.Inventory;
import org.bukkit.loot.LootTable;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.UUID;

public class StorageMinecartMock extends LootableMinecart implements StorageMinecart
{

	private final Inventory inventory;

	/**
	 * Constructs a new {@link LootableMinecart} on the provided {@link ServerMock} with a specified {@link UUID}.
	 *
	 * @param server The server to create the entity on.
	 * @param uuid   The UUID of the entity.
	 */
	public StorageMinecartMock(@NotNull ServerMock server, @NotNull UUID uuid)
	{
		super(server, uuid);
		inventory = server.createInventory(this, 3 * 9);
	}

	@Override
	public @NotNull Entity getEntity()
	{
		return this;
	}

	@Override
	public @NotNull Material getMinecartMaterial()
	{
		return Material.CHEST_MINECART;
	}

	@Override
	public @NotNull Inventory getInventory()
	{
		return this.inventory;
	}

	@Override
	public @NotNull EntityType getType()
	{
		return EntityType.CHEST_MINECART;
	}

	@Override
	public boolean canPlayerLoot(@NotNull UUID player)
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

}
