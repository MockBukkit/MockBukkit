package be.seeseemelk.mockbukkit.block.state;

import be.seeseemelk.mockbukkit.UnimplementedOperationException;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.block.EnderChest;
import org.jetbrains.annotations.NotNull;

/**
 * Mock implementation of an {@link EnderChest}.
 *
 * @see TileStateMock
 */
public class EnderChestMock extends TileStateMock implements EnderChest
{

	private boolean isOpen = false;

	/**
	 * Constructs a new {@link EnderChestMock} for the provided {@link Material}.
	 * Only supports {@link Material#ENDER_CHEST}
	 *
	 * @param material The material this state is for.
	 */
	public EnderChestMock(@NotNull Material material)
	{
		super(material);
		checkType(material, Material.ENDER_CHEST);
	}

	/**
	 * Constructs a new {@link EnderChestMock} for the provided {@link Block}.
	 * Only supports {@link Material#ENDER_CHEST}
	 *
	 * @param block The block this state is for.
	 */
	protected EnderChestMock(@NotNull Block block)
	{
		super(block);
		checkType(block, Material.ENDER_CHEST);
	}

	/**
	 * Constructs a new {@link EnderChestMock} by cloning the data from an existing one.
	 *
	 * @param state The state to clone.
	 */
	protected EnderChestMock(@NotNull EnderChestMock state)
	{
		super(state);
		this.isOpen = state.isOpen;
	}

	@Override
	public @NotNull BlockState getSnapshot()
	{
		return new EnderChestMock(this);
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
