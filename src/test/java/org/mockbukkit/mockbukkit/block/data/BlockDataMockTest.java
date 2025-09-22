package org.mockbukkit.mockbukkit.block.data;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import lombok.extern.slf4j.Slf4j;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.Registry;
import org.bukkit.Tag;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.BlockState;
import org.bukkit.block.data.FaceAttachable;
import org.bukkit.block.data.type.Bed;
import org.bukkit.block.data.type.WallSign;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockbukkit.mockbukkit.MockBukkit;
import org.mockbukkit.mockbukkit.MockBukkitExtension;
import org.mockbukkit.mockbukkit.MockBukkitInject;
import org.mockbukkit.mockbukkit.ServerMock;
import org.mockbukkit.mockbukkit.block.BlockMock;
import org.mockbukkit.mockbukkit.block.state.BedStateMock;
import org.mockbukkit.mockbukkit.exception.UnimplementedOperationException;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNotSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertThrowsExactly;
import static org.junit.jupiter.api.Assertions.assertTrue;

@Slf4j
@ExtendWith({ MockBukkitExtension.class })
@ExtendWith(MockBukkitExtension.class)
class BlockDataMockTest
{

	@MockBukkitInject
	private ServerMock server;

	@Test
	void matches_DoesMatch()
	{
		BlockDataMock blockData1 = new BlockDataMock(Material.STONE);
		BlockDataMock blockData2 = new BlockDataMock(Material.STONE);

		assertTrue(blockData1.matches(blockData2));
	}

	@Test
	void matches_DifferentMaterials_DoesntMatch()
	{
		BlockDataMock blockData1 = new BlockDataMock(Material.STONE);
		BlockDataMock blockData2 = new BlockDataMock(Material.DIRT);

		assertFalse(blockData1.matches(blockData2));
	}

	@Test
	void getAsString_NoData()
	{
		BlockDataMock blockData = new BlockDataMock(Material.STONE);

		assertEquals("minecraft:stone", blockData.getAsString());
	}

	@Test
	void testGetWithNonExistentKey2()
	{
		BlockDataMock blockData = BlockDataMock.mock(Material.ACACIA_BUTTON);

		// Check the defaults:
		assertEquals(false, blockData.get(BlockDataKey.POWERED));
		assertEquals(FaceAttachable.AttachedFace.WALL, blockData.get(BlockDataKey.FACE));
		assertEquals(BlockFace.NORTH, blockData.get(BlockDataKey.FACING));
	}

	@Test
	void testHashCode()
	{
		BlockDataMock blockData = BlockDataMock.mock(Material.ACACIA_BUTTON);
		BlockDataMock blockData2 = BlockDataMock.mock(Material.ACACIA_BUTTON);
		assertEquals(blockData2.hashCode(), blockData.hashCode());

		blockData.set(BlockDataKey.POWERED, true);
		assertNotEquals(blockData2.hashCode(), blockData.hashCode());
	}

	@Test
	void testMatchesNotEquals()
	{
		BlockDataMock blockData = BlockDataMock.newData(null, "acacia_button[facing=east]");
		BlockDataMock blockData2 = BlockDataMock.newData(null, "acacia_button[facing=east, powered=true]");

		assertTrue(blockData2.matches(blockData));
		assertFalse(blockData.matches(blockData2));
	}

	@Test
	void mock_NullInput_ThrowsException()
	{
		assertThrowsExactly(NullPointerException.class, () -> BlockDataMock.mock(null));
	}

	@Test
	void testCheckTypeBlock()
	{
		BlockDataMock blockData = new BlockDataMock(Material.STONE);
		Block block = new BlockMock(Material.STONE);
		blockData.checkType(block.getType(), Material.STONE);
	}

	@Test
	void testCheckTypeBlockWrongType()
	{
		BlockDataMock blockData = new BlockDataMock(Material.STONE);
		Block block = new BlockMock(Material.DIRT);
		assertThrowsExactly(IllegalArgumentException.class, () -> blockData.checkType(block.getType(), Material.STONE));
	}

