package be.seeseemelk.mockbukkit.entity;

import be.seeseemelk.mockbukkit.ServerMock;
import org.bukkit.entity.Projectile;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;

/**
 * The {@link AbstractProjectileMock} is an {@link EntityMock} representing a generic {@link Projectile}.
 * Not everything that extends {@link AbstractProjectileMock} extends {@link ProjectileMock}.
 */
public abstract class AbstractProjectileMock extends EntityMock implements Projectile
{

	private boolean doesBounce;

	public AbstractProjectileMock(@NotNull ServerMock server, @NotNull UUID uuid)
	{
		super(server, uuid);
		this.doesBounce = false;
	}

	@Override
	public boolean doesBounce()
	{
		return this.doesBounce;
	}

	@Override
	public void setBounce(boolean doesBounce)
	{
		this.doesBounce = doesBounce;
	}

}
