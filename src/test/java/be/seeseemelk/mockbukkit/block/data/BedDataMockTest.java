package be.seeseemelk.mockbukkit.block.data;

import org.bukkit.Material;
import org.bukkit.block.BlockFace;
import org.bukkit.block.data.type.Bed;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.fail;

public class BedDataMockTest
{

	@Test
	void constructor_DefaultValues()
	{
		BedDataMock bed = new BedDataMock(Material.RED_BED);

		assertEquals(Bed.Part.FOOT, bed.getPart());
		assertFalse(bed.isOccupied());
		assertEquals(BlockFace.NORTH, bed.getFacing());
	}

	@Test
	void getAsString_NoData()
	{
		BedDataMock bed = new BedDataMock(Material.RED_BED);

		assertEquals("minecraft:red_bed[facing=north,occupied=false,part=foot]", bed.getAsString());
	}

	@Test
	void blockDataMock_Mock_CorrectType()
	{
		for (Material material : Material.values())
		{
			if (material.name().endsWith("_BED") && !(BlockDataMock.mock(material) instanceof BedDataMock))
			{
				fail("BlockDataMock for '" + material + "' is not a " + BedDataMock.class.getSimpleName());
			}
		}
	}

}
