package be.seeseemelk.mockbukkit.entity;

import be.seeseemelk.mockbukkit.ServerMock;
import org.bukkit.entity.Monster;
import org.bukkit.entity.SpawnCategory;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;

public abstract class MonsterMock extends CreatureMock implements Monster
{

	protected MonsterMock(ServerMock server, UUID uuid)
	{
		super(server, uuid);
	}

	@Override
	public @NotNull SpawnCategory getSpawnCategory()
	{
		return SpawnCategory.MONSTER;
	}

}
