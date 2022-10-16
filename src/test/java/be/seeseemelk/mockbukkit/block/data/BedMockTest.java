package be.seeseemelk.mockbukkit.block.data;

import be.seeseemelk.mockbukkit.MockBukkit;
import com.destroystokyo.paper.MaterialTags;
import org.bukkit.Material;
import org.bukkit.block.BlockFace;
import org.bukkit.block.data.type.Bed;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertThrowsExactly;
import static org.junit.jupiter.api.Assertions.assertTrue;

class BedMockTest
{

	private BedMock bed;

	@BeforeEach
	void setUp()
	{
		MockBukkit.mock();
		this.bed = new BedMock(Material.RED_BED);
	}

	@AfterEach
	void teardown()
	{
		MockBukkit.unmock();
	}

	@Test
	void constructor_DefaultValues()
	{
		assertEquals(Bed.Part.FOOT, bed.getPart());
		assertFalse(bed.isOccupied());
		assertEquals(BlockFace.NORTH, bed.getFacing());
	}

	@Test
	void constructor_Material()
	{
		assertDoesNotThrow(() -> new BedMock(Material.RED_BED));
	}

	@Test
	void constructor_Material_WrongType_ThrowsException()
	{
		assertThrowsExactly(IllegalArgumentException.class, () -> new BedMock(Material.BEDROCK));
	}

	@Test
	void setPart_Null_ThrowsException()
	{
		assertThrowsExactly(NullPointerException.class, () -> bed.setPart(null));
	}

	@Test
	void setPart_Valid()
	{
		bed.setPart(Bed.Part.HEAD);
		assertEquals(Bed.Part.HEAD, bed.getPart());
	}

	@Test
	void setOccupied_Valid()
	{
		bed.setOccupied(true);
		assertTrue(bed.isOccupied());
	}

	@Test
	void setFacing_Valid()
	{
		for (BlockFace face : BlockFace.values())
		{
			if (!bed.getFaces().contains(face))
				continue;
			assertDoesNotThrow(() -> bed.setFacing(face));
		}
	}

	@Test
	void setFacing_Invalid()
	{
		for (BlockFace face : BlockFace.values())
		{
			if (bed.getFaces().contains(face))
				continue;
			assertThrowsExactly(IllegalArgumentException.class, () -> bed.setFacing(face));
		}
	}

	@Test
	void getFaces()
	{
		Set<BlockFace> validFaces = Set.of(BlockFace.NORTH, BlockFace.EAST, BlockFace.SOUTH, BlockFace.WEST);
		assertEquals(validFaces, bed.getFaces());
	}

	@Test
	void getFacing_Immutable()
	{
		Set<BlockFace> faces = bed.getFaces();
		assertThrows(UnsupportedOperationException.class, () -> faces.add(BlockFace.NORTH_EAST));
	}

	@Test
	void getAsString()
	{
		assertEquals("minecraft:red_bed[facing=north,occupied=false,part=foot]", bed.getAsString());
	}

	@Test
	void blockDataMock_Mock_CorrectType()
	{
		for (Material material : MaterialTags.BEDS.getValues())
		{
			assertInstanceOf(BedMock.class, BlockDataMock.mock(material));
		}
	}

}
