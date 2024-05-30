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
 *
 * @see AbstractProjectileMock
 */
public abstract class ProjectileMock extends AbstractProjectileMock implements Projectile
{

	private @Nullable ProjectileSource source;

	/**
	 * Constructs a new {@link ProjectileMock} on the provided {@link ServerMock} with a specified {@link UUID}.
	 *
	 * @param server The server to create the entity on.
	 * @param uuid   The UUID of the entity.
	 */
	protected ProjectileMock(@NotNull ServerMock server, @NotNull UUID uuid)
	{
		super(server, uuid);
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
		this.source = source;
	}

	@Override
	public boolean hasLeftShooter()
	{
		// TODO: Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public void setHasLeftShooter(boolean leftShooter)
	{
		// TODO: Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public boolean hasBeenShot()
	{
		// TODO: Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public boolean canHitEntity(@NotNull Entity entity)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public void hitEntity(@NotNull Entity entity)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public void hitEntity(@NotNull Entity entity, @NotNull Vector vector)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public @Nullable UUID getOwnerUniqueId()
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public void setHasBeenShot(boolean beenShot)
	{
		// TODO: Auto-generated method stub
		throw new UnimplementedOperationException();
	}

}
