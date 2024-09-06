package org.mockbukkit.mockbukkit.inventory;

import org.mockbukkit.mockbukkit.MockBukkitExtension;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockBukkitExtension.class)
class AnvilInventoryMockTest
{

	private AnvilInventoryMock inventory;

	@BeforeEach
	void setUp()
	{
		this.inventory = new AnvilInventoryMock(null);
	}

	@Test
	void testRepairCostAmountDefault()
	{
		assertEquals(0, inventory.getRepairCostAmount());
	}

	@Test
	void testRepairCostAmountSet()
	{
		inventory.setRepairCostAmount(1);
		assertEquals(1, inventory.getRepairCostAmount());
	}

	@Test
	void testRepairCostAmountSetNegative()
	{
		assertThrows(IllegalArgumentException.class, () -> inventory.setRepairCostAmount(-1));
	}

	@Test
	void testGetRenameTextNotSet()
	{
		assertNull(inventory.getRenameText());
	}

	@Test
	void testSetRenameText()
	{
		inventory.setRenameText("test");
		assertEquals("test", inventory.getRenameText());
	}

	@Test
	void testGetRepairCostDefault()
	{
		assertEquals(0, inventory.getRepairCost());
	}

	@Test
	void testSetRepairCost()
	{
		inventory.setRepairCost(1);
		assertEquals(1, inventory.getRepairCost());
	}

	@Test
	void testSetRepairCostNegative()
	{
		assertThrows(IllegalArgumentException.class, () -> inventory.setRepairCost(-1));
	}

	@Test
	void testGetMaxRepairCostDefault()
	{
		assertEquals(40, inventory.getMaximumRepairCost());
	}

	@Test
	void testSetMaxRepairCost()
	{
		inventory.setMaximumRepairCost(1);
		assertEquals(1, inventory.getMaximumRepairCost());
	}

	@Test
	void testSetMaxRepairCostNegative()
	{
		assertThrows(IllegalArgumentException.class, () -> inventory.setMaximumRepairCost(-1));
	}

}
