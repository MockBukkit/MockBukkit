package be.seeseemelk.mockbukkit.entity;

import be.seeseemelk.mockbukkit.ServerMock;
import org.bukkit.entity.Projectile;
import org.bukkit.projectiles.ProjectileSource;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.UUID;

/**
 * The {@link ProjectileMock} is an {@link EntityMock} representing a generic {@link Projectile}.
 *
 * @author TheBusyBiscuit
 */
public abstract class ProjectileMock extends EntityMock implements Projectile
{

	private ProjectileSource source;
	private boolean bounce;

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
	public boolean doesBounce()
	{
		return bounce;
	}

	@Override
	public void setBounce(boolean doesBounce)
	{
		this.bounce = doesBounce;
	}

}
