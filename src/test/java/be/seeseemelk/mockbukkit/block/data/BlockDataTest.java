package be.seeseemelk.mockbukkit.block.data;

import org.bukkit.Material;
import org.junit.Assert;
import org.junit.Test;

public class BlockDataTest
{

	@Test
	public void testMatches()
	{
		BlockDataMock blockData1 = new BlockDataMock(Material.STONE);
		BlockDataMock blockData2 = new BlockDataMock(Material.STONE);
		Assert.assertTrue(blockData1.matches(blockData2));
	}
}
