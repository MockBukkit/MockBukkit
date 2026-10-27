package org.mockbukkit.mockbukkit.block.state;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.CreakingHeart;
import org.bukkit.entity.Creaking;
import org.jetbrains.annotations.NotNull;
import org.jspecify.annotations.Nullable;
import org.mockbukkit.mockbukkit.exception.UnimplementedOperationException;

/**
 * Mock implementation of a {@link CreakingHeart}.
 *
 * @see TileStateMock
 */
public class CreakingHeartStateMock extends TileStateMock implements CreakingHeart
{

	protected CreakingHeartStateMock(@NotNull Block block)
	{
		super(block);
	}

	protected CreakingHeartStateMock(@NotNull Material material)
	{
		super(material);
	}

	protected CreakingHeartStateMock(@NotNull TileStateMock state)
	{
		super(state);
	}

	@Override
	public @NotNull CreakingHeartStateMock getSnapshot()
	{
		return new CreakingHeartStateMock(this);
	}

	@Override
	public @Nullable Creaking getCreaking()
	{
		//TODO: Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public void setCreaking(@Nullable Creaking creaking)
	{
		//TODO: Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public @Nullable Creaking spawnCreaking()
	{
		//TODO: Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public @Nullable Location spreadResin()
	{
		//TODO: Auto-generated method stub
		throw new UnimplementedOperationException();
	}

}
