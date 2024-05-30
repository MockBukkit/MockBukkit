package be.seeseemelk.mockbukkit.block.state;

import be.seeseemelk.mockbukkit.MockBukkit;
import be.seeseemelk.mockbukkit.block.BlockMock;
import com.destroystokyo.paper.MaterialTags;
import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotSame;
import static org.junit.jupiter.api.Assertions.assertThrowsExactly;
import static org.junit.jupiter.api.Assertions.fail;

class BedMockTest
{

	@BeforeEach
	void setup()
	{
		MockBukkit.mock();
	}

	@AfterEach
	void teardown()
	{
		MockBukkit.unmock();
	}

	@Test
	void constructor_Material()
	{
		for (Material value : MaterialTags.BEDS.getValues())
		{
			assertDoesNotThrow(() -> new BedMock(value));
		}
	}

	@Test
	void constructor_Material_WrongType_ThrowsException()
	{
		assertThrowsExactly(IllegalArgumentException.class, () -> new BedMock(Material.BEDROCK));
	}

	@Test
	void constructor_Block()
	{
		for (Material value : MaterialTags.BEDS.getValues())
		{
			assertDoesNotThrow(() -> new BedMock(new BlockMock(value)));
		}
	}

	@Test
	void constructor_Block_WrongType_ThrowsException()
	{
		assertThrowsExactly(IllegalArgumentException.class, () -> new BedMock(new BlockMock(Material.BEDROCK)));
	}

	@Test
	void getColor_ReturnCorrectColor()
	{
		assertEquals(new BedMock(Material.BLACK_BED).getColor(), DyeColor.BLACK);
		assertEquals(new BedMock(Material.BLUE_BED).getColor(), DyeColor.BLUE);
		assertEquals(new BedMock(Material.BROWN_BED).getColor(), DyeColor.BROWN);
		assertEquals(new BedMock(Material.CYAN_BED).getColor(), DyeColor.CYAN);
		assertEquals(new BedMock(Material.GRAY_BED).getColor(), DyeColor.GRAY);
		assertEquals(new BedMock(Material.GREEN_BED).getColor(), DyeColor.GREEN);
		assertEquals(new BedMock(Material.LIGHT_BLUE_BED).getColor(), DyeColor.LIGHT_BLUE);
		assertEquals(new BedMock(Material.LIGHT_GRAY_BED).getColor(), DyeColor.LIGHT_GRAY);
		assertEquals(new BedMock(Material.LIME_BED).getColor(), DyeColor.LIME);
		assertEquals(new BedMock(Material.MAGENTA_BED).getColor(), DyeColor.MAGENTA);
		assertEquals(new BedMock(Material.ORANGE_BED).getColor(), DyeColor.ORANGE);
		assertEquals(new BedMock(Material.PINK_BED).getColor(), DyeColor.PINK);
		assertEquals(new BedMock(Material.PURPLE_BED).getColor(), DyeColor.PURPLE);
		assertEquals(new BedMock(Material.RED_BED).getColor(), DyeColor.RED);
		assertEquals(new BedMock(Material.WHITE_BED).getColor(), DyeColor.WHITE);
		assertEquals(new BedMock(Material.YELLOW_BED).getColor(), DyeColor.YELLOW);
	}

	@Test
	void setColor_ThrowsException()
	{
		assertThrowsExactly(UnsupportedOperationException.class,
				() -> new BedMock(Material.RED_BED).setColor(DyeColor.BLACK));
	}

	@Test
	void getSnapshot_DifferentInstance()
	{
		BedMock bed = new BedMock(Material.RED_BED);
		assertNotSame(bed, bed.getSnapshot());
	}

	@Test
	void blockStateMock_mockState_CorrectType()
	{
		for (Material mat : MaterialTags.BEDS.getValues())
		{
			if (BlockStateMock.mockState(new BlockMock(mat)) instanceof BedMock)
				continue;
			fail("BlockState for '" + mat + "' is not a " + BedMock.class.getSimpleName());
		}
	}

}
