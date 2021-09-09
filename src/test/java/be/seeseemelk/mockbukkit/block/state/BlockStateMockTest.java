package be.seeseemelk.mockbukkit.block.state;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.block.Chest;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import be.seeseemelk.mockbukkit.MockBukkit;
import be.seeseemelk.mockbukkit.WorldMock;
import be.seeseemelk.mockbukkit.block.BlockMock;

class BlockStateMockTest
{

	@BeforeEach
	public void setUp() throws Exception
	{
		MockBukkit.mock();
	}

	@AfterEach
	public void tearDown() throws Exception
	{
		MockBukkit.unmock();
	}

	@Test
	void testPlaced()
	{
		Location location = new Location(new WorldMock(), 400, 100, 1200);
		Block block = new BlockMock(Material.DIRT, location);
		BlockState state = block.getState();

		assertNotNull(state);
		assertTrue(state.isPlaced());
		assertEquals(block, state.getBlock());

		assertEquals(block.getType(), state.getType());
		assertEquals(location, state.getLocation());
		assertEquals(block.getWorld(), state.getWorld());
		assertEquals(block.getX(), state.getX());
		assertEquals(block.getY(), state.getY());
		assertEquals(block.getZ(), state.getZ());
	}

	@Test
	void getBlockNotPlaced()
	{
		BlockState state = new BlockStateMock(Material.SAND);
		assertFalse(state.isPlaced());
	}

	@Test
	void getBlockNotPlacedException()
	{
		BlockState state = new BlockStateMock(Material.SAND);
		assertThrows(IllegalStateException.class, state::getBlock);
	}

	@Test
	void testUpdateWrongType()
	{
		Block block = new BlockMock(Material.CHEST);
		BlockState chest = new ChestMock(block);
		block.setType(Material.IRON_BLOCK);
		assertFalse(chest.update());
	}

	@Test
	void testUpdateNotPlacedReturnsTrue()
	{
		BlockState state = new BlockStateMock(Material.IRON_BLOCK);
		assertFalse(state.isPlaced());
		assertTrue(state.update());
	}

	@Test
	void testUpdateForce()
	{
		Block block = new BlockMock(Material.CHEST);
		BlockState chest = new ChestMock(block);
		block.setType(Material.IRON_BLOCK);

		assertFalse(block.getState() instanceof Chest);
		assertTrue(chest.update(true));
		assertTrue(block.getState() instanceof Chest);
	}
}
