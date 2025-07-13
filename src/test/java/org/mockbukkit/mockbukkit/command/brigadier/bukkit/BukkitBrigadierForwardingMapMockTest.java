package org.mockbukkit.mockbukkit.command.brigadier.bukkit;

import io.papermc.paper.command.brigadier.Commands;
import io.papermc.paper.plugin.lifecycle.event.types.LifecycleEvents;
import org.bukkit.command.Command;
import org.bukkit.command.defaults.HelpCommand;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockbukkit.mockbukkit.MockBukkitExtension;
import org.mockbukkit.mockbukkit.MockBukkitInject;
import org.mockbukkit.mockbukkit.ServerMock;
import org.mockbukkit.mockbukkit.plugin.PluginMock;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(MockBukkitExtension.class)
class BukkitBrigadierForwardingMapMockTest
{

	@MockBukkitInject
	ServerMock serverMock;

	BukkitBrigadierForwardingMapMock map;

	@BeforeEach
	void setUp()
	{
		map = BukkitBrigadierForwardingMapMock.INSTANCE;
	}

	@Test
	void size_insert()
	{
		int initial = map.size();
		map.put("other_command", new HelpCommand());
		assertEquals(initial + 1, map.size());
	}

	@Test
	void size_remove()
	{
		int initial = map.size();
		map.remove("bukkit:reload");
		assertEquals(initial - 1, map.size());
	}

	@Test
	void putGetAndRemove()
	{
		Command command = new HelpCommand();
		map.put("other_command", command);
		assertEquals(command, map.get("other_command"));
		assertTrue(map.containsKey("other_command"));
		assertTrue(map.values().contains(command));
		assertTrue(map.keySet().contains("other_command"));
		assertTrue(map.values().iterator().hasNext());
		assertTrue(map.keySet().iterator().hasNext());
		assertTrue(map.values().stream().anyMatch(command::equals));
		assertTrue(map.keySet().stream().anyMatch("other_command"::equals));
		assertTrue(map.entrySet().stream().anyMatch(entry -> entry.getValue().equals(command) && entry.getKey().equals("other_command")));
		assertEquals(command, map.remove("other_command"));
		assertFalse(map.containsKey("other_command"));
		assertFalse(map.containsValue(command));
	}

	@Test
	void containsValueWithNonCommand()
	{
		assertFalse(map.containsValue("Hello world"));
	}

	@Test
	void containsKeyWithNonString()
	{
		assertFalse(map.containsKey("Hello world"));
	}

	@Test
	void removeWithNonString()
	{
		assertNull(map.remove(1));
	}

	@Test
	void getWithNonString()
	{
		assertNull(map.get(1));
	}

	@Test
	void testClear()
	{
		assertNotEquals(0, map.size());
		map.clear();
		assertEquals(0, map.size());
	}

	@Test
	void testIsEmpty()
	{
		assertFalse(map.isEmpty());
		map.remove("bukkit:reload");
		map.remove("bukkit:rl");
		map.remove("bukkit:timings");
		map.remove("timings");
		assertTrue(map.isEmpty());
	}

	@Test
	void testDoubleRemoveShouldntError()
	{
		assertNotNull(map.remove("bukkit:reload"));
		assertNull(map.remove("bukkit:reload"));
	}

	@Test
	void put_brigadier()
	{
		int initial = map.size();
		PluginMock.builder().withOnEnable((pluginMock) ->
				pluginMock.getLifecycleManager().registerEventHandler(LifecycleEvents.COMMANDS, event ->
						event.registrar().register(Commands.literal("other_command").executes(context ->
								com.mojang.brigadier.Command.SINGLE_SUCCESS).build(), "some bukkit help description string", List.of("an_alias")
						)
				)
		).build();
		// Current implementation initializes before the first command is dispatched
		serverMock.dispatchCommand(serverMock.getConsoleSender(), "ignored");
		// plugin prefix and alias mutates on this, therefore 4
		assertEquals(initial + 4, map.size());
		assertFalse(map.isEmpty());
		assertTrue(map.containsKey("other_command"));
		assertTrue(map.values().iterator().hasNext());
		assertTrue(map.keySet().stream().anyMatch("other_command"::equals));
	}


}
