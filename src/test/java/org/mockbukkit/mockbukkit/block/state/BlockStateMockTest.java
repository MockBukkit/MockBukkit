package org.mockbukkit.mockbukkit.block.state;

import org.mockbukkit.mockbukkit.MockBukkit;
import org.mockbukkit.mockbukkit.world.WorldMock;
import org.mockbukkit.mockbukkit.block.BlockMock;
import org.mockbukkit.mockbukkit.block.data.BlockDataMock;
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
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

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
		BlockState chest = new ChestStateMock(block);
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
		BlockState chest = new ChestStateMock(block);
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
		BlockState chest = new ChestStateMock(block);
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
		BlockStateMock state = new ChestStateMock(block);
		BlockState stateCopy = state.getSnapshot();
		assertNotSame(stateCopy.getBlockData(), state.getBlockData());
		assertEquals(stateCopy.getBlockData(), state.getBlockData());
	}

	@Test
	void setBlockData_assertClone()
	{
		Block block = new BlockMock(Material.CHEST);
		BlockStateMock state = new ChestStateMock(block);
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
	@MethodSource("getMaterialAndStates")
	void mockState(Material bedMaterial, Class<?> expectedClass)
	{
		BlockData bed = BlockDataMock.mock(bedMaterial);
		BlockState actual = bed.createBlockState();
		assertNotNull(actual);
		assertInstanceOf(expectedClass, actual);
	}

	/**
	 * Get the list of {@link Material} and the expected {@link BlockState}.
	 *
	 * @return The material and the expected block state.
	 */
	private static Stream<Arguments> getMaterialAndStates()
	{
		return Stream.of(
				// Banners
				Arguments.of(Material.WHITE_BANNER, org.mockbukkit.mockbukkit.block.state.BannerStateMock.class),
				Arguments.of(Material.ORANGE_BANNER, org.mockbukkit.mockbukkit.block.state.BannerStateMock.class),
				Arguments.of(Material.MAGENTA_BANNER, org.mockbukkit.mockbukkit.block.state.BannerStateMock.class),
				Arguments.of(Material.LIGHT_BLUE_BANNER, org.mockbukkit.mockbukkit.block.state.BannerStateMock.class),
				Arguments.of(Material.YELLOW_BANNER, org.mockbukkit.mockbukkit.block.state.BannerStateMock.class),
				Arguments.of(Material.LIME_BANNER, org.mockbukkit.mockbukkit.block.state.BannerStateMock.class),
				Arguments.of(Material.PINK_BANNER, org.mockbukkit.mockbukkit.block.state.BannerStateMock.class),
				Arguments.of(Material.GRAY_BANNER, org.mockbukkit.mockbukkit.block.state.BannerStateMock.class),
				Arguments.of(Material.LIGHT_GRAY_BANNER, org.mockbukkit.mockbukkit.block.state.BannerStateMock.class),
				Arguments.of(Material.CYAN_BANNER, org.mockbukkit.mockbukkit.block.state.BannerStateMock.class),
				Arguments.of(Material.PURPLE_BANNER, org.mockbukkit.mockbukkit.block.state.BannerStateMock.class),
				Arguments.of(Material.BLUE_BANNER, org.mockbukkit.mockbukkit.block.state.BannerStateMock.class),
				Arguments.of(Material.BROWN_BANNER, org.mockbukkit.mockbukkit.block.state.BannerStateMock.class),
				Arguments.of(Material.GREEN_BANNER, org.mockbukkit.mockbukkit.block.state.BannerStateMock.class),
				Arguments.of(Material.RED_BANNER, org.mockbukkit.mockbukkit.block.state.BannerStateMock.class),
				Arguments.of(Material.BLACK_BANNER, org.mockbukkit.mockbukkit.block.state.BannerStateMock.class),
				// Beds
				Arguments.of(Material.WHITE_BED, org.mockbukkit.mockbukkit.block.state.BedStateMock.class),
				Arguments.of(Material.ORANGE_BED, org.mockbukkit.mockbukkit.block.state.BedStateMock.class),
				Arguments.of(Material.MAGENTA_BED, org.mockbukkit.mockbukkit.block.state.BedStateMock.class),
				Arguments.of(Material.LIGHT_BLUE_BED, org.mockbukkit.mockbukkit.block.state.BedStateMock.class),
				Arguments.of(Material.YELLOW_BED, org.mockbukkit.mockbukkit.block.state.BedStateMock.class),
				Arguments.of(Material.LIME_BED, org.mockbukkit.mockbukkit.block.state.BedStateMock.class),
				Arguments.of(Material.PINK_BED, org.mockbukkit.mockbukkit.block.state.BedStateMock.class),
				Arguments.of(Material.GRAY_BED, org.mockbukkit.mockbukkit.block.state.BedStateMock.class),
				Arguments.of(Material.LIGHT_GRAY_BED, org.mockbukkit.mockbukkit.block.state.BedStateMock.class),
				Arguments.of(Material.CYAN_BED, org.mockbukkit.mockbukkit.block.state.BedStateMock.class),
				Arguments.of(Material.PURPLE_BED, org.mockbukkit.mockbukkit.block.state.BedStateMock.class),
				Arguments.of(Material.BLUE_BED, org.mockbukkit.mockbukkit.block.state.BedStateMock.class),
				Arguments.of(Material.BROWN_BED, org.mockbukkit.mockbukkit.block.state.BedStateMock.class),
				Arguments.of(Material.GREEN_BED, org.mockbukkit.mockbukkit.block.state.BedStateMock.class),
				Arguments.of(Material.RED_BED, org.mockbukkit.mockbukkit.block.state.BedStateMock.class),
				Arguments.of(Material.BLACK_BED, org.mockbukkit.mockbukkit.block.state.BedStateMock.class),
				// Skulker Boxes
				Arguments.of(Material.WHITE_SHULKER_BOX, org.mockbukkit.mockbukkit.block.state.ShulkerBoxStateMock.class),
				Arguments.of(Material.ORANGE_SHULKER_BOX, org.mockbukkit.mockbukkit.block.state.ShulkerBoxStateMock.class),
				Arguments.of(Material.MAGENTA_SHULKER_BOX, org.mockbukkit.mockbukkit.block.state.ShulkerBoxStateMock.class),
				Arguments.of(Material.LIGHT_BLUE_SHULKER_BOX, org.mockbukkit.mockbukkit.block.state.ShulkerBoxStateMock.class),
				Arguments.of(Material.YELLOW_SHULKER_BOX, org.mockbukkit.mockbukkit.block.state.ShulkerBoxStateMock.class),
				Arguments.of(Material.LIME_SHULKER_BOX, org.mockbukkit.mockbukkit.block.state.ShulkerBoxStateMock.class),
				Arguments.of(Material.PINK_SHULKER_BOX, org.mockbukkit.mockbukkit.block.state.ShulkerBoxStateMock.class),
				Arguments.of(Material.GRAY_SHULKER_BOX, org.mockbukkit.mockbukkit.block.state.ShulkerBoxStateMock.class),
				Arguments.of(Material.LIGHT_GRAY_SHULKER_BOX, org.mockbukkit.mockbukkit.block.state.ShulkerBoxStateMock.class),
				Arguments.of(Material.CYAN_SHULKER_BOX, org.mockbukkit.mockbukkit.block.state.ShulkerBoxStateMock.class),
				Arguments.of(Material.PURPLE_SHULKER_BOX, org.mockbukkit.mockbukkit.block.state.ShulkerBoxStateMock.class),
				Arguments.of(Material.BLUE_SHULKER_BOX, org.mockbukkit.mockbukkit.block.state.ShulkerBoxStateMock.class),
				Arguments.of(Material.BROWN_SHULKER_BOX, org.mockbukkit.mockbukkit.block.state.ShulkerBoxStateMock.class),
				Arguments.of(Material.GREEN_SHULKER_BOX, org.mockbukkit.mockbukkit.block.state.ShulkerBoxStateMock.class),
				Arguments.of(Material.RED_SHULKER_BOX, org.mockbukkit.mockbukkit.block.state.ShulkerBoxStateMock.class),
				Arguments.of(Material.BLACK_SHULKER_BOX, org.mockbukkit.mockbukkit.block.state.ShulkerBoxStateMock.class),
				// Sign
				Arguments.of(Material.OAK_SIGN, org.mockbukkit.mockbukkit.block.state.SignStateMock.class),
				Arguments.of(Material.SPRUCE_SIGN, org.mockbukkit.mockbukkit.block.state.SignStateMock.class),
				Arguments.of(Material.BIRCH_SIGN, org.mockbukkit.mockbukkit.block.state.SignStateMock.class),
				Arguments.of(Material.JUNGLE_SIGN, org.mockbukkit.mockbukkit.block.state.SignStateMock.class),
				Arguments.of(Material.ACACIA_SIGN, org.mockbukkit.mockbukkit.block.state.SignStateMock.class),
				Arguments.of(Material.CHERRY_SIGN, org.mockbukkit.mockbukkit.block.state.SignStateMock.class),
				Arguments.of(Material.DARK_OAK_SIGN, org.mockbukkit.mockbukkit.block.state.SignStateMock.class),
				Arguments.of(Material.MANGROVE_SIGN, org.mockbukkit.mockbukkit.block.state.SignStateMock.class),
				Arguments.of(Material.BAMBOO_SIGN, org.mockbukkit.mockbukkit.block.state.SignStateMock.class),
				Arguments.of(Material.CRIMSON_SIGN, org.mockbukkit.mockbukkit.block.state.SignStateMock.class),
				Arguments.of(Material.WARPED_SIGN, org.mockbukkit.mockbukkit.block.state.SignStateMock.class),
				// Hanging Sign (TODO: Not implemented yet, see issue https://github.com/MockBukkit/MockBukkit/issues/1088)
//				Arguments.of(Material.OAK_HANGING_SIGN, org.mockbukkit.mockbukkit.block.state.HangingSignMock.class),
//				Arguments.of(Material.SPRUCE_HANGING_SIGN, org.mockbukkit.mockbukkit.block.state.HangingSignMock.class),
//				Arguments.of(Material.BIRCH_HANGING_SIGN, org.mockbukkit.mockbukkit.block.state.HangingSignMock.class),
//				Arguments.of(Material.JUNGLE_HANGING_SIGN, org.mockbukkit.mockbukkit.block.state.HangingSignMock.class),
//				Arguments.of(Material.ACACIA_HANGING_SIGN, org.mockbukkit.mockbukkit.block.state.HangingSignMock.class),
//				Arguments.of(Material.CHERRY_HANGING_SIGN, org.mockbukkit.mockbukkit.block.state.HangingSignMock.class),
//				Arguments.of(Material.DARK_OAK_HANGING_SIGN, org.mockbukkit.mockbukkit.block.state.HangingSignMock.class),
//				Arguments.of(Material.MANGROVE_HANGING_SIGN, org.mockbukkit.mockbukkit.block.state.HangingSignMock.class),
//				Arguments.of(Material.BAMBOO_HANGING_SIGN, org.mockbukkit.mockbukkit.block.state.HangingSignMock.class),
//				Arguments.of(Material.CRIMSON_HANGING_SIGN, org.mockbukkit.mockbukkit.block.state.HangingSignMock.class),
//				Arguments.of(Material.WARPED_HANGING_SIGN, org.mockbukkit.mockbukkit.block.state.HangingSignMock.class),
				// Wall Sign (TODO: Not implemented yet, see issue https://github.com/MockBukkit/MockBukkit/issues/1088)
//				Arguments.of(Material.OAK_WALL_SIGN, org.mockbukkit.mockbukkit.block.data.WallSignMock.class),
//				Arguments.of(Material.SPRUCE_WALL_SIGN, org.mockbukkit.mockbukkit.block.state.WallSignMock.class),
//				Arguments.of(Material.BIRCH_WALL_SIGN, org.mockbukkit.mockbukkit.block.state.WallSignMock.class),
//				Arguments.of(Material.ACACIA_WALL_SIGN, org.mockbukkit.mockbukkit.block.state.WallSignMock.class),
//				Arguments.of(Material.CHERRY_WALL_SIGN, org.mockbukkit.mockbukkit.block.state.WallSignMock.class),
//				Arguments.of(Material.JUNGLE_WALL_SIGN, org.mockbukkit.mockbukkit.block.state.WallSignMock.class),
//				Arguments.of(Material.DARK_OAK_WALL_SIGN, org.mockbukkit.mockbukkit.block.state.WallSignMock.class),
//				Arguments.of(Material.MANGROVE_WALL_SIGN, org.mockbukkit.mockbukkit.block.state.WallSignMock.class),
//				Arguments.of(Material.BAMBOO_WALL_SIGN, org.mockbukkit.mockbukkit.block.state.WallSignMock.class),
				// Wall Hanging Sign (TODO: Not implemented yet, see issue https://github.com/MockBukkit/MockBukkit/issues/1088)
//				Arguments.of(Material.OAK_WALL_HANGING_SIGN, org.mockbukkit.mockbukkit.block.state.WallHangingSignMock.class),
//				Arguments.of(Material.SPRUCE_WALL_HANGING_SIGN, org.mockbukkit.mockbukkit.block.state.WallHangingSignMock.class),
//				Arguments.of(Material.BIRCH_WALL_HANGING_SIGN, org.mockbukkit.mockbukkit.block.state.WallHangingSignMock.class),
//				Arguments.of(Material.ACACIA_WALL_HANGING_SIGN, org.mockbukkit.mockbukkit.block.state.WallHangingSignMock.class),
//				Arguments.of(Material.CHERRY_WALL_HANGING_SIGN, org.mockbukkit.mockbukkit.block.state.WallHangingSignMock.class),
//				Arguments.of(Material.JUNGLE_WALL_HANGING_SIGN, org.mockbukkit.mockbukkit.block.state.WallHangingSignMock.class),
//				Arguments.of(Material.DARK_OAK_WALL_HANGING_SIGN, org.mockbukkit.mockbukkit.block.state.WallHangingSignMock.class),
//				Arguments.of(Material.MANGROVE_WALL_HANGING_SIGN, org.mockbukkit.mockbukkit.block.state.WallHangingSignMock.class),
//				Arguments.of(Material.CRIMSON_WALL_HANGING_SIGN, org.mockbukkit.mockbukkit.block.state.WallHangingSignMock.class),
//				Arguments.of(Material.WARPED_WALL_HANGING_SIGN, org.mockbukkit.mockbukkit.block.state.WallHangingSignMock.class),
//				Arguments.of(Material.BAMBOO_WALL_HANGING_SIGN, org.mockbukkit.mockbukkit.block.state.WallHangingSignMock.class),
				// Skulls
				Arguments.of(Material.SKELETON_SKULL, org.mockbukkit.mockbukkit.block.state.SkullStateMock.class),
				Arguments.of(Material.SKELETON_WALL_SKULL, org.mockbukkit.mockbukkit.block.state.SkullStateMock.class),
				Arguments.of(Material.WITHER_SKELETON_SKULL, org.mockbukkit.mockbukkit.block.state.SkullStateMock.class),
				Arguments.of(Material.WITHER_SKELETON_WALL_SKULL, org.mockbukkit.mockbukkit.block.state.SkullStateMock.class),
				// Other blocks
				Arguments.of(Material.STRUCTURE_BLOCK, org.mockbukkit.mockbukkit.block.state.StructureStateMock.class),
				Arguments.of(Material.SMOKER, org.mockbukkit.mockbukkit.block.state.SmokerStateMock.class),
				Arguments.of(Material.END_GATEWAY, org.mockbukkit.mockbukkit.block.state.EndGatewayStateMock.class),
				Arguments.of(Material.SCULK_CATALYST, org.mockbukkit.mockbukkit.block.state.SculkCatalystStateMock.class),
				Arguments.of(Material.SCULK_SHRIEKER, org.mockbukkit.mockbukkit.block.state.SculkShriekerStateMock.class),
				Arguments.of(Material.SCULK_SENSOR, org.mockbukkit.mockbukkit.block.state.SculkSensorStateMock.class),
				Arguments.of(Material.BEACON, org.mockbukkit.mockbukkit.block.state.BeaconStateMock.class),
				Arguments.of(Material.BEEHIVE, org.mockbukkit.mockbukkit.block.state.BeehiveStateMock.class),
				Arguments.of(Material.BREWING_STAND, org.mockbukkit.mockbukkit.block.state.BrewingStandStateMock.class),
				Arguments.of(Material.BLAST_FURNACE, org.mockbukkit.mockbukkit.block.state.BlastFurnaceStateMock.class),
				Arguments.of(Material.COMPARATOR, org.mockbukkit.mockbukkit.block.state.ComparatorStateMock.class),
				Arguments.of(Material.ENCHANTING_TABLE, org.mockbukkit.mockbukkit.block.state.EnchantingTableStateMock.class),
				Arguments.of(Material.JIGSAW, org.mockbukkit.mockbukkit.block.state.JigsawStateMock.class),
				Arguments.of(Material.JUKEBOX, org.mockbukkit.mockbukkit.block.state.JukeboxStateMock.class),
				Arguments.of(Material.SPAWNER, org.mockbukkit.mockbukkit.block.state.CreatureSpawnerStateMock.class),
				Arguments.of(Material.DAYLIGHT_DETECTOR, org.mockbukkit.mockbukkit.block.state.DaylightDetectorStateMock.class),
				Arguments.of(Material.COMMAND_BLOCK, org.mockbukkit.mockbukkit.block.state.CommandBlockStateMock.class),
				Arguments.of(Material.CHAIN_COMMAND_BLOCK, org.mockbukkit.mockbukkit.block.state.CommandBlockStateMock.class),
				Arguments.of(Material.REPEATING_COMMAND_BLOCK, org.mockbukkit.mockbukkit.block.state.CommandBlockStateMock.class),
				Arguments.of(Material.CAMPFIRE, org.mockbukkit.mockbukkit.block.state.CampfireStateMock.class),
				Arguments.of(Material.SOUL_CAMPFIRE, org.mockbukkit.mockbukkit.block.state.CampfireStateMock.class),
				Arguments.of(Material.BELL, org.mockbukkit.mockbukkit.block.state.BellStateMock.class),
				Arguments.of(Material.LECTERN, org.mockbukkit.mockbukkit.block.state.LecternStateMock.class),
				Arguments.of(Material.HOPPER, org.mockbukkit.mockbukkit.block.state.HopperStateMock.class),
				Arguments.of(Material.BARREL, org.mockbukkit.mockbukkit.block.state.BarrelStateMock.class),
				Arguments.of(Material.DISPENSER, org.mockbukkit.mockbukkit.block.state.DispenserStateMock.class),
				Arguments.of(Material.DROPPER, org.mockbukkit.mockbukkit.block.state.DropperStateMock.class),
				Arguments.of(Material.CHEST, org.mockbukkit.mockbukkit.block.state.ChestStateMock.class),
				Arguments.of(Material.TRAPPED_CHEST, org.mockbukkit.mockbukkit.block.state.ChestStateMock.class),
				Arguments.of(Material.ENDER_CHEST, org.mockbukkit.mockbukkit.block.state.EnderChestStateMock.class)
		);
	}

}
