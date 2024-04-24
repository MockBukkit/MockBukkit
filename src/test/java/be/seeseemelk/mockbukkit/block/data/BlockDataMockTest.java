package be.seeseemelk.mockbukkit.block.data;

import be.seeseemelk.mockbukkit.MockBukkitExtension;
import be.seeseemelk.mockbukkit.MockBukkitInject;
import be.seeseemelk.mockbukkit.ServerMock;
import be.seeseemelk.mockbukkit.block.BlockMock;
import org.bukkit.Material;
import org.bukkit.Tag;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.data.type.WallSign;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.util.LinkedHashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotSame;
import static org.junit.jupiter.api.Assertions.assertThrowsExactly;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith({ MockBukkitExtension.class})
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
		BlockDataMock blockData = new BlockDataMock(Material.STONE);

		assertThrowsExactly(IllegalArgumentException.class, () -> blockData.get("non-existent-key"));
	}

	@Test
	void testHashCode()
	{
		BlockDataMock blockData = new BlockDataMock(Material.STONE);
		BlockDataMock blockData2 = new BlockDataMock(Material.STONE);
		assertEquals(blockData2.hashCode(), blockData.hashCode());

		blockData.set("key", "value");
		assertNotEquals(blockData2.hashCode(), blockData.hashCode());
	}

	@Test
	void testMatchesNotEquals()
	{
		BlockDataMock blockData = new BlockDataMock(Material.STONE);
		BlockDataMock blockData2 = new BlockDataMock(Material.STONE);
		blockData2.set("key", "value");

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
		blockData.checkType(block, Material.STONE);
	}

	@Test
	void testCheckTypeBlockWrongType()
	{
		BlockDataMock blockData = new BlockDataMock(Material.STONE);
		Block block = new BlockMock(Material.DIRT);
		assertThrowsExactly(IllegalArgumentException.class, () -> blockData.checkType(block, Material.STONE));
	}

	@Test
	void testCheckTypeBlockTag()
	{
		BlockDataMock blockData = new BlockDataMock(Material.ACACIA_PLANKS);
		Block block = new BlockMock(Material.ACACIA_PLANKS);
		blockData.checkType(block, Tag.PLANKS);
	}

	@Test
	void clone_isClone()
	{
		WallSign wallSign = (WallSign) BlockDataMock.mock(Material.ACACIA_WALL_SIGN);
		wallSign.setFacing(BlockFace.NORTH);
		WallSign clone = (WallSign) wallSign.clone();
		assertNotSame(wallSign,clone);
		assertEquals(wallSign,clone);
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
		// ```java
		//		String dataString = "minecraft:chest[waterlogged=true]"
		//		BlockData data = Bukkit.createBlockData(dataString);
		//		dataString.equals(data.getAsString(true)); // This would return true
		//		dataString.equals(data.getAsString(false)); // This would return false as all states are present
		//		dataString.equals(data.getAsString()); // This is equivalent to the above, "getAsString(false)"
		// ```

		BlockDataMock data = new BlockDataMock(Material.CHEST);
		data.set("waterlogged", "true");

		assertEquals("minecraft:chest[waterlogged=true]", data.getAsString(true));
		assertEquals("minecraft:chest", data.getAsString(false));
		assertEquals("minecraft:chest", data.getAsString());

		data.set("multi", "value2");

		assertEquals("minecraft:chest[waterlogged=true,multi=value2]", data.getAsString(true));
	}

}
