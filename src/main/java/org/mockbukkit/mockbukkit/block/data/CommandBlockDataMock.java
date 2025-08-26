package org.mockbukkit.mockbukkit.block.data;

import com.google.common.base.Preconditions;
import org.bukkit.Material;
import org.bukkit.block.BlockFace;
import org.bukkit.block.data.type.CommandBlock;
import org.jetbrains.annotations.NotNull;

import java.util.Set;

/**
 * Mock implementation of {@link CommandBlock}.
 *
 * @see BlockDataMock
 */
public class CommandBlockDataMock extends BlockDataMock implements CommandBlock
{

	/**
	 * Constructs a new {@link CommandBlock} for the provided {@link Material}.
	 *
	 * @param material The material this data is for.
	 */
	public CommandBlockDataMock(@NotNull Material material)
	{
		super(material);
	}

	/**
	 * Create a new {@link CommandBlock} based on an existing {@link CommandBlock}.
	 *
	 * @param other the other block data.
	 */
	protected CommandBlockDataMock(@NotNull CommandBlockDataMock other)
	{
		super(other);
	}

	@Override
	public boolean isConditional()
	{
		return this.get(BlockDataKey.CONDITIONAL);
	}

	@Override
	public void setConditional(boolean conditional)
	{
		this.set(BlockDataKey.CONDITIONAL, conditional);
	}

	@Override
	public @NotNull BlockFace getFacing()
	{
		return this.get(BlockDataKey.FACING);
	}

	@Override
	public void setFacing(@NotNull BlockFace blockFace)
	{
		Preconditions.checkArgument(blockFace != null, "blockFace cannot be null!");
		Preconditions.checkArgument(blockFace.isCartesian(), "Invalid face, only cartesian face are allowed for this property!");
		this.set(BlockDataKey.FACING, blockFace);
	}

	@Override
	public @NotNull Set<BlockFace> getFaces()
	{
		return this.getLimitationValue(BlockDataLimitation.Type.FACES);
	}

	@Override
	public @NotNull CommandBlockDataMock clone()
	{
		return new CommandBlockDataMock(this);
	}

}
