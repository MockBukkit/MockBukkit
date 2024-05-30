package be.seeseemelk.mockbukkit.block;

import be.seeseemelk.mockbukkit.ChunkCoordinate;
import be.seeseemelk.mockbukkit.ChunkMock;
import be.seeseemelk.mockbukkit.Coordinate;
import be.seeseemelk.mockbukkit.MockBukkit;
import be.seeseemelk.mockbukkit.WorldMock;
import be.seeseemelk.mockbukkit.block.data.BlockDataMock;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Biome;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.data.BlockData;
import org.bukkit.block.data.type.Slab;
import org.bukkit.block.data.type.TrapDoor;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertThrowsExactly;
import static org.junit.jupiter.api.Assertions.assertTrue;

class BlockMockTest
{

	private BlockMock block;
	private Location location;

	@BeforeEach
	void setUp()
	{
		MockBukkit.mock();
		World world = new WorldMock();
		location = new Location(world, 120, 60, 120);
		block = new BlockMock(location);
	}

	@AfterEach
	void teardown()
	{
		MockBukkit.unmock();
	}

	@Test
	void constructor_NullMaterial_ThrowsException()
	{
		assertThrowsExactly(NullPointerException.class, () -> new BlockMock((Material) null));
	}

	@Test
	void constructorWithLocation_NullMaterial_ThrowsException()
	{
		assertThrowsExactly(NullPointerException.class, () -> new BlockMock(null, null));
	}

	@Test
	void constructor_MaterialWithBlockDataMockSubclass_CorrectBlockDataType()
	{
		BlockMock slabBlock = new BlockMock(Material.ACACIA_SLAB, location);
		BlockData blockData = slabBlock.getBlockData();
		assertInstanceOf(Slab.class, blockData);
	}

	@Test
	void constructor_MaterialNotABLock()
	{
		assertThrows(IllegalArgumentException.class, () -> new BlockMock(Material.IRON_AXE));
	}

	@Test
	void getType_Default_Air()
	{
		assertEquals(Material.AIR, block.getType());
	}

	@Test
	void setType_NullParameter_ThrowsException()
	{
		assertThrowsExactly(NullPointerException.class, () -> block.setType(null));
	}

	@Test
	void setType_Stone_Set()
	{
		block.setType(Material.STONE);
		assertEquals(Material.STONE, block.getType());
	}

	@Test
	void setType_SetToMaterialWithBlockDataMockSubclass()
	{
		block.setType(Material.JUNGLE_TRAPDOOR);
		assertInstanceOf(TrapDoor.class, block.getBlockData());
	}

	@Test
	void getLightLevel()
	{
		block.setLightFromSky((byte) 15);
		assertEquals(15, block.getLightLevel());
		block.setLightFromSky((byte) 5);
		assertEquals(5, block.getLightLevel());
		block.setLightFromBlocks((byte) 15);
		assertEquals(15, block.getLightLevel());
	}

	@Test
	void getLightFromSky()
	{
		assertEquals(15, block.getLightFromSky());
		block.setLightFromSky((byte) 0);
		assertEquals(0, block.getLightFromSky());
	}

	@ParameterizedTest
	@ValueSource(bytes =
	{ 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15 })
	void setLightFromSky_GivenValidValues(byte lightLevel)
	{
		assertDoesNotThrow(() -> block.setLightFromSky(lightLevel));
	}

	@ParameterizedTest
	@ValueSource(bytes =
	{ -1, 16 })
	void setLightFromSky_GivenInvalidValues(byte invalidLightLevel)
	{
		IllegalArgumentException e = assertThrows(IllegalArgumentException.class,
				() -> block.setLightFromSky(invalidLightLevel));

		assertEquals("Light level should be between 0 and 15.", e.getMessage());
	}

	@Test
	void getLightFromBlocks()
	{
		assertEquals(0, block.getLightFromBlocks());
		block.setLightFromBlocks((byte) 15);
		assertEquals(15, block.getLightFromBlocks());
	}

	@ParameterizedTest
	@ValueSource(bytes =
	{ 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15 })
	void setLightFromBlocks_GivenValidValues(byte lightLevel)
	{
		assertDoesNotThrow(() -> block.setLightFromBlocks(lightLevel));
	}

	@ParameterizedTest
	@ValueSource(bytes =
	{ -1, 16 })
	void setLightFromBlocks_GivenInvalidValues(byte invalidLightLevel)
	{
		IllegalArgumentException e = assertThrows(IllegalArgumentException.class,
				() -> block.setLightFromBlocks(invalidLightLevel));

		assertEquals("Light level should be between 0 and 15.", e.getMessage());
	}

	@Test
	void getLocation_Default_Null()
	{
		assertNull(new BlockMock().getLocation());
	}

	@Test
	void getLocation_CustomLocation_LocationSet()
	{
		WorldMock world = new WorldMock();
		Location location = new Location(world, 5, 2, 1);
		block = new BlockMock(Material.AIR, location);
		assertEquals(location, block.getLocation());
	}

	@Test
	void getLocation_CustomLocation_ApplyToProvided()
	{
		WorldMock world = new WorldMock();
		Location location = new Location(world, 5, 2, 1);
		block = new BlockMock(Material.AIR, location);
		Location location2 = new Location(null, 0, 0, 0);
		block.getLocation(location2);
		assertEquals(block.getLocation(), location2);
	}

