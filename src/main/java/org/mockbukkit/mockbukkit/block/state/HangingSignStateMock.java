package org.mockbukkit.mockbukkit.block.state;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.HangingSign;
import org.jetbrains.annotations.NotNull;

/**
 * Mock implementation of a {@link HangingSign}.
 *
 * @see SignStateMock
 */
public class HangingSignStateMock extends SignStateMock implements HangingSign
{

	public HangingSignStateMock(@NotNull Material material)
	{
		super(material);
	}

	protected HangingSignStateMock(@NotNull Block block)
	{
		super(block);
	}

	protected HangingSignStateMock(@NotNull SignStateMock state)
	{
		super(state);
	}

	@Override
	public @NotNull SignStateMock copy()
	{
		return new HangingSignStateMock(this);
	}

}
