package be.seeseemelk.mockbukkit.block.data;

import be.seeseemelk.mockbukkit.MockBukkit;
import com.google.common.collect.ImmutableSet;
import org.bukkit.Material;
import org.bukkit.Tag;
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
import static org.junit.jupiter.api.Assertions.assertThrowsExactly;
import static org.junit.jupiter.api.Assertions.fail;

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
	void getFacing_ImmutableSet()
	{
		assertInstanceOf(ImmutableSet.class, bed.getFaces());
	}

	@Test
	void getAsString()
	{
		assertEquals("minecraft:red_bed[facing=north,occupied=false,part=foot]", bed.getAsString());
	}

	@Test
	void blockDataMock_Mock_CorrectType()
	{
		for (Material material : Material.values())
		{
			if (Tag.BEDS.isTagged(material) && !(BlockDataMock.mock(material) instanceof BedMock))
			{
				fail("BlockDataMock for '" + material + "' is not a " + BedMock.class.getSimpleName());
			}
		}
	}

	@Test
	void testConstructor_InvalidMaterial()
	{
		assertThrowsExactly(IllegalArgumentException.class, () -> new BedMock(Material.BARRIER));
	}

}
