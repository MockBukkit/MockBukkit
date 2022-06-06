package be.seeseemelk.mockbukkit.entity;

import be.seeseemelk.mockbukkit.ServerMock;
import org.bukkit.entity.Monster;

import java.util.UUID;

public class SimpleMonsterMock extends MonsterMock implements Monster
{

	public SimpleMonsterMock(ServerMock server, UUID uuid)
	{
		super(server, uuid);
	}

	public SimpleMonsterMock(ServerMock server)
	{
		this(server, UUID.randomUUID());
	}

}
