package org.mockbukkit.mockbukkit.entity;

import org.mockbukkit.mockbukkit.ServerMock;
import com.google.common.base.Preconditions;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.minecart.PoweredMinecart;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;

/**
 * Mock implementation of an {@link PoweredMinecart}.
 *
 * @see MinecartMock
 */
public class PoweredMinecartMock extends MinecartMock implements PoweredMinecart
{

	private double zPush;
	private double xPush;
	private int fuel;

	/**
	 * Constructs a new {@link PoweredMinecartMock} on the provided {@link ServerMock} with a specified {@link UUID}.
	 *
	 * @param server The server to create the entity on.
	 * @param uuid   The UUID of the entity.
	 */
	public PoweredMinecartMock(@NotNull ServerMock server, @NotNull UUID uuid)
	{
		super(server, uuid);
		this.fuel = 0;
		this.xPush = 0;
		this.zPush = 0;
	}

	@Override
	public @NotNull Material getMinecartMaterial()
	{
		return Material.FURNACE_MINECART;
	}

	@Override
	public int getFuel()
	{
		return this.fuel;
	}

	@Override
	public void setFuel(int fuel)
	{
		Preconditions.checkArgument(fuel >= 0, "ticks cannot be negative");
		this.fuel = fuel;
	}

	@Override
	public double getPushX()
	{
		return this.xPush;
	}

	@Override
	public double getPushZ()
	{
		return this.zPush;
	}

	@Override
	public void setPushX(double xPush)
	{
		this.xPush = xPush;
	}

	@Override
	public void setPushZ(double zPush)
	{
		this.zPush = zPush;
	}

	@Override
	public @NotNull EntityType getType()
	{
		return EntityType.FURNACE_MINECART;
	}

}
