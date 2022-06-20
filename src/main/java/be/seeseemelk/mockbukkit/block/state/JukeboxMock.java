package be.seeseemelk.mockbukkit.block.state;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.block.Jukebox;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class JukeboxMock extends TileStateMock implements Jukebox
{

	private ItemStack recordItem;
	private boolean playing;

	public JukeboxMock(@NotNull Material material)
	{
		super(material);
		if (material != Material.JUKEBOX)
			throw new IllegalArgumentException("Cannot create a Jukebox state from " + material);
		setRecord(null);
	}

	public JukeboxMock(@NotNull Block block)
	{
		super(block);
		if (block.getType() != Material.JUKEBOX)
			throw new IllegalArgumentException("Cannot create a Jukebox state from " + block.getType());
		setRecord(null);
	}

	public JukeboxMock(@NotNull JukeboxMock state)
	{
		super(state);
		this.recordItem = state.recordItem;
		this.playing = state.playing;
	}

	@Override
	public @NotNull BlockState getSnapshot()
	{
		return new JukeboxMock(this);
	}

	@Override
	public @NotNull Material getPlaying()
	{
		return this.recordItem.getType();
	}

	@Override
	public void setPlaying(@Nullable Material recordType)
	{
		setRecord(new ItemStack(recordType == null ? Material.AIR : recordType));
	}

	@Override
	public @NotNull ItemStack getRecord()
	{
		return this.recordItem;
	}

	@Override
	public void setRecord(@Nullable ItemStack recordItem)
	{
		this.recordItem = recordItem == null ? new ItemStack(Material.AIR) : recordItem;
		this.playing = !this.recordItem.getType().isAir();
	}

	@Override
	public boolean isPlaying()
	{
		return this.playing;
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

}
