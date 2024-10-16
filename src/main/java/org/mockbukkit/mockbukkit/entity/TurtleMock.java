package org.mockbukkit.mockbukkit.entity;

import org.mockbukkit.mockbukkit.ServerMock;
import org.mockbukkit.mockbukkit.exception.UnimplementedOperationException;
import com.google.common.base.Preconditions;
import org.bukkit.Location;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Turtle;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;

public class TurtleMock extends AnimalsMock implements Turtle
{

	private Location home = new Location(null, 0, 0, 0);
	private boolean hasEgg = false;

	/**
	 * Constructs a new @{{@link TurtleMock}} on the provided {@link ServerMock} with a specified {@link UUID}.
	 *
	 * @param server The server to create the entity on.
	 * @param uuid   The UUID of the entity.
	 */
	public TurtleMock(@NotNull ServerMock server, @NotNull UUID uuid)
	{
		super(server, uuid);
	}

	@Override
	public boolean hasEgg()
	{
		return this.hasEgg;
	}

	@Override
	public boolean isLayingEgg()
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public @NotNull Location getHome()
	{
		return this.home;
	}

	@Override
	public void setHome(@NotNull Location location)
	{
		Preconditions.checkArgument(location != null, "Location cannot be null");
		this.home = location;
	}

	@Override
	public boolean isGoingHome()
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public boolean isDigging()
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public void setHasEgg(boolean hasEgg)
	{
		this.hasEgg = hasEgg;
	}

	@Override
	public @NotNull EntityType getType()
	{
		return EntityType.TURTLE;
	}

}
