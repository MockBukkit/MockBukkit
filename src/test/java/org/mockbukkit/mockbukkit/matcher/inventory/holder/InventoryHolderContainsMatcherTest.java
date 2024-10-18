package org.mockbukkit.mockbukkit.matcher.inventory.holder;

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

import static org.mockbukkit.mockbukkit.matcher.inventory.holder.InventoryHolderContainsMatcher.hasItemInInventory;

@ExtendWith(MockBukkitExtension.class)
class InventoryHolderContainsMatcherTest extends AbstractMatcherTest
{
	@MockBukkitInject
	private ServerMock server;
	private AllayMock allay;
	private ItemStack stack = new ItemStack(Material.STONE);

	@BeforeEach
	void setUp()
	{
		this.allay = new AllayMock(server, UUID.randomUUID());
		allay.simulatePlayerInteract(Material.STONE);
		allay.simulateItemPickup(stack);
	}

	@Test
	void testMatches()
	{
		assertMatches(hasItemInInventory(stack), allay);
	}

	@Test
	void testDoesNotMatch()
	{
		assertDoesNotMatch(hasItemInInventory(new ItemStack(Material.DIAMOND)), allay);
	}

	@Test
	void testDoesNotMatchNull()
	{
		assertNullSafe(createMatcher());
	}

	@Override
	protected Matcher<?> createMatcher()
	{
		return hasItemInInventory(stack);
	}

}
