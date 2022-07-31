package be.seeseemelk.mockbukkit.entity;

import be.seeseemelk.mockbukkit.ServerMock;
import org.bukkit.Sound;
import org.bukkit.entity.Fish;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;

public abstract class FishMock extends CreatureMock implements Fish
{

	private boolean isFromBucket = false;

	protected FishMock(@NotNull ServerMock server, @NotNull UUID uuid)
	{
		super(server, uuid);
	}

	@Override
	public boolean isFromBucket()
	{
		return this.isFromBucket;
	}

	@Override
	public void setFromBucket(boolean fromBucket)
	{
		this.isFromBucket = fromBucket;
	}

	@Override
	public abstract @NotNull ItemStack getBaseBucketItem();

	@Override
	public @NotNull Sound getPickupSound(){
		return Sound.ITEM_BUCKET_FILL_FISH;
	}

}
