package org.mockbukkit.mockbukkit.command;

import org.mockbukkit.mockbukkit.ServerMock;
import com.google.common.base.Preconditions;
import org.bukkit.command.Command;
import org.bukkit.command.CommandMap;
import org.bukkit.command.SimpleCommandMap;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;

import java.util.Map;

/**
 * Mock implementation of a {@link CommandMap}.
 */
public class CommandMapMock extends SimpleCommandMap implements CommandMap
{

	/**
	 * @param server The ServerMock this command map is for.
	 */
	@ApiStatus.Internal
	public CommandMapMock(@NotNull ServerMock server, Map<String, Command> backing)
	{
		super(server, backing);
		Preconditions.checkNotNull(server, "server cannot be null");
	}

}
