package org.mockbukkit.mockbukkit.block.data;

import com.google.common.base.Preconditions;
import org.bukkit.Material;
import org.bukkit.block.BlockFace;
import org.bukkit.block.data.type.Grindstone;
import org.jetbrains.annotations.NotNull;

import java.util.Set;

public class GrindstoneDataMock extends BlockDataMock implements Grindstone
{

	public GrindstoneDataMock(@NotNull Material material)
	{
		super(material);
	}

	protected GrindstoneDataMock(@NotNull GrindstoneDataMock other)
	{
		super(other);
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
	public @NotNull AttachedFace getAttachedFace()
	{
		return this.get(BlockDataKey.FACE);
	}

	@Override
	public void setAttachedFace(@NotNull AttachedFace attachedFace)
	{
		Preconditions.checkArgument(attachedFace != null, "attachedFace cannot be null!");
		this.set(BlockDataKey.FACE, attachedFace);
	}

	@Override
	@SuppressWarnings({"MethodDoesntCallSuperMethod", "java:S2975", "java:S1182"})
	public @NotNull GrindstoneDataMock clone()
	{
		return new GrindstoneDataMock(this);
	}

}
