package org.mockbukkit.mockbukkit.command;

import com.mojang.brigadier.tree.CommandNode;
import io.papermc.paper.command.brigadier.CommandSourceStack;
import io.papermc.paper.command.brigadier.Commands;
import org.bukkit.entity.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockbukkit.mockbukkit.MockBukkitExtension;
import org.mockbukkit.mockbukkit.MockBukkitInject;
import org.mockbukkit.mockbukkit.ServerMock;
import org.mockbukkit.mockbukkit.command.brigadier.PaperCommandsMock;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockBukkitExtension.class)
class VanillaCommandWrapperMockTest
{

	@MockBukkitInject
	private ServerMock serverMock;
	private VanillaCommandWrapperMock command;

	@BeforeEach
	void setUp()
	{
		CommandNode<CommandSourceStack> commandNode = Commands.literal("a_command")
				.then(Commands.literal("1next"))
				.then(Commands.literal("2next"))
				.build();
		this.command = new VanillaCommandWrapperMock(commandNode);
		PaperCommandsMock.INSTANCE.getDispatcher().getRoot().addChild(commandNode);
	}

	@Test
	void tabComplete()
	{
		Player player = serverMock.addPlayer();
		List<String> completions = command.tabComplete(player, "a_command", new String[]{ "2" }, null);
		assertEquals(1, completions.size());
		assertEquals("2next", completions.getFirst());
		List<String> completions2 = command.tabComplete(player, "a_command", new String[]{ "1" }, null);
		assertEquals(1, completions2.size());
		assertEquals("1next", completions2.getFirst());
		List<String> completions3 = command.tabComplete(player, "a_command", new String[]{ "" }, null);
		assertEquals(2, completions3.size());
	}

	@Test
	void tabComplete_nullSender()
	{
		assertThrows(IllegalArgumentException.class, () -> command.tabComplete(null, "a_command", new String[0], null));
	}

	@Test
	void tabComplete_nullAlias()
	{
		Player player = serverMock.addPlayer();
		assertThrows(IllegalArgumentException.class, () -> command.tabComplete(player, null, new String[0], null));
	}

	@Test
	void tabComplete_nullArgs()
	{
		Player player = serverMock.addPlayer();
		assertThrows(IllegalArgumentException.class, () -> command.tabComplete(player, "a_command", null, null));
	}

}
