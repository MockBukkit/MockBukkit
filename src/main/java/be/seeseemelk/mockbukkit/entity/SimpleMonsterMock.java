package be.seeseemelk.mockbukkit.entity;

import be.seeseemelk.mockbukkit.ServerMock;
import org.bukkit.entity.Monster;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;

public class SimpleMonsterMock extends MonsterMock implements Monster
{

	public SimpleMonsterMock(@NotNull ServerMock server, @NotNull UUID uuid)
	{
		super(server, uuid);
	}

	public SimpleMonsterMock(@NotNull ServerMock server)
	{
		this(server, UUID.randomUUID());
	}

}
