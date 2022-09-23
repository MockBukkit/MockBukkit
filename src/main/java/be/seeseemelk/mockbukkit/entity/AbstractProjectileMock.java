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

}
