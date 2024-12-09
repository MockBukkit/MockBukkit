package org.mockbukkit.mockbukkit.block.state;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.Jigsaw;
import org.jetbrains.annotations.NotNull;

/**
 * Mock implementation of a {@link Jigsaw}.
 *
 * @see TileStateMock
 */
public class JigsawStateMock extends TileStateMock implements Jigsaw
{

	/**
	 * Constructs a new {@link JigsawStateMock} for the provided {@link Material}.
	 * Only supports {@link Material#JIGSAW}
	 *
	 * @param material The material this state is for.
	 */
	public JigsawStateMock(@NotNull Material material)
	{
		super(material);
		checkType(material, Material.JIGSAW);
	}

	/**
	 * Constructs a new {@link JigsawStateMock} for the provided {@link Block}.
	 * Only supports {@link Material#JIGSAW}
	 *
	 * @param block The block this state is for.
	 */
	protected JigsawStateMock(@NotNull Block block)
	{
		super(block);
		checkType(block, Material.JIGSAW);
	}

	/**
	 * Constructs a new {@link JigsawStateMock} by cloning the data from an existing one.
	 *
	 * @param state The state to clone.
	 */
	protected JigsawStateMock(@NotNull JigsawStateMock state)
	{
		super(state);
	}

	@Override
	public @NotNull JigsawStateMock getSnapshot()
	{
		return new JigsawStateMock(this);
	}

	@Override
	public @NotNull JigsawStateMock copy()
	{
		return new JigsawStateMock(this);
	}

}
