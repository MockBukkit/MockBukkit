package be.seeseemelk.mockbukkit.entity;

import be.seeseemelk.mockbukkit.ServerMock;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Spider;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;

public class SpiderMock extends MonsterMock implements Spider
{

	public SpiderMock(@NotNull ServerMock server, @NotNull UUID uuid)
	{
		super(server, uuid);
	}

	@Override
	public @NotNull EntityType getType()
	{
		return EntityType.SPIDER;
	}

}
