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

class SwitchMockTest
{

	SwitchMock switchMock;

	@BeforeEach
	void setUp()
	{
		MockBukkit.mock();
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

	@Test
	void constructor_Material()
	{
		for (Material button : Tag.BUTTONS.getValues())
		{
			assertDoesNotThrow(() -> new SwitchMock(button));
		}
		assertDoesNotThrow(() -> new SwitchMock(Material.LEVER));

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
	void setPowered() {
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
	
	void setAttachedFace() {
		for(AttachedFace face : AttachedFace.values()) {
			switchMock.setAttachedFace(face);
			assertEquals(face,switchMock.getAttachedFace());
		}
	}
	
	@Test
	void setAttachedFace_notNull()
	{
		assertThrows(NullPointerException.class, () -> switchMock.setAttachedFace(null));
	}

	@Test
	void blockDataMock_Mock_CorrectType()
	{
		for (Material material : Tag.BUTTONS.getValues())
		{
			assertInstanceOf(SwitchMock.class, BlockDataMock.mock(material));
		}
		assertInstanceOf(SwitchMock.class, BlockDataMock.mock(Material.LEVER));
	}
	
	
	
}
