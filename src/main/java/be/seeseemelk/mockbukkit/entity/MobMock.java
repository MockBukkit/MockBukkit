package be.seeseemelk.mockbukkit.entity;

import be.seeseemelk.mockbukkit.ServerMock;
import be.seeseemelk.mockbukkit.UnimplementedOperationException;
import com.destroystokyo.paper.entity.Pathfinder;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Mob;
import org.bukkit.loot.LootTable;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

public abstract class MobMock extends LivingEntityMock implements Mob
{

	private boolean aware = true;
	private boolean leftHanded;

	protected MobMock(@NotNull ServerMock server, @NotNull UUID uuid)
	{
		super(server, uuid);
	}

	@Override
	public @NotNull Pathfinder getPathfinder()
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public boolean isInDaylight()
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public void lookAt(@NotNull Location location)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public void lookAt(@NotNull Location location, float headRotationSpeed, float maxHeadPitch)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public void lookAt(@NotNull Entity entity)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public void lookAt(@NotNull Entity entity, float headRotationSpeed, float maxHeadPitch)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public void lookAt(double x, double y, double z)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public void lookAt(double x, double y, double z, float headRotationSpeed, float maxHeadPitch)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public int getHeadRotationSpeed()
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public int getMaxHeadPitch()
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public void setTarget(@Nullable LivingEntity target)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Nullable
	@Override
	public LivingEntity getTarget()
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public void setAware(boolean aware)
	{
		this.aware = aware;
	}

	@Override
	public boolean isAware()
	{
		return this.aware;
	}

	@Override
	public boolean isLeftHanded()
	{
		return this.leftHanded;
	}

	@Override
	public void setLeftHanded(boolean leftHanded)
	{
		this.leftHanded = leftHanded;
	}

	@Override
	public void setLootTable(@Nullable LootTable table)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Nullable
	@Override
	public LootTable getLootTable()
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public void setSeed(long seed)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public long getSeed()
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	/**
	 * Called immediately after the entity is spawned.
	 */
	public void finalizeSpawn()
	{
		/* TODO: Unimplemented (#354)
		this.registerAttribute(Attribute.GENERIC_FOLLOW_RANGE);
		this.getAttribute(Attribute.GENERIC_FOLLOW_RANGE).addModifier(new AttributeModifier("Random spawn bonus", ThreadLocalRandom.current().nextGaussian() * 0.05D, AttributeModifier.Operation.MULTIPLY_SCALAR_1));
		 */
		this.setLeftHanded(ThreadLocalRandom.current().nextFloat() < 0.05F);
	}

}
