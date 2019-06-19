package be.seeseemelk.mockbukkit.entity;

import java.util.UUID;

import be.seeseemelk.mockbukkit.ServerMock;

public class SimpleMobMock extends MobMock
{

	public SimpleMobMock(ServerMock server, UUID uuid)
	{
		super(server, uuid);
	}
	
	public SimpleMobMock(ServerMock server)
	{
		this(server, UUID.randomUUID());
	}

}
