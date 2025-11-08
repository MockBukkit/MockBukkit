package org.mockbukkit.mockbukkit.block.data;

import com.google.common.base.Preconditions;
import org.bukkit.Material;
import org.bukkit.block.BlockFace;
import org.bukkit.block.data.type.Bell;
import org.jetbrains.annotations.NotNull;

import java.util.Set;

public class BellDataMock extends BlockDataMock implements Bell
{

	public BellDataMock(@NotNull Material material)
	{
		super(material);
	}

	protected BellDataMock(@NotNull BellDataMock other)
	{
		super(other);
	}

	@Override
	public @NotNull Attachment getAttachment()
	{
		return this.get(BlockDataKey.ATTACHMENT);
	}

	@Override
	public void setAttachment(@NotNull Attachment attachment)
	{
		Preconditions.checkArgument(attachment != null, "attachment cannot be null!");
		this.set(BlockDataKey.ATTACHMENT, attachment);
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
		Preconditions.checkArgument(blockFace.isCartesian() && blockFace.getModY() == 0, "Invalid face, only cartesian horizontal face are allowed for this property!");
		this.set(BlockDataKey.FACING, blockFace);
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

	@Override
	@SuppressWarnings({"MethodDoesntCallSuperMethod", "java:S2975", "java:S1182"})
	public @NotNull BellDataMock clone()
	{
		return new BellDataMock(this);
	}


}
