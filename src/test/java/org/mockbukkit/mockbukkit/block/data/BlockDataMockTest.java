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

			BlockDataMock data = BlockDataMock.mock(Material.CAMPFIRE);
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
			"ACACIA_DOOR, 'minecraft:acacia_door[facing=north,half=lower,hinge=left,open=false,powered=false]'",
			"BAMBOO_DOOR, 'minecraft:bamboo_door[facing=north,half=lower,hinge=left,open=false,powered=false]'",
			"BIRCH_DOOR, 'minecraft:birch_door[facing=north,half=lower,hinge=left,open=false,powered=false]'",
			"CHERRY_DOOR, 'minecraft:cherry_door[facing=north,half=lower,hinge=left,open=false,powered=false]'",
			"COPPER_DOOR, 'minecraft:copper_door[facing=north,half=lower,hinge=left,open=false,powered=false]'",
			"CRIMSON_DOOR, 'minecraft:crimson_door[facing=north,half=lower,hinge=left,open=false,powered=false]'",
			"DARK_OAK_DOOR, 'minecraft:dark_oak_door[facing=north,half=lower,hinge=left,open=false,powered=false]'",
			"EXPOSED_COPPER_DOOR, 'minecraft:exposed_copper_door[facing=north,half=lower,hinge=left,open=false,powered=false]'",
			"IRON_DOOR, 'minecraft:iron_door[facing=north,half=lower,hinge=left,open=false,powered=false]'",
			"JUNGLE_DOOR, 'minecraft:jungle_door[facing=north,half=lower,hinge=left,open=false,powered=false]'",
			"LARGE_FERN, 'minecraft:large_fern[half=lower]'",
			"LILAC, 'minecraft:lilac[half=lower]'",
			"MANGROVE_DOOR, 'minecraft:mangrove_door[facing=north,half=lower,hinge=left,open=false,powered=false]'",
			"OAK_DOOR, 'minecraft:oak_door[facing=north,half=lower,hinge=left,open=false,powered=false]'",
			"OXIDIZED_COPPER_DOOR, 'minecraft:oxidized_copper_door[facing=north,half=lower,hinge=left,open=false,powered=false]'",
			"PALE_OAK_DOOR, 'minecraft:pale_oak_door[facing=north,half=lower,hinge=left,open=false,powered=false]'",
			"PEONY, 'minecraft:peony[half=lower]'",
			"PITCHER_PLANT, 'minecraft:pitcher_plant[half=lower]'",
			"ROSE_BUSH, 'minecraft:rose_bush[half=lower]'",
			"SPRUCE_DOOR, 'minecraft:spruce_door[facing=north,half=lower,hinge=left,open=false,powered=false]'",
			"SUNFLOWER, 'minecraft:sunflower[half=lower]'",
			"TALL_GRASS, 'minecraft:tall_grass[half=lower]'",
			"TALL_SEAGRASS, 'minecraft:tall_seagrass[half=lower]'",
			"WARPED_DOOR, 'minecraft:warped_door[facing=north,half=lower,hinge=left,open=false,powered=false]'",
			"WAXED_COPPER_DOOR, 'minecraft:waxed_copper_door[facing=north,half=lower,hinge=left,open=false,powered=false]'",
			"WAXED_EXPOSED_COPPER_DOOR, 'minecraft:waxed_exposed_copper_door[facing=north,half=lower,hinge=left,open=false,powered=false]'",
			"WAXED_OXIDIZED_COPPER_DOOR, 'minecraft:waxed_oxidized_copper_door[facing=north,half=lower,hinge=left,open=false,powered=false]'",
			"WAXED_WEATHERED_COPPER_DOOR, 'minecraft:waxed_weathered_copper_door[facing=north,half=lower,hinge=left,open=false,powered=false]'",
			"WEATHERED_COPPER_DOOR, 'minecraft:weathered_copper_door[facing=north,half=lower,hinge=left,open=false,powered=false]'"
		})
		void givenSamples(Material material, String expectedOutput)
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
