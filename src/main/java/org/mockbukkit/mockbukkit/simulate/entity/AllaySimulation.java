package org.mockbukkit.mockbukkit.simulate.entity;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.mockbukkit.mockbukkit.entity.AllayMock;

import java.util.List;

public class AllaySimulation
{

	private final AllayMock allayMock;

	public AllaySimulation(AllayMock allayMock)
	{
		this.allayMock = allayMock;
	}

	/**
	 * Simulates the Interaction of a Player with the Allay to set it's current item.
	 *
	 * @param material The {@link Material} of the Item the Allay should collect
	 */
	public void simulatePlayerInteract(@NotNull Material material)
	{
		allayMock.simulatePlayerInteract(material);
	}

	/**
	 * Simulate the retrieval of the Allay's current items.
	 *
	 * @return A {@link List} of {@link ItemStack}s that the Allay is holding
	 */
	public @Nullable ItemStack simulateItemRetrieval()
	{
		return allayMock.simulateItemRetrieval();
	}

	/**
	 * Simulate the Allay picking up an {@link ItemStack} from the ground.
	 * If the Itemstack is not of the current Type, this will throw a {@link IllegalArgumentException}.
	 * If the Inventory is full, this will throw a {@link IllegalStateException}.
	 *
	 * @param item The {@link ItemStack} to pick up
	 */
	public void simulateItemPickup(@NotNull ItemStack item)
	{
		allayMock.simulateItemPickup(item);
	}

}
