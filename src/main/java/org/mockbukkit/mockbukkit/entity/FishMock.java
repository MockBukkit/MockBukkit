package org.mockbukkit.mockbukkit.entity;

import org.mockbukkit.mockbukkit.ServerMock;
import org.bukkit.Sound;
import org.bukkit.entity.Fish;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;

/**
 * Mock implementation of a {@link Fish}.
 *
 * @see CreatureMock
 */
public abstract class FishMock extends CreatureMock implements Fish
{

	private boolean isFromBucket = false;

	/**
	 * Constructs a new {@link FishMock} on the provided {@link ServerMock} with a specified {@link UUID}.
	 *
	 * @param server The server to create the entity on.
	 * @param uuid   The UUID of the entity.
	 */
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
	public @NotNull Sound getPickupSound()
	{
		return Sound.ITEM_BUCKET_FILL_FISH;
	}

}
