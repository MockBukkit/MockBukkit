package be.seeseemelk.mockbukkit.block.data;

import org.bukkit.Material;
import org.bukkit.SoundGroup;
import org.bukkit.block.data.BlockData;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import be.seeseemelk.mockbukkit.UnimplementedOperationException;

public class BlockDataMock implements BlockData
{
	private final Material type;

	public BlockDataMock(Material type)
	{
		this.type = type;
	}

	@Override
	public @NotNull Material getMaterial()
	{
		return type;
	}

	@Override
	public @NotNull String getAsString()
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public @NotNull String getAsString(boolean hideUnspecified)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public @NotNull BlockData merge(@NotNull BlockData data)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public SoundGroup getSoundGroup()
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public boolean matches(@Nullable BlockData data)
	{
		if (data == null)
		{
			return false;
		}
		else
		{
			return data.getMaterial() == type;
		}
	}

	@Override
	public @NotNull BlockData clone()
	{
		try
		{
			return (BlockData) super.clone();
		}
		catch (CloneNotSupportedException e)
		{
			return new BlockDataMock(type);
		}
	}
}
