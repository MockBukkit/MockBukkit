package org.mockbukkit.mockbukkit.entity;

import org.mockbukkit.mockbukkit.ServerMock;
import org.mockbukkit.mockbukkit.UnimplementedOperationException;
import com.google.common.base.Preconditions;
import org.bukkit.entity.Fireball;
import org.bukkit.util.Vector;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;

/**
 * Mock implementation of a {@link Fireball}.
 *
 * @see ProjectileMock
 */
public class FireballMock extends ProjectileMock implements Fireball
{

	private boolean isIncendiary;
	private @NotNull Vector direction = new Vector();
	private float blastYield = 1.0f;

	/**
	 * Constructs a new {@link FireballMock} on the provided {@link ServerMock} with a specified {@link UUID}.
	 *
	 * @param server The server to create the entity on.
	 * @param uuid   The UUID of the entity.
	 */
	public FireballMock(@NotNull ServerMock server, @NotNull UUID uuid)
	{
		super(server, uuid);
	}

	@Override
	public void setDirection(@NotNull Vector direction)
	{
		Preconditions.checkNotNull(direction, "Direction cannot be null");
		this.direction = direction;
	}

	@Override
	public @NotNull Vector getDirection()
	{
		return this.direction;
	}

	@Override
	public void setPower(@NotNull Vector power)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public @NotNull Vector getPower()
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public void setYield(float blastYield)
	{
		this.blastYield = blastYield;
	}

	@Override
	public float getYield()
	{
		return this.blastYield;
	}

	@Override
	public void setIsIncendiary(boolean isIncendiary)
	{
		this.isIncendiary = isIncendiary;
	}

	@Override
	public boolean isIncendiary()
	{
		return this.isIncendiary;
	}

}
