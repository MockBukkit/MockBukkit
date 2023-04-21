package org.mockbukkit.mockbukkit.entity;

import org.mockbukkit.mockbukkit.MockBukkit;
import org.mockbukkit.mockbukkit.ServerMock;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.opentest4j.AssertionFailedError;

import java.util.Arrays;
import java.util.Objects;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class AllayMockTest
{

	private ServerMock serverMock;
	private AllayMock allayMock;

	@BeforeEach
	void setUp()
	{
		serverMock = MockBukkit.mock();
		allayMock = new AllayMock(serverMock, UUID.randomUUID());
	}

	@AfterEach
	void tearDown()
	{
		MockBukkit.unmock();
	}

	@Test
	void testCurrentItem()
	{
		assertDoesNotThrow(() -> allayMock.simulatePlayerInteract(Material.DIAMOND));

		assertDoesNotThrow(() -> allayMock.assertCurrentItem(Material.DIAMOND));
	}

	@Test
	void testItemPickUp()
	{
		assertDoesNotThrow(() -> allayMock.simulatePlayerInteract(Material.DIAMOND));

		assertDoesNotThrow(() -> allayMock.simulateItemPickup(new ItemStack(Material.DIAMOND, 2)));

		assertDoesNotThrow(() -> allayMock.assertInventoryContains(new ItemStack(Material.DIAMOND, 2)));
	}

	@Test
	void testItemPickUpToMany()
	{
		allayMock.simulatePlayerInteract(Material.DIAMOND);
		ItemStack item = new ItemStack(Material.DIAMOND, 5);

		allayMock.simulateItemPickup(new ItemStack(Material.DIAMOND, 63));
		Assertions.assertThrows(IllegalStateException.class,
				() -> allayMock.simulateItemPickup(item));

	}

	@Test
	void testItemPickUpWrongItem()
	{
		allayMock.simulatePlayerInteract(Material.DIAMOND);
		ItemStack item = new ItemStack(Material.IRON_INGOT, 1);

		Assertions.assertThrows(IllegalArgumentException.class,
				() -> allayMock.simulateItemPickup(item));
	}

	@Test
	void testItemRetrieval()
	{
		ItemStack addedItem = new ItemStack(Material.DIAMOND, 2);

		allayMock.simulatePlayerInteract(Material.DIAMOND);

		allayMock.simulateItemPickup(new ItemStack(addedItem));

		ItemStack content = allayMock.simulateItemRetrieval();

		assertEquals(0, Arrays.stream(allayMock.getInventory().getContents()).filter(Objects::nonNull).count());
		assertEquals(content, addedItem);
	}

	@Test
	void testAssertCurrentItemWithWrongItem()
	{
		allayMock.simulatePlayerInteract(Material.DIAMOND);

		Assertions.assertThrows(AssertionFailedError.class,
				() -> allayMock.assertCurrentItem(Material.IRON_INGOT));
	}

	@Test
	void testAssertInventoryContainsWithWrongItem()
	{
		ItemStack item = new ItemStack(Material.IRON_INGOT);
		assertThrows(AssertionFailedError.class, () -> allayMock.assertInventoryContains(item));
	}

}
