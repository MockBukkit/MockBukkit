package be.seeseemelk.mockbukkit.command;

import org.bukkit.command.CommandMap;
import org.bukkit.command.SimpleCommandMap;
import org.jetbrains.annotations.NotNull;

import be.seeseemelk.mockbukkit.ServerMock;

public class MockCommandMap extends SimpleCommandMap implements CommandMap
{
	public MockCommandMap(@NotNull ServerMock serverMock)
	{
		super(serverMock);
	}
}