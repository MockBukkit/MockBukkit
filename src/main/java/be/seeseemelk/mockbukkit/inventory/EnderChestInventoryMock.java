package be.seeseemelk.mockbukkit.inventory;

import org.bukkit.block.EnderChest;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.InventoryHolder;

import be.seeseemelk.mockbukkit.entity.PlayerMock;

/**
 * This {@link InventoryMock} mocks an {@link EnderChest} but pretty much behaves like any small chest. A
 * {@link PlayerMock} carries an instance of an {@link EnderChestInventoryMock}.
 *
 * @author TheBusyBiscuit
 *
 */
public class EnderChestInventoryMock extends InventoryMock
{
	public EnderChestInventoryMock(InventoryHolder holder)
	{
		super(holder, 27, InventoryType.ENDER_CHEST);
	}
}
