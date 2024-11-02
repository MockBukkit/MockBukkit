package org.mockbukkit.mockbukkit.block.data;

import org.mockbukkit.mockbukkit.MockBukkit;
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

class SwitchDataMockTest
{

	SwitchDataMock switchDataMock;

	@BeforeEach
	void setUp()
	{
		MockBukkit.getOrCreateMock();
		switchDataMock = new SwitchDataMock(Material.ACACIA_BUTTON);
	}

	@AfterEach
	void tearDown()
	{
		MockBukkit.unmock();
	}

	@Test
	void constructor_DefaultValues()
	{
		assertEquals(BlockFace.NORTH, switchDataMock.getFacing());
		assertFalse(switchDataMock.isPowered());
	}

	@ParameterizedTest
	@MethodSource("getPossibleMaterials")
	void constructor_Material(Material material)
	{
		assertDoesNotThrow(() -> new SwitchDataMock(material));
	}

	@Test
	void constructor_Material_WrongType_ThrowsException()
	{
		assertThrowsExactly(IllegalArgumentException.class, () -> new SwitchDataMock(Material.BEDROCK));
	}

	@Test
	void setFacing_Valid()
	{
		for (BlockFace face : BlockFace.values())
		{
			if (!switchDataMock.getFaces().contains(face))
				continue;
			assertDoesNotThrow(() -> switchDataMock.setFacing(face));
			assertEquals(face, switchDataMock.getFacing());
		}
	}

	@Test
	void setFacing_Invalid()
	{
		for (BlockFace face : BlockFace.values())
		{
			if (switchDataMock.getFaces().contains(face))
				continue;
			assertThrowsExactly(IllegalArgumentException.class, () -> switchDataMock.setFacing(face));
		}
	}

	@Test
	void getFaces()
	{
		Set<BlockFace> validFaces = Set.of(BlockFace.NORTH, BlockFace.EAST, BlockFace.SOUTH, BlockFace.WEST);
		assertEquals(validFaces, switchDataMock.getFaces());
	}

	@Test
	void getFacing_Immutable()
	{
		Set<BlockFace> faces = switchDataMock.getFaces();
		assertThrows(UnsupportedOperationException.class, () -> faces.add(BlockFace.NORTH_EAST));
	}

	@Test
	void setFacing_notNull()
	{
		assertThrows(NullPointerException.class, () -> switchDataMock.setFacing(null));
	}

	@Test
	void setPowered()
	{
		switchDataMock.setPowered(true);
		assertTrue(switchDataMock.isPowered());
		switchDataMock.setPowered(false);
		assertFalse(switchDataMock.isPowered());
	}

	@Test
	void getAsString()
	{
		switchDataMock.setFacing(BlockFace.NORTH);
		switchDataMock.setAttachedFace(AttachedFace.WALL);
		switchDataMock.setPowered(false);
		assertEquals("minecraft:acacia_button[face=wall,facing=north,powered=false]", switchDataMock.getAsString());
	}

	@ParameterizedTest
	@EnumSource
	void setAttachedFace(AttachedFace face)
	{
		switchDataMock.setAttachedFace(face);
		assertEquals(face, switchDataMock.getAttachedFace());
	}

	@Test
	void setAttachedFace_notNull()
	{
		assertThrows(NullPointerException.class, () -> switchDataMock.setAttachedFace(null));
	}

	@ParameterizedTest
	@MethodSource("getPossibleMaterials")
	void blockDataMock_Mock_CorrectType(Material material)
	{
		assertInstanceOf(SwitchDataMock.class, BlockDataMock.mock(material));
	}

	@ParameterizedTest
	@EnumSource
	void setFace(Face face)
	{
		switchDataMock.setFace(face);
		assertEquals(face, switchDataMock.getFace());
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
