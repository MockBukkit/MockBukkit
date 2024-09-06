package org.mockbukkit.mockbukkit.entity;

import org.mockbukkit.mockbukkit.ServerMock;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.GlowItemFrame;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;

/**
 * Mock implementation of an {@link GlowItemFrame}.
 *
 * @see ItemFrameMock
 */
public class GlowItemFrameMock extends ItemFrameMock implements GlowItemFrame
{

	/**
	 * Constructs a new {@link GlowItemFrame} on the provided {@link ServerMock} with a specified {@link UUID}.
	 *
	 * @param server The server to create the entity on.
	 * @param uuid   The UUID of the entity.
	 */
	public GlowItemFrameMock(@NotNull ServerMock server, @NotNull UUID uuid)
	{
		super(server, uuid);
	}

	@Override
	public @NotNull EntityType getType()
	{
		return EntityType.GLOW_ITEM_FRAME;
	}

}
