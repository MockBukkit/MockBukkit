package org.mockbukkit.mockbukkit.entity;

import org.mockbukkit.mockbukkit.ServerMock;
import org.bukkit.entity.Camel;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Horse;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;

public class CamelMock extends AbstractHorseMock implements Camel
{

	private boolean isDashing = false;
	private boolean isSitting = false;

	/**
	 * Constructs a new {@link CamelMock} on the provided {@link ServerMock} with a specified {@link UUID}.
	 *
	 * @param server The server to create the entity on.
	 * @param uuid   The UUID of the entity.
	 */
	public CamelMock(@NotNull ServerMock server, @NotNull UUID uuid)
	{
		super(server, uuid);
	}

	@Override
	public boolean isDashing()
	{
		return this.isDashing;
	}

	@Override
	public void setDashing(boolean dashing)
	{
		this.isDashing = dashing;
	}

	@Override
	@Deprecated(since = "1.11")
	public Horse.@NotNull Variant getVariant()
	{
		return Horse.Variant.CAMEL;
	}

	@Override
	public boolean isSitting()
	{
		return this.isSitting;
	}

	@Override
	public void setSitting(boolean sitting)
	{
		this.isSitting = sitting;
	}

	@Override
	public @NotNull EntityType getType()
	{
		return EntityType.CAMEL;
	}

}
