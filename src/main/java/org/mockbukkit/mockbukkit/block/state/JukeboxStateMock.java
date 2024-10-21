package org.mockbukkit.mockbukkit.block.state;

import org.mockbukkit.mockbukkit.exception.UnimplementedOperationException;
import org.mockbukkit.mockbukkit.inventory.ItemStackMock;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.block.Jukebox;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.JukeboxInventory;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Mock implementation of a {@link Jukebox}.
 *
 * @see TileStateMock
 */
public class JukeboxStateMock extends TileStateMock implements Jukebox
{

	private ItemStack recordItem;
	private boolean playing;

	/**
	 * Constructs a new {@link JukeboxStateMock} for the provided {@link Material}.
	 * Only supports {@link Material#JUKEBOX}
	 *
	 * @param material The material this state is for.
	 */
	public JukeboxStateMock(@NotNull Material material)
	{
		super(material);
		checkType(material, Material.JUKEBOX);
		setRecord(null);
	}

	/**
	 * Constructs a new {@link JukeboxStateMock} for the provided {@link Block}.
	 * Only supports {@link Material#JUKEBOX}
	 *
	 * @param block The block this state is for.
	 */
	protected JukeboxStateMock(@NotNull Block block)
	{
		super(block);
		checkType(block, Material.JUKEBOX);
		setRecord(null);
	}

	/**
	 * Constructs a new {@link JukeboxStateMock} by cloning the data from an existing one.
	 *
	 * @param state The state to clone.
	 */
	protected JukeboxStateMock(@NotNull JukeboxStateMock state)
	{
		super(state);
		this.recordItem = state.recordItem;
		this.playing = state.playing;
	}

	@Override
	public @NotNull BlockState getSnapshot()
	{
		return new JukeboxStateMock(this);
	}

	@Override
	public @NotNull Material getPlaying()
	{
		return this.recordItem.getType();
	}

	@Override
	public void setPlaying(@Nullable Material recordType)
	{
		setRecord(new ItemStackMock(recordType == null ? Material.AIR : recordType));
	}

	@Override
	public boolean hasRecord()
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public @NotNull ItemStack getRecord()
	{
		return this.recordItem;
	}

	@Override
	public void setRecord(@Nullable ItemStack recordItem)
	{
		this.recordItem = recordItem == null ? new ItemStackMock(Material.AIR) : recordItem;
		this.playing = !this.recordItem.getType().isAir();
	}

	@Override
	public boolean isPlaying()
	{
		return this.playing;
	}

	@Override
	public boolean startPlaying()
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public void stopPlaying()
	{
		this.playing = false;
	}

	@Override
	public boolean eject()
	{
		if (!isPlaced())
			throw new IllegalStateException("Cannot eject from an unplaced jukebox");

		if (this.getRecord().getType().isAir())
			return false;

		getWorld().dropItem(getLocation().add(0, 1, 0), getRecord());

		setRecord(null);
		return true;
	}

	@Override
	public @NotNull JukeboxInventory getInventory()
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public @NotNull JukeboxInventory getSnapshotInventory()
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

}
