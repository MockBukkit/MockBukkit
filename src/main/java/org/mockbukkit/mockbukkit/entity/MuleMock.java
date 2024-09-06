package org.mockbukkit.mockbukkit.entity;

import org.mockbukkit.mockbukkit.ServerMock;

import org.bukkit.entity.EntityType;
import org.bukkit.entity.Horse;
import org.bukkit.entity.Mule;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;

/**
 * Mock implementation of a {@link Mule}.
 *
 * @see ChestedHorseMock
 */
public class MuleMock extends ChestedHorseMock implements Mule
{

	/**
	 * Constructs a new {@link MuleMock} on the provided {@link ServerMock} with a specified {@link UUID}.
	 *
	 * @param server The server to create the entity on.
	 * @param uuid   The UUID of the entity.
	 */
	public MuleMock(@NotNull ServerMock server, @NotNull UUID uuid)
	{
		super(server, uuid);
	}

	@Override
	@Deprecated(since = "1.11")
	public Horse.@NotNull Variant getVariant()
	{
		return Horse.Variant.MULE;
	}

	@Override
	public @NotNull EntityType getType()
	{
		return EntityType.MULE;
	}

}
