package be.seeseemelk.mockbukkit.block.state;

import be.seeseemelk.mockbukkit.block.BlockMock;
import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertNotSame;
import static org.junit.jupiter.api.Assertions.assertThrowsExactly;

public class BedMockTest
{

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
		assertThrowsExactly(UnsupportedOperationException.class, () -> new BedMock(Material.RED_BED).setColor(DyeColor.BLACK));
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
		assertInstanceOf(BedMock.class, BlockStateMock.mockState(new BlockMock(Material.BLACK_BED)));
		assertInstanceOf(BedMock.class, BlockStateMock.mockState(new BlockMock(Material.BLUE_BED)));
		assertInstanceOf(BedMock.class, BlockStateMock.mockState(new BlockMock(Material.BROWN_BED)));
		assertInstanceOf(BedMock.class, BlockStateMock.mockState(new BlockMock(Material.CYAN_BED)));
		assertInstanceOf(BedMock.class, BlockStateMock.mockState(new BlockMock(Material.GRAY_BED)));
		assertInstanceOf(BedMock.class, BlockStateMock.mockState(new BlockMock(Material.GREEN_BED)));
		assertInstanceOf(BedMock.class, BlockStateMock.mockState(new BlockMock(Material.LIGHT_BLUE_BED)));
		assertInstanceOf(BedMock.class, BlockStateMock.mockState(new BlockMock(Material.LIGHT_GRAY_BED)));
		assertInstanceOf(BedMock.class, BlockStateMock.mockState(new BlockMock(Material.LIME_BED)));
		assertInstanceOf(BedMock.class, BlockStateMock.mockState(new BlockMock(Material.MAGENTA_BED)));
		assertInstanceOf(BedMock.class, BlockStateMock.mockState(new BlockMock(Material.ORANGE_BED)));
		assertInstanceOf(BedMock.class, BlockStateMock.mockState(new BlockMock(Material.PINK_BED)));
		assertInstanceOf(BedMock.class, BlockStateMock.mockState(new BlockMock(Material.PURPLE_BED)));
		assertInstanceOf(BedMock.class, BlockStateMock.mockState(new BlockMock(Material.RED_BED)));
		assertInstanceOf(BedMock.class, BlockStateMock.mockState(new BlockMock(Material.WHITE_BED)));
		assertInstanceOf(BedMock.class, BlockStateMock.mockState(new BlockMock(Material.YELLOW_BED)));
	}

}
