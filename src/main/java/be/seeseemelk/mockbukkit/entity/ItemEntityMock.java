package be.seeseemelk.mockbukkit.entity;

import java.util.UUID;

import org.bukkit.entity.Item;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import be.seeseemelk.mockbukkit.ServerMock;

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
