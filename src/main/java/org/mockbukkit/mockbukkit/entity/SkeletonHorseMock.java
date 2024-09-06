package org.mockbukkit.mockbukkit.entity;

import org.mockbukkit.mockbukkit.ServerMock;

import org.bukkit.entity.EntityType;
import org.bukkit.entity.Horse;
import org.bukkit.entity.SkeletonHorse;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;

/**
 * Mock implementation of a {@link SkeletonHorse}.
 *
 * @see AbstractHorseMock
 */
public class SkeletonHorseMock extends AbstractHorseMock implements SkeletonHorse
{

	private boolean isTrapped = false;
	private int trapTime = 0;

	/**
	 * Constructs a new {@link SkeletonHorseMock} on the provided {@link ServerMock} with a specified {@link UUID}.
	 *
	 * @param server The server to create the entity on.
	 * @param uuid   The UUID of the entity.
	 */
	public SkeletonHorseMock(@NotNull ServerMock server, @NotNull UUID uuid)
	{
		super(server, uuid);
	}

	@Override
	public boolean isTrapped()
	{
		return this.isTrapped;
	}

	@Override
	public void setTrapped(boolean trapped)
	{
		this.isTrapped = trapped;
	}

	@Override
	public int getTrapTime()
	{
		return this.trapTime;
	}

	@Override
	public void setTrapTime(int trapTime)
	{
		this.trapTime = trapTime;
	}

	@Override
	@Deprecated(since = "1.18")
	public boolean isTrap()
	{
		return this.isTrapped;
	}

	@Override
	@Deprecated(since = "1.18")
	public void setTrap(boolean trap)
	{
		this.isTrapped = trap;
	}

	@Override
	@Deprecated(since = "1.11")
	public Horse.@NotNull Variant getVariant()
	{
		return Horse.Variant.SKELETON_HORSE;
	}

	@Override
	public @NotNull EntityType getType()
	{
		return EntityType.SKELETON_HORSE;
	}

}
