package be.seeseemelk.mockbukkit.entity;

import be.seeseemelk.mockbukkit.ServerMock;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Allay;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

public class AllayMock extends CreatureMock implements Allay
{
	private final Inventory inventory;
	private Material currentItem;

	protected AllayMock(ServerMock server, UUID uuid)
	{
		super(server, uuid);
		inventory = Bukkit.createInventory(null, 9);
	}

	/**
	 * Simulates the Interaction of a Player with the Allay to set it's current item.
	 * @param item The {@link Material} of the Item the Allay should collect
	 */
	public void simulatePlayerInteract(@NotNull Material item)
	{
		this.currentItem = item;
	}

	/**
	 * Simulate the retrieval of the Allay's current items.
	 * @return A {@link List} of {@link ItemStack}s that the Allay is holding
	 */
	public List<ItemStack> simulateItemRetrieval()
	{
		List<ItemStack> items = new ArrayList<>();

		for (ItemStack inventoryItems :
				inventory.getContents())
		{
			if (inventoryItems != null)
			{
				items.add(inventoryItems);
			}
		}

		return items;
	}

	/**
	 * Simulate the Allay picking up an {@link ItemStack} from the ground.
	 * If the Itemstack is not of the current Type, this will throw a {@link IllegalArgumentException}.
	 * If the Inventory is full, this will throw a {@link IllegalStateException}.
	 * @param item The {@link ItemStack} to pick up
	 */
	public void simulateItemPickup(@NotNull ItemStack item)
	{

		if (item.getType() == currentItem)
		{
			inventory.addItem(item);
			if (Arrays.stream(inventory.getContents()).count() > 1)
			{
				throw new IllegalStateException("Allay cannot hold more than 1 ItemStack");
			}
		}else
		{
			throw new IllegalArgumentException("Item is not the same type as the Allay is currently holding");
		}
	}

	@Override
	public @NotNull Inventory getInventory()
	{
		return inventory;
	}


}
