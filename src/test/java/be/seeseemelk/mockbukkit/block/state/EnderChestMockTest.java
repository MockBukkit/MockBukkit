package be.seeseemelk.mockbukkit.block.state;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.EnderChest;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import be.seeseemelk.mockbukkit.MockBukkit;
import be.seeseemelk.mockbukkit.block.BlockMock;

class EnderChestMockTest
{

	@BeforeEach
	void setUp() throws Exception
	{
		MockBukkit.mock();
	}

	@AfterEach
	void tearDown() throws Exception
	{
		MockBukkit.unmock();
	}

	@Test
	void testMaterialEnderChestBlockState()
	{
		Block block = new BlockMock(Material.ENDER_CHEST);
		assertTrue(block.getState() instanceof EnderChest);
	}

	@Test
	void testMaterialEnderChestMockConstructor()
	{
		assertTrue(new EnderChestMock(Material.ENDER_CHEST) instanceof EnderChest);
	}

	@Test
	void testOpen()
	{
		EnderChest chest = new EnderChestMock(Material.ENDER_CHEST);
		chest.open();
		assertTrue(chest.isOpen());
	}

	@Test
	void testClose()
	{
		EnderChest chest = new EnderChestMock(Material.ENDER_CHEST);

		assertFalse(chest.isOpen());
		chest.open();
		chest.close();
		assertFalse(chest.isOpen());
	}

	@Test
	void testIsOpen()
	{
		EnderChest chest = new EnderChestMock(Material.ENDER_CHEST);

		assertFalse(chest.isOpen());
		chest.open();
		assertTrue(chest.isOpen());
	}

}
