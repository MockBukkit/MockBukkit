package org.mockbukkit.mockbukkit.entity;

import org.mockbukkit.mockbukkit.ServerMock;
import com.google.common.base.Preconditions;
import org.bukkit.Location;
import org.bukkit.entity.Dolphin;
import org.bukkit.entity.EntityType;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;

public class DolphinMock extends AgeableMock implements Dolphin
{

	private int moistness = 2400;
	private boolean hasFish = false;
	private Location treasureLocation = new Location(null, 0, 0, 0);

	/**
	 * Constructs a new {@link DolphinMock} on the provided {@link ServerMock} with a specified {@link UUID}.
	 *
	 * @param server The server to create the entity on.
	 * @param uuid   The UUID of the entity.
	 */
	public DolphinMock(@NotNull ServerMock server, @NotNull UUID uuid)
	{
		super(server, uuid);
	}

	@Override
	public int getMoistness()
	{
		return this.moistness;
	}

	@Override
	public void setMoistness(int moistness)
	{
		this.moistness = moistness;
	}

	@Override
	public void setHasFish(boolean hasFish)
	{
		this.hasFish = hasFish;
	}

	@Override
	public boolean hasFish()
	{
		return this.hasFish;
	}

	@Override
	public @NotNull Location getTreasureLocation()
	{
		return this.treasureLocation;
	}

	@Override
	public void setTreasureLocation(@NotNull Location location)
	{
		Preconditions.checkArgument(location != null, "Location can't be null.");
		this.treasureLocation = location;
	}

	@Override
	public @NotNull EntityType getType()
	{
		return EntityType.DOLPHIN;
	}

}
