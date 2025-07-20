package org.mockbukkit.mockbukkit;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Cow;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.extension.ParameterContext;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockbukkit.mockbukkit.entity.CowMock;
import org.mockbukkit.mockbukkit.entity.PlayerMock;
import org.mockbukkit.mockbukkit.plugin.PluginMock;
import org.mockbukkit.mockbukkit.world.WorldMock;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNotSame;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

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
		CowMock cowMock;

		@MockBukkitInject
		WorldMock worldMock;

		@MockBukkitInject
		Plugin pluginMock;

		@MockBukkitInject
		Location location;

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
		void cowMockIsNotNull()
		{
			assertNotNull(cowMock);
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

		@Test
		void locationIsNotNull()
		{
			assertNotNull(location);
			assertEquals(serverMock.getWorlds().getFirst(), location.getWorld());
			assertEquals(0, location.getX());
			assertEquals(0, location.getY());
			assertEquals(0, location.getZ());
			assertEquals(0, location.getPitch());
			assertEquals(0, location.getYaw());
		}

	}

	@Nested
	class TestAllTypesWithNamesClass
	{

		@MockBukkitInject(name = "Bumba")
		PlayerMock playerMock;

		@MockBukkitInject(name = "Studio100")
		WorldMock worldMock;

		@MockBukkitInject(name = "CodeMonkey")
		Plugin pluginMock;

		@Test
		void playerMockIsNotNull()
		{
			assertNotNull(playerMock);
			assertEquals("Bumba", playerMock.getName());
		}

		@Test
		void worldMockIsNotNull()
		{
			assertNotNull(worldMock);
			assertEquals("Studio100", worldMock.getName());
		}

		@Test
		void pluginMockIsNotNull()
		{
			assertNotNull(pluginMock);
			assertEquals("CodeMonkey v1.0.0", pluginMock.getDescription().getFullName());
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
		void testInteger()
		{
			assertNull(someInteger);
		}

	}

	@ParameterizedTest
	@CsvSource({ "dummy" })
	void shouldInjectMocksViaMethodParameters(
			String value,
			@MockBukkitInject ServerMock server,
			@MockBukkitInject Player player,
			@MockBukkitInject World world,
			@MockBukkitInject Plugin plugin,
			@MockBukkitInject Location location,
			@MockBukkitInject Cow cowMock
	)
	{
		assertEquals("dummy", value);

		assertNotNull(server);
		assertInstanceOf(ServerMock.class, server);
		assertNotNull(player);
		assertInstanceOf(PlayerMock.class, player);
		assertNotNull(world);
		assertInstanceOf(WorldMock.class, world);
		assertNotNull(plugin);
		assertInstanceOf(Plugin.class, plugin);
		assertNotNull(location);
		assertInstanceOf(Location.class, location);
		assertNotNull(cowMock);
		assertInstanceOf(Cow.class, cowMock);
	}

	@SuppressWarnings({ "java:S1144", "java:S1172" })
	private void testMethodWithInteger(@MockBukkitInject Integer param)
	{
		// I just need the methods signature. It doesn't have to do anything.
	}

	@RequiredArgsConstructor
	private static class TestParameterContext implements ParameterContext
	{

		@Getter
		private final Parameter parameter;

		@Override
		public int getIndex()
		{
			return 0;
		}

		@Override
		public Optional<Object> getTarget()
		{
			return Optional.empty();
		}

	}

	@Test
	@SneakyThrows
	void checkForInvalidSupportedAnnotation()
	{
		// I can't do this via a normal way, because it will crash before I reach the correct point inside the code.
		var extension = new MockBukkitExtension();
		Method testMethod = getClass().getDeclaredMethod("testMethodWithInteger", Integer.class);
		Parameter parameter = testMethod.getParameters()[0];

		ParameterContext parameterContext = new TestParameterContext(parameter);

		assertFalse(extension.supportsParameter(parameterContext, null));
		assertNull(extension.resolveParameter(parameterContext, null));
	}

	public static class MyPlugin extends JavaPlugin
	{

		public int someInt = 42;

		@Override
		public void onEnable()
		{
			getLogger().info("Enabled!");
		}

	}

	@Nested
	class TestCustomPluginInjection
	{

		@MockBukkitInject
		MyPlugin plugin;

		@Test
		void testIsProperlyInjected()
		{
			assertNotNull(plugin);
			assertInstanceOf(MyPlugin.class, plugin);
			assertTrue(plugin.isEnabled());
			assertEquals(42, plugin.someInt);
		}

	}

	@Nested
	class TestOnlyEntityMocking
	{
		// This test when a server/world hasn't been populated before.

		@Test
		void noWorldsCreated()
		{
			assertTrue(MockBukkit.getOrCreateMock().getWorlds().isEmpty());
		}

		@Test
		void testIsProperlyInjected(@MockBukkitInject CowMock cowMock)
		{
			assertNotNull(cowMock);
			assertEquals(1, MockBukkit.getOrCreateMock().getWorlds().size());
			var cowLocation = cowMock.getLocation();
			assertEquals(0, cowLocation.getX());
			assertEquals(0, cowLocation.getY());
			assertEquals(0, cowLocation.getZ());
			assertEquals(0, cowLocation.getPitch());
			assertEquals(0, cowLocation.getYaw());
		}

	}

}
