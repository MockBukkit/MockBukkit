package org.mockbukkit.mockbukkit.block.state;

import org.mockbukkit.mockbukkit.MockBukkit;
import org.mockbukkit.mockbukkit.block.BlockMock;
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

class BedStateMockTest
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
			assertDoesNotThrow(() -> new BedStateMock(value));
		}
	}

	@Test
	void constructor_Material_WrongType_ThrowsException()
	{
		assertThrowsExactly(IllegalArgumentException.class, () -> new BedStateMock(Material.BEDROCK));
	}

	@Test
	void constructor_Block()
	{
		for (Material value : MaterialTags.BEDS.getValues())
		{
			assertDoesNotThrow(() -> new BedStateMock(new BlockMock(value)));
		}
	}

	@Test
	void constructor_Block_WrongType_ThrowsException()
	{
		assertThrowsExactly(IllegalArgumentException.class, () -> new BedStateMock(new BlockMock(Material.BEDROCK)));
	}

	@Test
	void getColor_ReturnCorrectColor()
	{
		assertEquals(new BedStateMock(Material.BLACK_BED).getColor(), DyeColor.BLACK);
		assertEquals(new BedStateMock(Material.BLUE_BED).getColor(), DyeColor.BLUE);
		assertEquals(new BedStateMock(Material.BROWN_BED).getColor(), DyeColor.BROWN);
		assertEquals(new BedStateMock(Material.CYAN_BED).getColor(), DyeColor.CYAN);
		assertEquals(new BedStateMock(Material.GRAY_BED).getColor(), DyeColor.GRAY);
		assertEquals(new BedStateMock(Material.GREEN_BED).getColor(), DyeColor.GREEN);
		assertEquals(new BedStateMock(Material.LIGHT_BLUE_BED).getColor(), DyeColor.LIGHT_BLUE);
		assertEquals(new BedStateMock(Material.LIGHT_GRAY_BED).getColor(), DyeColor.LIGHT_GRAY);
		assertEquals(new BedStateMock(Material.LIME_BED).getColor(), DyeColor.LIME);
		assertEquals(new BedStateMock(Material.MAGENTA_BED).getColor(), DyeColor.MAGENTA);
		assertEquals(new BedStateMock(Material.ORANGE_BED).getColor(), DyeColor.ORANGE);
		assertEquals(new BedStateMock(Material.PINK_BED).getColor(), DyeColor.PINK);
		assertEquals(new BedStateMock(Material.PURPLE_BED).getColor(), DyeColor.PURPLE);
		assertEquals(new BedStateMock(Material.RED_BED).getColor(), DyeColor.RED);
		assertEquals(new BedStateMock(Material.WHITE_BED).getColor(), DyeColor.WHITE);
		assertEquals(new BedStateMock(Material.YELLOW_BED).getColor(), DyeColor.YELLOW);
	}

	@Test
	void setColor_ThrowsException()
	{
		assertThrowsExactly(UnsupportedOperationException.class, () -> new BedStateMock(Material.RED_BED).setColor(DyeColor.BLACK));
	}

	@Test
	void getSnapshot_DifferentInstance()
	{
		BedStateMock bed = new BedStateMock(Material.RED_BED);
		assertNotSame(bed, bed.getSnapshot());
	}

	@Test
	void blockStateMock_mockState_CorrectType()
	{
		for (Material mat : MaterialTags.BEDS.getValues())
		{
			if (BlockStateMock.mockState(new BlockMock(mat)) instanceof BedStateMock)
				continue;
			fail("BlockState for '" + mat + "' is not a " + BedStateMock.class.getSimpleName());
		}
	}

}
