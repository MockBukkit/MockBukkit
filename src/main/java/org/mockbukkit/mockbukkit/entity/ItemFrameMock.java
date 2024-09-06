package org.mockbukkit.mockbukkit.entity;

import org.mockbukkit.mockbukkit.ServerMock;
import com.google.common.base.Preconditions;
import org.bukkit.Rotation;
import org.bukkit.Sound;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.ItemFrame;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.UUID;

/**
 * Mock implementation of an {@link ItemFrame}.
 *
 * @see HangingMock
 */
public class ItemFrameMock extends HangingMock implements ItemFrame
{
	private ItemStack itemStack = ItemStack.empty();
	private Rotation rotation = Rotation.NONE;

	private float dropChance = 1.0F;

	private boolean visible = true;
	private boolean fixed = false;

	/**
	 * Constructs a new {@link ItemFrame} on the provided {@link ServerMock} with a specified {@link UUID}.
	 *
	 * @param server The server to create the entity on.
	 * @param uuid   The UUID of the entity.
	 */
	public ItemFrameMock(@NotNull ServerMock server, @NotNull UUID uuid)
	{
		super(server, uuid);
	}

	@Override
	public @NotNull ItemStack getItem()
	{
		return this.itemStack;
	}

	@Override
	public void setItem(@Nullable ItemStack item)
	{
		this.setItem(item, true);
	}

	@Override
	public void setItem(@Nullable ItemStack item, boolean playSound)
	{
		this.itemStack = item == null || item.isEmpty() ? ItemStack.empty() : item.clone();

		if (!itemStack.isEmpty() && playSound && isInWorld())
		{
			getWorld().playSound(this, Sound.ENTITY_ITEM_FRAME_ADD_ITEM, 1, 1);
		}
	}

	@Override
	public float getItemDropChance()
	{
		return this.dropChance;
	}

	@Override
	public void setItemDropChance(float chance)
	{
		Preconditions.checkArgument(0.0 <= chance && chance <= 1.0, "Chance (%s) outside range [0, 1]", chance);
		this.dropChance = chance;
	}

	@Override
	public @NotNull Rotation getRotation()
	{
		return this.rotation;
	}

	@Override
	public void setRotation(@NotNull Rotation rotation) throws IllegalArgumentException
	{
		Preconditions.checkArgument(rotation != null, "Rotation cannot be null");
		this.rotation = rotation;
	}

	@Override
	public boolean isVisible()
	{
		return this.visible;
	}

	@Override
	public void setVisible(boolean visible)
	{
		this.visible = visible;
	}

	@Override
	public boolean isFixed()
	{
		return this.fixed;
	}

	@Override
	public void setFixed(boolean fixed)
	{
		this.fixed = fixed;
	}

	@Override
	public @NotNull EntityType getType()
	{
		return EntityType.ITEM_FRAME;
	}

}