	@Test
	void testCheckTypeBlockTag()
	{
		BlockDataMock blockData = new BlockDataMock(Material.ACACIA_PLANKS);
		Block block = new BlockMock(Material.ACACIA_PLANKS);
		blockData.checkType(block.getType(), Tag.PLANKS);
	}

	@Test
	void clone_isClone()
	{
		WallSign wallSign = (WallSign) BlockDataMock.mock(Material.ACACIA_WALL_SIGN);
		wallSign.setFacing(BlockFace.NORTH);
		WallSign clone = (WallSign) wallSign.clone();
		assertNotSame(wallSign, clone);
		assertEquals(wallSign, clone);
		assertEquals(wallSign.getFacing(), clone.getFacing());
	}

	@Test
	void clone_isCloneChangeInValue()
	{
		WallSign wallSign = (WallSign) BlockDataMock.mock(Material.ACACIA_WALL_SIGN);
		wallSign.setFacing(BlockFace.EAST);
		WallSign clone = (WallSign) wallSign.clone();
		clone.setFacing(BlockFace.WEST);
		assertNotEquals(wallSign.getFacing(), clone.getFacing());
	}

	@Nested
	class GetAsString
	{

		@Test
		void test_getAsString()
		{
			// https://jd.papermc.io/paper/1.16/org/bukkit/block/data/BlockData.html#getAsString(boolean)
			// defaults:
			//    "minecraft:chest": {
			//        "facing": "north",
			//        "type": "single",
			//        "waterlogged": false
			//    },
			//
			// getAsString(true) : minecraft:chest[waterlogged=true]
			// getAsString(false): minecraft:chest[facing=north,type=single,waterlogged=true]
			// getAsString()     : minecraft:chest[facing=north,type=single,waterlogged=true]

			BlockDataMock data = BlockDataMockFactory.mock(Material.CAMPFIRE);
			assertEquals("minecraft:campfire", data.getAsString(true));
			assertNotEquals(data.getAsString(true), data.getAsString(false));
			data.set(BlockDataKey.FACING, BlockFace.SOUTH);
			assertEquals("minecraft:campfire[facing=south]", data.getAsString(true));
			assertNotEquals(data.getAsString(true), data.getAsString(false));
		}

