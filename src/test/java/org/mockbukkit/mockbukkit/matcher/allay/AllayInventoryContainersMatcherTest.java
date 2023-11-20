package org.mockbukkit.mockbukkit.matcher.allay;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.hamcrest.Matcher;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockbukkit.mockbukkit.MockBukkitExtension;
import org.mockbukkit.mockbukkit.MockBukkitInject;
import org.mockbukkit.mockbukkit.ServerMock;
import org.mockbukkit.mockbukkit.entity.AllayMock;
import org.mockbukkit.testutils.matcher.AbstractMatcherTest;

import java.util.UUID;

import static org.mockbukkit.mockbukkit.matcher.allay.AllayInventoryContainsMatcher.inventoryContains;

@ExtendWith(MockBukkitExtension.class)
class AllayInventoryContainersMatcherTest extends AbstractMatcherTest
{

	@MockBukkitInject
	private ServerMock server;
	private AllayMock allay;
	private ItemStack stack = new ItemStack(Material.STONE);

	@BeforeEach
	void setUp()
	{
		allay = new AllayMock(server, UUID.randomUUID());
		allay.simulatePlayerInteract(Material.STONE);
		allay.simulateItemPickup(stack);
	}

	@Override
	protected Matcher<?> createMatcher()
	{
		return inventoryContains(stack);
	}

	@Test
	void testMatches()
	{
		assertMatches(inventoryContains(stack), allay);
		assertMismatchDescription("doesn't have Itemstack \"ItemStack{DIRT x 1}\" in Inventory",
				inventoryContains(new ItemStack(Material.DIRT)), allay);
	}

	@Test
	void testDoesNotMatch()
	{
		assertDoesNotMatch(inventoryContains(new ItemStack(Material.WATER)), allay);
	}

	@Test
	void testDoesNotMatchNull()
	{
		assertNullSafe(createMatcher());
	}

	@Test
	void testHasReadableDescription()
	{
		assertDescription("Should have Itemstack in inventory", inventoryContains(stack));
	}

	@Test
	void testDoesntMatchUnknownType()
	{
		assertUnknownTypeSafe(createMatcher());
	}

}
