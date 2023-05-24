package be.seeseemelk.mockbukkit.entity;

import be.seeseemelk.mockbukkit.MockBukkitExtension;
import be.seeseemelk.mockbukkit.MockBukkitInject;
import be.seeseemelk.mockbukkit.ServerMock;
import org.bukkit.Material;
import org.bukkit.entity.minecart.CommandMinecart;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockBukkitExtension.class)
class CommandMinecartMockTest
{

	CommandMinecart minecart;
	@MockBukkitInject
	ServerMock server;

	@BeforeEach
	void setUp()
	{
		minecart = new CommandMinecartMock(server, UUID.randomUUID());
	}

	@Test
	void tesTGetCommandDefault()
	{
		assertEquals("", minecart.getCommand());
	}

	@Test
	void testSetCommand()
	{
		minecart.setCommand("say Hello World!");
		assertEquals("say Hello World!", minecart.getCommand());
	}

	@Test
	void testSetCommandNull()
	{
		minecart.setCommand("say Hello World!");
		minecart.setCommand(null);
		assertEquals("", minecart.getCommand());
	}

	@Test
	void testGetMinecartMaterial()
	{
		assertEquals(Material.COMMAND_BLOCK_MINECART, minecart.getMinecartMaterial());
	}

	@Test
	void testGetSuccessCountDefault()
	{
		assertEquals(0, minecart.getSuccessCount());
	}

	@Test
	void testGetSuccessCount()
	{
		minecart.setSuccessCount(42);
		assertEquals(42, minecart.getSuccessCount());
	}

	@Test
	void testSuccessCountResetWhenChangingCommand()
	{
		minecart.setSuccessCount(42);
		assertEquals(42, minecart.getSuccessCount());
		minecart.setCommand("say Hello World!");
		assertEquals(0, minecart.getSuccessCount());
	}


}
