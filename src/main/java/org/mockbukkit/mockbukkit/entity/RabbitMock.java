package org.mockbukkit.mockbukkit.entity;

import org.mockbukkit.mockbukkit.ServerMock;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Rabbit;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;

public class RabbitMock extends AnimalsMock implements Rabbit
{

	private Rabbit.Type type = Rabbit.Type.BLACK;
	private int moreCarrotTicks = 0;

	/**
	 * Constructs a new {@link RabbitMock} on the provided {@link ServerMock} with a specified {@link UUID}.
	 *
	 * @param server The server to create the entity on.
	 * @param uuid   The UUID of the entity.
	 */
	public RabbitMock(@NotNull ServerMock server, @NotNull UUID uuid)
	{
		super(server, uuid);
	}

	@Override
	public @NotNull Rabbit.Type getRabbitType()
	{
		return this.type;
	}

	@Override
	public void setRabbitType(@NotNull Type type)
	{
		this.type = type;
	}

	@Override
	public void setMoreCarrotTicks(int ticks)
	{
		this.moreCarrotTicks = ticks;
	}

	@Override
	public int getMoreCarrotTicks()
	{
		return this.moreCarrotTicks;
	}

	@Override
	public @NotNull EntityType getType()
	{
		return EntityType.RABBIT;
	}

}
