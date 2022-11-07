package be.seeseemelk.mockbukkit.block.state;

import org.bukkit.Material;
import org.bukkit.block.Bell;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.jetbrains.annotations.NotNull;

/**
 * Mock implementation of a {@link Bell}.
 *
 * @see TileStateMock
 */
public class BellMock extends TileStateMock implements Bell
{

	/**
	 * Constructs a new {@link BellMock} for the provided {@link Material}.
	 * Only supports {@link Material#BELL}
	 *
	 * @param material The material this state is for.
	 */
	public BellMock(@NotNull Material material)
	{
		super(material);
		checkType(material, Material.BELL);
	}

	/**
	 * Constructs a new {@link BellMock} for the provided {@link Block}.
	 * Only supports {@link Material#BELL}
	 *
	 * @param block The block this state is for.
	 */
	protected BellMock(@NotNull Block block)
	{
		super(block);
		checkType(block, Material.BELL);
	}

	/**
	 * Constructs a new {@link BellMock} by cloning the data from an existing one.
	 *
	 * @param state The state to clone.
	 */
	protected BellMock(@NotNull BellMock state)
	{
		super(state);
	}

	@Override
	public @NotNull BlockState getSnapshot()
	{
		return new BellMock(this);
	}

}
