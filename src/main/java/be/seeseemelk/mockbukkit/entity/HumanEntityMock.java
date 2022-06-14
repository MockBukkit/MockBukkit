package be.seeseemelk.mockbukkit.entity;

import be.seeseemelk.mockbukkit.ServerMock;
import be.seeseemelk.mockbukkit.WorldMock;
import org.bukkit.Location;
import org.bukkit.entity.HumanEntity;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.UUID;

public abstract class HumanEntityMock extends LivingEntityMock implements HumanEntity
{
	private Location lastDeathLocation = new Location(new WorldMock(), 0, 0, 0);

	protected HumanEntityMock(@NotNull ServerMock server, @NotNull UUID uuid)
	{
		super(server, uuid);
	}

	@Override
	public @Nullable Location getLastDeathLocation()
	{
		return lastDeathLocation;
	}

	@Override
	public void setLastDeathLocation(@Nullable Location location)
	{
		this.lastDeathLocation = location;
	}
}

