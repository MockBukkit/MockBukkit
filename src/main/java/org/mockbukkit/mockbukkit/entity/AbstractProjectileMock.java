package org.mockbukkit.mockbukkit.entity;

import org.bukkit.entity.Entity;
import org.bukkit.entity.Projectile;
import org.bukkit.projectiles.ProjectileSource;
import org.bukkit.util.Vector;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.mockbukkit.mockbukkit.ServerMock;
import org.mockbukkit.mockbukkit.exception.UnimplementedOperationException;

import java.util.UUID;

/**
 * Mock implementation of a {@link Projectile}.
 * Not everything that extends {@link AbstractProjectileMock} extends {@link ProjectileMock}.
 *
 * @see EntityMock
 */
public abstract class AbstractProjectileMock extends EntityMock implements Projectile
{

	private @Nullable ProjectileSource source;
	private @Nullable UUID owner;
	private boolean hasBeenShot = false;
	private boolean hasLeftShooter = false;

	/**
	 * Constructs a new {@link AbstractProjectileMock} on the provided {@link ServerMock} with a specified {@link UUID}.
	 *
	 * @param server The server to create the entity on.
	 * @param uuid   The UUID of the entity.
	 */
	protected AbstractProjectileMock(@NotNull ServerMock server, @NotNull UUID uuid)
	{
		super(server, uuid);
	}

	@Override
	@Deprecated(forRemoval = true)
	public boolean doesBounce()
	{
		throw new UnsupportedOperationException("Deprecated; Does not do anything");
	}

	@Override
	@Deprecated(forRemoval = true)
	public void setBounce(boolean doesBounce)
	{
		throw new UnsupportedOperationException("Deprecated; Does not do anything");
	}


	@Override
	public @Nullable UUID getOwnerUniqueId()
	{
		return this.owner;
	}

	@Override
	@Nullable
	public ProjectileSource getShooter()
	{
		return source;
	}

	@Override
	public void setShooter(@Nullable ProjectileSource source)
	{
		if (source instanceof Entity e)
		{
			this.owner = e.getUniqueId();
		}
		else
		{
			this.owner = null;
		}

		this.source = source;
	}

	@Override
	public boolean hasLeftShooter()
	{
		return this.hasLeftShooter;
	}

	@Override
	public void setHasLeftShooter(boolean leftShooter)
	{
		this.hasLeftShooter = leftShooter;
	}

	@Override
	public boolean hasBeenShot()
	{
		return this.hasBeenShot;
	}

	@Override
	public void setHasBeenShot(boolean beenShot)
	{
		this.hasBeenShot = beenShot;
	}

	@Override
	public boolean canHitEntity(@NotNull Entity entity)
	{
		throw new UnimplementedOperationException();
	}

	@Override
	public void hitEntity(@NotNull Entity entity)
	{
		throw new UnimplementedOperationException();
	}

	@Override
	public void hitEntity(@NotNull Entity entity, @NotNull Vector vector)
	{
		throw new UnimplementedOperationException();
	}

}
