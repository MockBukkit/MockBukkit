package org.mockbukkit.mockbukkit.entity;

import org.mockbukkit.mockbukkit.ServerMock;
import org.bukkit.entity.Monster;
import org.bukkit.entity.SpawnCategory;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;

/**
 * Mock implementation of a {@link Monster}.
 *
 * @see CreatureMock
 */
public abstract class MonsterMock extends CreatureMock implements Monster
{

	/**
	 * Constructs a new {@link MonsterMock} on the provided {@link ServerMock} with a specified {@link UUID}.
	 *
	 * @param server The server to create the entity on.
	 * @param uuid   The UUID of the entity.
	 */
	protected MonsterMock(@NotNull ServerMock server, @NotNull UUID uuid)
	{
		super(server, uuid);
	}

	@Override
	public @NotNull SpawnCategory getSpawnCategory()
	{
		return SpawnCategory.MONSTER;
	}

}
