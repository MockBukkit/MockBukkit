package be.seeseemelk.mockbukkit.block.data;

import be.seeseemelk.mockbukkit.MockBukkitExtension;
import org.bukkit.Material;
import org.bukkit.Tag;
import org.bukkit.block.BlockFace;
import org.bukkit.block.data.Bisected;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertThrowsExactly;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(MockBukkitExtension.class)
class TrapDoorMockTest
{

	private static final Set<BlockFace> VALID_FACES = Set.of(BlockFace.NORTH, BlockFace.SOUTH, BlockFace.EAST,
			BlockFace.WEST);

	private TrapDoorMock trapDoor;

	@BeforeEach
	void setUp()
	{
		this.trapDoor = new TrapDoorMock(Material.BIRCH_TRAPDOOR);
	}

	@Test
	void constructor_DefaultValues()
	{
		assertEquals(Bisected.Half.BOTTOM, trapDoor.getHalf());
		assertFalse(trapDoor.isOpen());
		assertFalse(trapDoor.isPowered());
		assertFalse(trapDoor.isWaterlogged());
		assertEquals(BlockFace.NORTH, trapDoor.getFacing());
	}

	@Test
	void constructor_Material()
	{
		assertDoesNotThrow(() -> new TrapDoorMock(Material.BIRCH_TRAPDOOR));
	}

	@Test
	void constructor_Material_WrongType_ThrowsException()
	{
		assertThrowsExactly(IllegalArgumentException.class, () -> new TrapDoorMock(Material.WET_SPONGE));
	}

	@Test
	void setHalf_Valid()
	{
		trapDoor.setHalf(Bisected.Half.TOP);
		assertEquals(Bisected.Half.TOP, trapDoor.getHalf());
	}

	@Test
	void setHalf_NullInput_ThrowsException()
	{
		assertThrowsExactly(NullPointerException.class, () -> trapDoor.setHalf(null));
	}

	@Test
	void setFacing_Valid()
	{
		for (BlockFace face : VALID_FACES)
		{
			trapDoor.setFacing(face);
			assertEquals(face, trapDoor.getFacing());
		}
	}

	@Test
	void setFacing_Invalid_ThrowsException()
	{
		final Set<BlockFace> invalidFaces = Arrays.stream(BlockFace.values())
				.filter(face -> !VALID_FACES.contains(face)).collect(Collectors.toSet());
		for (BlockFace invalid : invalidFaces)
		{
			assertThrowsExactly(IllegalArgumentException.class, () -> trapDoor.setFacing(invalid));
		}
	}

	@Test
	void setFacing_NullInput_ThrowsException()
	{
		assertThrowsExactly(NullPointerException.class, () -> trapDoor.setFacing(null));
	}

	@Test
	void getFaces_HasCorrectValues()
	{
		assertEquals(VALID_FACES, trapDoor.getFaces());
	}

	@Test
	void setOpen_Valid()
	{
		trapDoor.setOpen(true);
		assertTrue(trapDoor.isOpen());
	}

	@Test
	void setPowered_Valid()
	{
		trapDoor.setPowered(true);
		assertTrue(trapDoor.isPowered());
	}

	@Test
	void setWaterlogged_Valid()
	{
		trapDoor.setWaterlogged(true);
		assertTrue(trapDoor.isWaterlogged());
	}

	@Test
	void blockDataMock_Mock_CorrectType()
	{
		for (Material material : Tag.TRAPDOORS.getValues())
		{
			assertInstanceOf(TrapDoorMock.class, BlockDataMock.mock(material));
		}
	}

}
