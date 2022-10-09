package be.seeseemelk.mockbukkit.block.data;

import org.bukkit.Material;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrowsExactly;
import static org.junit.jupiter.api.Assertions.assertTrue;

class BlockDataMockTest
{

	@Test
	void matches_DoesMatch()
	{
		BlockDataMock blockData1 = new BlockDataMock(Material.STONE);
		BlockDataMock blockData2 = new BlockDataMock(Material.STONE);

		assertTrue(blockData1.matches(blockData2));
	}

	@Test
	void matches_DifferentMaterials_DoesntMatch()
	{
		BlockDataMock blockData1 = new BlockDataMock(Material.STONE);
		BlockDataMock blockData2 = new BlockDataMock(Material.DIRT);

		assertFalse(blockData1.matches(blockData2));
	}

	@Test
	void getAsString_NoData()
	{
		BlockDataMock blockData = new BlockDataMock(Material.STONE);

		assertEquals("minecraft:stone", blockData.getAsString());
	}

	@Test
	void testGetWithNonExistentKey()
	{
		BlockDataMock blockData = new BlockDataMock(Material.STONE);

		assertThrowsExactly(IllegalArgumentException.class, () -> blockData.get("non-existent-key"));
	}

	@Test
	void testHashCode()
	{
		BlockDataMock blockData = new BlockDataMock(Material.STONE);
		BlockDataMock blockData2 = new BlockDataMock(Material.STONE);
		assertEquals(blockData2.hashCode(), blockData.hashCode());

		blockData.set("key", "value");
		assertNotEquals(blockData2.hashCode(), blockData.hashCode());
	}

	@Test
	void testMatchesNotEquals()
	{
		BlockDataMock blockData = new BlockDataMock(Material.STONE);
		BlockDataMock blockData2 = new BlockDataMock(Material.STONE);
		blockData2.set("key", "value");

		assertTrue(blockData.matches(blockData2));
	}

	@Test
	void mock_NullInput_ThrowsException()
	{
		assertThrowsExactly(NullPointerException.class, () -> BlockDataMock.mock(null));
	}

}
