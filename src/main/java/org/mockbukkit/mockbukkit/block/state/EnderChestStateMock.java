package org.mockbukkit.mockbukkit.block.state;

import org.mockbukkit.mockbukkit.exception.UnimplementedOperationException;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.EnderChest;
import org.jetbrains.annotations.NotNull;

/**
 * Mock implementation of an {@link EnderChest}.
 *
 * @see TileStateMock
 */
public class EnderChestStateMock extends TileStateMock implements EnderChest
{

	private boolean isOpen = false;

	/**
	 * Constructs a new {@link EnderChestStateMock} for the provided {@link Material}.
	 * Only supports {@link Material#ENDER_CHEST}
	 *
	 * @param material The material this state is for.
	 */
	public EnderChestStateMock(@NotNull Material material)
	{
		super(material);
		checkType(material, Material.ENDER_CHEST);
	}

	/**
	 * Constructs a new {@link EnderChestStateMock} for the provided {@link Block}.
	 * Only supports {@link Material#ENDER_CHEST}
	 *
	 * @param block The block this state is for.
	 */
	protected EnderChestStateMock(@NotNull Block block)
	{
		super(block);
		checkType(block, Material.ENDER_CHEST);
	}

	/**
	 * Constructs a new {@link EnderChestStateMock} by cloning the data from an existing one.
	 *
	 * @param state The state to clone.
	 */
	protected EnderChestStateMock(@NotNull EnderChestStateMock state)
	{
		super(state);
		this.isOpen = state.isOpen;
	}

	@Override
	public @NotNull EnderChestStateMock getSnapshot()
	{
		return new EnderChestStateMock(this);
	}

	@Override
	public @NotNull EnderChestStateMock copy()
	{
		return new EnderChestStateMock(this);
	}

	@Override
	public void open()
	{
		isOpen = true;
	}

	@Override
	public void close()
	{
		isOpen = false;
	}

	@Override
	public boolean isOpen()
	{
		return isOpen;
	}

	@Override
	public boolean isBlocked()
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

}
