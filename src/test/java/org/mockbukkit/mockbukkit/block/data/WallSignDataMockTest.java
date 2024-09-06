package org.mockbukkit.mockbukkit.block.data;

import org.mockbukkit.mockbukkit.MockBukkitExtension;
import org.bukkit.Material;
import org.bukkit.Tag;
import org.bukkit.block.BlockFace;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertThrowsExactly;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(MockBukkitExtension.class)
class WallSignDataMockTest
{

	private WallSignDataMock sign;

	@BeforeEach
	void setUp()
	{
		sign = new WallSignDataMock(Material.ACACIA_WALL_SIGN);
	}

	@Test
	void constructor_DefaultValues()
	{
		assertEquals(BlockFace.NORTH, sign.getFacing());
		assertFalse(sign.isWaterlogged());
	}

	@Test
	void constructor_Material()
	{
		for (Material wallSignType : Tag.WALL_SIGNS.getValues())
		{
			assertDoesNotThrow(() -> new WallSignDataMock(wallSignType));
		}
	}

	@Test
	void constructor_Material_WrongType_ThrowsException()
	{
		assertThrowsExactly(IllegalArgumentException.class, () -> new WallSignDataMock(Material.BEDROCK));
	}

	@Test
	void setFacing_Valid()
	{
		for (BlockFace face : BlockFace.values())
		{
			if (!sign.getFaces().contains(face))
				continue;
			assertDoesNotThrow(() -> sign.setFacing(face));
			assertEquals(face, sign.getFacing());
		}
	}

	@Test
	void setFacing_Invalid()
	{
		for (BlockFace face : BlockFace.values())
		{
			if (sign.getFaces().contains(face))
				continue;
			assertThrowsExactly(IllegalArgumentException.class, () -> sign.setFacing(face));
		}
	}

	@Test
	void getFaces()
	{
		Set<BlockFace> validFaces = Set.of(BlockFace.NORTH, BlockFace.EAST, BlockFace.SOUTH, BlockFace.WEST);
		assertEquals(validFaces, sign.getFaces());
	}

	@Test
	void getFacing_Immutable()
	{
		Set<BlockFace> faces = sign.getFaces();
		assertThrows(UnsupportedOperationException.class, () -> faces.add(BlockFace.NORTH_EAST));
	}

	@Test
	void getFacing_notNull()
	{
		assertThrows(NullPointerException.class, () -> sign.setFacing(null));
	}

	@Test
	void setWaterLogged()
	{
		sign.setWaterlogged(true);
		assertTrue(sign.isWaterlogged());
		sign.setWaterlogged(false);
		assertFalse(sign.isWaterlogged());
	}

	@Test
	void getAsString()
	{
		sign.setWaterlogged(true);
		sign.setFacing(BlockFace.SOUTH);
		assertEquals("minecraft:acacia_wall_sign[facing=south,waterlogged=true]", sign.getAsString());
	}

	@Test
	void blockDataMock_Mock_CorrectType()
	{
		for (Material material : Tag.WALL_SIGNS.getValues())
		{
			assertInstanceOf(WallSignDataMock.class, BlockDataMock.mock(material));
		}
	}

}
