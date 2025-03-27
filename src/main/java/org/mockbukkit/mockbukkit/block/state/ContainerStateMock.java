package org.mockbukkit.mockbukkit.block.state;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.Container;
import org.bukkit.inventory.Inventory;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.mockbukkit.mockbukkit.inventory.InventoryMock;

import java.util.Arrays;
import java.util.Objects;

/**
 * Mock implementation of a {@link Container}.
 *
 * @see TileStateMock
 */
public abstract class ContainerStateMock extends LockableTileStateMock implements Container
{

	private Inventory inventory;
	private final Inventory snapshot;
	private @Nullable Component customName;

	/**
	 * Constructs a new {@link ContainerStateMock} for the provided {@link Material}.
	 *
	 * @param material The material this state is for.
	 */
	protected ContainerStateMock(@NotNull Material material)
	{
		super(material);
		this.inventory = createInventory();
		this.snapshot = createInventory();
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
		this.snapshot = createInventory();
	}

	/**
	 * Constructs a new {@link ContainerStateMock} by cloning the data from an existing one.
	 *
	 * @param state The state to clone.
	 */
	protected ContainerStateMock(@NotNull ContainerStateMock state)
	{
		super(state);
		this.inventory = state.inventory;
		this.customName = state.customName();
		this.snapshot = ((InventoryMock) state.getSnapshotInventory()).getSnapshot();
	}

	/**
	 * @return A new inventory, of the correct type for the state.
	 */
	protected abstract @NotNull InventoryMock createInventory();

	@Override
	public abstract @NotNull ContainerStateMock getSnapshot();

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
		if (!this.isPlaced())
		{
			return snapshot;
		}
		return this.inventory;
	}

	@Override
	public @NotNull Inventory getSnapshotInventory()
	{
		return snapshot;
	}

	@Override
	public boolean update(boolean force, boolean applyPhysics)
	{
		if (super.update(force, applyPhysics))
		{
			this.inventory.clear();
			this.inventory.setContents(snapshot.getContents());
			return true;
		}
		return false;
	}

	@Override
	public boolean equals(Object o)
	{
		if (this == o)
		{
			return true;
		}
		if (!(o instanceof ContainerStateMock that))
		{
			return false;
		}
		if (!super.equals(o))
		{
			return false;
		}
		if (isPlaced() && !Objects.equals(inventory, that.inventory))
		{
			return false;
		}
		return Objects.equals(customName, that.customName) && Arrays.equals(snapshot.getContents(), that.snapshot.getContents());
	}

	@Override
	public int hashCode()
	{
		if (isPlaced())
		{
			return Objects.hash(super.hashCode(), inventory, customName, Arrays.hashCode(snapshot.getContents()));
		}
		return Objects.hash(super.hashCode(), customName, Arrays.hashCode(snapshot.getContents()));
	}

	@Override
	protected String toStringInternal()
	{
		return super.toStringInternal() +
				", customName=" + customName +
				", inventory=" + inventory;
	}

}