		/*
		 * See: https://github.com/MockBukkit/MockBukkit/issues/1433
		 */
		@ParameterizedTest
		@CsvSource({
			"'ACACIA_STAIRS', 'minecraft:acacia_stairs[facing=north,half=bottom,shape=straight,waterlogged=false]'",
			"'ACACIA_TRAPDOOR', 'minecraft:acacia_trapdoor[facing=north,half=bottom,open=false,powered=false,waterlogged=false]'",
			"'ANDESITE_STAIRS', 'minecraft:andesite_stairs[facing=north,half=bottom,shape=straight,waterlogged=false]'",
			"'BAMBOO_DOOR', 'minecraft:bamboo_door[facing=north,half=lower,hinge=left,open=false,powered=false]'",
			"'BAMBOO_MOSAIC_STAIRS', 'minecraft:bamboo_mosaic_stairs[facing=north,half=bottom,shape=straight,waterlogged=false]'",
			"'BAMBOO_STAIRS', 'minecraft:bamboo_stairs[facing=north,half=bottom,shape=straight,waterlogged=false]'",
			"'BAMBOO_TRAPDOOR', 'minecraft:bamboo_trapdoor[facing=north,half=bottom,open=false,powered=false,waterlogged=false]'",
			"'BIRCH_DOOR', 'minecraft:birch_door[facing=north,half=lower,hinge=left,open=false,powered=false]'",
			"'BIRCH_STAIRS', 'minecraft:birch_stairs[facing=north,half=bottom,shape=straight,waterlogged=false]'",
			"'BIRCH_TRAPDOOR', 'minecraft:birch_trapdoor[facing=north,half=bottom,open=false,powered=false,waterlogged=false]'",
			"'BLACKSTONE_STAIRS', 'minecraft:blackstone_stairs[facing=north,half=bottom,shape=straight,waterlogged=false]'",
			"'BRICK_STAIRS', 'minecraft:brick_stairs[facing=north,half=bottom,shape=straight,waterlogged=false]'",
			"'CHERRY_DOOR', 'minecraft:cherry_door[facing=north,half=lower,hinge=left,open=false,powered=false]'",
			"'CHERRY_STAIRS', 'minecraft:cherry_stairs[facing=north,half=bottom,shape=straight,waterlogged=false]'",
			"'CHERRY_TRAPDOOR', 'minecraft:cherry_trapdoor[facing=north,half=bottom,open=false,powered=false,waterlogged=false]'",
			"'COBBLED_DEEPSLATE_STAIRS', 'minecraft:cobbled_deepslate_stairs[facing=north,half=bottom,shape=straight,waterlogged=false]'",
			"'COBBLESTONE_STAIRS', 'minecraft:cobblestone_stairs[facing=north,half=bottom,shape=straight,waterlogged=false]'",
			"'COPPER_DOOR', 'minecraft:copper_door[facing=north,half=lower,hinge=left,open=false,powered=false]'",
			"'COPPER_TRAPDOOR', 'minecraft:copper_trapdoor[facing=north,half=bottom,open=false,powered=false,waterlogged=false]'",
			"'CRIMSON_DOOR', 'minecraft:crimson_door[facing=north,half=lower,hinge=left,open=false,powered=false]'",
			"'CRIMSON_STAIRS', 'minecraft:crimson_stairs[facing=north,half=bottom,shape=straight,waterlogged=false]'",
			"'CRIMSON_TRAPDOOR', 'minecraft:crimson_trapdoor[facing=north,half=bottom,open=false,powered=false,waterlogged=false]'",
			"'CUT_COPPER_STAIRS', 'minecraft:cut_copper_stairs[facing=north,half=bottom,shape=straight,waterlogged=false]'",
			"'DARK_OAK_DOOR', 'minecraft:dark_oak_door[facing=north,half=lower,hinge=left,open=false,powered=false]'",
			"'DARK_OAK_STAIRS', 'minecraft:dark_oak_stairs[facing=north,half=bottom,shape=straight,waterlogged=false]'",
			"'DARK_OAK_TRAPDOOR', 'minecraft:dark_oak_trapdoor[facing=north,half=bottom,open=false,powered=false,waterlogged=false]'",
			"'DARK_PRISMARINE_STAIRS', 'minecraft:dark_prismarine_stairs[facing=north,half=bottom,shape=straight,waterlogged=false]'",
			"'DEEPSLATE_BRICK_STAIRS', 'minecraft:deepslate_brick_stairs[facing=north,half=bottom,shape=straight,waterlogged=false]'",
			"'DEEPSLATE_TILE_STAIRS', 'minecraft:deepslate_tile_stairs[facing=north,half=bottom,shape=straight,waterlogged=false]'",
			"'DIORITE_STAIRS', 'minecraft:diorite_stairs[facing=north,half=bottom,shape=straight,waterlogged=false]'",
			"'END_STONE_BRICK_STAIRS', 'minecraft:end_stone_brick_stairs[facing=north,half=bottom,shape=straight,waterlogged=false]'",
			"'EXPOSED_COPPER_DOOR', 'minecraft:exposed_copper_door[facing=north,half=lower,hinge=left,open=false,powered=false]'",
			"'EXPOSED_COPPER_TRAPDOOR', 'minecraft:exposed_copper_trapdoor[facing=north,half=bottom,open=false,powered=false,waterlogged=false]'",
			"'EXPOSED_CUT_COPPER_STAIRS', 'minecraft:exposed_cut_copper_stairs[facing=north,half=bottom,shape=straight,waterlogged=false]'",
			"'GRANITE_STAIRS', 'minecraft:granite_stairs[facing=north,half=bottom,shape=straight,waterlogged=false]'",
			"'IRON_DOOR', 'minecraft:iron_door[facing=north,half=lower,hinge=left,open=false,powered=false]'",
			"'IRON_TRAPDOOR', 'minecraft:iron_trapdoor[facing=north,half=bottom,open=false,powered=false,waterlogged=false]'",
			"'JUNGLE_DOOR', 'minecraft:jungle_door[facing=north,half=lower,hinge=left,open=false,powered=false]'",
			"'JUNGLE_STAIRS', 'minecraft:jungle_stairs[facing=north,half=bottom,shape=straight,waterlogged=false]'",
			"'JUNGLE_TRAPDOOR', 'minecraft:jungle_trapdoor[facing=north,half=bottom,open=false,powered=false,waterlogged=false]'",
			"'MANGROVE_DOOR', 'minecraft:mangrove_door[facing=north,half=lower,hinge=left,open=false,powered=false]'",
			"'MANGROVE_STAIRS', 'minecraft:mangrove_stairs[facing=north,half=bottom,shape=straight,waterlogged=false]'",
			"'MANGROVE_TRAPDOOR', 'minecraft:mangrove_trapdoor[facing=north,half=bottom,open=false,powered=false,waterlogged=false]'",
			"'MOSSY_COBBLESTONE_STAIRS', 'minecraft:mossy_cobblestone_stairs[facing=north,half=bottom,shape=straight,waterlogged=false]'",
			"'MOSSY_STONE_BRICK_STAIRS', 'minecraft:mossy_stone_brick_stairs[facing=north,half=bottom,shape=straight,waterlogged=false]'",
			"'MUD_BRICK_STAIRS', 'minecraft:mud_brick_stairs[facing=north,half=bottom,shape=straight,waterlogged=false]'",
			"'NETHER_BRICK_STAIRS', 'minecraft:nether_brick_stairs[facing=north,half=bottom,shape=straight,waterlogged=false]'",
			"'OAK_DOOR', 'minecraft:oak_door[facing=north,half=lower,hinge=left,open=false,powered=false]'",
			"'OAK_STAIRS', 'minecraft:oak_stairs[facing=north,half=bottom,shape=straight,waterlogged=false]'",
			"'OAK_TRAPDOOR', 'minecraft:oak_trapdoor[facing=north,half=bottom,open=false,powered=false,waterlogged=false]'",
			"'OXIDIZED_COPPER_DOOR', 'minecraft:oxidized_copper_door[facing=north,half=lower,hinge=left,open=false,powered=false]'",
			"'OXIDIZED_COPPER_TRAPDOOR', 'minecraft:oxidized_copper_trapdoor[facing=north,half=bottom,open=false,powered=false,waterlogged=false]'",
			"'OXIDIZED_CUT_COPPER_STAIRS', 'minecraft:oxidized_cut_copper_stairs[facing=north,half=bottom,shape=straight,waterlogged=false]'",
			"'PALE_OAK_DOOR', 'minecraft:pale_oak_door[facing=north,half=lower,hinge=left,open=false,powered=false]'",
			"'PALE_OAK_STAIRS', 'minecraft:pale_oak_stairs[facing=north,half=bottom,shape=straight,waterlogged=false]'",
			"'PALE_OAK_TRAPDOOR', 'minecraft:pale_oak_trapdoor[facing=north,half=bottom,open=false,powered=false,waterlogged=false]'",
			"'POLISHED_ANDESITE_STAIRS', 'minecraft:polished_andesite_stairs[facing=north,half=bottom,shape=straight,waterlogged=false]'",
			"'POLISHED_BLACKSTONE_BRICK_STAIRS', 'minecraft:polished_blackstone_brick_stairs[facing=north,half=bottom,shape=straight,waterlogged=false]'",
			"'POLISHED_BLACKSTONE_STAIRS', 'minecraft:polished_blackstone_stairs[facing=north,half=bottom,shape=straight,waterlogged=false]'",
			"'POLISHED_DEEPSLATE_STAIRS', 'minecraft:polished_deepslate_stairs[facing=north,half=bottom,shape=straight,waterlogged=false]'",
			"'POLISHED_DIORITE_STAIRS', 'minecraft:polished_diorite_stairs[facing=north,half=bottom,shape=straight,waterlogged=false]'",
			"'POLISHED_GRANITE_STAIRS', 'minecraft:polished_granite_stairs[facing=north,half=bottom,shape=straight,waterlogged=false]'",
			"'POLISHED_TUFF_STAIRS', 'minecraft:polished_tuff_stairs[facing=north,half=bottom,shape=straight,waterlogged=false]'",
			"'PRISMARINE_BRICK_STAIRS', 'minecraft:prismarine_brick_stairs[facing=north,half=bottom,shape=straight,waterlogged=false]'",
			"'PRISMARINE_STAIRS', 'minecraft:prismarine_stairs[facing=north,half=bottom,shape=straight,waterlogged=false]'",
			"'PURPUR_STAIRS', 'minecraft:purpur_stairs[facing=north,half=bottom,shape=straight,waterlogged=false]'",
			"'QUARTZ_STAIRS', 'minecraft:quartz_stairs[facing=north,half=bottom,shape=straight,waterlogged=false]'",
			"'RED_NETHER_BRICK_STAIRS', 'minecraft:red_nether_brick_stairs[facing=north,half=bottom,shape=straight,waterlogged=false]'",
			"'RED_SANDSTONE_STAIRS', 'minecraft:red_sandstone_stairs[facing=north,half=bottom,shape=straight,waterlogged=false]'",
			"'RESIN_BRICK_STAIRS', 'minecraft:resin_brick_stairs[facing=north,half=bottom,shape=straight,waterlogged=false]'",
			"'SANDSTONE_STAIRS', 'minecraft:sandstone_stairs[facing=north,half=bottom,shape=straight,waterlogged=false]'",
			"'SMOOTH_QUARTZ_STAIRS', 'minecraft:smooth_quartz_stairs[facing=north,half=bottom,shape=straight,waterlogged=false]'",
			"'SMOOTH_RED_SANDSTONE_STAIRS', 'minecraft:smooth_red_sandstone_stairs[facing=north,half=bottom,shape=straight,waterlogged=false]'",
			"'SMOOTH_SANDSTONE_STAIRS', 'minecraft:smooth_sandstone_stairs[facing=north,half=bottom,shape=straight,waterlogged=false]'",
			"'SPRUCE_DOOR', 'minecraft:spruce_door[facing=north,half=lower,hinge=left,open=false,powered=false]'",
			"'SPRUCE_STAIRS', 'minecraft:spruce_stairs[facing=north,half=bottom,shape=straight,waterlogged=false]'",
			"'SPRUCE_TRAPDOOR', 'minecraft:spruce_trapdoor[facing=north,half=bottom,open=false,powered=false,waterlogged=false]'",
			"'STONE_BRICK_STAIRS', 'minecraft:stone_brick_stairs[facing=north,half=bottom,shape=straight,waterlogged=false]'",
			"'STONE_STAIRS', 'minecraft:stone_stairs[facing=north,half=bottom,shape=straight,waterlogged=false]'",
			"'TUFF_BRICK_STAIRS', 'minecraft:tuff_brick_stairs[facing=north,half=bottom,shape=straight,waterlogged=false]'",
			"'TUFF_STAIRS', 'minecraft:tuff_stairs[facing=north,half=bottom,shape=straight,waterlogged=false]'",
			"'WARPED_DOOR', 'minecraft:warped_door[facing=north,half=lower,hinge=left,open=false,powered=false]'",
			"'WARPED_STAIRS', 'minecraft:warped_stairs[facing=north,half=bottom,shape=straight,waterlogged=false]'",
			"'WARPED_TRAPDOOR', 'minecraft:warped_trapdoor[facing=north,half=bottom,open=false,powered=false,waterlogged=false]'",
			"'WAXED_COPPER_DOOR', 'minecraft:waxed_copper_door[facing=north,half=lower,hinge=left,open=false,powered=false]'",
			"'WAXED_COPPER_TRAPDOOR', 'minecraft:waxed_copper_trapdoor[facing=north,half=bottom,open=false,powered=false,waterlogged=false]'",
			"'WAXED_CUT_COPPER_STAIRS', 'minecraft:waxed_cut_copper_stairs[facing=north,half=bottom,shape=straight,waterlogged=false]'",
			"'WAXED_EXPOSED_COPPER_DOOR', 'minecraft:waxed_exposed_copper_door[facing=north,half=lower,hinge=left,open=false,powered=false]'",
			"'WAXED_EXPOSED_COPPER_TRAPDOOR', 'minecraft:waxed_exposed_copper_trapdoor[facing=north,half=bottom,open=false,powered=false,waterlogged=false]'",
			"'WAXED_EXPOSED_CUT_COPPER_STAIRS', 'minecraft:waxed_exposed_cut_copper_stairs[facing=north,half=bottom,shape=straight,waterlogged=false]'",
			"'WAXED_OXIDIZED_COPPER_DOOR', 'minecraft:waxed_oxidized_copper_door[facing=north,half=lower,hinge=left,open=false,powered=false]'",
			"'WAXED_OXIDIZED_COPPER_TRAPDOOR', 'minecraft:waxed_oxidized_copper_trapdoor[facing=north,half=bottom,open=false,powered=false,waterlogged=false]'",
			"'WAXED_OXIDIZED_CUT_COPPER_STAIRS', 'minecraft:waxed_oxidized_cut_copper_stairs[facing=north,half=bottom,shape=straight,waterlogged=false]'",
			"'WAXED_WEATHERED_COPPER_DOOR', 'minecraft:waxed_weathered_copper_door[facing=north,half=lower,hinge=left,open=false,powered=false]'",
			"'WAXED_WEATHERED_COPPER_TRAPDOOR', 'minecraft:waxed_weathered_copper_trapdoor[facing=north,half=bottom,open=false,powered=false,waterlogged=false]'",
			"'WAXED_WEATHERED_CUT_COPPER_STAIRS', 'minecraft:waxed_weathered_cut_copper_stairs[facing=north,half=bottom,shape=straight,waterlogged=false]'",
			"'WEATHERED_COPPER_DOOR', 'minecraft:weathered_copper_door[facing=north,half=lower,hinge=left,open=false,powered=false]'",
			"'WEATHERED_COPPER_TRAPDOOR', 'minecraft:weathered_copper_trapdoor[facing=north,half=bottom,open=false,powered=false,waterlogged=false]'",
			"'WEATHERED_CUT_COPPER_STAIRS', 'minecraft:weathered_cut_copper_stairs[facing=north,half=bottom,shape=straight,waterlogged=false]'",
		})
		void givenSamples(Material material, String expectedOutput)
		{
			var blockData = material.createBlockData();
			var actual = blockData.getAsString(false);
			assertEquals(expectedOutput, actual);
		}

