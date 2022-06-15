package be.seeseemelk.mockbukkit.entity;

import be.seeseemelk.mockbukkit.ServerMock;
import com.google.common.base.Preconditions;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Allay;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.fail;

public class AllayMock extends CreatureMock implements Allay
{

	private final Inventory inventory;
	private Material currentItem;

	protected AllayMock(ServerMock server, UUID uuid)
	{
		super(server, uuid);
		this.inventory = Bukkit.createInventory(null, 9);
	}

	/**
	 * Simulates the Interaction of a Player with the Allay to set it's current item.
	 *
	 * @param material The {@link Material} of the Item the Allay should collect
	 */
	public void simulatePlayerInteract(@NotNull Material material)
	{
		Preconditions.checkNotNull(material, "Material cannot be null");
		this.currentItem = material;
	}

	/**
	 * Simulate the retrieval of the Allay's current items.
	 *
	 * @return A {@link List} of {@link ItemStack}s that the Allay is holding
	 */
	public ItemStack simulateItemRetrieval()
	{
		ItemStack item = this.inventory.getContents()[0];
		this.inventory.clear();

		return item;
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
		Preconditions.checkNotNull(item, "ItemStack cannot be null");
		if (item.getType() != this.currentItem)
		{
			throw new IllegalArgumentException("Item is not the same type as the Allay is currently holding");
		}
		inventory.addItem(item);
		if (Arrays.stream(inventory.getContents())
				.filter(Objects::nonNull)
				.count() > 1)
		{
			throw new IllegalStateException("Allay cannot hold more than 1 ItemStack");
		}
	}

	/**
	 * Asserts that the Allay uses the given {@link Material} to pick up an {@link ItemStack} from the ground.
	 *
	 * @param item The {@link Material} to pick up
	 */
	public void assertCurrentItem(@NotNull Material item)
	{
		assertCurrentItem(item, "Allay is not holding the correct item");
	}

	/**
	 * Asserts that the Allay uses the given {@link Material} to pick up an {@link ItemStack} from the ground.
	 *
	 * @param item    The {@link Material} to pick up
	 * @param message The message to display if the assertion fails
	 */
	public void assertCurrentItem(@NotNull Material item, String message)
	{
		if (item != this.currentItem)
		{
			fail(message);
		}
	}

	@Override
	public @NotNull Inventory getInventory()
	{
		return this.inventory;
	}

	public void assertInventoryContains(ItemStack item)
	{
		assertInventoryContains(item, "Inventory does not contain the given ItemStack");
	}

	private void assertInventoryContains(ItemStack item, String s)
	{
		if (!inventory.contains(item))
		{
			fail(s);
		}
	}

}
