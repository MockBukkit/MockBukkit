package org.mockbukkit.mockbukkit.entity;

import org.mockbukkit.mockbukkit.ServerMock;
import org.mockbukkit.mockbukkit.util.AdventureConverters;
import com.google.common.base.Preconditions;
import net.kyori.adventure.sound.Sound;
import org.bukkit.entity.Bogged;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Skeleton;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;

/**
 * Mock implementation of an {@link Bogged}.
 *
 * @see AbstractSkeletonMock
 */
public class BoggedMock extends AbstractSkeletonMock implements Bogged
{

	private boolean sheared;

	/**
	 * Constructs a new {@link BoggedMock} on the provided {@link ServerMock} with a specified {@link UUID}.
	 *
	 * @param server The server to create the entity on.
	 * @param uuid   The UUID of the entity.
	 */
	public BoggedMock(@NotNull ServerMock server, @NotNull UUID uuid)
	{
		super(server, uuid);
	}

	@Override
	public void shear(@NotNull Sound.Source source)
	{
		Preconditions.checkNotNull(source, "The source cannot be null");

		if (this.isInWorld())
		{
			this.getWorld().playSound(this, org.bukkit.Sound.ENTITY_BOGGED_SHEAR, AdventureConverters.soundSourceToCategory(source), 1.0F, 1.0F);
		}
		this.setSheared(true);
	}

	@Override
	public boolean readyToBeSheared()
	{
		return !isSheared() && !isDead();
	}

	@Override
	public @NotNull Skeleton.SkeletonType getSkeletonType()
	{
		return Skeleton.SkeletonType.BOGGED;
	}

	@Override
	public boolean isSheared()
	{
		return this.sheared;
	}

	@Override
	public void setSheared(boolean flag)
	{
		this.sheared = flag;
	}

	@Override
	public @NotNull EntityType getType()
	{
		return EntityType.BOGGED;
	}

}
