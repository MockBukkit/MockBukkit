package be.seeseemelk.mockbukkit.entity;

import be.seeseemelk.mockbukkit.ServerMock;
import be.seeseemelk.mockbukkit.UnimplementedOperationException;
import org.bukkit.entity.Projectile;
import org.bukkit.projectiles.ProjectileSource;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.UUID;

/**
 * The {@link ProjectileMock} is an {@link EntityMock} representing a generic {@link Projectile}.
 */
public abstract class ProjectileMock extends AbstractProjectileMock implements Projectile
{

	private @Nullable ProjectileSource source;

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
		//TODO: Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public void setHasLeftShooter(boolean leftShooter)
	{
		//TODO: Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public boolean hasBeenShot()
	{
		//TODO: Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public void setHasBeenShot(boolean beenShot)
	{
		//TODO: Auto-generated method stub
		throw new UnimplementedOperationException();
	}

}
