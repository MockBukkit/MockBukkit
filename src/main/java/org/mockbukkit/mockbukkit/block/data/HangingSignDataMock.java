package org.mockbukkit.mockbukkit.block.data;

import com.google.common.base.Preconditions;
import org.bukkit.Material;
import org.bukkit.block.BlockFace;
import org.bukkit.block.data.type.HangingSign;
import org.jetbrains.annotations.NotNull;

public class HangingSignDataMock extends BlockDataMock implements HangingSign
{

	/**
	 * Constructs a new {@link HangingSignDataMock} for the provided {@link Material}.
	 *
	 * @param material The material this data is for.
	 */
	public HangingSignDataMock(@NotNull Material material)
	{
		super(material);
	}

	/**
	 * Create a new {@link HangingSignDataMock} based on an existing {@link HangingSignDataMock}.
	 *
	 * @param other the other block data.
	 */
	protected HangingSignDataMock(@NotNull HangingSignDataMock other)
	{
		super(other);
	}

	@Override
	public boolean isAttached()
	{
		return this.get(BlockDataKey.ATTACHED);
	}

	@Override
	public void setAttached(boolean attached)
	{
		this.set(BlockDataKey.ATTACHED, attached);
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
	public @NotNull HangingSignDataMock clone()
	{
		return new HangingSignDataMock(this);
	}

}
