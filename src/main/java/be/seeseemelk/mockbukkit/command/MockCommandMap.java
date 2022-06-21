package be.seeseemelk.mockbukkit.command;

import be.seeseemelk.mockbukkit.ServerMock;
import com.google.common.base.Preconditions;
import org.bukkit.command.CommandMap;
import org.bukkit.command.SimpleCommandMap;
import org.jetbrains.annotations.NotNull;

public class MockCommandMap extends SimpleCommandMap implements CommandMap
{

	public MockCommandMap(@NotNull ServerMock serverMock)
	{
		super(serverMock);
		Preconditions.checkNotNull(serverMock, "ServerMock cannot be null");
	}

}
