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

public abstract class AbstractHorseMock extends AnimalsMock implements AbstractHorse
{

	private UUID owner;
	private int maxDomestication;
	private int domestication;
	private double jumpStrength;
	private boolean tamed;
	private boolean isEating;
	private boolean isMouthOpen;
	private boolean rearing;

	protected AbstractHorseMock(@NotNull ServerMock server, @NotNull UUID uuid)
	{
		super(server, uuid);
	}

	@Override
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
		Preconditions.checkArgument(value <= this.getMaxDomestication(), "Domestication cannot be greater than the max domestication");
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
	public void setOwner(AnimalTamer owner)
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

	public void setOwnerUUID(UUID uuid)
	{
		this.owner = uuid;
	}

	@Override
	public boolean isEatingHaystack()
	{
		return this.isEating;
	}

	@Override
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
	@Deprecated
	public boolean isEatingGrass()
	{
		return this.isEatingHaystack();
	}

	@Override
	@Deprecated
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
