package org.mockbukkit.mockbukkit.entity;

import org.mockbukkit.mockbukkit.ServerMock;
import com.google.common.base.Preconditions;
import org.bukkit.entity.AnimalTamer;
import org.bukkit.entity.Creature;
import org.bukkit.entity.Sittable;
import org.bukkit.entity.Tameable;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.UUID;

/**
 * Mock implementation of a {@link Tameable} {@link Creature}.
 *
 * @see AnimalsMock
 */
public class TameableAnimalMock extends AnimalsMock implements Tameable, Creature
{

	private @Nullable UUID owner;
	private boolean tamed;
	private boolean sitting;

	/**
	 * Constructs a new  on the provided {@link ServerMock} with a specified {@link UUID}.
	 *
	 * @param server The server to create the entity on.
	 * @param uuid   The UUID of the entity.
	 */
	public TameableAnimalMock(@NotNull ServerMock server, @NotNull UUID uuid)
	{
		super(server, uuid);
	}

	/**
	 * Sets the owner of this animal by UUID.
	 *
	 * @param uuid The UUID.
	 * @see #getOwner()
	 * @see #getOwnerUniqueId()
	 */
	public void setOwnerUUID(@Nullable UUID uuid)
	{
		this.owner = uuid;
	}

	@Override
	public AnimalTamer getOwner()
	{
		if (this.getOwnerUniqueId() == null)
		{
			return null;
		}

		AnimalTamer tamer = getServer().getPlayer(this.getOwnerUniqueId());
		if (tamer == null)
		{
			tamer = getServer().getOfflinePlayer(this.getOwnerUniqueId());
		}

		return tamer;
	}

	@Override
	public boolean isTamed()
	{
		return this.tamed;
	}

	@Override
	public void setOwner(@Nullable AnimalTamer tamer)
	{
		if (tamer != null)
		{
			this.setTamed(true);
			this.setOwnerUUID(tamer.getUniqueId());
		}
		else
		{
			this.setTamed(false);
			this.setOwnerUUID(null);
		}
	}

	@Override
	public void setTamed(boolean tame)
	{
		this.tamed = tame;
		if (!tame)
		{
			this.setOwnerUUID(null);
		}
	}

	@Override
	public @Nullable UUID getOwnerUniqueId()
	{
		return this.owner;
	}

	/**
	 * Checks if the animal is sitting.
	 *
	 * @return Whether the animal is sitting.
	 * @throws IllegalStateException If the animal doesn't implement {@link Sittable}.
	 * @see Sittable#isSitting
	 */
	// Sitting methods implemented here for animals that implement Sittable.
	public boolean isSitting()
	{
		Preconditions.checkState(this instanceof Sittable, "Not sittable");
		return this.sitting;
	}

	/**
	 * Sets whether the animal is sitting.
	 *
	 * @param sitting Whether the animal is sitting.
	 * @throws IllegalStateException If the animal doesn't implement {@link Sittable}.
	 * @see Sittable#setSitting(boolean)
	 */
	public void setSitting(boolean sitting)
	{
		Preconditions.checkState(this instanceof Sittable, "Not sittable");
		this.sitting = sitting;
	}

	@Override
	public @NotNull String toString()
	{
		return getClass().getSimpleName() + "{owner=" + this.getOwner() + ",tamed=" + this.isTamed() + "}";
	}

}
