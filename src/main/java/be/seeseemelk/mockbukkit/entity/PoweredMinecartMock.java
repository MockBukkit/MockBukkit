package be.seeseemelk.mockbukkit.entity;

import java.util.UUID;

import org.bukkit.Material;
import org.bukkit.entity.minecart.PoweredMinecart;
import org.jetbrains.annotations.NotNull;

import com.google.common.base.Preconditions;

import be.seeseemelk.mockbukkit.ServerMock;

public class PoweredMinecartMock extends MinecartMock implements PoweredMinecart
{

	private double zPush;
	private double xPush;
	private int fuel;

	protected PoweredMinecartMock(@NotNull ServerMock server, @NotNull UUID uuid)
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

}
