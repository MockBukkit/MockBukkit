package org.mockbukkit.mockbukkit.block.data;

import com.google.common.base.Preconditions;
import org.bukkit.Material;
import org.bukkit.block.BlockFace;
import org.bukkit.block.data.type.Cocoa;
import org.jetbrains.annotations.NotNull;

import java.util.Set;

import static org.mockbukkit.mockbukkit.block.data.BlockDataKey.FACING;

public class CocoaDataMock extends BlockDataMock implements Cocoa
{

	public CocoaDataMock(@NotNull Material material)
	{
		super(material);
	}

	protected CocoaDataMock(@NotNull CocoaDataMock other)
	{
		super(other);
	}

	@Override
	public int getAge()
	{
		return this.get(BlockDataKey.AGE_KEY);
	}

	@Override
	public void setAge(int age)
	{
		this.set(BlockDataKey.AGE_KEY, age);
	}

	@Override
	public int getMaximumAge()
	{
		return this.getLimitationValue(BlockDataLimitation.Type.MAX_AGE);
	}

	@Override
	public @NotNull BlockFace getFacing()
	{
		return this.get(FACING);
	}

	@Override
	public void setFacing(@NotNull BlockFace blockFace)
	{
		Preconditions.checkArgument(blockFace != null, "blockFace cannot be null!");
		Preconditions.checkArgument(blockFace.isCartesian() && blockFace.getModY() == 0, "Invalid face, only cartesian horizontal face are allowed for this property!");
		this.set(FACING, blockFace);
	}

	@Override
	public @NotNull Set<BlockFace> getFaces()
	{
		return this.getLimitationValue(BlockDataLimitation.Type.FACES);
	}

	@Override
	@SuppressWarnings({"MethodDoesntCallSuperMethod", "java:S2975", "java:S1182"})
	public @NotNull CocoaDataMock clone()
	{
		return new CocoaDataMock(this);
	}

}
