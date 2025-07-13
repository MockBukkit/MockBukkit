package org.mockbukkit.mockbukkit;

import lombok.Getter;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockbukkit.mockbukkit.entity.PlayerMock;
import org.mockbukkit.mockbukkit.plugin.PluginMock;
import org.mockbukkit.mockbukkit.world.WorldMock;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNotSame;
import static org.junit.jupiter.api.Assertions.assertNull;

@ExtendWith(MockBukkitExtension.class)
class MockBukkitExtensionDifferentMocksTest
{

	// Test classes
	@Getter
	@Nested
	class MixinClassTest
	{

		@MockBukkitInject
		private ServerMock serverMock;

		@Test
		void serverMockIsNotNull()
		{
			assertNotNull(serverMock);
		}

	}

	@Nested
	class TestChildClass extends MixinClassTest
	{
		// Inherits the tests from TestMixinClass
	}

	@Nested
	class TestDirectClass
	{

		@MockBukkitInject
		ServerMock serverMock;

		@Test
		void serverMockIsNotNull()
		{
			assertNotNull(serverMock);
		}

	}

	@Nested
	class TestAllTypesClass
	{

		@MockBukkitInject
		ServerMock serverMock;

		@MockBukkitInject
		PlayerMock playerMock;

		@MockBukkitInject
		WorldMock worldMock;

		@MockBukkitInject
		Plugin pluginMock;

		@Test
		void serverMockIsNotNull()
		{
			assertNotNull(serverMock);
		}

		@Test
		void playerMockIsNotNull()
		{
			assertNotNull(playerMock);
		}

		@Test
		void worldMockIsNotNull()
		{
			assertNotNull(worldMock);
		}

		@Test
		void pluginMockIsNotNull()
		{
			assertNotNull(pluginMock);
		}

	}

	@Nested
	class TestMultiplesClass
	{

		@MockBukkitInject
		Player player1;

		@MockBukkitInject
		PlayerMock player2;

		@MockBukkitInject
		World world1;

		@MockBukkitInject
		WorldMock world2;

		@MockBukkitInject
		Plugin plugin1;

		@MockBukkitInject
		PluginMock plugin2;

		@Test
		void playerMocksNotNull()
		{
			assertNotNull(player1);
			assertNotNull(player2);

			assertNotSame(player1, player2);

			assertNotEquals(player1.getUniqueId(), player2.getUniqueId());
			assertNotEquals(player1.getName(), player2.getName());
		}

		@Test
		void worldMocksNotNull()
		{
			assertNotNull(world1);
			assertNotNull(world2);

			assertNotSame(world1, world2);

			assertNotEquals(world1.getUID(), world2.getUID());
			assertNotEquals(world1.getName(), world2.getName());
		}

		@Test
		void pluginMocksNotNull()
		{
			assertNotNull(plugin1);
			assertNotNull(plugin2);

			assertNotSame(plugin1, plugin2);

			assertNotEquals(plugin1.getDescription().getFullName(), plugin2.getDescription().getFullName());
		}

	}

	@Nested
	class TestInvalidThingie
	{
		@MockBukkitInject
		private Integer someInteger;

		@Test
		void testInteger() {
			assertNull(someInteger);
		}
	}

}
