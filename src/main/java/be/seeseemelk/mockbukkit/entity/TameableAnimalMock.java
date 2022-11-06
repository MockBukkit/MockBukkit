package be.seeseemelk.mockbukkit.entity;

import be.seeseemelk.mockbukkit.ServerMock;
import org.bukkit.entity.AnimalTamer;
import org.bukkit.entity.Creature;
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
	 * Constructs a new {@link TameableAnimalMock} on the provided {@link ServerMock} with a specified {@link UUID}.
	 *
	 * @param server The server to create the entity on.
	 * @param uuid   The UUID of the entity.
	 */
	public TameableAnimalMock(@NotNull ServerMock server, @NotNull UUID uuid)
	{
		super(server, uuid);
	}

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

	// Sitting methods implemented here for animals that implement Sittable.
	public boolean isSitting()
	{
		return this.sitting;
	}

	public void setSitting(boolean sitting)
	{
		this.sitting = sitting;
	}

	@Override
	public @NotNull String toString()
	{
		return getClass().getSimpleName() + "{owner=" + this.getOwner() + ",tamed=" + this.isTamed() + "}";
	}

}
