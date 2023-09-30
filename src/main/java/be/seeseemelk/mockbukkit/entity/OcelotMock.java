package be.seeseemelk.mockbukkit.entity;

import be.seeseemelk.mockbukkit.ServerMock;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Ocelot;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;

public class OcelotMock extends AnimalsMock implements Ocelot
{

	@Deprecated
	private Ocelot.Type type = Ocelot.Type.WILD_OCELOT;
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
	@Deprecated
	public @NotNull Type getCatType()
	{
		return this.type;
	}

	@Override
	@Deprecated
	public void setCatType(@NotNull Type type)
	{
		this.type = type;
	}

	@NotNull
	@Override
	public EntityType getType()
	{
		return EntityType.OCELOT;
	}

}
