package org.mockbukkit.mockbukkit.entity;

import org.mockbukkit.mockbukkit.ServerMock;
import org.bukkit.entity.Monster;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;

/**
 * A very simple class that allows one to create an instance of a {@link MonsterMock} when a specific type of entity is not required.
 * This should only be used for testing code that doesn't care what type of entity it is.
 *
 * @see MonsterMock
 */
public class SimpleMonsterMock extends MonsterMock implements Monster
{

	/**
	 * Constructs a new {@link SimpleMonsterMock} on the provided {@link ServerMock} with a specified {@link UUID}.
	 *
	 * @param server The server to create the entity on.
	 * @param uuid   The UUID of the entity.
	 */
	public SimpleMonsterMock(@NotNull ServerMock server, @NotNull UUID uuid)
	{
		super(server, uuid);
	}

	/**
	 * Constructs a new {@link SimpleMonsterMock} on the provided {@link ServerMock} with a random {@link UUID}.
	 *
	 * @param server The server to create the entity on.
	 */
	public SimpleMonsterMock(@NotNull ServerMock server)
	{
		this(server, UUID.randomUUID());
	}

}
