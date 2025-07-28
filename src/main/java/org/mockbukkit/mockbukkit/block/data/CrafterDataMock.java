package org.mockbukkit.mockbukkit.block.data;

import com.google.common.base.Preconditions;
import org.bukkit.Material;
import org.bukkit.block.data.type.Crafter;
import org.jetbrains.annotations.NotNull;

public class CrafterDataMock extends BlockDataMock implements Crafter
{

	/**
	 * Constructs a new {@link BlockDataMock} for the provided {@link Material}.
	 *
	 * @param material The material this data is for.
	 */
	public CrafterDataMock(@NotNull Material material)
	{
		super(material);
	}

	@Override
	public boolean isCrafting()
	{
		return this.get(BlockDataKey.CRAFTING);
	}

	@Override
	public void setCrafting(boolean crafting)
	{
		this.set(BlockDataKey.CRAFTING, crafting);
	}

	@Override
	public boolean isTriggered()
	{
		return this.get(BlockDataKey.TRIGGERED);
	}

	@Override
	public void setTriggered(boolean triggered)
	{
		this.set(BlockDataKey.TRIGGERED, triggered);
	}

	@Override
	public @NotNull org.bukkit.block.Orientation getOrientation()
	{
		return this.get(BlockDataKey.ORIENTATION);
	}

	@Override
	public void setOrientation(@NotNull org.bukkit.block.Orientation orientation)
	{
		Preconditions.checkArgument(orientation != null, "orientation cannot be null!");
		this.set(BlockDataKey.ORIENTATION, orientation);
	}

}
