package be.seeseemelk.mockbukkit.block.state;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.block.Container;
import org.bukkit.inventory.Inventory;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import be.seeseemelk.mockbukkit.inventory.InventoryMock;

/**
 * The {@link ContainerMock} is an extension of a {@link TileStateMock} which can also hold an {@link Inventory}.
 *
 * @author TheBusyBiscuit
 *
 */
public abstract class ContainerMock extends TileStateMock implements Container
{

	private final Inventory inventory;
	private String customName;
	private String lock = "";

	public ContainerMock(@NotNull Material material)
	{
		super(material);
		this.inventory = createInventory();
	}

	protected ContainerMock(@NotNull Block block)
	{
		super(block);
		this.inventory = createInventory();
	}

	protected ContainerMock(@NotNull ContainerMock state)
	{
		super(state);
		this.inventory = state.getInventory();
	}

	protected abstract InventoryMock createInventory();

	@Override
	public abstract BlockState getSnapshot();

	@Override
	public boolean isLocked()
	{
		return !lock.isEmpty();
	}

	@Override
	@NotNull
	public String getLock()
	{
		return lock;
	}

	@Override
	public void setLock(String key)
	{
		if (key == null)
		{
			lock = "";
		}
		else
		{
			lock = key;
		}
	}

	@Override
	@Nullable
	public String getCustomName()
	{
		return customName;
	}

	@Override
	public void setCustomName(String name)
	{
		this.customName = name;
	}

	@Override
	public Inventory getInventory()
	{
		return inventory;
	}

	@Override
	public Inventory getSnapshotInventory()
	{
		return ((InventoryMock) inventory).getSnapshot();
	}
}
