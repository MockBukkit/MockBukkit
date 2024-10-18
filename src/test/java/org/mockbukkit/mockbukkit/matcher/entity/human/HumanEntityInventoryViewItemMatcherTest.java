package org.mockbukkit.mockbukkit.matcher.entity.human;

import org.bukkit.Material;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.ItemStack;
import org.hamcrest.Matcher;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockbukkit.mockbukkit.MockBukkitExtension;
import org.mockbukkit.mockbukkit.MockBukkitInject;
import org.mockbukkit.mockbukkit.ServerMock;
import org.mockbukkit.mockbukkit.entity.HumanEntityMock;
import org.mockbukkit.mockbukkit.inventory.InventoryMock;
import org.mockbukkit.testutils.matcher.AbstractMatcherTest;

import static org.mockbukkit.mockbukkit.matcher.entity.human.HumanEntityInventoryViewItemMatcher.hasItemInInventoryView;

@ExtendWith(MockBukkitExtension.class)
class HumanEntityInventoryViewItemMatcherTest extends AbstractMatcherTest
{

	@MockBukkitInject
	ServerMock serverMock;
	private HumanEntityMock human;
	private InventoryMock inventoryMock;

	@BeforeEach
	void setUp()
	{
		this.human = serverMock.addPlayer();
		this.inventoryMock = serverMock.createInventory(human, InventoryType.BARREL);
	}

	@Test
	void itemInInventory_matches()
	{
		human.openInventory(inventoryMock);
		inventoryMock.addItem(new ItemStack(Material.DIAMOND));
		assertMatches(hasItemInInventoryView(Material.DIAMOND), human);
	}

	@Test
	void itemInInventory_noMatch()
	{
		human.openInventory(inventoryMock);
		inventoryMock.addItem(new ItemStack(Material.DIRT));
		assertDoesNotMatch(hasItemInInventoryView(Material.DIAMOND_BLOCK), human);
	}

	@Test
	void itemInInventory_noView()
	{
		assertDoesNotMatch(hasItemInInventoryView(Material.BEACON), human);
	}

	@Test
	void nullSafe()
	{
		testIsNullSafe();
	}

	@Test
	void unknownTypeSafe()
	{
		testCopesWithUnknownTypes();
	}

	@Override
	protected Matcher<?> createMatcher()
	{
		return hasItemInInventoryView(Material.APPLE);
	}

}
