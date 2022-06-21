package be.seeseemelk.mockbukkit.block.state;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.block.EnderChest;
import org.jetbrains.annotations.NotNull;

/**
 * A simple mock of the {@link EnderChest} {@link BlockState}, it is a pretty generic implementation of
 * {@link TileStateMock}.
 *
 * @author TheBusyBiscuit
 */
public class EnderChestMock extends TileStateMock implements EnderChest
{

	private boolean isOpen = false;

	public EnderChestMock(@NotNull Material material)
	{
		super(material);
		checkType(material == Material.ENDER_CHEST);
	}

	protected EnderChestMock(@NotNull Block block)
	{
		super(block);
		checkType(block.getType() == Material.ENDER_CHEST);
	}

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

}
