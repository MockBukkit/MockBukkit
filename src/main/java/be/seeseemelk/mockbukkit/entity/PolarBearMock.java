package be.seeseemelk.mockbukkit.entity;

import be.seeseemelk.mockbukkit.ServerMock;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.PolarBear;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;

public class PolarBearMock extends AnimalsMock implements PolarBear
{

	private boolean isStanding = false;

	public PolarBearMock(@NotNull ServerMock server, @NotNull UUID uuid)
	{
		super(server, uuid);
	}

	@Override
	public boolean isStanding()
	{
		return this.isStanding;
	}

	@Override
	public void setStanding(boolean standing)
	{
		this.isStanding = standing;
	}

	@Override
	public boolean canBreed()
	{
		return false;
	}

	@Override
	public @NotNull EntityType getType()
	{
		return EntityType.POLAR_BEAR;
	}

}
