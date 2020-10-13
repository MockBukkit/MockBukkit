package be.seeseemelk.mockbukkit;

import org.bukkit.command.CommandMap;
import org.bukkit.command.SimpleCommandMap;

public class MockCommandMap extends SimpleCommandMap implements CommandMap
{
    public MockCommandMap(ServerMock serverMock)
    {
        super(serverMock);
    }
}