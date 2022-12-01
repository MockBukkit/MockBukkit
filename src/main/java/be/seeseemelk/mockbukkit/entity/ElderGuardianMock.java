package be.seeseemelk.mockbukkit.entity;

import be.seeseemelk.mockbukkit.ServerMock;
import org.bukkit.entity.ElderGuardian;
import org.bukkit.entity.EntityType;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;

public class ElderGuardianMock extends GuardianMock implements ElderGuardian
{

	public ElderGuardianMock(@NotNull ServerMock server, @NotNull UUID uuid)
	{
		super(server, uuid);
	}

	@Override
	public @NotNull EntityType getType()
	{
		return EntityType.ELDER_GUARDIAN;
	}

	@Override
	public boolean isElder()
	{
		return true;
	}

}
