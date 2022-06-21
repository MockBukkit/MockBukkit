package be.seeseemelk.mockbukkit.entity;

import be.seeseemelk.mockbukkit.ServerMock;
import org.bukkit.entity.Ambient;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;

public class AmbientMock extends MobMock implements Ambient
{

	public AmbientMock(@NotNull ServerMock server, @NotNull UUID uuid)
	{
		super(server, uuid);
	}

	@Override
	public @NotNull String toString()
	{
		return "AmbientMock";
	}

}
