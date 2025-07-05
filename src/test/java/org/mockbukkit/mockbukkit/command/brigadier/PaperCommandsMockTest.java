package org.mockbukkit.mockbukkit.command.brigadier;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import io.papermc.paper.command.brigadier.BasicCommand;
import io.papermc.paper.command.brigadier.CommandSourceStack;
import io.papermc.paper.command.brigadier.Commands;
import io.papermc.paper.plugin.lifecycle.event.types.LifecycleEvents;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockbukkit.mockbukkit.MockBukkitExtension;
import org.mockbukkit.mockbukkit.MockBukkitInject;
import org.mockbukkit.mockbukkit.ServerMock;
import org.mockbukkit.mockbukkit.plugin.PluginMock;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockBukkitExtension.class)
class PaperCommandsMockTest
{

	@MockBukkitInject
	ServerMock serverMock;
	List<Object> arguments = List.of();

	@ParameterizedTest
	@ValueSource(strings = { "new_command", "an_alias", "pluginmock:new_command", "pluginmock:an_alias" })
	void commandWithArgumentsTest(String alias)
	{
		PluginMock.builder().withOnEnable((pluginMock) ->
		{
			pluginMock.getLifecycleManager().registerEventHandler(LifecycleEvents.COMMANDS, event ->
			{
				event.registrar().register(argumentBuilderGreedy().build(), "some bukkit help description string", List.of("an_alias"));
			});
		}).build();
		serverMock.dispatchCommand(serverMock.getConsoleSender(), alias + " Hello world!");
		assertEquals(1, arguments.size());
		assertEquals("Hello world!", arguments.getFirst());
	}

	@Test
	void commandWithArgumentsTest_doesNotExist()
	{
		PluginMock.builder().withOnEnable((pluginMock) ->
		{
			pluginMock.getLifecycleManager().registerEventHandler(LifecycleEvents.COMMANDS, event ->
			{
				event.registrar().register(argumentBuilderGreedy().build(), "some bukkit help description string", List.of("an_alias"));
			});
		}).build();
		serverMock.dispatchCommand(serverMock.getConsoleSender(), "this_does Not exist!");
		assertEquals(0, arguments.size());
	}

	@Test
	void basicCommand()
	{
		PluginMock.builder().withOnEnable((pluginMock) ->
				pluginMock.getLifecycleManager().registerEventHandler(LifecycleEvents.COMMANDS, event ->
						event.registrar().register("basic", createBasicCommand()))
		).build();
		serverMock.dispatchCommand(serverMock.getConsoleSender(), "basic Not exist!");
		assertEquals(List.of("Not", "exist!"), arguments);
	}

	LiteralArgumentBuilder<CommandSourceStack> argumentBuilderGreedy()
	{
		return Commands.literal("new_command")
				.then(Commands.argument("my_argument", StringArgumentType.greedyString()).executes(context ->
						{
							arguments = List.of(context.getArgument("my_argument", String.class));
							return Command.SINGLE_SUCCESS;
						})
				);
	}

	BasicCommand createBasicCommand()
	{
		return new BasicCommand()
		{
			@Override
			public void execute(CommandSourceStack commandSourceStack, String[] args)
			{
				arguments = Arrays.asList(args);
			}
		};
	}

}
