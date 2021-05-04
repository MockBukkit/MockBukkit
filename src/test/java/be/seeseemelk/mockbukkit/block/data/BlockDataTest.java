package be.seeseemelk.mockbukkit.block.data;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.bukkit.Material;
import org.junit.jupiter.api.Test;

class BlockDataTest
{

	@Test
	void testMatches()
	{
		BlockDataMock blockData1 = new BlockDataMock(Material.STONE);
		BlockDataMock blockData2 = new BlockDataMock(Material.STONE);
		assertTrue(blockData1.matches(blockData2));
	}
}
