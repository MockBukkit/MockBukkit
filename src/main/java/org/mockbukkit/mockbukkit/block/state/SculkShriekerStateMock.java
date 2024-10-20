package org.mockbukkit.mockbukkit.block.state;

import org.mockbukkit.mockbukkit.exception.UnimplementedOperationException;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.block.SculkShrieker;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Mock implementation of a {@link SculkShrieker}.
 *
 * @see TileStateMock
 */
public class SculkShriekerStateMock extends TileStateMock implements SculkShrieker
{

	private int warningLevel;

	/**
	 * Constructs a new {@link SculkShriekerStateMock} for the provided {@link Material}.
	 * Only supports {@link Material#SCULK_SHRIEKER}
	 *
	 * @param material The material this state is for.
	 */
	public SculkShriekerStateMock(@NotNull Material material)
	{
		super(material);
		checkType(material, Material.SCULK_SHRIEKER);
	}

	/**
	 * Constructs a new {@link SculkShriekerStateMock} for the provided {@link Block}.
	 * Only supports {@link Material#SCULK_SHRIEKER}
	 *
	 * @param block The block this state is for.
	 */
	protected SculkShriekerStateMock(@NotNull Block block)
	{
		super(block);
		checkType(block, Material.SCULK_SHRIEKER);
	}

	/**
	 * Constructs a new {@link SculkShriekerStateMock} by cloning the data from an existing one.
	 *
	 * @param state The state to clone.
	 */
	protected SculkShriekerStateMock(@NotNull SculkShriekerStateMock state)
	{
		super(state);
		this.warningLevel = state.warningLevel;
	}

	@Override
	public @NotNull BlockState getSnapshot()
	{
		return new SculkShriekerStateMock(this);
	}

	@Override
	public int getWarningLevel()
	{
		return this.warningLevel;
	}

	@Override
	public void setWarningLevel(int level)
	{
		this.warningLevel = level;
	}

	@Override
	public void tryShriek(@Nullable Player player)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

}
