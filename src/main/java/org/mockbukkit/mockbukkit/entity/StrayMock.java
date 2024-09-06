package org.mockbukkit.mockbukkit.entity;

import org.mockbukkit.mockbukkit.ServerMock;

import org.bukkit.entity.EntityType;
import org.bukkit.entity.Skeleton;
import org.bukkit.entity.Stray;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;

/**
 * Mock implementation of a {@link Stray}.
 *
 * @see AbstractSkeletonMock
 */
public class StrayMock extends AbstractSkeletonMock implements Stray
{

	/**
	 * Constructs a new {@link StrayMock} on the provided {@link ServerMock} with a specified {@link UUID}.
	 *
	 * @param server The server to create the entity on.
	 * @param uuid   The UUID of the entity.
	 */
	public StrayMock(@NotNull ServerMock server, @NotNull UUID uuid)
	{
		super(server, uuid);
	}

	@Override
	@Deprecated(since = "1.17")
	public Skeleton.@NotNull SkeletonType getSkeletonType()
	{
		return Skeleton.SkeletonType.STRAY;
	}

	@Override
	public EntityType getType()
	{
		return EntityType.STRAY;
	}

}
