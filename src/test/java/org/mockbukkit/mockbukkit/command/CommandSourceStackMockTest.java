package org.mockbukkit.mockbukkit.command;

import org.bukkit.Location;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockbukkit.mockbukkit.MockBukkitExtension;
import org.mockbukkit.mockbukkit.MockBukkitInject;
import org.mockbukkit.mockbukkit.ServerMock;
import org.mockbukkit.mockbukkit.world.WorldMock;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotSame;

@ExtendWith(MockBukkitExtension.class)
class CommandSourceStackMockTest
{

	@MockBukkitInject
	private ServerMock server;

	@Test
	void getLocation_ReturnsCopy()
	{
		WorldMock world = server.addSimpleWorld("world");
		Location location = new Location(world, 5, 2, 1);
		CommandSourceStackMock stack = new CommandSourceStackMock(location, server.getConsoleSender(), null);

		Location first = stack.getLocation();
		assertNotSame(first, stack.getLocation());

		first.add(0.5, 0.5, 0.5);
		assertEquals(new Location(world, 5, 2, 1), stack.getLocation());
	}

}
