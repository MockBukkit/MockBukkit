package org.mockbukkit.mockbukkit.entity;

import org.mockbukkit.mockbukkit.ServerMock;
import org.mockbukkit.mockbukkit.inventory.ItemStackMock;
import com.google.common.base.Preconditions;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.Tag;
import org.bukkit.entity.Axolotl;
import org.bukkit.entity.EntityType;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;

/**
 * Mock implementation of an {@link Axolotl}.
 *
 * @see AnimalsMock
 */
public class AxolotlMock extends AnimalsMock implements Axolotl
{

	private boolean isPlayingDead = false;
	private @NotNull Variant variant = Variant.LUCY;

	private boolean fromBucket = false;

	/**
	 * Constructs a new {@link AxolotlMock} on the provided {@link ServerMock} with a specified {@link UUID}.
	 *
	 * @param server The server to create the entity on.
	 * @param uuid   The UUID of the entity.
	 */
	public AxolotlMock(@NotNull ServerMock server, @NotNull UUID uuid)
	{
		super(server, uuid);
	}

	@Override
	public boolean isPlayingDead()
	{
		return this.isPlayingDead;
	}

	@Override
	public void setPlayingDead(boolean playingDead)
	{
		this.isPlayingDead = playingDead;
	}

	@Override
	public @NotNull Variant getVariant()
	{
		return this.variant;
	}

	@Override
	public void setVariant(@NotNull Variant variant)
	{
		Preconditions.checkNotNull(variant, "Variant can't be null");
		this.variant = variant;
	}

	@Override
	public boolean isFromBucket()
	{
		return this.fromBucket;
	}

	@Override
	public void setFromBucket(boolean fromBucket)
	{
		this.fromBucket = fromBucket;
	}

	@Override
	public @NotNull ItemStack getBaseBucketItem()
	{
		return new ItemStackMock(Material.AXOLOTL_BUCKET);
	}

	@Override
	public @NotNull Sound getPickupSound()
	{
		return Sound.ITEM_BUCKET_FILL_AXOLOTL;
	}

	@Override
	public boolean isBreedItem(@NotNull ItemStack stack)
	{
		Preconditions.checkNotNull(stack, "ItemStack cannot be null");
		return Tag.ITEMS_AXOLOTL_FOOD.isTagged(stack.getType());
	}

	@Override
	public EntityType getType()
	{
		return EntityType.AXOLOTL;
	}

}
