package be.seeseemelk.mockbukkit.block.state;

import be.seeseemelk.mockbukkit.MockBukkit;
import be.seeseemelk.mockbukkit.WorldMock;
import be.seeseemelk.mockbukkit.block.BlockMock;
import be.seeseemelk.mockbukkit.block.data.BlockDataMock;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.block.Chest;
import org.bukkit.block.data.BlockData;
import org.bukkit.block.data.type.TrapDoor;
import org.bukkit.block.data.type.WallSign;
import org.bukkit.material.MaterialData;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNotSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class BlockStateMockTest
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

	@Test
	void testEquals()
	{
		Block block1 = new BlockMock(Material.DIRT);
		Block block2 = new BlockMock(Material.DIRT);
		assertEquals(block1.getState(), block2.getState());
	}

	@Test
	void testUpdateForceChangesType()
	{
		Block block = new BlockMock(Material.CHEST);
		BlockState chest = new ChestMock(block);
		chest.setType(Material.IRON_BLOCK);

		assertTrue(chest.update(true));
		assertEquals(Material.IRON_BLOCK, block.getType());
	}

	@Test
	void setType_SetToMaterialWithBlockDataMockSubclass()
	{
		Block block = new BlockMock();
		BlockState state = block.getState();
		state.setType(Material.JUNGLE_TRAPDOOR);
		assertInstanceOf(TrapDoor.class, state.getBlockData());
	}

	@Test
	void constructor_blockDataIsSet()
	{
		Block block = new BlockMock(Material.JUNGLE_TRAPDOOR);
		BlockState state = block.getState();
		assertInstanceOf(TrapDoor.class, state.getBlockData());
	}

	@Test
	void clone_copyBlockData()
	{
		Block block = new BlockMock(Material.CHEST);
		BlockStateMock state = new ChestMock(block);
		BlockState stateCopy = state.getSnapshot();
		assertNotSame(stateCopy.getBlockData(), state.getBlockData());
		assertEquals(stateCopy.getBlockData(), state.getBlockData());
	}

	@Test
	void setBlockData_assertClone()
	{
		Block block = new BlockMock(Material.CHEST);
		BlockStateMock state = new ChestMock(block);
		BlockDataMock data = BlockDataMock.mock(Material.CHEST);
		state.setBlockData(data);
		BlockData dataCopy = state.getBlockData();
		assertNotSame(data, dataCopy);
		assertEquals(data, dataCopy);
	}

	@Test
	void equals_differentLocation()
	{
		World world = MockBukkit.getMock().addSimpleWorld("world");
		BlockState blockState1 = new BlockMock(Material.STONE, new Location(world, 1, 2, 3)).getState();
		BlockState blockState2 = new BlockMock(Material.STONE).getState();
		assertNotEquals(blockState1, blockState2);
	}

	@Test
	void equals_differentMaterial()
	{
		BlockStateMock blockState1 = (BlockStateMock) new BlockMock(Material.STONE).getState();
		BlockState blockState2 = blockState1.getSnapshot();
		assertEquals(blockState1, blockState2);
		blockState2.setType(Material.AIR);
		assertNotEquals(blockState1, blockState2);
	}

	@Test
	void equals_differentBlockData()
	{
		BlockStateMock blockState1 = (BlockStateMock) new BlockMock(Material.ACACIA_WALL_SIGN).getState();
		BlockState blockState2 = blockState1.getSnapshot();
		WallSign wallSign = (WallSign) BlockDataMock.mock(Material.ACACIA_WALL_SIGN);
		blockState2.setBlockData(wallSign);
		assertEquals(blockState1, blockState2);
		wallSign.setWaterlogged(true);
		blockState2.setBlockData(wallSign);
		assertNotEquals(blockState1, blockState2);
	}

	@Test
	void setType_checkBlockData()
	{
		BlockStateMock blockState1 = (BlockStateMock) new BlockMock(Material.ACACIA_WALL_SIGN).getState();
		blockState1.setType(Material.OAK_WALL_SIGN);
		assertEquals(Material.OAK_WALL_SIGN, blockState1.getBlockData().getMaterial());
	}

	@Test
	void setData_checkBlockData()
	{
		BlockStateMock blockState1 = (BlockStateMock) new BlockMock(Material.ACACIA_WALL_SIGN).getState();
		blockState1.setData(new MaterialData(Material.OAK_WALL_SIGN));
		assertEquals(Material.OAK_WALL_SIGN, blockState1.getBlockData().getMaterial());
	}

}
