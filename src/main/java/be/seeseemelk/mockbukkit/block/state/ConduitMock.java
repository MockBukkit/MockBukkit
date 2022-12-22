package be.seeseemelk.mockbukkit.block.state;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.Conduit;
import org.jetbrains.annotations.NotNull;

/**
 * Mock implementation of a {@link Conduit}.
 *
 * @see TileStateMock
 */
public class ConduitMock extends TileStateMock implements Conduit
{

	/**
	 * Constructs a new {@link ConduitMock} for the provided {@link Material}.
	 * Only supports {@link Material#CONDUIT}
	 *
	 * @param material The material this state is for.
	 */
	public ConduitMock(@NotNull Material material)
	{
		super(material);
		checkType(material, Material.CONDUIT);
	}

	/**
	 * Constructs a new {@link ConduitMock} for the provided {@link Block}.
	 * Only supports {@link Material#CONDUIT}
	 *
	 * @param block The block this state is for.
	 */
	protected ConduitMock(@NotNull Block block)
	{
		super(block);
		checkType(block, Material.CONDUIT);
	}

	/**
	 * Constructs a new {@link ConduitMock} by cloning the data from an existing one.
	 *
	 * @param state The state to clone.
	 */
	protected ConduitMock(@NotNull ConduitMock state)
	{
		super(state);
	}

	@Override
	public @NotNull ConduitMock getSnapshot()
	{
		return new ConduitMock(this);
	}

}
