package be.seeseemelk.mockbukkit.block.state;

import be.seeseemelk.mockbukkit.UnimplementedOperationException;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.block.TileState;
import org.bukkit.persistence.PersistentDataContainer;
import org.jetbrains.annotations.NotNull;

import be.seeseemelk.mockbukkit.persistence.PersistentDataContainerMock;

/**
 * This {@link BlockStateMock} represents a {@link TileState} which is capable of storing persistent data using a
 * {@link PersistentDataContainerMock}.
 *
 * @author TheBusyBiscuit
 *
 */
public abstract class TileStateMock extends BlockStateMock implements TileState
{

	private final PersistentDataContainerMock container;

	protected TileStateMock(@NotNull Material material)
	{
		super(material);
		this.container = new PersistentDataContainerMock();
	}

	protected TileStateMock(@NotNull Block block)
	{
		super(block);
		this.container = new PersistentDataContainerMock();
	}

	protected TileStateMock(@NotNull TileStateMock state)
	{
		super(state);
		this.container = new PersistentDataContainerMock(state.container);
	}

	@Override
	public PersistentDataContainer getPersistentDataContainer()
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
	public abstract BlockState getSnapshot();

}
