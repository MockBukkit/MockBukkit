package be.seeseemelk.mockbukkit.inventory;

import be.seeseemelk.mockbukkit.MockBukkit;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

class WorkbenchInventoryMockTest
{

	private WorkbenchInventoryMock workbench;

	@BeforeEach
	void setup()
	{
		MockBukkit.mock();
		workbench = new WorkbenchInventoryMock(null);
	}

	@AfterEach
	void tearDown()
	{
		MockBukkit.unmock();
	}

	@Test
	void testGetResultDefault()
	{
		assertNull(workbench.getResult());
	}

	@Test
	void testGetResult()
	{
		ItemStack item = new ItemStack(Material.OAK_BOAT);
		workbench.setResult(item);
		assertEquals(item, workbench.getResult());
	}

	@Test
	void testGetMatrixDefault()
	{
		assertNotNull(workbench.getMatrix());
	}

	@Test
	void testSetMatrix()
	{
		ItemStack[] matrix = new ItemStack[10];
		matrix[0] = new ItemStack(Material.OAK_BOAT);
		workbench.setMatrix(matrix);
		assertArrayEquals(matrix, workbench.getMatrix());
	}

}
