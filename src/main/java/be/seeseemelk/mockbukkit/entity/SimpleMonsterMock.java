package be.seeseemelk.mockbukkit.entity;

import java.util.UUID;

import org.bukkit.entity.Monster;

import be.seeseemelk.mockbukkit.ServerMock;

public class SimpleMonsterMock extends MobMock implements Monster
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
