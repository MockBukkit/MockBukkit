package be.seeseemelk.mockbukkit.block.data;

import be.seeseemelk.mockbukkit.MockBukkit;
import org.bukkit.Material;
import org.bukkit.Tag;
import org.bukkit.block.BlockFace;
import org.bukkit.block.data.FaceAttachable.AttachedFace;
import org.bukkit.block.data.type.Switch.Face;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertThrowsExactly;
import static org.junit.jupiter.api.Assertions.assertTrue;

class SwitchMockTest
{

	SwitchMock switchMock;

	@BeforeEach
	void setUp()
	{
		MockBukkit.getOrCreateMock();
		switchMock = new SwitchMock(Material.ACACIA_BUTTON);
	}

	@AfterEach
	void tearDown()
	{
		MockBukkit.unmock();
	}

	@Test
	void constructor_DefaultValues()
	{
		assertEquals(BlockFace.NORTH, switchMock.getFacing());
		assertFalse(switchMock.isPowered());
	}

	@ParameterizedTest
	@MethodSource("getPossibleMaterials")
	void constructor_Material(Material material)
	{
		assertDoesNotThrow(() -> new SwitchMock(material));
	}

	@Test
	void constructor_Material_WrongType_ThrowsException()
	{
		assertThrowsExactly(IllegalArgumentException.class, () -> new SwitchMock(Material.BEDROCK));
	}

	@Test
	void setFacing_Valid()
	{
		for (BlockFace face : BlockFace.values())
		{
			if (!switchMock.getFaces().contains(face))
				continue;
			assertDoesNotThrow(() -> switchMock.setFacing(face));
			assertEquals(face, switchMock.getFacing());
		}
	}

	@Test
	void setFacing_Invalid()
	{
		for (BlockFace face : BlockFace.values())
		{
			if (switchMock.getFaces().contains(face))
				continue;
			assertThrowsExactly(IllegalArgumentException.class, () -> switchMock.setFacing(face));
		}
	}

	@Test
	void getFaces()
	{
		Set<BlockFace> validFaces = Set.of(BlockFace.NORTH, BlockFace.EAST, BlockFace.SOUTH, BlockFace.WEST);
		assertEquals(validFaces, switchMock.getFaces());
	}

	@Test
	void getFacing_Immutable()
	{
		Set<BlockFace> faces = switchMock.getFaces();
		assertThrows(UnsupportedOperationException.class, () -> faces.add(BlockFace.NORTH_EAST));
	}

	@Test
	void setFacing_notNull()
	{
		assertThrows(NullPointerException.class, () -> switchMock.setFacing(null));
	}

	@Test
	void setPowered()
	{
		switchMock.setPowered(true);
		assertTrue(switchMock.isPowered());
		switchMock.setPowered(false);
		assertFalse(switchMock.isPowered());
	}

	@Test
	void getAsString()
	{
		switchMock.setFacing(BlockFace.NORTH);
		switchMock.setAttachedFace(AttachedFace.WALL);
		switchMock.setPowered(false);
		assertEquals("minecraft:acacia_button[face=wall,facing=north,powered=false]", switchMock.getAsString());
	}

	@ParameterizedTest
	@EnumSource
	void setAttachedFace(AttachedFace face)
	{
		switchMock.setAttachedFace(face);
		assertEquals(face, switchMock.getAttachedFace());
	}

	@Test
	void setAttachedFace_notNull()
	{
		assertThrows(NullPointerException.class, () -> switchMock.setAttachedFace(null));
	}

	@ParameterizedTest
	@MethodSource("getPossibleMaterials")
	void blockDataMock_Mock_CorrectType(Material material)
	{
		assertInstanceOf(SwitchMock.class, BlockDataMock.mock(material));
	}

	@ParameterizedTest
	@EnumSource
	void setFace(Face face)
	{
		switchMock.setFace(face);
		assertEquals(face, switchMock.getFace());
	}

	private static Stream<Material> getPossibleMaterials()
	{
		// Tags requires MockBukkit to have been mocked once before
		MockBukkit.mock();
		Set<Material> possibleMaterials = new HashSet<>(Tag.BUTTONS.getValues());
		possibleMaterials.add(Material.LEVER);
		return Stream.of(possibleMaterials.toArray(new Material[possibleMaterials.size()]));
	}

}
