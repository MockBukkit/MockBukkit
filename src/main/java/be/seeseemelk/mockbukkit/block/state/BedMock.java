package be.seeseemelk.mockbukkit.block.state;

import com.destroystokyo.paper.MaterialTags;
import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.block.Bed;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Mock implementation of a {@link Bed}.
 *
 * @see TileStateMock
 */
public class BedMock extends TileStateMock implements Bed
{

	/**
	 * Constructs a new {@link BedMock} for the provided {@link Material}.
	 * Only supports materials in {@link MaterialTags#BEDS}
	 *
	 * @param material The material this state is for.
	 */
	public BedMock(@NotNull Material material)
	{
		super(material);
		checkType(material, MaterialTags.BEDS);
	}

	/**
	 * Constructs a new {@link BedMock} for the provided {@link Block}.
	 * Only supports materials in {@link MaterialTags#BEDS}
	 *
	 * @param block The block this state is for.
	 */
	protected BedMock(@NotNull Block block)
	{
		super(block);
		checkType(block, MaterialTags.BEDS);
	}

	/**
	 * Constructs a new {@link BedMock} by cloning the data from an existing one.
	 *
	 * @param state The state to clone.
	 */
	protected BedMock(@NotNull BedMock state)
	{
		super(state);
	}

	@Override
	public @Nullable DyeColor getColor()
	{
		return switch (getType())
		{
		case BLACK_BED -> DyeColor.BLACK;
		case BLUE_BED -> DyeColor.BLUE;
		case BROWN_BED -> DyeColor.BROWN;
		case CYAN_BED -> DyeColor.CYAN;
		case GRAY_BED -> DyeColor.GRAY;
		case GREEN_BED -> DyeColor.GREEN;
		case LIGHT_BLUE_BED -> DyeColor.LIGHT_BLUE;
		case LIGHT_GRAY_BED -> DyeColor.LIGHT_GRAY;
		case LIME_BED -> DyeColor.LIME;
		case MAGENTA_BED -> DyeColor.MAGENTA;
		case ORANGE_BED -> DyeColor.ORANGE;
		case PINK_BED -> DyeColor.PINK;
		case PURPLE_BED -> DyeColor.PURPLE;
		case RED_BED -> DyeColor.RED;
		case WHITE_BED -> DyeColor.WHITE;
		case YELLOW_BED -> DyeColor.YELLOW;
		default -> throw new IllegalArgumentException("Unknown DyeColor for " + getType());
		};
	}

	@Override
	@Deprecated(forRemoval = true, since = "1.19")
	public void setColor(DyeColor color)
	{
		throw new UnsupportedOperationException("Must set block type to appropriate bed colour");
	}

	@Override
	public @NotNull BlockState getSnapshot()
	{
		return new BedMock(this);
	}

}
