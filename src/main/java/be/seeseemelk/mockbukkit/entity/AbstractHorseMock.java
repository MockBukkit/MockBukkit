package be.seeseemelk.mockbukkit.entity;

import be.seeseemelk.mockbukkit.ServerMock;
import be.seeseemelk.mockbukkit.UnimplementedOperationException;
import com.google.common.base.Preconditions;
import org.bukkit.entity.AbstractHorse;
import org.bukkit.entity.AnimalTamer;
import org.bukkit.entity.Horse;
import org.bukkit.inventory.AbstractHorseInventory;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.UUID;

/**
 * Mock implementation of an {@link AbstractHorse}.
 *
 * @see AnimalsMock
 */
public abstract class AbstractHorseMock extends AnimalsMock implements AbstractHorse
{

	private @Nullable UUID owner;
	private int maxDomestication = 100;
	private int domestication;
	private double jumpStrength = 0.7;
	private boolean tamed;
	private boolean isEating;
	private boolean isMouthOpen;
	private boolean rearing;

	/**
	 * Constructs a new {@link AbstractHorseMock} on the provided {@link ServerMock} with a specified {@link UUID}.
	 *
	 * @param server The server to create the entity on.
	 * @param uuid   The UUID of the entity.
	 */
	protected AbstractHorseMock(@NotNull ServerMock server, @NotNull UUID uuid)
	{
		super(server, uuid);
	}

	@Override
	@Deprecated(since = "1.11")
	public void setVariant(Horse.Variant variant)
	{
		throw new UnsupportedOperationException("Not supported.");
	}

	@Override
	public int getDomestication()
	{
		return this.domestication;
	}

	@Override
	public void setDomestication(int value)
	{
		Preconditions.checkArgument(value >= 0, "Domestication cannot be less than zero");
		Preconditions.checkArgument(value <= this.getMaxDomestication(),
				"Domestication cannot be greater than the max domestication");
		this.domestication = value;
	}

	@Override
	public int getMaxDomestication()
	{
		return this.maxDomestication;
	}

	@Override
	public void setMaxDomestication(int value)
	{
		Preconditions.checkArgument(value > 0, "Max domestication cannot be zero or less");
		this.maxDomestication = value;
	}

	@Override
	public double getJumpStrength()
	{
		return this.jumpStrength;
	}

	@Override
	public void setJumpStrength(double strength)
	{
		Preconditions.checkArgument(strength >= 0, "Jump strength cannot be less than zero");
		this.jumpStrength = strength;
	}

	@Override
	public boolean isTamed()
	{
		return this.tamed;
	}

	@Override
	public void setTamed(boolean tamed)
	{
		this.tamed = tamed;
	}

	@Override
	public @Nullable UUID getOwnerUniqueId()
	{
		return this.owner;
	}

	@Override
	public AnimalTamer getOwner()
	{
		if (this.getOwnerUniqueId() == null)
		{
			return null;
		}
		return getServer().getOfflinePlayer(this.getOwnerUniqueId());
	}

	@Override
	public void setOwner(@Nullable AnimalTamer owner)
	{
		if (owner != null)
		{
			this.setTamed(true);
			this.setOwnerUUID(owner.getUniqueId());
		}
		else
		{
			this.setTamed(false);
			this.setOwnerUUID(null);
		}
	}

	/**
	 * Sets the return value of {@link #getOwner()} and {@link #getOwnerUniqueId()}.
	 *
	 * @param uuid The UUID to set.
	 */
	public void setOwnerUUID(@Nullable UUID uuid)
	{
		this.owner = uuid;
	}

	@Override
	@Deprecated(since = "1.18")
	public boolean isEatingHaystack()
	{
		return this.isEating;
	}

	@Override
	@Deprecated(since = "1.18")
	public void setEatingHaystack(boolean eatingHaystack)
	{
		this.isEating = eatingHaystack;
	}

	@Override
	public @NotNull AbstractHorseInventory getInventory()
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public boolean isEatingGrass()
	{
		return this.isEatingHaystack();
	}

	@Override
	public void setEatingGrass(boolean eating)
	{
		this.setEatingHaystack(eating);
	}

	@Override
	public boolean isRearing()
	{
		return this.rearing;
	}

	@Override
	public void setRearing(boolean rearing)
	{
		this.rearing = rearing;
	}

	@Override
	public boolean isEating()
	{
		return this.isMouthOpen;
	}

	@Override
	public void setEating(boolean eating)
	{
		this.isMouthOpen = eating;
	}

}
