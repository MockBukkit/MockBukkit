package be.seeseemelk.mockbukkit.entity;

import be.seeseemelk.mockbukkit.ServerMock;
import be.seeseemelk.mockbukkit.UnimplementedOperationException;
import org.bukkit.block.data.BlockData;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.TNTPrimed;
import org.bukkit.event.entity.ExplosionPrimeEvent;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.UUID;

/**
 * Mock implementation of a {@link TNTPrimed}.
 *
 * @see EntityMock
 */
public class TNTPrimedMock extends EntityMock implements TNTPrimed
{

	private int fuseTicks = 80;
	private Entity source;
	private float explosionYield = 4;
	private boolean incendiary = false;

	/**
	 * Constructs a new {@link TNTPrimedMock} on the provided {@link ServerMock} with a specified {@link UUID}.
	 *
	 * @param server The server to create the entity on.
	 * @param uuid   The UUID of the entity.
	 */
	protected TNTPrimedMock(@NotNull ServerMock server, @NotNull UUID uuid)
	{
		super(server, uuid);
	}

	@Override
	public void setFuseTicks(int fuseTicks)
	{
		this.fuseTicks = fuseTicks;
	}

	@Override
	public int getFuseTicks()
	{
		return this.fuseTicks;
	}

	@Override
	public @Nullable Entity getSource()
	{
		return this.source;
	}

	@Override
	public void setSource(@Nullable Entity source)
	{
		if (source instanceof LivingEntity)
		{
			this.source = source;
		}
		else
		{
			this.source = null;
		}
	}

	@Override
	public void setBlockData(@NotNull BlockData blockData)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();

	}

	@Override
	public @NotNull BlockData getBlockData()
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public void setYield(float explosionYield)
	{
		this.explosionYield = explosionYield;
	}

	@Override
	public float getYield()
	{
		return this.explosionYield;
	}

	@Override
	public void setIsIncendiary(boolean incendiary)
	{
		this.incendiary = incendiary;
	}

	@Override
	public boolean isIncendiary()
	{
		return this.incendiary;
	}

	@Override
	public @NotNull EntityType getType()
	{
		return EntityType.PRIMED_TNT;
	}

	/**
	 * Simulate server tick.
	 *
	 * @param ticks The number of ticks to simulate.
	 */
	public void tick(int ticks)
	{
		setFuseTicks(getFuseTicks() - ticks);
		if (getFuseTicks() <= 0)
		{
			explode();
			this.remove();
		}
	}

	/**
	 * Simulate one server tick.
	 */
	public void tick()
	{
		tick(1);
	}

	private void explode()
	{
		ExplosionPrimeEvent event = new ExplosionPrimeEvent(this);
		server.getPluginManager().callEvent(event);
	}

}