		@ParameterizedTest
		@CsvFileSource(resources = "/blocks/block_data_as_string.csv")
		void givenPossibleValues(Material material, String expectedOutput)
		{
			var blockData = material.createBlockData();
			var actual = blockData.getAsString(false);
			assertEquals(expectedOutput, actual);
		}
	}

	@ParameterizedTest
	@ValueSource(strings = {
			"WHITE_BED",
			"ORANGE_BED",
			"MAGENTA_BED",
			"LIGHT_BLUE_BED",
			"YELLOW_BED",
			"LIME_BED",
			"PINK_BED",
			"GRAY_BED",
			"LIGHT_GRAY_BED",
			"CYAN_BED",
			"PURPLE_BED",
			"BLUE_BED",
			"BROWN_BED",
			"GREEN_BED",
			"RED_BED",
			"BLACK_BED"
	})
	void createBlockState_GivenBedMaterial(Material bedMaterial)
	{
		BedDataMock bed = (BedDataMock) BlockDataMock.mock(bedMaterial);
		BlockState actual = bed.createBlockState();
		assertNotNull(actual);
		assertInstanceOf(BedStateMock.class, actual);
	}

	@Test
	void serializeDeserializeBed()
	{
		BedDataMock bed = (BedDataMock) BlockDataMock.mock(Material.BLACK_BED);
		bed.setFacing(BlockFace.EAST);
		bed.setOccupied(true);
		bed.setPart(Bed.Part.HEAD);
		String serialized = bed.getAsString();
		BlockDataMock blockDataMock = BlockDataMock.newData(null, serialized);
		assertEquals(blockDataMock, bed);
	}

