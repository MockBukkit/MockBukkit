package be.seeseemelk.mockbukkit.entity;

import be.seeseemelk.mockbukkit.ServerMock;
import be.seeseemelk.mockbukkit.UnimplementedOperationException;
import com.google.common.base.Preconditions;
import net.kyori.adventure.text.Component;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.FishHook;
import org.bukkit.entity.SpawnCategory;
import org.bukkit.util.Vector;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.UUID;

public class FishHookMock extends ProjectileMock implements FishHook
{

	private int minWaitTime = 100;
	private int maxWaitTime = 600;
	private boolean applyLure = true;
	private double biteChance = -1;
	private Entity hookedEntity;
	private HookState state = HookState.UNHOOKED;

	public FishHookMock(@NotNull ServerMock server, @NotNull UUID uuid)
	{
		super(server, uuid);
	}

	@Override
	public int getMinWaitTime()
	{
		return this.minWaitTime;
	}

	@Override
	public void setMinWaitTime(int minWaitTime)
	{
		Preconditions.checkArgument(minWaitTime >= 0 && minWaitTime <= this.getMaxWaitTime(), "The minimum wait time should be between 0 and the maximum wait time.");
		this.minWaitTime = minWaitTime;
	}

	@Override
	public int getMaxWaitTime()
	{
		return this.maxWaitTime;
	}

	@Override
	public void setMaxWaitTime(int maxWaitTime)
	{
		Preconditions.checkArgument(maxWaitTime >= 0 && maxWaitTime >= this.getMinWaitTime(), "The maximum wait time should be higher than or equal to 0 and the minimum wait time.");
		this.maxWaitTime = maxWaitTime;
	}

	@Override
	public boolean getApplyLure()
	{
		return this.applyLure;
	}

	@Override
	public void setApplyLure(boolean applyLure)
	{
		this.applyLure = applyLure;
	}

	@Override
	public double getBiteChance()
	{
		if (this.biteChance == -1)
		{
			if (!getWorld().isClearWeather()) {
				return 1 / 300.0;
			}
			return 1 / 500.0;
		}
		return this.biteChance;
	}

	@Override
	public void setBiteChance(double chance) throws IllegalArgumentException
	{
		Preconditions.checkArgument(chance >= 0 && chance <= 1, "The bite chance must be between 0 and 1.");
		this.biteChance = chance;
	}

	@Override
	public boolean isInOpenWater()
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Nullable
	@Override
	public Entity getHookedEntity()
	{
		return hookedEntity;
	}

	@Override
	public void setHookedEntity(@Nullable Entity entity)
	{
		this.state = HookState.HOOKED_ENTITY;
		this.hookedEntity = entity;
	}

	@Override
	public boolean pullHookedEntity()
	{
		if (hookedEntity == null)
		{
			return false;
		}

		if (this.getShooter() instanceof Entity shooter)
		{
			Location shooterLoc = shooter.getLocation();
			Location hookLoc = this.getLocation();
			Vector velocity = new Vector(shooterLoc.getX() - hookLoc.getX(), shooterLoc.getY() - hookLoc.getY(), shooterLoc.getZ() - hookLoc.getZ()).multiply(0.1);
			hookedEntity.setVelocity(hookedEntity.getVelocity().add(velocity));
		}
		return true;
	}

	/**
	 * Updates the {@link HookState} of the hook.
	 * Normally the server does this every tick.
	 * @see #getState()
	 */
	public void updateState()
	{
		if (hookedEntity != null)
		{
			state = HookState.HOOKED_ENTITY;
		}
		else if (this.getLocation().getBlock().isLiquid())
		{
			state = HookState.BOBBING;
		}
		else
		{
			state = HookState.UNHOOKED;
		}
	}

	@NotNull
	@Override
	public HookState getState()
	{
		return state;
	}

	@Override
	public int getWaitTime()
	{
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException();
	}

	@Override
	public void setWaitTime(int ticks)
	{
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException();
	}

	@NotNull
	@Override
	public EntityType getType()
	{
		return EntityType.FISHING_HOOK;
	}

	@NotNull
	@Override
	public SpawnCategory getSpawnCategory()
	{
		return SpawnCategory.MISC;
	}

	@Override
	public String toString()
	{
		return "FishingHookMock";
	}

}
