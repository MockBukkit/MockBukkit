package be.seeseemelk.mockbukkit.plugin.lifecycle.event;

import be.seeseemelk.mockbukkit.MockBukkit;
import be.seeseemelk.mockbukkit.MockBukkitExtension;
import be.seeseemelk.mockbukkit.MockBukkitInject;
import be.seeseemelk.mockbukkit.MockPlugin;
import be.seeseemelk.mockbukkit.ServerMock;
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
	void pluginOnEnable()
	{
		MockPlugin mockPlugin = MockBukkit.createMockPlugin();
		LifecycleEventManager<Plugin> lifecycleEventManager = mockPlugin.getLifecycleManager();
		AtomicBoolean atomicBoolean = new AtomicBoolean(false);
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
		serverMock.addPlayer().performCommand("new-command");
		assertTrue(atomicBoolean.get());
	}

}
