package be.seeseemelk.mockbukkit.block.state;

import static org.junit.Assert.assertTrue;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.EnderChest;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import be.seeseemelk.mockbukkit.MockBukkit;
import be.seeseemelk.mockbukkit.block.BlockMock;

public class EnderChestMockTest
{

	@Before
	public void setUp() throws Exception
	{
		MockBukkit.mock();
	}

	@After
	public void tearDown() throws Exception
	{
		MockBukkit.unmock();
	}

	@Test
	public void testMaterialEnderChestBlockState()
	{
		Block block = new BlockMock(Material.ENDER_CHEST);
		assertTrue(block.getState() instanceof EnderChest);
	}

	@Test
	public void testMaterialEnderChestMockConstructor()
	{
		assertTrue(new EnderChestMock(Material.ENDER_CHEST) instanceof EnderChest);
	}

}
