package org.mockbukkit.mockbukkit.block.state;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.CreakingHeart;
import org.jetbrains.annotations.NotNull;

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

}
