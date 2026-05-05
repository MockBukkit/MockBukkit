package org.mockbukkit.mockbukkit.entity;

import org.bukkit.entity.EntityType;
import org.bukkit.entity.Parched;
import org.bukkit.entity.Skeleton;
import org.jspecify.annotations.NullMarked;
import org.mockbukkit.mockbukkit.ServerMock;

import java.util.UUID;

/**
 * Mock implementation of an {@link Parched}.
 *
 * @see AbstractSkeletonMock
 */
@NullMarked
public class ParchedMock extends AbstractSkeletonMock implements Parched
{

	/**
	 * Constructs a new {@link Parched} on the provided {@link ServerMock} with a specified {@link UUID}.
	 *
	 * @param server The server to create the entity on.
	 * @param uuid   The UUID of the entity.
	 */
	public ParchedMock(ServerMock server, UUID uuid)
	{
		super(server, uuid);
	}

	@Override
	@Deprecated(since = "1.17", forRemoval = true)
	public Skeleton.SkeletonType getSkeletonType()
	{
		return Skeleton.SkeletonType.PARCHED;
	}

	@Override
	public EntityType getType()
	{
		return EntityType.PARCHED;
	}

}
