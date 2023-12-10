package be.seeseemelk.mockbukkit.entity;

import be.seeseemelk.mockbukkit.ServerMock;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.TNTPrimed;
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
	private float yield = 4;
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
		return fuseTicks;
	}

	@Override
	public @Nullable Entity getSource()
	{
		return source;
	}

	@Override
	public void setSource(@Nullable Entity source)
	{
		this.source = source;
	}

	@Override
	public void setYield(float yield)
	{
		this.yield = yield;
	}

	@Override
	public float getYield()
	{
		return yield;
	}

	@Override
	public void setIsIncendiary(boolean incendiary)
	{
		this.incendiary = incendiary;
	}

	@Override
	public boolean isIncendiary()
	{
		return incendiary;
	}

	@Override
	public @NotNull EntityType getType()
	{
		return EntityType.PRIMED_TNT;
	}

}
