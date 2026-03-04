package org.mockbukkit.mockbukkit.command;

import org.bukkit.command.CommandMap;
import org.bukkit.command.SimpleCommandMap;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;
import org.mockbukkit.mockbukkit.ServerMock;
import org.mockbukkit.mockbukkit.command.brigadier.bukkit.BukkitBrigadierForwardingMapMock;

/**
 * Mock implementation of a {@link CommandMap}.
 */
public class CommandMapMock extends SimpleCommandMap implements CommandMap
{

	/**
	 * @param server The ServerMock this command map is for.
	 */
	@ApiStatus.Internal
	public CommandMapMock(@NotNull ServerMock server)
	{
		super(server, BukkitBrigadierForwardingMapMock.INSTANCE);
	}

}
