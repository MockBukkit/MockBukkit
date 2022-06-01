package be.seeseemelk.mockbukkit.entity;

import be.seeseemelk.mockbukkit.ServerMock;
import org.bukkit.entity.Ambient;

import java.util.UUID;

public class AmbientMock extends MobMock implements Ambient
{

	public AmbientMock(ServerMock server, UUID uuid)
	{
		super(server, uuid);
	}

	@Override
	public String toString()
	{
		return "AmbientMock";
	}

}
