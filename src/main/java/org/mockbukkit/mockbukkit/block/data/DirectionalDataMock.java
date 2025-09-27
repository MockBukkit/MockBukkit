package org.mockbukkit.mockbukkit.block.data;

import com.google.common.base.Preconditions;
import org.bukkit.Material;
import org.bukkit.block.BlockFace;
import org.bukkit.block.data.Directional;
import org.jetbrains.annotations.NotNull;

import java.util.Set;

/**
 * Mock implementation of {@link Directional}.
 *
 * @see BlockDataMock
 */
public class DirectionalDataMock extends BlockDataMock implements Directional
{

	public DirectionalDataMock(@NotNull Material material)
	{
		super(material);
	}

	protected DirectionalDataMock(@NotNull BlockDataMock other)
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
		Preconditions.checkArgument(this.getFaces().contains(blockFace), "Invalid face: %s. Possible values are: %s", blockFace, this.getFaces());
		this.set(BlockDataKey.FACING, blockFace);
	}

	@Override
	public @NotNull Set<BlockFace> getFaces()
	{
		return this.getLimitationValue(BlockDataLimitation.Type.FACES);
	}

	@Override
	@SuppressWarnings({"MethodDoesntCallSuperMethod", "java:S2975", "java:S1182"})
	public @NotNull DirectionalDataMock clone()
	{
		return new DirectionalDataMock(this);
	}

}
