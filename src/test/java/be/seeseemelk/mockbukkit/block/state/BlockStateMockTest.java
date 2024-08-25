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
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

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

	@ParameterizedTest
	@CsvSource({
			// Banners
			"WHITE_BANNER, be.seeseemelk.mockbukkit.block.state.BannerMock",
			"ORANGE_BANNER, be.seeseemelk.mockbukkit.block.state.BannerMock",
			"MAGENTA_BANNER, be.seeseemelk.mockbukkit.block.state.BannerMock",
			"LIGHT_BLUE_BANNER, be.seeseemelk.mockbukkit.block.state.BannerMock",
			"YELLOW_BANNER, be.seeseemelk.mockbukkit.block.state.BannerMock",
			"LIME_BANNER, be.seeseemelk.mockbukkit.block.state.BannerMock",
			"PINK_BANNER, be.seeseemelk.mockbukkit.block.state.BannerMock",
			"GRAY_BANNER, be.seeseemelk.mockbukkit.block.state.BannerMock",
			"LIGHT_GRAY_BANNER, be.seeseemelk.mockbukkit.block.state.BannerMock",
			"CYAN_BANNER, be.seeseemelk.mockbukkit.block.state.BannerMock",
			"PURPLE_BANNER, be.seeseemelk.mockbukkit.block.state.BannerMock",
			"BLUE_BANNER, be.seeseemelk.mockbukkit.block.state.BannerMock",
			"BROWN_BANNER, be.seeseemelk.mockbukkit.block.state.BannerMock",
			"GREEN_BANNER, be.seeseemelk.mockbukkit.block.state.BannerMock",
			"RED_BANNER, be.seeseemelk.mockbukkit.block.state.BannerMock",
			"BLACK_BANNER, be.seeseemelk.mockbukkit.block.state.BannerMock",
			// Beds
			"WHITE_BED, be.seeseemelk.mockbukkit.block.state.BedMock",
			"ORANGE_BED, be.seeseemelk.mockbukkit.block.state.BedMock",
			"MAGENTA_BED, be.seeseemelk.mockbukkit.block.state.BedMock",
			"LIGHT_BLUE_BED, be.seeseemelk.mockbukkit.block.state.BedMock",
			"YELLOW_BED, be.seeseemelk.mockbukkit.block.state.BedMock",
			"LIME_BED, be.seeseemelk.mockbukkit.block.state.BedMock",
			"PINK_BED, be.seeseemelk.mockbukkit.block.state.BedMock",
			"GRAY_BED, be.seeseemelk.mockbukkit.block.state.BedMock",
			"LIGHT_GRAY_BED, be.seeseemelk.mockbukkit.block.state.BedMock",
			"CYAN_BED, be.seeseemelk.mockbukkit.block.state.BedMock",
			"PURPLE_BED, be.seeseemelk.mockbukkit.block.state.BedMock",
			"BLUE_BED, be.seeseemelk.mockbukkit.block.state.BedMock",
			"BROWN_BED, be.seeseemelk.mockbukkit.block.state.BedMock",
			"GREEN_BED, be.seeseemelk.mockbukkit.block.state.BedMock",
			"RED_BED, be.seeseemelk.mockbukkit.block.state.BedMock",
			"BLACK_BED, be.seeseemelk.mockbukkit.block.state.BedMock",
			// Skulker Boxes
			"WHITE_SHULKER_BOX, be.seeseemelk.mockbukkit.block.state.ShulkerBoxMock",
			"ORANGE_SHULKER_BOX, be.seeseemelk.mockbukkit.block.state.ShulkerBoxMock",
			"MAGENTA_SHULKER_BOX, be.seeseemelk.mockbukkit.block.state.ShulkerBoxMock",
			"LIGHT_BLUE_SHULKER_BOX, be.seeseemelk.mockbukkit.block.state.ShulkerBoxMock",
			"YELLOW_SHULKER_BOX, be.seeseemelk.mockbukkit.block.state.ShulkerBoxMock",
			"LIME_SHULKER_BOX, be.seeseemelk.mockbukkit.block.state.ShulkerBoxMock",
			"PINK_SHULKER_BOX, be.seeseemelk.mockbukkit.block.state.ShulkerBoxMock",
			"GRAY_SHULKER_BOX, be.seeseemelk.mockbukkit.block.state.ShulkerBoxMock",
			"LIGHT_GRAY_SHULKER_BOX, be.seeseemelk.mockbukkit.block.state.ShulkerBoxMock",
			"CYAN_SHULKER_BOX, be.seeseemelk.mockbukkit.block.state.ShulkerBoxMock",
			"PURPLE_SHULKER_BOX, be.seeseemelk.mockbukkit.block.state.ShulkerBoxMock",
			"BLUE_SHULKER_BOX, be.seeseemelk.mockbukkit.block.state.ShulkerBoxMock",
			"BROWN_SHULKER_BOX, be.seeseemelk.mockbukkit.block.state.ShulkerBoxMock",
			"GREEN_SHULKER_BOX, be.seeseemelk.mockbukkit.block.state.ShulkerBoxMock",
			"RED_SHULKER_BOX, be.seeseemelk.mockbukkit.block.state.ShulkerBoxMock",
			"BLACK_SHULKER_BOX, be.seeseemelk.mockbukkit.block.state.ShulkerBoxMock",
			// Sign
			"OAK_SIGN, be.seeseemelk.mockbukkit.block.state.SignMock",
			"SPRUCE_SIGN, be.seeseemelk.mockbukkit.block.state.SignMock",
			"BIRCH_SIGN, be.seeseemelk.mockbukkit.block.state.SignMock",
			"JUNGLE_SIGN, be.seeseemelk.mockbukkit.block.state.SignMock",
			"ACACIA_SIGN, be.seeseemelk.mockbukkit.block.state.SignMock",
			"CHERRY_SIGN, be.seeseemelk.mockbukkit.block.state.SignMock",
			"DARK_OAK_SIGN, be.seeseemelk.mockbukkit.block.state.SignMock",
			"MANGROVE_SIGN, be.seeseemelk.mockbukkit.block.state.SignMock",
			"BAMBOO_SIGN, be.seeseemelk.mockbukkit.block.state.SignMock",
			"CRIMSON_SIGN, be.seeseemelk.mockbukkit.block.state.SignMock",
			"WARPED_SIGN, be.seeseemelk.mockbukkit.block.state.SignMock",
			// Hanging Sign (TODO: Not implemented yet, see issue https://github.com/MockBukkit/MockBukkit/issues/1088)
//			"OAK_HANGING_SIGN, be.seeseemelk.mockbukkit.block.state.HangingSignMock",
//			"SPRUCE_HANGING_SIGN, be.seeseemelk.mockbukkit.block.state.HangingSignMock",
//			"BIRCH_HANGING_SIGN, be.seeseemelk.mockbukkit.block.state.HangingSignMock",
//			"JUNGLE_HANGING_SIGN, be.seeseemelk.mockbukkit.block.state.HangingSignMock",
//			"ACACIA_HANGING_SIGN, be.seeseemelk.mockbukkit.block.state.HangingSignMock",
//			"CHERRY_HANGING_SIGN, be.seeseemelk.mockbukkit.block.state.HangingSignMock",
//			"DARK_OAK_HANGING_SIGN, be.seeseemelk.mockbukkit.block.state.HangingSignMock",
//			"MANGROVE_HANGING_SIGN, be.seeseemelk.mockbukkit.block.state.HangingSignMock",
//			"BAMBOO_HANGING_SIGN, be.seeseemelk.mockbukkit.block.state.HangingSignMock",
//			"CRIMSON_HANGING_SIGN, be.seeseemelk.mockbukkit.block.state.HangingSignMock",
//			"WARPED_HANGING_SIGN, be.seeseemelk.mockbukkit.block.state.HangingSignMock",
			// Wall Sign (TODO: Not implemented yet, see issue https://github.com/MockBukkit/MockBukkit/issues/1088)
//			"OAK_WALL_SIGN, be.seeseemelk.mockbukkit.block.state.WallSignMock",
//			"SPRUCE_WALL_SIGN, be.seeseemelk.mockbukkit.block.state.WallSignMock",
//			"BIRCH_WALL_SIGN, be.seeseemelk.mockbukkit.block.state.WallSignMock",
//			"ACACIA_WALL_SIGN, be.seeseemelk.mockbukkit.block.state.WallSignMock",
//			"CHERRY_WALL_SIGN, be.seeseemelk.mockbukkit.block.state.WallSignMock",
//			"JUNGLE_WALL_SIGN, be.seeseemelk.mockbukkit.block.state.WallSignMock",
//			"DARK_OAK_WALL_SIGN, be.seeseemelk.mockbukkit.block.state.WallSignMock",
//			"MANGROVE_WALL_SIGN, be.seeseemelk.mockbukkit.block.state.WallSignMock",
//			"BAMBOO_WALL_SIGN, be.seeseemelk.mockbukkit.block.state.WallSignMock",
			// Wall Hanging Sign (TODO: Not implemented yet, see issue https://github.com/MockBukkit/MockBukkit/issues/1088)
//			"OAK_WALL_HANGING_SIGN, be.seeseemelk.mockbukkit.block.state.WallHangingSignMock",
//			"SPRUCE_WALL_HANGING_SIGN, be.seeseemelk.mockbukkit.block.state.WallHangingSignMock",
//			"BIRCH_WALL_HANGING_SIGN, be.seeseemelk.mockbukkit.block.state.WallHangingSignMock",
//			"ACACIA_WALL_HANGING_SIGN, be.seeseemelk.mockbukkit.block.state.WallHangingSignMock",
//			"CHERRY_WALL_HANGING_SIGN, be.seeseemelk.mockbukkit.block.state.WallHangingSignMock",
//			"JUNGLE_WALL_HANGING_SIGN, be.seeseemelk.mockbukkit.block.state.WallHangingSignMock",
//			"DARK_OAK_WALL_HANGING_SIGN, be.seeseemelk.mockbukkit.block.state.WallHangingSignMock",
//			"MANGROVE_WALL_HANGING_SIGN, be.seeseemelk.mockbukkit.block.state.WallHangingSignMock",
//			"CRIMSON_WALL_HANGING_SIGN, be.seeseemelk.mockbukkit.block.state.WallHangingSignMock",
//			"WARPED_WALL_HANGING_SIGN, be.seeseemelk.mockbukkit.block.state.WallHangingSignMock",
//			"BAMBOO_WALL_HANGING_SIGN, be.seeseemelk.mockbukkit.block.state.WallHangingSignMock",
			// Skulls
			"SKELETON_SKULL, be.seeseemelk.mockbukkit.block.state.SkullMock",
			"SKELETON_WALL_SKULL, be.seeseemelk.mockbukkit.block.state.SkullMock",
			"WITHER_SKELETON_SKULL, be.seeseemelk.mockbukkit.block.state.SkullMock",
			"WITHER_SKELETON_WALL_SKULL, be.seeseemelk.mockbukkit.block.state.SkullMock",
			// Other blocks
			"STRUCTURE_BLOCK, be.seeseemelk.mockbukkit.block.state.StructureMock",
			"SMOKER, be.seeseemelk.mockbukkit.block.state.SmokerMock",
			"END_GATEWAY, be.seeseemelk.mockbukkit.block.state.EndGatewayMock",
			"SCULK_CATALYST, be.seeseemelk.mockbukkit.block.state.SculkCatalystMock",
			"SCULK_SHRIEKER, be.seeseemelk.mockbukkit.block.state.SculkShriekerMock",
			"SCULK_SENSOR, be.seeseemelk.mockbukkit.block.state.SculkSensorMock",
			"BEACON, be.seeseemelk.mockbukkit.block.state.BeaconMock",
			"BEEHIVE, be.seeseemelk.mockbukkit.block.state.BeehiveMock",
			"BREWING_STAND, be.seeseemelk.mockbukkit.block.state.BrewingStandMock",
			"BLAST_FURNACE, be.seeseemelk.mockbukkit.block.state.BlastFurnaceMock",
			"COMPARATOR, be.seeseemelk.mockbukkit.block.state.ComparatorMock",
			"ENCHANTING_TABLE, be.seeseemelk.mockbukkit.block.state.EnchantingTableMock",
			"JIGSAW, be.seeseemelk.mockbukkit.block.state.JigsawMock",
			"JUKEBOX, be.seeseemelk.mockbukkit.block.state.JukeboxMock",
			"SPAWNER, be.seeseemelk.mockbukkit.block.state.CreatureSpawnerMock",
			"DAYLIGHT_DETECTOR, be.seeseemelk.mockbukkit.block.state.DaylightDetectorMock",
			"COMMAND_BLOCK, be.seeseemelk.mockbukkit.block.state.CommandBlockMock",
			"CHAIN_COMMAND_BLOCK, be.seeseemelk.mockbukkit.block.state.CommandBlockMock",
			"REPEATING_COMMAND_BLOCK, be.seeseemelk.mockbukkit.block.state.CommandBlockMock",
			"CAMPFIRE, be.seeseemelk.mockbukkit.block.state.CampfireMock",
			"SOUL_CAMPFIRE, be.seeseemelk.mockbukkit.block.state.CampfireMock",
			"BELL, be.seeseemelk.mockbukkit.block.state.BellMock",
			"LECTERN, be.seeseemelk.mockbukkit.block.state.LecternMock",
			"HOPPER, be.seeseemelk.mockbukkit.block.state.HopperMock",
			"BARREL, be.seeseemelk.mockbukkit.block.state.BarrelMock",
			"DISPENSER, be.seeseemelk.mockbukkit.block.state.DispenserMock",
			"DROPPER, be.seeseemelk.mockbukkit.block.state.DropperMock",
			"CHEST, be.seeseemelk.mockbukkit.block.state.ChestMock",
			"TRAPPED_CHEST, be.seeseemelk.mockbukkit.block.state.ChestMock",
			"ENDER_CHEST, be.seeseemelk.mockbukkit.block.state.EnderChestMock"
	})
	void mockState(Material bedMaterial, Class<?> expectedClass)
	{
		BlockData bed = BlockDataMock.mock(bedMaterial);
		BlockState actual = bed.createBlockState();
		assertNotNull(actual);
		assertInstanceOf(expectedClass, actual);
	}

}
