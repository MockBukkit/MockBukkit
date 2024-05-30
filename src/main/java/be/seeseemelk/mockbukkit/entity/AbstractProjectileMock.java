package be.seeseemelk.mockbukkit.entity;

import be.seeseemelk.mockbukkit.ServerMock;
import be.seeseemelk.mockbukkit.UnimplementedOperationException;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Projectile;
import org.bukkit.projectiles.ProjectileSource;
import org.bukkit.util.Vector;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.UUID;

/**
 * Mock implementation of a {@link Projectile}.
 * Not everything that extends {@link AbstractProjectileMock} extends {@link ProjectileMock}.
 *
 * @see EntityMock
 */
public abstract class AbstractProjectileMock extends EntityMock implements Projectile
{

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
		throw new UnimplementedOperationException();
	}

	@Override
	public void setShooter(@Nullable ProjectileSource source)
	{
		throw new UnimplementedOperationException();
	}

	@Override
	public boolean hasLeftShooter()
	{
		throw new UnimplementedOperationException();
	}

	@Override
	public void setHasLeftShooter(boolean leftShooter)
	{
		throw new UnimplementedOperationException();
	}

	@Override
	public boolean hasBeenShot()
	{
		throw new UnimplementedOperationException();
	}

	@Override
	public void setHasBeenShot(boolean beenShot)
	{
		throw new UnimplementedOperationException();
	}

	@Override
	public @Nullable ProjectileSource getShooter()
	{
		throw new UnimplementedOperationException();
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
