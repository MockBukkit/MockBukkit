package be.seeseemelk.mockbukkit.block.state;

import be.seeseemelk.mockbukkit.inventory.InventoryMock;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.block.Container;
import org.bukkit.inventory.Inventory;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Mock implementation of a {@link Container}.
 *
 * @see TileStateMock
 */
public abstract class ContainerStateMock extends TileStateMock implements Container
{

	private final Inventory inventory;
	private @Nullable Component customName;
	private @NotNull String lock = "";

	/**
	 * Constructs a new {@link ContainerStateMock} for the provided {@link Material}.
	 *
	 * @param material The material this state is for.
	 */
	protected ContainerStateMock(@NotNull Material material)
	{
		super(material);
		this.inventory = createInventory();
	}

	/**
	 * Constructs a new {@link ContainerStateMock} for the provided {@link Block}.
	 *
	 * @param block The block this state is for.
	 */
	protected ContainerStateMock(@NotNull Block block)
	{
		super(block);
		this.inventory = createInventory();
	}

	/**
	 * Constructs a new {@link ContainerStateMock} by cloning the data from an existing one.
	 *
	 * @param state The state to clone.
	 */
	protected ContainerStateMock(@NotNull ContainerStateMock state)
	{
		super(state);
		this.inventory = state.getInventory();
		this.customName = state.customName();
		this.lock = state.getLock();
	}

	/**
	 * @return A new inventory, of the correct type for the state.
	 */
	protected abstract InventoryMock createInventory();

	@Override
	public abstract @NotNull BlockState getSnapshot();

	@Override
	public boolean isLocked()
	{
		return !this.lock.isEmpty();
	}

	@Override
	public @NotNull String getLock()
	{
		return this.lock;
	}

	@Override
	public void setLock(@Nullable String key)
	{
		this.lock = key == null ? "" : key;
	}

	@Override
	public @Nullable Component customName()
	{
		return this.customName;
	}

	@Override
	public void customName(@Nullable Component customName)
	{
		this.customName = customName;
	}

	@Override
	@Nullable
	public String getCustomName()
	{
		return this.customName == null ? null : LegacyComponentSerializer.legacySection().serialize(this.customName);
	}

	@Override
	public void setCustomName(@Nullable String name)
	{
		this.customName = name == null ? null : LegacyComponentSerializer.legacySection().deserialize(name);
	}

	@Override
	public @NotNull Inventory getInventory()
	{
		return this.inventory;
	}

	@Override
	public @NotNull Inventory getSnapshotInventory()
	{
		return ((InventoryMock) this.inventory).getSnapshot();
	}

}