	@Test
	void getChunk_LocalBlock_Matches()
	{
		WorldMock world = new WorldMock();
		Coordinate coordinate = new Coordinate(-10, 5, 30);
		Block worldBlock = world.getBlockAt(coordinate);
		Block chunkBlock = ((ChunkMock) worldBlock.getChunk()).getBlock(coordinate.toLocalCoordinate());
		assertEquals(worldBlock, chunkBlock);
	}

	@Test
	void getChunk_LocalBlock_NegativeY()
	{
		WorldMock world = new WorldMock(Material.STONE, -64, 320, 70);
		Coordinate coordinate = new Coordinate(55, -40, 100);
		Block worldBlock = world.getBlockAt(coordinate);
		ChunkCoordinate chunkCoordinate = coordinate.toChunkCoordinate();
		Block chunkBlock = world.getChunkAt(chunkCoordinate).getBlock(coordinate.toLocalCoordinate());
		assertEquals(worldBlock, chunkBlock);
	}

	@Test
	void getWorld_AnyWorld_WorldReturned()
	{
		WorldMock world = new WorldMock();
		block = new BlockMock(new Location(world, 0, 0, 0));
		assertEquals(world, block.getWorld());
	}

	@Test
	void getXYZ_FromLocation_XYZReturned()
	{
		block = new BlockMock(new Location(null, 1, 2, 3));
		assertEquals(1, block.getX());
		assertEquals(2, block.getY());
		assertEquals(3, block.getZ());
	}

	@Test
	void assertType_CorrectType_DoesNotFail()
	{
		block.setType(Material.STONE);
		block.assertType(Material.STONE);
		block.setType(Material.DIRT);
		block.assertType(Material.DIRT);
	}

	@Test
	void assertType_IncorrectType_Fails()
	{
		block.setType(Material.STONE);
		assertThrows(AssertionError.class, () -> block.assertType(Material.DIRT));
	}

	@Test
	void testGetRelativeBlockFace()
	{
		Block relative = block.getRelative(BlockFace.UP);
		assertEquals(block.getX(), relative.getX());
		assertEquals(block.getY() + 1, relative.getY());
		assertEquals(block.getZ(), relative.getZ());
	}

	@Test
	void testGetRelativeBlockFaceAndDistance()
	{
		Block relative = block.getRelative(BlockFace.UP, 4);
		assertEquals(block.getX(), relative.getX());
		assertEquals(block.getY() + 4, relative.getY());
		assertEquals(block.getZ(), relative.getZ());
	}

	@Test
	void testGetRelativeCoordinates()
	{
		Block relative = block.getRelative(2, 6, 0);
		assertEquals(block.getX() + 2, relative.getX());
		assertEquals(block.getY() + 6, relative.getY());
		assertEquals(block.getZ(), relative.getZ());
	}

	@Test
	void testGetBlockData()
	{
		assertEquals(block.getType(), block.getBlockData().getMaterial());
	}

	@Test
	void testSetBlockData()
	{
		BlockDataMock blockData = new BlockDataMock(Material.DIRT);
		Material oldType = block.getType();

		block.setBlockData(blockData);
		assertEquals(blockData, block.getBlockData());
		assertEquals(blockData.getMaterial(), block.getType());
		block.setType(oldType);
	}

	@Test
	void testWaterIsLiquid()
	{
		block.setType(Material.WATER);
		assertTrue(block.isLiquid());
	}

	@Test
	void testLavaIsLiquid()
	{
		block.setType(Material.LAVA);
		assertTrue(block.isLiquid());
	}

	@Test
	void testBubbleColumnIsLiquid()
	{
		block.setType(Material.BUBBLE_COLUMN);
		assertTrue(block.isLiquid());
	}

	@Test
	void testAirIsLiquid()
	{
		block.setType(Material.AIR);
		assertTrue(block.isEmpty());
	}

	@Test
	void testStoneIsNotLiquid()
	{
		block.setType(Material.STONE);
		assertFalse(block.isLiquid());
	}

	@Test
	void testStoneIsNotEmpty()
	{
		block.setType(Material.STONE);
		assertFalse(block.isEmpty());
	}

	@Test
	void testBreakNaturally()
	{
		block.setType(Material.STONE);
		block.breakNaturally();
		assertTrue(block.isEmpty());
	}

	@Test
	void getBiome()
	{
		Biome worldBiome = block.getWorld().getBiome(block.getLocation());
		assertNotNull(worldBiome);
		Biome blockBiome = block.getBiome();
		assertNotNull(blockBiome);
		assertEquals(worldBiome, blockBiome);
	}

	@Test
	void setBiome()
	{
		block.setBiome(Biome.DESERT);
		assertEquals(Biome.DESERT, block.getBiome());
	}

	@Test
	void testGetFace_Valid()
	{
		Block b = block.getRelative(BlockFace.NORTH);
		assertEquals(BlockFace.NORTH, block.getFace(b));
	}

	@Test
	void testGetFace_Invalid()
	{
		Block b = block.getRelative(BlockFace.NORTH, 2);
		assertNull(block.getFace(b));
	}

	@Test
	void isSolid_Solid()
	{
		Block block = new BlockMock(Material.ANCIENT_DEBRIS);
		assertTrue(block.isSolid());
	}

	@Test
	void isSolid_NonSolid()
	{
		Block block = new BlockMock(Material.AIR);
		assertFalse(block.isSolid());
	}

}
