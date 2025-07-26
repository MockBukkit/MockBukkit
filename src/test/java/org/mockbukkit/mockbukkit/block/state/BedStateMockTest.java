package org.mockbukkit.mockbukkit.block.state;

import com.destroystokyo.paper.MaterialTags;
import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockbukkit.mockbukkit.MockBukkitExtension;
import org.mockbukkit.mockbukkit.block.BlockMock;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotSame;
import static org.junit.jupiter.api.Assertions.assertThrowsExactly;
import static org.junit.jupiter.api.Assertions.fail;

@ExtendWith(MockBukkitExtension.class)
class BedStateMockTest
{

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
		assertEquals(DyeColor.BLACK, new BedStateMock(Material.BLACK_BED).getColor());
		assertEquals(DyeColor.BLUE, new BedStateMock(Material.BLUE_BED).getColor());
		assertEquals(DyeColor.BROWN, new BedStateMock(Material.BROWN_BED).getColor());
		assertEquals(DyeColor.CYAN, new BedStateMock(Material.CYAN_BED).getColor());
		assertEquals(DyeColor.GRAY, new BedStateMock(Material.GRAY_BED).getColor());
		assertEquals(DyeColor.GREEN, new BedStateMock(Material.GREEN_BED).getColor());
		assertEquals(DyeColor.LIGHT_BLUE, new BedStateMock(Material.LIGHT_BLUE_BED).getColor());
		assertEquals(DyeColor.LIGHT_GRAY, new BedStateMock(Material.LIGHT_GRAY_BED).getColor());
		assertEquals(DyeColor.LIME, new BedStateMock(Material.LIME_BED).getColor());
		assertEquals(DyeColor.MAGENTA, new BedStateMock(Material.MAGENTA_BED).getColor());
		assertEquals(DyeColor.ORANGE, new BedStateMock(Material.ORANGE_BED).getColor());
		assertEquals(DyeColor.PINK, new BedStateMock(Material.PINK_BED).getColor());
		assertEquals(DyeColor.PURPLE, new BedStateMock(Material.PURPLE_BED).getColor());
		assertEquals(DyeColor.RED, new BedStateMock(Material.RED_BED).getColor());
		assertEquals(DyeColor.WHITE, new BedStateMock(Material.WHITE_BED).getColor());
		assertEquals(DyeColor.YELLOW, new BedStateMock(Material.YELLOW_BED).getColor());
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
