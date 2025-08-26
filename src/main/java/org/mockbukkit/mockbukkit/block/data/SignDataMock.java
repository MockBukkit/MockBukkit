package org.mockbukkit.mockbukkit.block.data;

import com.google.common.base.Preconditions;
import org.bukkit.Material;
import org.bukkit.block.BlockFace;
import org.bukkit.block.data.type.Sign;
import org.jetbrains.annotations.NotNull;

public class SignDataMock extends BlockDataMock implements Sign
{

	/**
	 * Constructs a new {@link SignDataMock} for the provided {@link Material}.
	 *
	 * @param material The material this data is for.
	 */
	public SignDataMock(@NotNull Material material)
	{
		super(material);
	}

	/**
	 * Create a new {@link SignDataMock} based on an existing {@link SignDataMock}.
	 *
	 * @param other the other block data.
	 */
	protected SignDataMock(@NotNull SignDataMock other)
	{
		super(other);
	}

	@Override
	public @NotNull BlockFace getRotation()
	{
		int id = this.get(BlockDataKey.ROTATION);
		return RotatableDataMock.getBlockFaceFromId(id);
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
	public boolean isWaterlogged()
	{
		return this.get(BlockDataKey.WATERLOGGED);
	}

	@Override
	public void setWaterlogged(boolean waterlogged)
	{
		this.set(BlockDataKey.WATERLOGGED, waterlogged);
	}

	@Override
	@SuppressWarnings({"MethodDoesntCallSuperMethod", "java:S2975", "java:S1182"})
	public @NotNull SignDataMock clone()
	{
		return new SignDataMock(this);
	}

}
