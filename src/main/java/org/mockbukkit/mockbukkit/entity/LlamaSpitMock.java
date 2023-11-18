package org.mockbukkit.mockbukkit.entity;

import org.mockbukkit.mockbukkit.ServerMock;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LlamaSpit;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;

public class LlamaSpitMock extends ProjectileMock implements LlamaSpit
{

	/**
	 * Constructs a new {@link LlamaSpitMock} on the provided {@link ServerMock} with a specified {@link UUID}.
	 *
	 * @param server The server to create the entity on.
	 * @param uuid   The UUID of the entity.
	 */
	public LlamaSpitMock(@NotNull ServerMock server, @NotNull UUID uuid)
	{
		super(server, uuid);
	}

	@Override
	public @NotNull EntityType getType()
	{
		return EntityType.LLAMA_SPIT;
	}

}
