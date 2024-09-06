package org.mockbukkit.mockbukkit.entity;

import org.mockbukkit.mockbukkit.ServerMock;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.ItemDisplay;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.UUID;

public class ItemDisplayMock extends DisplayMock implements ItemDisplay
{

	private ItemStack itemStack = ItemStack.empty();
	private ItemDisplayTransform itemDisplayTransform = ItemDisplayTransform.NONE;

	/**
	 * Constructs a new EntityMock on the provided {@link ServerMock} with a specified {@link UUID}.
	 *
	 * @param server The server to create the entity on.
	 * @param uuid   The UUID of the entity.
	 */
	public ItemDisplayMock(@NotNull ServerMock server, @NotNull UUID uuid)
	{
		super(server, uuid);
	}

	@Override
	public @Nullable ItemStack getItemStack()
	{
		return itemStack.clone();
	}

	@Override
	public void setItemStack(@Nullable ItemStack item)
	{
		if (item == null)
		{
			this.itemStack = ItemStack.empty();
		}
		else
		{
			this.itemStack = item.clone();
		}
	}

	@Override
	public @NotNull ItemDisplayTransform getItemDisplayTransform()
	{
		return this.itemDisplayTransform;
	}

	@Override
	public void setItemDisplayTransform(@NotNull ItemDisplayTransform display)
	{
		this.itemDisplayTransform = display;
	}

	@Override
	public EntityType getType()
	{
		return EntityType.ITEM_DISPLAY;
	}

}
