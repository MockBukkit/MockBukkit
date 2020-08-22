package be.seeseemelk.mockbukkit.entity;

import be.seeseemelk.mockbukkit.ServerMock;
import org.bukkit.entity.Creature;

import java.util.UUID;

public abstract class CreatureMock extends MobMock implements Creature
{
	public CreatureMock(ServerMock server, UUID uuid)
	{
		super(server, uuid);
	}
}
