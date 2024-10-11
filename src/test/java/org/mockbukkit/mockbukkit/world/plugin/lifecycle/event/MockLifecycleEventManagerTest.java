package org.mockbukkit.mockbukkit.plugin.lifecycle.event;

import org.mockbukkit.mockbukkit.MockBukkit;
import org.mockbukkit.mockbukkit.MockBukkitExtension;
import org.mockbukkit.mockbukkit.MockBukkitInject;
import org.mockbukkit.mockbukkit.MockPlugin;
import org.mockbukkit.mockbukkit.ServerMock;
import com.mojang.brigadier.Command;
import io.papermc.paper.command.brigadier.Commands;
import io.papermc.paper.plugin.lifecycle.event.LifecycleEventManager;
import io.papermc.paper.plugin.lifecycle.event.LifecycleEventOwner;
import io.papermc.paper.plugin.lifecycle.event.handler.configuration.PrioritizedLifecycleEventHandlerConfiguration;
import io.papermc.paper.plugin.lifecycle.event.types.LifecycleEvents;
import org.bukkit.plugin.Plugin;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(MockBukkitExtension.class)
class MockLifecycleEventManagerTest
{

	@MockBukkitInject
	ServerMock serverMock;

	@Test
	void
	pluginOnEnable()
	{
		AtomicBoolean atomicBoolean = new AtomicBoolean(false);
		MockPlugin.builder().withOnEnable((mockPlugin) -> {
			LifecycleEventManager<Plugin> lifecycleEventManager = mockPlugin.getLifecycleManager();
			PrioritizedLifecycleEventHandlerConfiguration<LifecycleEventOwner> config = LifecycleEvents.COMMANDS.newHandler((event) ->
			{
				final Commands commands = event.registrar();
				commands.register(Commands.literal("new-command").executes(ctx ->
				{
					atomicBoolean.set(true);
					return Command.SINGLE_SUCCESS;
				}).build(), "some bukkit help description string", List.of("an-alias"));
			}).monitor();
			lifecycleEventManager.registerEventHandler(config);
		});

		serverMock.addPlayer().performCommand("new-command");
		assertTrue(atomicBoolean.get());
	}

}
