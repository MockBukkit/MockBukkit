package org.mockbukkit.mockbukkit.command.brigadier.argument;

import com.mojang.brigadier.Command;
import io.papermc.paper.command.brigadier.Commands;
import io.papermc.paper.command.brigadier.argument.ArgumentTypes;
import io.papermc.paper.command.brigadier.argument.resolvers.selector.PlayerSelectorArgumentResolver;
import io.papermc.paper.plugin.lifecycle.event.types.LifecycleEvents;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockbukkit.mockbukkit.MockBukkitExtension;
import org.mockbukkit.mockbukkit.MockBukkitInject;
import org.mockbukkit.mockbukkit.ServerMock;
import org.mockbukkit.mockbukkit.entity.PlayerMock;
import org.mockbukkit.mockbukkit.plugin.PluginMock;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockBukkitExtension.class)
class PlayerArgumentTypeMockTest
{

	@MockBukkitInject
	private ServerMock serverMock;

	@Test
	void playerArgumentType_ResolveByName()
	{
		Player player = serverMock.addPlayer("MockPlayer");
		PluginMock.builder().withOnEnable(pluginMock ->
				pluginMock.getLifecycleManager().registerEventHandler(LifecycleEvents.COMMANDS, event ->
						event.registrar().register(
								Commands.literal("testplayer")
										.then(Commands.argument("player", ArgumentTypes.player())
												.executes(context ->
												{
													PlayerSelectorArgumentResolver resolver = context.getArgument("player", PlayerSelectorArgumentResolver.class);
													List<Player> resolved = resolver.resolve(context.getSource());
													assertEquals(1, resolved.size());
													assertEquals(player, resolved.getFirst());
													return Command.SINGLE_SUCCESS;
												}))
										.build(),
								null,
								List.of()
						))).build();
		serverMock.dispatchCommand(serverMock.getConsoleSender(), "testplayer MockPlayer");
	}

	@Test
	void playerArgumentType_ResolveBySelector_All_FailsForSingle()
	{
		serverMock.addPlayer("Player1");
		serverMock.addPlayer("Player2");
		PluginMock.builder().withOnEnable(pluginMock ->
				pluginMock.getLifecycleManager().registerEventHandler(LifecycleEvents.COMMANDS, event ->
						event.registrar().register(
								Commands.literal("testplayer")
										.then(Commands.argument("player", ArgumentTypes.player())
												.executes(context ->
												{
													PlayerSelectorArgumentResolver resolver = context.getArgument("player", PlayerSelectorArgumentResolver.class);
													List<Player> resolved = resolver.resolve(context.getSource());
													assertEquals(0, resolved.size());
													return Command.SINGLE_SUCCESS;
												}))
										.build(),
								null,
								List.of()
						))).build();
		serverMock.dispatchCommand(serverMock.getConsoleSender(), "testplayer @a");
		serverMock.getConsoleSender().assertSaid("Only one player is allowed, but the provided selector allows more than one");
	}

	@Test
	void playerArgumentType_ResolveBySelector_All_SucceedsForMultiple()
	{
		serverMock.addPlayer("Player1");
		serverMock.addPlayer("Player2");
		PluginMock.builder().withOnEnable(pluginMock ->
				pluginMock.getLifecycleManager().registerEventHandler(LifecycleEvents.COMMANDS, event ->
						event.registrar().register(
								Commands.literal("testplayers")
										.then(Commands.argument("players", ArgumentTypes.players())
												.executes(context ->
												{
													PlayerSelectorArgumentResolver resolver = context.getArgument("players", PlayerSelectorArgumentResolver.class);
													List<Player> resolved = resolver.resolve(context.getSource());
													assertEquals(2, resolved.size());
													return Command.SINGLE_SUCCESS;
												}))
										.build(),
								null,
								List.of()
						))).build();
		serverMock.dispatchCommand(serverMock.getConsoleSender(), "testplayers @a");
	}

	@Test
	void playerArgumentType_ResolveBySelector_Self()
	{
		Player player = serverMock.addPlayer("MockPlayer");
		PluginMock.builder().withOnEnable(pluginMock ->
				pluginMock.getLifecycleManager().registerEventHandler(LifecycleEvents.COMMANDS, event ->
						event.registrar().register(
								Commands.literal("testplayer")
										.then(Commands.argument("player", ArgumentTypes.player())
												.executes(context ->
												{
													PlayerSelectorArgumentResolver resolver = context.getArgument("player", PlayerSelectorArgumentResolver.class);
													List<Player> resolved = resolver.resolve(context.getSource());
													assertEquals(1, resolved.size());
													assertEquals(player, resolved.get(0));
													return Command.SINGLE_SUCCESS;
												}))
										.build(),
								null,
								List.of()
						))).build();
		serverMock.dispatchCommand(player, "testplayer @s");
	}

