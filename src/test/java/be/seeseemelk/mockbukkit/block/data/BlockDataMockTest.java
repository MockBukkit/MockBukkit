package be.seeseemelk.mockbukkit.block.data;

import be.seeseemelk.mockbukkit.MockBukkitExtension;
import be.seeseemelk.mockbukkit.MockBukkitInject;
import be.seeseemelk.mockbukkit.ServerMock;
import be.seeseemelk.mockbukkit.block.BlockMock;
import org.bukkit.Material;
import org.bukkit.Tag;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.data.BlockData;
import org.bukkit.block.data.type.WallSign;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotSame;
import static org.junit.jupiter.api.Assertions.assertThrowsExactly;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(
{ MockBukkitExtension.class })
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
	void testGetWithNonExistentKey()
	{
		// Stone has no possible states
		BlockDataMock blockData = new BlockDataMock(Material.STONE);

		assertThrowsExactly(IllegalStateException.class, () -> blockData.get("non-existent-key"));
	}

	@Test
	void testGetWithNonExistentKey2()
	{
		// Stone has no possible states
		BlockDataMock blockData = new BlockDataMock(Material.ACACIA_BUTTON);

		assertThrowsExactly(IllegalStateException.class, () -> blockData.get("non-existent-key"));

		// Check the defaults:
		assertEquals(false, blockData.get("powered"));
		assertEquals("wall", blockData.get("face"));
		assertEquals("north", blockData.get("facing"));
	}

	@Test
	void testHashCode()
	{
		BlockDataMock blockData = new BlockDataMock(Material.ACACIA_BUTTON);
		BlockDataMock blockData2 = new BlockDataMock(Material.ACACIA_BUTTON);
		assertEquals(blockData2.hashCode(), blockData.hashCode());

		blockData.set("powered", true);
		assertNotEquals(blockData2.hashCode(), blockData.hashCode());
	}

	@Test
	void testMatchesNotEquals()
	{
		BlockDataMock blockData = new BlockDataMock(Material.ACACIA_BUTTON);
		BlockDataMock blockData2 = new BlockDataMock(Material.ACACIA_BUTTON);
		blockData2.set("powered", true);

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
		// "minecraft:chest": {
		// "facing": "north",
		// "type": "single",
		// "waterlogged": false
		// },
		//
		// getAsString(true) : minecraft:chest[waterlogged=true]
		// getAsString(false):
		// minecraft:chest[facing=north,type=single,waterlogged=true]
		// getAsString() : minecraft:chest[facing=north,type=single,waterlogged=true]

		BlockData tmp = Material.CHEST.createBlockData();
		String a = tmp.getAsString(false);
		String b = tmp.getAsString(true);

		BlockDataMock data = new BlockDataMock(Material.CHEST);
		data.set("waterlogged", "true");

		assertEquals("minecraft:chest[waterlogged=true]", data.getAsString(true));
		assertEquals("minecraft:chest[facing=north,type=single,waterlogged=true]", data.getAsString(false));
		assertEquals("minecraft:chest[facing=north,type=single,waterlogged=true]", data.getAsString());

		data = new BlockDataMock(Material.CHEST);
		assertEquals("minecraft:chest", data.getAsString(true));
		assertEquals("minecraft:chest[facing=north,type=single,waterlogged=false]", data.getAsString(false));
		assertEquals("minecraft:chest[facing=north,type=single,waterlogged=false]", data.getAsString());
	}

}
