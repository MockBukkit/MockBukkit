package org.mockbukkit.mockbukkit.block.state;

import com.google.common.base.Preconditions;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.Crafter;
import org.jspecify.annotations.NullMarked;
import org.mockbukkit.mockbukkit.inventory.CrafterInventoryMock;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * Mock implementation of a {@link Crafter}.
 *
 * @see LootableContainerStateMock
 */
@NullMarked
public class CrafterStateMock extends LootableContainerStateMock implements Crafter
{
	private final Set<Integer> disabledSlots = new HashSet<>();

	private int craftingTicks = 0;
	private boolean triggered = false;

	protected CrafterStateMock(Material material)
	{
		super(material);
	}

	protected CrafterStateMock(Block block)
	{
		super(block);
	}

	protected CrafterStateMock(CrafterStateMock state)
	{
		super(state);

		this.disabledSlots.addAll(state.disabledSlots);
		this.craftingTicks = state.craftingTicks;
		this.triggered = state.triggered;
	}

	@Override
	public int getCraftingTicks()
	{
		return this.craftingTicks;
	}

	@Override
	public void setCraftingTicks(int ticks)
	{
		this.craftingTicks = ticks;
	}

	@Override
	public boolean isSlotDisabled(int slot)
	{
		Preconditions.checkArgument(slot >= 0 && slot < 9, "Invalid slot index %s for Crafter", slot);
		return disabledSlots.contains(slot);
	}

	@Override
	public void setSlotDisabled(int slot, boolean disabled)
	{
		Preconditions.checkArgument(slot >= 0 && slot < 9, "Invalid slot index %s for Crafter", slot);
		if (disabled)
		{
			this.disabledSlots.add(slot);
		}
		else
		{
			this.disabledSlots.remove(slot);
		}
	}

	@Override
	public boolean isTriggered()
	{
		return this.triggered;
	}

	@Override
	public void setTriggered(boolean triggered)
	{
		this.triggered = triggered;
	}

	@Override
	protected CrafterInventoryMock createInventory()
	{
		return new CrafterInventoryMock(this);
	}

	@Override
	public CrafterStateMock getSnapshot()
	{
		return new CrafterStateMock(this);
	}

	@Override
	public boolean equals(Object o)
	{
		if (!(o instanceof CrafterStateMock that))
		{
			return false;
		}
		if (!super.equals(o))
		{
			return false;
		}
		return craftingTicks == that.craftingTicks && triggered == that.triggered
				&& Objects.equals(disabledSlots, that.disabledSlots);
	}

	@Override
	public int hashCode()
	{
		return Objects.hash(super.hashCode(), disabledSlots, craftingTicks, triggered);
	}

}