	@Test
	void playerArgumentType_ResolveBySelector_Nearest_FromConsole()
	{
		serverMock.addPlayer("Player1");

		PluginMock.builder().withOnEnable(pluginMock ->
				pluginMock.getLifecycleManager().registerEventHandler(LifecycleEvents.COMMANDS, event ->
						event.registrar().register(
								Commands.literal("testplayer")
										.then(Commands.argument("player", ArgumentTypes.player())
												.executes(context ->
												{
													PlayerSelectorArgumentResolver resolver = context.getArgument("player", PlayerSelectorArgumentResolver.class);
													List<Player> resolved = resolver.resolve(context.getSource());
													assertEquals(0, resolved.size());
													return Command.SINGLE_SUCCESS;
												}))
										.build(),
								null,
								List.of()
						))).build();
		serverMock.dispatchCommand(serverMock.getConsoleSender(), "testplayer @p");
		serverMock.getConsoleSender().assertSaid("No player was found");
	}

	@Test
	void playerArgumentType_ResolveBySelector_Nearest_FromPlayer()
	{
		PlayerMock player1 = serverMock.addPlayer("Player1");
		player1.setLocation(new Location(player1.getWorld(), 0, 0, 0));
		PlayerMock player2 = serverMock.addPlayer("Player2");
		player2.setLocation(new Location(player2.getWorld(), 100, 0, 0));

		PluginMock.builder().withOnEnable(pluginMock ->
				pluginMock.getLifecycleManager().registerEventHandler(LifecycleEvents.COMMANDS, event ->
						event.registrar().register(
								Commands.literal("testplayer")
										.then(Commands.argument("player", ArgumentTypes.player())
												.executes(context ->
												{
													PlayerSelectorArgumentResolver resolver = context.getArgument("player", PlayerSelectorArgumentResolver.class);
													List<Player> resolved = resolver.resolve(context.getSource());
													assertEquals(1, resolved.size());
													assertEquals(player1, resolved.get(0));
													return Command.SINGLE_SUCCESS;
												}))
										.build(),
								null,
								List.of()
						))).build();
		serverMock.dispatchCommand(player1, "testplayer @p");
	}

	@Test
	void playerArgumentType_ResolveBySelector_Random()
	{
		serverMock.addPlayer("Player1");
		serverMock.addPlayer("Player2");

		PluginMock.builder().withOnEnable(pluginMock ->
				pluginMock.getLifecycleManager().registerEventHandler(LifecycleEvents.COMMANDS, event ->
						event.registrar().register(
								Commands.literal("testplayer")
										.then(Commands.argument("player", ArgumentTypes.player())
												.executes(context ->
												{
													PlayerSelectorArgumentResolver resolver = context.getArgument("player", PlayerSelectorArgumentResolver.class);
													List<Player> resolved = resolver.resolve(context.getSource());
													assertEquals(1, resolved.size());
													return Command.SINGLE_SUCCESS;
												}))
										.build(),
								null,
								List.of()
						))).build();
		serverMock.dispatchCommand(serverMock.getConsoleSender(), "testplayer @r");
	}

	@Test
	void playerArgumentType_ResolveByName_NotFound()
	{
		PluginMock.builder().withOnEnable(pluginMock ->
				pluginMock.getLifecycleManager().registerEventHandler(LifecycleEvents.COMMANDS, event ->
						event.registrar().register(
								Commands.literal("testplayer")
										.then(Commands.argument("player", ArgumentTypes.player())
												.executes(context ->
												{
													PlayerSelectorArgumentResolver resolver = context.getArgument("player", PlayerSelectorArgumentResolver.class);
													List<Player> resolved = resolver.resolve(context.getSource());
													assertEquals(0, resolved.size());
													return Command.SINGLE_SUCCESS;
												}))
										.build(),
								null,
								List.of()
						))).build();
		serverMock.dispatchCommand(serverMock.getConsoleSender(), "testplayer OfflinePlayer");
		serverMock.getConsoleSender().assertSaid("No player was found");
	}

}
