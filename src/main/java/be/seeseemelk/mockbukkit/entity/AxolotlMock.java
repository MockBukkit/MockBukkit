package be.seeseemelk.mockbukkit.entity;

import be.seeseemelk.mockbukkit.ServerMock;
import com.google.common.base.Preconditions;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.Tag;
import org.bukkit.entity.Axolotl;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;

public class AxolotlMock extends AnimalsMock implements Axolotl
{

	private boolean isPlayingDead = false;
	private Variant variant = Variant.LUCY;

	private boolean fromBucket = false;

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
		return new ItemStack(Material.AXOLOTL_BUCKET);
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
		return Tag.AXOLOTL_TEMPT_ITEMS.isTagged(stack.getType());
	}

	@Override
	public boolean isBreedItem(@NotNull Material material)
	{
		Preconditions.checkNotNull(material, "Material cannot be null");
		return isBreedItem(new ItemStack(material));
	}

}
