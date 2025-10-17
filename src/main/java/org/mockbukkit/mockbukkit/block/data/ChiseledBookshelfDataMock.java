package org.mockbukkit.mockbukkit.block.data;

import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableSet;
import org.bukkit.Material;
import org.bukkit.block.BlockFace;
import org.bukkit.block.data.type.ChiseledBookshelf;
import org.jetbrains.annotations.NotNull;

import java.util.Set;

public class ChiseledBookshelfDataMock extends BlockDataMock implements ChiseledBookshelf
{

	public ChiseledBookshelfDataMock(@NotNull Material material)
	{
		super(material);
	}

	protected ChiseledBookshelfDataMock(@NotNull BlockDataMock other)
	{
		super(other);
	}

	@Override
	public boolean isSlotOccupied(int slot)
	{
		return this.get(getKeyForSlot(slot));
	}

	@Override
	public void setSlotOccupied(int slot, boolean occupied)
	{
		BlockDataKey key = getKeyForSlot(slot);
		this.set(key, occupied);
	}

	@Override
	public @NotNull Set<Integer> getOccupiedSlots()
	{
		ImmutableSet.Builder<Integer> slotOccupied = ImmutableSet.builder();
		for (int index = 0; index < this.getMaximumOccupiedSlots(); index++)
		{
			if (this.isSlotOccupied(index))
			{
				slotOccupied.add(index);
			}
		}
		return slotOccupied.build();
	}

	@Override
	public int getMaximumOccupiedSlots()
	{
		return getLimitationValue(BlockDataLimitation.Type.MAX_OCCUPIED_SLOTS);
	}

	@Override
	public @NotNull BlockFace getFacing()
	{
		return this.get(BlockDataKey.FACING);
	}

	@Override
	public void setFacing(@NotNull BlockFace blockFace)
	{
		Preconditions.checkArgument(blockFace != null, "blockFace cannot be null!");
		Preconditions.checkArgument(blockFace.isCartesian() && blockFace.getModY() == 0, "Invalid face, only cartesian horizontal face are allowed for this property!");
		this.set(BlockDataKey.FACING, blockFace);
	}

	@Override
	public @NotNull Set<BlockFace> getFaces()
	{
		return this.getLimitationValue(BlockDataLimitation.Type.FACES);
	}

	@Override
	public @NotNull ChiseledBookshelfDataMock clone()
	{
		return new ChiseledBookshelfDataMock(this);
	}

	/**
	 * Converts the given slot into a key.
	 *
	 * @param slot The slot to convert.
	 *
	 * @return The key for the given slot.
	 *
	 * @throws IndexOutOfBoundsException If the slot is not between 0 and 5.
	 */
	private static BlockDataKey getKeyForSlot(int slot)
	{
		return switch (slot)
		{
			case 0 -> BlockDataKey.SLOT_OCCUPIED_0;
			case 1 -> BlockDataKey.SLOT_OCCUPIED_1;
			case 2 -> BlockDataKey.SLOT_OCCUPIED_2;
			case 3 -> BlockDataKey.SLOT_OCCUPIED_3;
			case 4 -> BlockDataKey.SLOT_OCCUPIED_4;
			case 5 -> BlockDataKey.SLOT_OCCUPIED_5;
			default -> throw new IndexOutOfBoundsException("0 <= slot <= 5");
		};
	}

}
