package org.mockbukkit.mockbukkit.matcher.entity.human;

import org.bukkit.event.inventory.InventoryType;
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

import static org.mockbukkit.mockbukkit.matcher.entity.human.HumanEntityInventoryViewTypeMatcher.hasInventoryViewType;

@ExtendWith(MockBukkitExtension.class)
class HumanEntityInventoryViewTypeMatcherTest extends AbstractMatcherTest
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
	void inventoryViewType_matches()
	{
		human.openInventory(inventoryMock);
		assertMatches(hasInventoryViewType(InventoryType.BARREL), human);
	}

	@Test
	void inventoryViewType_missMatch()
	{
		human.openInventory(inventoryMock);
		assertDoesNotMatch(hasInventoryViewType(InventoryType.LOOM), human);
	}

	@Test
	void inventoryViewType_noneSelected()
	{
		assertDoesNotMatch(hasInventoryViewType(InventoryType.CREATIVE), human);
	}

	@Test
	void isNullSafe()
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
		return hasInventoryViewType(InventoryType.CRAFTING);
	}

}
