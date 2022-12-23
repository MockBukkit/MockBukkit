package be.seeseemelk.mockbukkit.command;

import be.seeseemelk.mockbukkit.ServerMock;
import com.google.common.base.Preconditions;
import org.bukkit.command.CommandMap;
import org.bukkit.command.SimpleCommandMap;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;

/**
 * Mock implementation of a {@link CommandMap}.
 */
public class CommandMapMock extends SimpleCommandMap implements CommandMap
{

	/**
	 * @param serverMock The ServerMock this command map is for.
	 */
	@ApiStatus.Internal
	public CommandMapMock(@NotNull ServerMock serverMock)
	{
		super(serverMock);
		Preconditions.checkNotNull(serverMock, "ServerMock cannot be null");
	}

}
