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

class ButtonMockTest
{

	ButtonMock buttonMock;

	@BeforeEach
	void setUp()
	{
		MockBukkit.getOrCreateMock();
		buttonMock = new ButtonMock(Material.ACACIA_BUTTON);
	}

	@AfterEach
	void tearDown()
	{
		MockBukkit.unmock();
	}

	@Test
	void constructor_DefaultValues()
	{
		assertEquals(BlockFace.NORTH, buttonMock.getFacing());
		assertFalse(buttonMock.isPowered());
	}

	@ParameterizedTest
	@MethodSource("getPossibleMaterials")
	void constructor_Material(Material material)
	{
		assertDoesNotThrow(() -> new ButtonMock(material));
	}

	@Test
	void constructor_Material_WrongType_ThrowsException()
	{
		assertThrowsExactly(IllegalArgumentException.class, () -> new ButtonMock(Material.BEDROCK));
	}

	@Test
	void setFacing_Valid()
	{
		for (BlockFace face : BlockFace.values())
		{
			if (!buttonMock.getFaces().contains(face))
				continue;
			assertDoesNotThrow(() -> buttonMock.setFacing(face));
			assertEquals(face, buttonMock.getFacing());
		}
	}

	@Test
	void setFacing_Invalid()
	{
		for (BlockFace face : BlockFace.values())
		{
			if (buttonMock.getFaces().contains(face))
				continue;
			assertThrowsExactly(IllegalArgumentException.class, () -> buttonMock.setFacing(face));
		}
	}

	@Test
	void getFaces()
	{
		Set<BlockFace> validFaces = Set.of(BlockFace.NORTH, BlockFace.EAST, BlockFace.SOUTH, BlockFace.WEST);
		assertEquals(validFaces, buttonMock.getFaces());
	}

	@Test
	void getFacing_Immutable()
	{
		Set<BlockFace> faces = buttonMock.getFaces();
		assertThrows(UnsupportedOperationException.class, () -> faces.add(BlockFace.NORTH_EAST));
	}

	@Test
	void setFacing_notNull()
	{
		assertThrows(NullPointerException.class, () -> buttonMock.setFacing(null));
	}

	@Test
	void setPowered()
	{
		buttonMock.setPowered(true);
		assertTrue(buttonMock.isPowered());
		buttonMock.setPowered(false);
		assertFalse(buttonMock.isPowered());
	}

	@Test
	void getAsString()
	{
		buttonMock.setFacing(BlockFace.NORTH);
		buttonMock.setAttachedFace(AttachedFace.WALL);
		buttonMock.setPowered(false);
		assertEquals("minecraft:acacia_button[face=wall,facing=north,powered=false]", buttonMock.getAsString());
	}

	@ParameterizedTest
	@EnumSource
	void setAttachedFace(AttachedFace face)
	{
		buttonMock.setAttachedFace(face);
		assertEquals(face, buttonMock.getAttachedFace());
	}

	@Test
	void setAttachedFace_notNull()
	{
		assertThrows(NullPointerException.class, () -> buttonMock.setAttachedFace(null));
	}

	@ParameterizedTest
	@MethodSource("getPossibleMaterials")
	void blockDataMock_Mock_CorrectType(Material material)
	{
		assertInstanceOf(ButtonMock.class, BlockDataMock.mock(material));
	}

	@ParameterizedTest
	@EnumSource
	void setFace(Face face)
	{
		buttonMock.setFace(face);
		assertEquals(face, buttonMock.getFace());
	}

	private static Stream<Material> getPossibleMaterials()
	{
		// Tags requires MockBukkit to have been mocked once before
		MockBukkit.mock();
		Set<Material> possibleMaterials = new HashSet<>(Tag.BUTTONS.getValues());
		return Stream.of(possibleMaterials.toArray(new Material[possibleMaterials.size()]));
	}

}
