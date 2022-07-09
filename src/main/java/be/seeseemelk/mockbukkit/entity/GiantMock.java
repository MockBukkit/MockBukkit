package be.seeseemelk.mockbukkit.entity;

import be.seeseemelk.mockbukkit.ServerMock;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Giant;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;

public class GiantMock extends MonsterMock implements Giant
{

	public GiantMock(@NotNull ServerMock server, @NotNull UUID uuid)
	{
		super(server, uuid);
	}

	@Override
	public @NotNull EntityType getType()
	{
		return EntityType.GIANT;
	}

}
