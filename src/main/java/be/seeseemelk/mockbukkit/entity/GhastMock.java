package be.seeseemelk.mockbukkit.entity;

import be.seeseemelk.mockbukkit.ServerMock;
import com.google.common.base.Preconditions;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Flying;
import org.bukkit.entity.Ghast;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;


public class GhastMock extends MobMock implements Ghast, Flying
{

	private boolean isCharging = false;
	private int explosionPower = 1;

	public GhastMock(@NotNull ServerMock server, @NotNull UUID uuid)
	{
		super(server, uuid);
	}

	@Override
	public boolean isCharging()
	{
		return this.isCharging;
	}

	@Override
	public void setCharging(boolean flag)
	{
		this.isCharging = flag;
	}

	@Override
	public int getExplosionPower()
	{
		return this.explosionPower;
	}

	@Override
	public void setExplosionPower(int explosionPower)
	{
		Preconditions.checkArgument(explosionPower >= 0 && explosionPower <= 127, "The explosion power has to be between 0 and 127");
		this.explosionPower = explosionPower;
	}

	@Override
	public @NotNull EntityType getType()
	{
		return EntityType.GHAST;
	}

}
