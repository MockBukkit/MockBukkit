package org.mockbukkit.mockbukkit.entity;

import org.mockbukkit.mockbukkit.ServerMock;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.WindCharge;
import org.bukkit.event.entity.EntityRemoveEvent;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;

/**
 * Mock implementation of a {@link WindCharge}.
 *
 * @see AbstractWindChargeMock
 */
public class WindChargeMock extends AbstractWindChargeMock implements WindCharge
{

	/**
	 * Constructs a new {@link WindChargeMock} on the provided {@link ServerMock} with a specified {@link UUID}.
	 *
	 * @param server The server to create the entity on.
	 * @param uuid   The UUID of the entity.
	 */
	public WindChargeMock(@NotNull ServerMock server, @NotNull UUID uuid)
	{
		super(server, uuid);
	}

	@Override
	public void explode()
	{
		if (!isInWorld())
		{
			return;
		}

		getWorld().createExplosion(this, getLocation(), 1.2F, false);
		remove(EntityRemoveEvent.Cause.EXPLODE);
	}

	@Override
	public @NotNull EntityType getType()
	{
		return EntityType.WIND_CHARGE;
	}

}
