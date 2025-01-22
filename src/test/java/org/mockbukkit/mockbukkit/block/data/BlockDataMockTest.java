package org.mockbukkit.mockbukkit.block.data;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
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
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockbukkit.mockbukkit.MockBukkit;
import org.mockbukkit.mockbukkit.MockBukkitExtension;
import org.mockbukkit.mockbukkit.MockBukkitInject;
import org.mockbukkit.mockbukkit.ServerMock;
import org.mockbukkit.mockbukkit.block.BlockMock;
import org.mockbukkit.mockbukkit.block.state.BedStateMock;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
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

@ExtendWith({ MockBukkitExtension.class })
class BlockDataMockTest
{

	@MockBukkitInject
	ServerMock server;

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
		System.out.println(bedDataMock.getFacing());
		assertNotNull(bedDataMock.getFacing());
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
