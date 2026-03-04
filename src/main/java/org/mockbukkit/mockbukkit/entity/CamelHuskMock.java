package org.mockbukkit.mockbukkit.entity;

import org.bukkit.entity.CamelHusk;
import org.bukkit.entity.EntityType;
import org.jspecify.annotations.NullMarked;
import org.mockbukkit.mockbukkit.ServerMock;

import java.util.UUID;

/**
 * Mock implementation of an {@link CamelHusk}.
 *
 * @see CamelMock
 */
@NullMarked
public class CamelHuskMock extends CamelMock implements CamelHusk
{

	/**
	 * Constructs a new {@link CamelHusk} on the provided {@link ServerMock} with a specified {@link UUID}.
	 *
	 * @param server The server to create the entity on.
	 * @param uuid   The UUID of the entity.
	 */
	public CamelHuskMock(ServerMock server, UUID uuid)
	{
		super(server, uuid);
	}

	@Override
	public EntityType getType()
	{
		return EntityType.CAMEL_HUSK;
	}

}
