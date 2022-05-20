package be.seeseemelk.mockbukkit.entity;

import be.seeseemelk.mockbukkit.ServerMock;
import be.seeseemelk.mockbukkit.UnimplementedOperationException;
import org.apache.commons.lang.Validate;
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

	public AbstractHorseMock(@NotNull ServerMock server, @NotNull UUID uuid)
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
		Validate.isTrue(value >= 0, "Domestication cannot be less than zero");
		Validate.isTrue(value <= this.getMaxDomestication(), "Domestication cannot be greater than the max domestication");
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
		Validate.isTrue(value > 0, "Max domestication cannot be zero or less");
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
		Validate.isTrue(strength >= 0, "Jump strength cannot be less than zero");
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
		return isEating;
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
	public boolean isEatingGrass()
	{
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException();
	}

	@Override
	public void setEatingGrass(boolean eating)
	{
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean isRearing()
	{
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException();
	}

	@Override
	public void setRearing(boolean rearing)
	{
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean isEating()
	{
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException();
	}

	@Override
	public void setEating(boolean eating)
	{
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException();
	}

}
