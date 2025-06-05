package org.mockbukkit.mockbukkit.block.data;

import com.google.common.base.Preconditions;
import org.bukkit.Material;
import org.bukkit.block.BlockFace;
import org.bukkit.block.data.type.Lectern;
import org.jetbrains.annotations.NotNull;

import java.util.Set;

public class LecternDataMock extends BlockDataMock implements Lectern
{

	/**
	 * Constructs a new {@link LecternDataMock} for the provided {@link Material}.
	 *
	 * @param material The material this data is for.
	 */
	public LecternDataMock(@NotNull Material material)
	{
		super(material);
		setHasBook(false);
	}

	@Override
	public boolean hasBook()
	{
		return this.get(BlockDataKey.HAS_BOOK);
	}

	@Override
	public void setHasBook(boolean hasBook)
	{
		this.set(BlockDataKey.HAS_BOOK, hasBook);
	}

	@Override
	public @NotNull BlockFace getFacing()
	{
		return this.get(BlockDataKey.FACING);
	}

	@Override
	public void setFacing(@NotNull BlockFace facing)
	{
		Preconditions.checkArgument(facing != null, "blockFace cannot be null!");
		Preconditions.checkArgument(facing.isCartesian() && facing.getModY() == 0, "Invalid face, only cartesian horizontal face are allowed for this property!");
		this.set(BlockDataKey.FACING, facing);
	}

	@Override
	public @NotNull Set<BlockFace> getFaces()
	{
		return this.getLimitationValue(BlockDataLimitation.Type.FACES);
	}

	@Override
	public boolean isPowered()
	{
		return this.get(BlockDataKey.POWERED);
	}

	@Override
	public void setPowered(boolean powered)
	{
		this.set(BlockDataKey.POWERED, powered);
	}

}
