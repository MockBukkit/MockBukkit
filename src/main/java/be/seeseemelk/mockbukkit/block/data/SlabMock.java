package be.seeseemelk.mockbukkit.block.data;

import org.bukkit.Material;
import org.bukkit.Tag;
import org.bukkit.block.data.type.Slab;
import org.jetbrains.annotations.NotNull;

public class SlabMock extends BlockDataMock implements Slab
{

	private static final String TYPE = "type";
	private static final String WATERLOGGED = "waterlogged";

	public SlabMock(@NotNull Material type)
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