	@Test
	void serializeDeserialize_duplicateMaterialArgument()
	{
		BedDataMock bed = (BedDataMock) BlockDataMock.mock(Material.BLACK_BED);
		bed.setFacing(BlockFace.EAST);
		bed.setOccupied(true);
		bed.setPart(Bed.Part.HEAD);
		String serialized = bed.getAsString();
		BlockDataMock blockDataMock = BlockDataMock.newData(Registry.BLOCK.get(NamespacedKey.minecraft("black_bed")), serialized);
		assertEquals(blockDataMock, bed);
	}

	@Test
	void serializeDeserialize_duplicateMaterialArgument_noFields()
	{
		BlockDataMock blockDataMock = BlockDataMock.newData(Registry.BLOCK.get(NamespacedKey.minecraft("black_bed")), "minecraft:stone");
		assertInstanceOf(BedDataMock.class, blockDataMock);
	}

	@ParameterizedTest
	@MethodSource("getValidSerializations")
	void deserialize_validInput(String serialized)
	{
		assertDoesNotThrow(() -> BlockDataMock.newData(null, serialized));
	}

	@ParameterizedTest
	@MethodSource("getInvalidSerializations")
	void deserialize_invalidInput(String serialized)
	{
		assertThrows(IllegalArgumentException.class, () -> BlockDataMock.newData(null, serialized));
	}

