package org.mockbukkit.mockbukkit.block.state;

import org.mockbukkit.mockbukkit.exception.UnimplementedOperationException;
import org.mockbukkit.mockbukkit.persistence.PersistentDataContainerMock;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.block.TileState;
import org.bukkit.persistence.PersistentDataContainer;
import org.jetbrains.annotations.NotNull;

/**
 * Mock implementation of a {@link TileState}.
 *
 * @see BlockStateMock
 */
public abstract class TileStateMock extends BlockStateMock implements TileState
{

	private final @NotNull PersistentDataContainerMock container;

	/**
	 * Constructs a new {@link SculkCatalystStateMock} for the provided {@link Material}.
	 *
	 * @param material The material this state is for.
	 */
	protected TileStateMock(@NotNull Material material)
	{
		super(material);
		this.container = new PersistentDataContainerMock();
	}

	/**
	 * Constructs a new {@link SculkCatalystStateMock} for the provided {@link Block}.
	 *
	 * @param block The block this state is for.
	 */
	protected TileStateMock(@NotNull Block block)
	{
		super(block);
		this.container = new PersistentDataContainerMock();
	}

	/**
	 * Constructs a new {@link SculkCatalystStateMock} by cloning the data from an existing one.
	 *
	 * @param state The state to clone.
	 */
	protected TileStateMock(@NotNull TileStateMock state)
	{
		super(state);
		this.container = new PersistentDataContainerMock(state.container);
	}

	@Override
	public @NotNull PersistentDataContainer getPersistentDataContainer()
	{
		return container;
	}

	@Override
	public boolean isSnapshot()
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public abstract @NotNull BlockState getSnapshot();

}
