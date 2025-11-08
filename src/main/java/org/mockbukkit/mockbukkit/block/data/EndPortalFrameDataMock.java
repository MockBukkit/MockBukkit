package org.mockbukkit.mockbukkit.block.data;

import com.google.common.base.Preconditions;
import org.bukkit.Material;
import org.bukkit.block.BlockFace;
import org.bukkit.block.data.type.EndPortalFrame;
import org.jetbrains.annotations.NotNull;

import java.util.Set;

public class EndPortalFrameDataMock extends BlockDataMock implements EndPortalFrame
{

	public EndPortalFrameDataMock(@NotNull Material material)
	{
		super(material);
	}

	protected EndPortalFrameDataMock(@NotNull EndPortalFrameDataMock other)
	{
		super(other);
	}

	@Override
	public boolean hasEye()
	{
		return this.get(BlockDataKey.EYE);
	}

	@Override
	public void setEye(boolean eye)
	{
		this.set(BlockDataKey.EYE, eye);
	}

	@Override
	public @NotNull BlockFace getFacing()
	{
		return get(BlockDataKey.FACING);
	}

	@Override
	public void setFacing(@NotNull BlockFace blockFace)
	{
		Preconditions.checkArgument(blockFace != null, "blockFace cannot be null!");
		Preconditions.checkArgument(blockFace.isCartesian() && blockFace.getModY() == 0, "Invalid face, only cartesian horizontal face are allowed for this property!");
		set(BlockDataKey.FACING, blockFace);
	}

	@Override
	public @NotNull Set<BlockFace> getFaces()
	{
		return this.getLimitationValue(BlockDataLimitation.Type.FACES);
	}

	@Override
	@SuppressWarnings({"MethodDoesntCallSuperMethod", "java:S2975", "java:S1182"})
	public @NotNull EndPortalFrameDataMock clone()
	{
		return new EndPortalFrameDataMock(this);
	}


}
