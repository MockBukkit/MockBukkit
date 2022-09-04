package be.seeseemelk.mockbukkit.entity;

import be.seeseemelk.mockbukkit.ServerMock;
import org.bukkit.entity.Flying;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;

public abstract class FlyingMock extends MobMock implements Flying
{

	protected FlyingMock(@NotNull ServerMock server, @NotNull UUID uuid)
	{
		super(server, uuid);
	}

}
