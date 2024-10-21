package org.mockbukkit.mockbukkit.block.data;

import com.google.common.base.Preconditions;
import org.bukkit.Material;
import org.bukkit.Tag;
import org.bukkit.block.data.type.Slab;
import org.jetbrains.annotations.NotNull;

import static org.mockbukkit.mockbukkit.block.data.BlockDataKey.TYPE;
import static org.mockbukkit.mockbukkit.block.data.BlockDataKey.WATERLOGGED;

/**
 * Mock implementation of {@link Slab}.
 */
public class SlabDataMock extends BlockDataMock implements Slab
{

	/**
	 * Constructs a new {@link SlabDataMock} for the provided {@link Material}.
	 * Only supports materials in {@link Tag#SLABS}
	 *
	 * @param type The material this data is for.
	 */
	public SlabDataMock(@NotNull Material type)
	{
		super(type);
		checkType(type, Tag.SLABS);
		setType(Type.BOTTOM);
		setWaterlogged(false);
	}

	@Override
	public @NotNull Type getType()
	{
		return get(TYPE);
	}

	@Override
	public void setType(@NotNull Type type)
	{
		Preconditions.checkNotNull(type, "Type cannot be null");
		set(TYPE, type);
	}

	@Override
	public boolean isWaterlogged()
	{
		return super.get(WATERLOGGED);
	}

	@Override
	public void setWaterlogged(boolean waterlogged)
	{
		super.set(WATERLOGGED, waterlogged);
	}

}
