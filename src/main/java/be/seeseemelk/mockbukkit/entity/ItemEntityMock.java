package be.seeseemelk.mockbukkit.entity;

import java.util.UUID;

import org.bukkit.entity.Item;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import be.seeseemelk.mockbukkit.ServerMock;

/**
 * This is a mock of a dropped {@link Item} entity. It can hold an {@link ItemStack}, that pretty much covers it all.
 * 
 * @author TheBusyBiscuit
 *
 */
public class ItemEntityMock extends EntityMock implements Item
{

	private ItemStack item;

	// The default pickup delay
	private int delay = 10;

	public ItemEntityMock(@NotNull ServerMock server, @NotNull UUID uuid, @NotNull ItemStack item)
	{
		super(server, uuid);
		this.item = item.clone();
	}

	@Override
	public ItemStack getItemStack()
	{
		return item;
	}

	@Override
	public void setItemStack(ItemStack stack)
	{
		// "stack" is actually nullable here, but it seems like Spigot also throws an Exception
		// in that case anyway. Besides a "null" Item does not really make sense anyway.
		this.item = stack.clone();
	}

	@Override
	public int getPickupDelay()
	{
		return delay;
	}

	@Override
	public void setPickupDelay(int delay)
	{
		this.delay = Math.min(delay, 32767);
	}

}
