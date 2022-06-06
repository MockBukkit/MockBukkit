package be.seeseemelk.mockbukkit.entity;

import be.seeseemelk.mockbukkit.ServerMock;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.UUID;

public class SimpleMobMock extends MobMock
{

	public SimpleMobMock(@NotNull ServerMock server, @NotNull UUID uuid)
	{
		super(server, uuid);
	}

	public SimpleMobMock(@NotNull ServerMock server)
	{
		this(server, UUID.randomUUID());
	}

}
