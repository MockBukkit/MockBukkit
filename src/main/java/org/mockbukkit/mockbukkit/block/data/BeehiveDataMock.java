package org.mockbukkit.mockbukkit.block.data;

import com.google.common.base.Preconditions;
import org.bukkit.Material;
import org.bukkit.block.BlockFace;
import org.bukkit.block.data.type.Beehive;
import org.jetbrains.annotations.NotNull;

import java.util.Set;

public class BeehiveDataMock extends BlockDataMock implements Beehive
{

	public BeehiveDataMock(@NotNull Material type)
	{
		super(type);
	}

	protected BeehiveDataMock(BeehiveDataMock other)
	{
		super(other);
	}

	@Override
	public int getHoneyLevel()
	{
		return this.get(BlockDataKey.HONEY_LEVEL);
	}

	@Override
	public void setHoneyLevel(int honeyLevel)
	{
		this.set(BlockDataKey.HONEY_LEVEL, honeyLevel);
	}

	@Override
	public int getMaximumHoneyLevel()
	{
		return this.getLimitationValue(BlockDataLimitation.Type.MAX_HONEY_LEVEL);
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
	@SuppressWarnings({"MethodDoesntCallSuperMethod", "java:S2975", "java:S1182"})
	public @NotNull BeehiveDataMock clone()
	{
		return new BeehiveDataMock(this);
	}

}
