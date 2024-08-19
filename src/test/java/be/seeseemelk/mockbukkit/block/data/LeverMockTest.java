package be.seeseemelk.mockbukkit.block.data;

import be.seeseemelk.mockbukkit.MockBukkit;
import org.bukkit.Material;
import org.bukkit.block.BlockFace;
import org.bukkit.block.data.FaceAttachable.AttachedFace;
import org.bukkit.block.data.type.Switch.Face;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.Set;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class LeverMockTest
{

	LeverMock leverMock;

	@BeforeEach
	void setUp()
	{
		MockBukkit.getOrCreateMock();
		leverMock = new LeverMock(Material.LEVER);
	}

	@AfterEach
	void tearDown()
	{
		MockBukkit.unmock();
	}

	@Test
	void constructor_DefaultValues()
	{
		assertEquals(BlockFace.NORTH, leverMock.getFacing());
		assertFalse(leverMock.isPowered());
	}

	@ParameterizedTest
	@MethodSource("getPossibleMaterials")
	void constructor_Material(Material material)
	{
		assertDoesNotThrow(() -> new LeverMock(material));
	}

	@Test
	void constructor_Material_WrongType_ThrowsException()
	{
		assertThrowsExactly(IllegalArgumentException.class, () -> new LeverMock(Material.BEDROCK));
	}

	@Test
	void setFacing_Valid()
	{
		for (BlockFace face : BlockFace.values())
		{
			if (!leverMock.getFaces().contains(face))
				continue;
			assertDoesNotThrow(() -> leverMock.setFacing(face));
			assertEquals(face, leverMock.getFacing());
		}
	}

	@Test
	void setFacing_Invalid()
	{
		for (BlockFace face : BlockFace.values())
		{
			if (leverMock.getFaces().contains(face))
				continue;
			assertThrowsExactly(IllegalArgumentException.class, () -> leverMock.setFacing(face));
		}
	}

	@Test
	void getFaces()
	{
		Set<BlockFace> validFaces = Set.of(BlockFace.NORTH, BlockFace.EAST, BlockFace.SOUTH, BlockFace.WEST);
		assertEquals(validFaces, leverMock.getFaces());
	}

	@Test
	void getFacing_Immutable()
	{
		Set<BlockFace> faces = leverMock.getFaces();
		assertThrows(UnsupportedOperationException.class, () -> faces.add(BlockFace.NORTH_EAST));
	}

	@Test
	void setFacing_notNull()
	{
		assertThrows(NullPointerException.class, () -> leverMock.setFacing(null));
	}

	@Test
	void setPowered()
	{
		leverMock.setPowered(true);
		assertTrue(leverMock.isPowered());
		leverMock.setPowered(false);
		assertFalse(leverMock.isPowered());
	}

	@Test
	void getAsString()
	{
		leverMock.setFacing(BlockFace.NORTH);
		leverMock.setAttachedFace(AttachedFace.WALL);
		leverMock.setPowered(false);
		assertEquals("minecraft:lever[face=wall,facing=north,powered=false]", leverMock.getAsString());
	}

	@ParameterizedTest
	@EnumSource
	void setAttachedFace(AttachedFace face)
	{
		leverMock.setAttachedFace(face);
		assertEquals(face, leverMock.getAttachedFace());
	}

	@Test
	void setAttachedFace_notNull()
	{
		assertThrows(NullPointerException.class, () -> leverMock.setAttachedFace(null));
	}

	@ParameterizedTest
	@MethodSource("getPossibleMaterials")
	void blockDataMock_Mock_CorrectType(Material material)
	{
		assertInstanceOf(LeverMock.class, BlockDataMock.mock(material));
	}

	@ParameterizedTest
	@EnumSource
	void setFace(Face face)
	{
		leverMock.setFace(face);
		assertEquals(face, leverMock.getFace());
	}

	private static Stream<Material> getPossibleMaterials()
	{
		// Tags requires MockBukkit to have been mocked once before
		MockBukkit.mock();
		return Stream.of(Material.LEVER);
	}

}
