package org.mockbukkit.mockbukkit.matcher.inventory;

import org.bukkit.Material;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.hamcrest.Matcher;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockbukkit.mockbukkit.MockBukkitExtension;
import org.mockbukkit.mockbukkit.MockBukkitInject;
import org.mockbukkit.mockbukkit.ServerMock;
import org.mockbukkit.mockbukkit.inventory.InventoryMock;
import org.mockbukkit.testutils.matcher.AbstractMatcherTest;

import static org.mockbukkit.mockbukkit.matcher.inventory.InventoryItemAmountMatcher.containsAtLeast;

@ExtendWith(MockBukkitExtension.class)
class InventoryItemAmountMatcherTest extends AbstractMatcherTest
{

	@MockBukkitInject
	private ServerMock serverMock;
	private InventoryHolder inventoryHolder;
	private InventoryMock inventory;
	private ItemStack item;

	@BeforeEach
	void setUp()
	{
		this.inventoryHolder = serverMock.addPlayer();
		this.inventory = new InventoryMock(inventoryHolder, InventoryType.ANVIL);
		this.item = new ItemStack(Material.DIRT);
	}

	@Test
	void containsAtLeast_matches()
	{
		inventory.addItem(item);
		assertMatches(containsAtLeast(item, 1), inventory);
	}

	@Test
	void containsAtLeast_wrongItem()
	{
		inventory.addItem(new ItemStack(Material.APPLE));
		assertDoesNotMatch(containsAtLeast(item, 1), inventory);
	}

	@Test
	void containsAtLeast_emptyInventory()
	{
		assertDoesNotMatch(containsAtLeast(item, 1), inventory);
	}
	@Test
	void containsAtLeast_matchesSpecifiedAmount()
	{
		inventory.addItem(new ItemStack(Material.DIRT,2));
		inventory.addItem(new ItemStack(Material.DIRT, 3));
		assertMatches(containsAtLeast(item, 5), inventory);
	}

	@Test
	void nullSafe(){
		testIsNullSafe();
	}

	@Override
	protected Matcher<?> createMatcher()
	{
		return containsAtLeast(item, 1);
	}

}
