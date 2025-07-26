package org.mockbukkit.mockbukkit.inventory;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockbukkit.mockbukkit.MockBukkitExtension;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNotSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(MockBukkitExtension.class)
class ChestInventoryMockTest
{

	private ChestInventoryMock inventory;

	@BeforeEach
	void setUp()
	{
		this.inventory = new ChestInventoryMock(null, 9);
	}

	@ParameterizedTest
	@ValueSource(ints = { 9, 18, 27, 36, 45, 54 })
	void constructor_SetsSize(int size)
	{
		assertEquals(size, new ChestInventoryMock(null, size).getSize());
	}

	@Test
	void constructor_SetsSizeTooSmall()
	{
		IllegalArgumentException e = assertThrows(IllegalArgumentException.class, () -> new ChestInventoryMock(null, -1));
		assertEquals("Inventory size has to be > 0", e.getMessage());
	}

	@Test
	void constructor_SetsSizeTooBig()
	{
		IllegalArgumentException e = assertThrows(IllegalArgumentException.class, () -> new ChestInventoryMock(null, 63));
		assertEquals("Size for custom inventory must be a multiple of 9 between 9 and 54 slots (got 63)", e.getMessage());
	}

	@Test
	void constructor_SetsSizeTooNotDivisibleByNine()
	{
		IllegalArgumentException e = assertThrows(IllegalArgumentException.class, () -> new ChestInventoryMock(null, 10));
		assertEquals("Size for custom inventory must be a multiple of 9 between 9 and 54 slots (got 10)", e.getMessage());
	}

	@Test
	void testGetSnapshot()
	{
		assertNotNull(inventory.getSnapshot());

		ItemStack item = new ItemStackMock(Material.EMERALD);
		inventory.addItem(item);
		assertNotSame(inventory, inventory.getSnapshot());
		assertTrue(Arrays.stream(inventory.getContents()).anyMatch(stack -> stack != null && stack.isSimilar(item)));
	}

}
