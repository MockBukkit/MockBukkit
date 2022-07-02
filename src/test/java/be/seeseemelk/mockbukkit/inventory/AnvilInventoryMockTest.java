package be.seeseemelk.mockbukkit.inventory;

import be.seeseemelk.mockbukkit.MockBukkit;
import be.seeseemelk.mockbukkit.ServerMock;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class AnvilInventoryMockTest
{

	private ServerMock server;
	private AnvilInventoryMock inventory;

	@BeforeEach
	void setUp()
	{
		this.server = MockBukkit.mock();
		this.inventory = new AnvilInventoryMock(null);
	}

	@AfterEach
	void tearDown()
	{
		MockBukkit.unmock();
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
		assertThrows(IllegalStateException.class, () -> inventory.getRenameText());
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
