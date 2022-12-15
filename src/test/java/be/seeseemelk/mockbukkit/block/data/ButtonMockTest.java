package be.seeseemelk.mockbukkit.block.data;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Set;

import org.bukkit.Material;
import org.bukkit.Tag;
import org.bukkit.block.BlockFace;
import org.bukkit.block.data.FaceAttachable.AttachedFace;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import be.seeseemelk.mockbukkit.MockBukkit;

class ButtonMockTest
{

	ButtonMock button;

	@BeforeEach
	void setUp()
	{
		MockBukkit.mock();
		button = new ButtonMock(Material.ACACIA_BUTTON);
	}

	@AfterEach
	void tearDown()
	{
		MockBukkit.unmock();
	}

	@Test
	void constructor_DefaultValues()
	{
		assertEquals(BlockFace.NORTH, button.getFacing());
		assertFalse(button.isPowered());
	}

	@Test
	void constructor_Material()
	{
		for (Material button : Tag.BUTTONS.getValues())
		{
			assertDoesNotThrow(() -> new ButtonMock(button));
		}

	}

	@Test
	void constructor_Material_WrongType_ThrowsException()
	{
		assertThrowsExactly(IllegalArgumentException.class, () -> new WallSignMock(Material.BEDROCK));
	}
	
	@Test
	void setFacing_Valid()
	{
		for (BlockFace face : BlockFace.values())
		{
			if (!button.getFaces().contains(face))
				continue;
			assertDoesNotThrow(() -> button.setFacing(face));
			assertEquals(face, button.getFacing());
		}
	}

	@Test
	void setFacing_Invalid()
	{
		for (BlockFace face : BlockFace.values())
		{
			if (button.getFaces().contains(face))
				continue;
			assertThrowsExactly(IllegalArgumentException.class, () -> button.setFacing(face));
		}
	}

	@Test
	void getFaces()
	{
		Set<BlockFace> validFaces = Set.of(BlockFace.NORTH, BlockFace.EAST, BlockFace.SOUTH, BlockFace.WEST);
		assertEquals(validFaces, button.getFaces());
	}
	
	@Test
	void getFacing_Immutable()
	{
		Set<BlockFace> faces = button.getFaces();
		assertThrows(UnsupportedOperationException.class, () -> faces.add(BlockFace.NORTH_EAST));
	}

	@Test
	void setFacing_notNull()
	{
		assertThrows(NullPointerException.class, () -> button.setFacing(null));
	}
	
	@Test
	void setPowered() {
		button.setPowered(true);
		assertTrue(button.isPowered());
		button.setPowered(false);
		assertFalse(button.isPowered());
	}
	
	@Test
	void getAsString()
	{
		button.setFacing(BlockFace.NORTH);
		button.setAttachedFace(AttachedFace.WALL);
		button.setPowered(false);
		assertEquals("minecraft:acacia_button[face=wall,facing=north,powered=false]", button.getAsString());
	}
	
	void setAttachedFace() {
		for(AttachedFace face : AttachedFace.values()) {
			button.setAttachedFace(face);
			assertEquals(face,button.getAttachedFace());
		}
	}
	
	@Test
	void setAttachedFace_notNull()
	{
		assertThrows(NullPointerException.class, () -> button.setAttachedFace(null));
	}

	@Test
	void blockDataMock_Mock_CorrectType()
	{
		for (Material material : Tag.BUTTONS.getValues())
		{
			assertInstanceOf(ButtonMock.class, BlockDataMock.mock(material));
		}
	}
	
	
	
}
