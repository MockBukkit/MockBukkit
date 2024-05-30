package be.seeseemelk.mockbukkit.entity;

import be.seeseemelk.mockbukkit.ServerMock;
import be.seeseemelk.mockbukkit.UnimplementedOperationException;
import com.google.common.base.Preconditions;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.FishHook;
import org.bukkit.entity.SpawnCategory;
import org.bukkit.util.Vector;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.UUID;

/**
 * Mock implementation of a {@link FishHook}.
 *
 * @see ProjectileMock
 */
public class FishHookMock extends ProjectileMock implements FishHook
{

	private int minWaitTime = 100;
	private int maxWaitTime = 600;
	private int minLureTime = 20;
	private int maxLureTime = 80;
	private float minLureAngle = 0;
	private float maxLureAngle = 360;
	private boolean applyLure = true;
	private boolean skyInfluenced = true;
	private boolean rainInfluenced = true;
	private double biteChance = -1;
	private @Nullable Entity hookedEntity;
	private @NotNull HookState state = HookState.UNHOOKED;

	/**
	 * Constructs a new {@link FishHookMock} on the provided {@link ServerMock} with a specified {@link UUID}.
	 *
	 * @param server The server to create the entity on.
	 * @param uuid   The UUID of the entity.
	 */
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
		Preconditions.checkArgument(minWaitTime >= 0 && minWaitTime <= this.getMaxWaitTime(),
				"The minimum wait time should be between 0 and the maximum wait time.");
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
		Preconditions.checkArgument(maxWaitTime >= 0 && maxWaitTime >= this.getMinWaitTime(),
				"The maximum wait time should be higher than or equal to 0 and the minimum wait time.");
		this.maxWaitTime = maxWaitTime;
	}

	@Override
	public void setWaitTime(int min, int max)
	{
		this.setMinWaitTime(min);
		this.setMaxWaitTime(max);
	}

	@Override
	public int getMinLureTime()
	{
		return this.minLureTime;
	}

	@Override
	public void setMinLureTime(int minLureTime)
	{
		Preconditions.checkArgument(minLureTime >= 0 && minLureTime <= this.getMaxLureTime(),
				"The minimum lure time should be between 0 and the maximum lure time.");
		this.minLureTime = minLureTime;
	}

	@Override
	public int getMaxLureTime()
	{
		return this.maxLureTime;
	}

	@Override
	public void setMaxLureTime(int maxLureTime)
	{
		Preconditions.checkArgument(maxLureTime >= 0 && maxLureTime >= this.getMinLureTime(),
				"The maximum lure time should be higher than or equal to 0 and the minimum lure time.");
		this.maxLureTime = maxLureTime;
	}

	@Override
	public void setLureTime(int min, int max)
	{
		this.setMinLureTime(min);
		this.setMaxLureTime(max);
	}

	@Override
	public float getMinLureAngle()
	{
		return this.minLureAngle;
	}

	@Override
	public void setMinLureAngle(float minLureAngle)
	{
		Preconditions.checkArgument(minLureAngle >= 0 && minLureAngle <= this.getMaxLureAngle(),
				"The minimum lure angle should be between 0 and the maximum lure angle.");
		this.minLureAngle = minLureAngle;
	}

	@Override
	public float getMaxLureAngle()
	{
		return this.maxLureAngle;
	}

	@Override
	public void setMaxLureAngle(float maxLureAngle)
	{
		Preconditions.checkArgument(maxLureAngle >= 0 && maxLureAngle >= this.getMinLureAngle() && maxLureAngle <= 360,
				"The maximum lure angle should be higher than or equal to 0 and the minimum lure angle but also less than or equal to 360.");
		this.maxLureAngle = maxLureAngle;
	}

	@Override
	public void setLureAngle(float min, float max)
	{
		this.setMinLureAngle(min);
		this.setMaxLureAngle(max);
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
			if (!getWorld().isClearWeather())
			{
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
			Vector velocity = new Vector(shooterLoc.getX() - hookLoc.getX(), shooterLoc.getY() - hookLoc.getY(),
					shooterLoc.getZ() - hookLoc.getZ()).multiply(0.1);
			hookedEntity.setVelocity(hookedEntity.getVelocity().add(velocity));
		}
		return true;
	}

	@Override
	public boolean isSkyInfluenced()
	{
		return this.skyInfluenced;
	}

	@Override
	public void setSkyInfluenced(boolean skyInfluenced)
	{
		this.skyInfluenced = skyInfluenced;
	}

	@Override
	public boolean isRainInfluenced()
	{
		return this.rainInfluenced;
	}

	@Override
	public void setRainInfluenced(boolean rainInfluenced)
	{
		this.rainInfluenced = rainInfluenced;
	}

	/**
	 * Updates the {@link HookState} of the hook.
	 * Normally the server does this every tick.
	 *
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
		throw new UnimplementedOperationException();
	}

	@Override
	public void setWaitTime(int ticks)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
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
	public @NotNull String toString()
	{
		return "FishingHookMock";
	}

}