	@Test
	void deserialize_missingFields()
	{
		BlockDataMock blockDataMock = BlockDataMock.newData(null, "minecraft:black_bed");
		BedDataMock bedDataMock = (BedDataMock) blockDataMock;
		assertNotNull(bedDataMock.getFacing());
	}

	@Nested
	class CloneTest
	{

		/**
		 * Unit test to validate that extend {@link BlockDataMock} implement the clone method.
		 *
		 * @param material The material to be used in the block state
		 */
		@ParameterizedTest
		@MethodSource("getPossibleBlockData")
		void givenPossibleBlockData(Material material)
		{
			BlockDataMock blockData = BlockDataMockFactory.mock(material);

			BlockDataMock clone = blockData.clone();

			assertEquals(blockData, clone);
			assertNotSame(blockData, clone);

			assertEquals(blockData.getClass(), clone.getClass());
		}

		static Stream<Arguments> getPossibleBlockData()
		{
			List<Material> blockDataMocks = new ArrayList<>();
			for (Material material : Material.values())
			{
				try
				{
					if (!material.isBlock())
					{
						continue;
					}

					blockDataMocks.add(material);
				}
				catch (UnimplementedOperationException e)
				{
					log.warn("Material {} is throwing an UnimplementedOperationException", material);
				}
			}

			return blockDataMocks.stream().map(Arguments::of);
		}
	}

	static Stream<Arguments> getValidSerializations() throws IOException
	{
		try (InputStream inputStream = MockBukkit.class.getResourceAsStream("/blockData/validSerializations.json"))
		{
			JsonArray jsonArray = JsonParser.parseReader(new InputStreamReader(inputStream)).getAsJsonArray();
			return jsonArray.asList().stream().map(JsonElement::getAsString).map(Arguments::of);
		}
	}

	static Stream<Arguments> getInvalidSerializations() throws IOException
	{
		try (InputStream inputStream = MockBukkit.class.getResourceAsStream("/blockData/invalidSerializations.json"))
		{
			JsonArray jsonArray = JsonParser.parseReader(new InputStreamReader(inputStream)).getAsJsonArray();
			return jsonArray.asList().stream().map(JsonElement::getAsString).map(Arguments::of);
		}
	}

}
