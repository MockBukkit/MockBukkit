package org.mockbukkit.mockbukkit.block.data;

import com.google.common.base.Preconditions;
import org.bukkit.Material;
import org.bukkit.block.BlockFace;
import org.bukkit.block.data.type.Skull;
import org.jetbrains.annotations.NotNull;

public class SkullDataMock extends BlockDataMock implements Skull
{

	public SkullDataMock(@NotNull Material material)
	{
		super(material);
	}

	protected SkullDataMock(@NotNull SkullDataMock other)
	{
		super(other);
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
	public @NotNull BlockFace getRotation()
	{
		int data = this.get(BlockDataKey.ROTATION);
		return RotatableDataMock.getBlockFaceFromId(data);
	}

	@Override
	public void setRotation(@NotNull BlockFace blockFace)
	{
		Preconditions.checkArgument(blockFace != null, "blockFace cannot be null!");
		Preconditions.checkArgument(blockFace != BlockFace.SELF && blockFace.getModY() == 0, "Invalid face, only horizontal face are allowed for this property!");

		int id = RotatableDataMock.getIdFromBlockFace(blockFace);
		this.set(BlockDataKey.ROTATION, id);
	}

	@Override
	@SuppressWarnings({"MethodDoesntCallSuperMethod", "java:S2975", "java:S1182"})
	public @NotNull SkullDataMock clone()
	{
		return new SkullDataMock(this);
	}

}
