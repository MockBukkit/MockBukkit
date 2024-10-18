package org.mockbukkit.mockbukkit.inventory;

import org.mockbukkit.mockbukkit.MockBukkitExtension;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockBukkitExtension.class)
class WorkbenchInventoryMockTest
{

	private WorkbenchInventoryMock workbench;

	@BeforeEach
	void setup()
	{
		workbench = new WorkbenchInventoryMock(null);
	}

	@Test
	void testGetResultDefault()
	{
		assertNull(workbench.getResult());
	}

	@Test
	void testGetResult()
	{
		ItemStack item = new ItemStackMock(Material.OAK_BOAT);
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
		ItemStack[] matrix = new ItemStackMock[10];
		matrix[4] = new ItemStackMock(Material.OAK_BOAT);

		workbench.setMatrix(matrix);

		assertArrayEquals(matrix, workbench.getMatrix());
	}

	@Test
	void testSetMatrix_underMaxSize()
	{
		ItemStack[] matrix = new ItemStack[5];
		matrix[4] = new ItemStackMock(Material.OAK_BOAT);
		assertDoesNotThrow(() -> workbench.setMatrix(matrix));
	}

	@Test
	void testSetMatrix_overMaxSize()
	{
		assertThrows(IllegalArgumentException.class, () -> workbench.setMatrix(new ItemStack[12]));
	}

	@Test
	void testSetMatrix_null()
	{
		assertThrows(NullPointerException.class, () -> workbench.setMatrix(null));
	}

	@Test
	void testSetMatrix_SetsItems()
	{
		ItemStack[] matrix = new ItemStack[10];
		matrix[4] = new ItemStackMock(Material.OAK_BOAT);

		workbench.setMatrix(matrix);

		assertArrayEquals(matrix, workbench.getContents());
	}

}
