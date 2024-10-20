package org.mockbukkit.mockbukkit.entity;

import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.minecart.HopperMinecart;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.loot.LootTable;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.mockbukkit.mockbukkit.ServerMock;
import org.mockbukkit.mockbukkit.exception.UnimplementedOperationException;

import java.util.UUID;

public class HopperMinecartMock extends LootableMinecart implements HopperMinecart
{

	private boolean enabled = true;
	private Inventory inventory;

	/**
	 * Constructs a new {@link HopperMinecartMock} on the provided {@link ServerMock} with a specified {@link UUID}.
	 *
	 * @param server The server to create the entity on.
	 * @param uuid   The UUID of the entity.
	 */
	public HopperMinecartMock(@NotNull ServerMock server, @NotNull UUID uuid)
	{
		super(server, uuid);
	}

	@Override
	public boolean isEnabled()
	{
		return this.enabled;
	}

	@Override
	public void setEnabled(boolean enabled)
	{
		this.enabled = enabled;
	}

	@Override
	@Deprecated(forRemoval = true)
	public int getPickupCooldown()
	{
		throw new UnsupportedOperationException("Hopper minecarts don't have cooldowns");
	}

	@Override
	@Deprecated(forRemoval = true)
	public void setPickupCooldown(int cooldown)
	{
		throw new UnsupportedOperationException("Hopper minecarts don't have cooldowns");
	}

	@Override
	public @NotNull Entity getEntity()
	{
		return this;
	}

	@Override
	public @NotNull Material getMinecartMaterial()
	{
		return Material.HOPPER_MINECART;
	}

	@Override
	public @NotNull Inventory getInventory()
	{
		if (this.inventory == null)
		{
			this.inventory = server.createInventory(null, InventoryType.HOPPER);
		}
		return this.inventory;
	}

	@Override
	public @NotNull EntityType getType()
	{
		return EntityType.HOPPER_MINECART;
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
