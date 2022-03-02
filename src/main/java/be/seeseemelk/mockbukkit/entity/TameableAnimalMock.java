package be.seeseemelk.mockbukkit.entity;

import be.seeseemelk.mockbukkit.ServerMock;
import org.bukkit.entity.AnimalTamer;
import org.bukkit.entity.Creature;
import org.bukkit.entity.Tameable;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;

public class TameableAnimalMock extends AnimalsMock implements Tameable, Creature
{

	private UUID owner;
	private boolean tamed;
	private boolean sitting;

	public TameableAnimalMock(@NotNull ServerMock server, @NotNull UUID uuid)
	{
		super(server, uuid);
	}

	public UUID getOwnerUUID()
	{
		return this.owner;
	}

	public void setOwnerUUID(UUID uuid)
	{
		this.owner = uuid;
	}

	@Override
	public AnimalTamer getOwner()
	{
		if (this.getOwnerUUID() == null)
		{
			return null;
		}

		AnimalTamer owner = getServer().getPlayer(this.getOwnerUUID());
		if (owner == null)
		{
			owner = getServer().getOfflinePlayer(this.getOwnerUUID());
		}

		return owner;
	}

	@Override
	public boolean isTamed()
	{
		return this.tamed;
	}

	@Override
	public void setOwner(AnimalTamer tamer)
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
	public String toString()
	{
		return getClass().getSimpleName() + "{owner=" + this.getOwner() + ",tamed=" + this.isTamed() + "}";
	}

}
