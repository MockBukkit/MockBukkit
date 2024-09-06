package org.mockbukkit.mockbukkit.entity;

import org.mockbukkit.mockbukkit.ServerMock;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Ocelot;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;

public class OcelotMock extends AnimalsMock implements Ocelot
{

	private boolean isTrusting = false;

	/**
	 * Constructs a new {@link OcelotMock} on the provided {@link ServerMock} with a specified {@link UUID}.
	 *
	 * @param server The server to create the entity on.
	 * @param uuid   The UUID of the entity.
	 */
	public OcelotMock(@NotNull ServerMock server, @NotNull UUID uuid)
	{
		super(server, uuid);
	}

	@Override
	public boolean isTrusting()
	{
		return this.isTrusting;
	}

	@Override
	public void setTrusting(boolean trust)
	{
		this.isTrusting = trust;
	}

	@Override
	@Deprecated(since = "1.19")
	public @NotNull Type getCatType()
	{
		return Type.WILD_OCELOT;
	}

	@Override
	@Deprecated(since = "1.19")
	public void setCatType(@NotNull Type type)
	{
		throw new UnsupportedOperationException("Cats are a different entity!");
	}

	@NotNull
	@Override
	public EntityType getType()
	{
		return EntityType.OCELOT;
	}

}
