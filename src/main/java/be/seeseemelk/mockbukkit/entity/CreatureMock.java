package be.seeseemelk.mockbukkit.entity;

import be.seeseemelk.mockbukkit.ServerMock;
import org.bukkit.entity.Creature;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;

public abstract class CreatureMock extends MobMock implements Creature
{

	protected CreatureMock(@NotNull ServerMock server, @NotNull UUID uuid)
	{
		super(server, uuid);
	}

}
