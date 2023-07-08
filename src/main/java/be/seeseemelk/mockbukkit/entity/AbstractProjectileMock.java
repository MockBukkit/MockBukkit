package be.seeseemelk.mockbukkit.entity;

import be.seeseemelk.mockbukkit.ServerMock;
import org.bukkit.entity.Projectile;
import org.jetbrains.annotations.NotNull;

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

}
