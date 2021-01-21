package be.seeseemelk.mockbukkit.block.state;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.block.Chest;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import be.seeseemelk.mockbukkit.MockBukkit;
import be.seeseemelk.mockbukkit.WorldMock;
import be.seeseemelk.mockbukkit.block.BlockMock;

import static org.junit.Assert.*;

public class BlockStateMockTest
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
	public void testPlaced()
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
	public void getBlockNotPlaced()
	{
		BlockState state = new BlockStateMock(Material.SAND);
		assertFalse(state.isPlaced());
	}

	@Test(expected = IllegalStateException.class)
	public void getBlockNotPlacedException()
	{
		BlockState state = new BlockStateMock(Material.SAND);
		state.getBlock();
	}

	@Test
	public void testUpdateWrongType()
	{
		Block block = new BlockMock(Material.CHEST);
		BlockState chest = new ChestMock(block);
		block.setType(Material.IRON_BLOCK);
		assertFalse(chest.update());
	}

	@Test
	public void testUpdateNotPlacedReturnsTrue()
	{
		BlockState state = new BlockStateMock(Material.IRON_BLOCK);
		assertFalse(state.isPlaced());
		assertTrue(state.update());
	}

	@Test
	public void testUpdateForce()
	{
		Block block = new BlockMock(Material.CHEST);
		BlockState chest = new ChestMock(block);
		block.setType(Material.IRON_BLOCK);

		assertFalse(block.getState() instanceof Chest);
		assertTrue(chest.update(true));
		assertTrue(block.getState() instanceof Chest);
	}

	// Tests that different instances of the placed block state of a block, both with the same material, are equal
	@Test
	public void testEqualsAndHashCode() {
		Block block = new BlockMock(Material.STONE, new Location(null, 0, 64, 0));

		BlockState stateA = block.getState();
		BlockState stateB = block.getState();
		assertEquals(stateA, stateB);
		assertEquals(stateA.hashCode(), stateB.hashCode());
	}

	// Tests that different instances of the placed block state of a block, with different materials, are unequal
	// Also tests that block states with the same material, but with different parent blocks are unequal
	@Test
	public void testNotEquals() {
		Block blockA = new BlockMock(Material.STONE, new Location(null, 0, 64, 0));
		Block blockB = new BlockMock(Material.AIR, new Location(null, 0, 65, 0));
		Block blockC = new BlockMock(Material.STONE, new Location(null, 0, 65, 0));

		assertNotEquals(blockA.getState(), blockC.getState());
		assertNotEquals(blockB.getState(), blockC.getState());
	}

	// Tests that unplaced block states are equal if they have the same material
	@Test
	public void testEqualsAndHashCodeUnplaced() {
		BlockState stateA = new BlockStateMock(Material.STONE);
		BlockState stateB = new BlockStateMock(Material.STONE);

		assertEquals(stateA, stateB);
		assertEquals(stateA.hashCode(), stateB.hashCode());
	}

	// Tests that unplaced block states are unequal if they have the same material
	@Test
	public void testNotEqualsUnplaced() {
		assertNotEquals(new BlockStateMock(Material.STONE), new BlockStateMock(Material.AIR));
	}
}
