package org.mockbukkit.mockbukkit.entity;

import com.google.common.base.Preconditions;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.ShulkerBullet;
import org.bukkit.util.Vector;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.mockbukkit.mockbukkit.ServerMock;

import java.util.UUID;

/**
 * Mock implementation of a {@link ShulkerBullet}.
 *
 * @see AbstractProjectileMock
 */
public class ShulkerBulletMock extends AbstractProjectileMock implements ShulkerBullet
{

	private final Vector targetDelta = new Vector();

	private int flightSteps;

	private @Nullable Entity finalTarget;
	private @Nullable BlockFace movementDirection;

	/**
	 * Constructs a new {@link ShulkerBullet} on the provided {@link ServerMock} with a specified {@link UUID}.
	 *
	 * @param server The server to create the entity on.
	 * @param uuid   The UUID of the entity.
	 */
	public ShulkerBulletMock(@NotNull ServerMock server, @NotNull UUID uuid)
	{
		super(server, uuid);
	}

	@Override
	public @Nullable Entity getTarget()
	{
		return this.finalTarget;
	}

	@Override
	public void setTarget(@Nullable Entity target)
	{
		this.finalTarget = target;
		this.setCurrentMovementDirection(BlockFace.UP);
	}

	@Override
	public @NotNull Vector getTargetDelta()
	{
		return this.targetDelta.clone();
	}

	@Override
	public void setTargetDelta(@NotNull Vector vector)
	{
		Preconditions.checkNotNull(vector, "The vector can't be null!");
		this.targetDelta.copy(vector);
	}

	@Override
	public @Nullable BlockFace getCurrentMovementDirection()
	{
		return this.movementDirection;
	}

	@Override
	public void setCurrentMovementDirection(@Nullable BlockFace movementDirection)
	{
		if (movementDirection != null && movementDirection.isCartesian())
		{
			this.movementDirection = movementDirection;
		}
		else
		{
			this.movementDirection = null;
		}
	}

	@Override
	public int getFlightSteps()
	{
		return this.flightSteps;
	}

	@Override
	public void setFlightSteps(int steps)
	{
		this.flightSteps = steps;
	}

	@Override
	public @NotNull EntityType getType()
	{
		return EntityType.SHULKER_BULLET;
	}

}
