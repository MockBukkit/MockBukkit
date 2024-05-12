package be.seeseemelk.mockbukkit.inventory;

import be.seeseemelk.mockbukkit.MockBukkit;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

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
		ItemStack[] matrix = new ItemStack[9];
		matrix[4] = new ItemStack(Material.OAK_BOAT);

		workbench.setMatrix(matrix);
		assertArrayEquals(matrix, workbench.getMatrix());
	}

	@Test
	void testSetContents()
	{
		ItemStack[] contents = new ItemStack[10];
		contents[0] = new ItemStack(Material.STICK);
		contents[5] = new ItemStack(Material.OAK_PLANKS);
		contents[8] = new ItemStack(Material.OAK_PLANKS);

		workbench.setContents(contents);
		assertArrayEquals(contents, workbench.getContents());
		assertEquals(contents[0], workbench.getResult());
		assertArrayEquals(Arrays.copyOfRange(contents, 1, contents.length), workbench.getMatrix());
	}

	@Test
	void testGetResultItem()
	{
		ItemStack result = new ItemStack(Material.STICK);
		workbench.setResult(result);

		assertEquals(result, workbench.getItem(0));
	}

	@Test
	void testSetResultItem()
	{
		ItemStack result = new ItemStack(Material.STICK);
		workbench.setItem(0, result);

		assertEquals(result, workbench.getResult());
	}

	@Test
	void testGetMatrixItem()
	{
		ItemStack[] matrix = new ItemStack[9];
		matrix[4] = new ItemStack(Material.OAK_BOAT);

		workbench.setMatrix(matrix);

		assertEquals(matrix[4], workbench.getItem(5));
	}

	@Test
	void testSetMatrixItem()
	{
		ItemStack[] matrix = new ItemStack[9];
		matrix[4] = new ItemStack(Material.OAK_BOAT);

		workbench.setItem(5, matrix[4]);
		assertArrayEquals(matrix, workbench.getMatrix());
	}

	@Test
	void testGetSize()
	{
		assertEquals(10, workbench.getSize());
	}

	@Test
	void testSetMatrix_underMaxSize()
	{
		ItemStack[] matrix = new ItemStack[5];
		matrix[4] = new ItemStack(Material.OAK_BOAT);
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
		ItemStack[] matrix = new ItemStack[9];
		matrix[4] = new ItemStack(Material.OAK_BOAT);

		workbench.setMatrix(matrix);

		assertArrayEquals(matrix, workbench.getMatrix());
	}

}
