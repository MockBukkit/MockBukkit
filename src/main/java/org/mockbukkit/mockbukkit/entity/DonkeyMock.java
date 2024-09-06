package org.mockbukkit.mockbukkit.entity;

import org.mockbukkit.mockbukkit.ServerMock;
import org.bukkit.entity.Donkey;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Horse;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;

/**
 * Mock implementation of a {@link Donkey}.
 *
 * @see ChestedHorseMock
 */
public class DonkeyMock extends ChestedHorseMock implements Donkey
{

	/**
	 * Constructs a new {@link DonkeyMock} on the provided {@link ServerMock} with a specified {@link UUID}.
	 *
	 * @param server The server to create the entity on.
	 * @param uuid   The UUID of the entity.
	 */
	public DonkeyMock(@NotNull ServerMock server, @NotNull UUID uuid)
	{
		super(server, uuid);
	}

	@Override
	@Deprecated(since = "1.17")
	public Horse.@NotNull Variant getVariant()
	{
		return Horse.Variant.DONKEY;
	}

	@Override
	public @NotNull EntityType getType()
	{
		return EntityType.DONKEY;
	}

}
