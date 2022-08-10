package be.seeseemelk.mockbukkit.entity;

import be.seeseemelk.mockbukkit.ServerMock;
import com.destroystokyo.paper.event.entity.CreeperIgniteEvent;
import com.google.common.base.Preconditions;
import org.bukkit.entity.Creeper;
import org.bukkit.entity.EntityType;
import org.bukkit.event.entity.CreeperPowerEvent;
import org.bukkit.event.entity.ExplosionPrimeEvent;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;

public class CreeperMock extends MonsterMock implements Creeper
{

	private boolean powered = false;
	private int maxFuseTicks = 30;
	private int fuseTicks = 0;
	private int explosionRadius = 3;
	private boolean ignited = false;

	public CreeperMock(@NotNull ServerMock server, @NotNull UUID uuid)
	{
		super(server, uuid);
	}

	@Override
	public boolean isPowered()
	{
		return this.powered;
	}

	@Override
	public void setPowered(boolean value)
	{
		CreeperPowerEvent.PowerCause cause = powered ? CreeperPowerEvent.PowerCause.SET_ON : CreeperPowerEvent.PowerCause.SET_OFF;

		if (!this.callPowerEvent(cause))
		{
			this.powered = value;
		}
	}

	private boolean callPowerEvent(CreeperPowerEvent.PowerCause cause)
	{
		CreeperPowerEvent event = new CreeperPowerEvent( this, cause);
		this.getServer().getPluginManager().callEvent(event);
		return event.isCancelled();
	}

	@Override
	public void setMaxFuseTicks(int ticks)
	{
		Preconditions.checkArgument(ticks >= 0, "Ticks need to be bigger than 0");

		this.maxFuseTicks = ticks;
	}

	@Override
	public int getMaxFuseTicks()
	{
		return this.maxFuseTicks;
	}

	@Override
	public void setFuseTicks(int ticks)
	{
		Preconditions.checkArgument(ticks >= 0, "Ticks need to be bigger than 0");
		Preconditions.checkArgument(ticks <= this.getMaxFuseTicks(), "Ticks need to be smaller than maxFuseTicks");
		this.fuseTicks = ticks;
	}

	@Override
	public int getFuseTicks()
	{
		return this.fuseTicks;
	}

	@Override
	public void setExplosionRadius(int radius)
	{
		Preconditions.checkArgument(radius >= 0, "Radius needs to be bigger than 0");
		this.explosionRadius = radius;
	}

	@Override
	public int getExplosionRadius()
	{
		return this.explosionRadius;
	}

	@Override
	public void explode()
	{
		float f = this.isPowered() ? 2.0F : 1.0F;
		ExplosionPrimeEvent event = new ExplosionPrimeEvent(this, this.getExplosionRadius() * f, false);
		this.getServer().getPluginManager().callEvent(event);
		this.remove();
	}

	@Override
	public void ignite()
	{
		setIgnited(true);
	}

	@Override
	public void setIgnited(boolean ignited)
	{
		if (isIgnited() != ignited)
		{
			CreeperIgniteEvent event = new CreeperIgniteEvent(this, ignited);
			if (event.callEvent())
			{
				this.ignited = ignited;
			}
		}
	}

	@Override
	public boolean isIgnited()
	{
		return this.ignited;
	}

	@Override
	public @NotNull EntityType getType()
	{
		return EntityType.CREEPER;
	}

}
