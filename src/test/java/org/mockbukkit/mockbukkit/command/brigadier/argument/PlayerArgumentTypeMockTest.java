package org.mockbukkit.mockbukkit.command.brigadier.argument;

import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import io.papermc.paper.command.brigadier.argument.ArgumentTypes;
import org.bukkit.Location;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockbukkit.mockbukkit.MockBukkitExtension;
import org.mockbukkit.mockbukkit.MockBukkitInject;
import org.mockbukkit.mockbukkit.ServerMock;
import org.mockbukkit.mockbukkit.command.CommandSourceStackMock;
import org.mockbukkit.mockbukkit.entity.PlayerMock;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockBukkitExtension.class)
class PlayerArgumentTypeMockTest
{

	@MockBukkitInject
	private ServerMock serverMock;

	@Test
	void playerArgumentType_ResolveByName() throws CommandSyntaxException
	{
		var reader = new StringReader("notch");

		var notch = serverMock.addPlayer("notch");
		var stack = CommandSourceStackMock.from(notch);

		var actual = ArgumentTypes.player().parse(reader, notch).resolve(stack);

		assertEquals(List.of(notch), actual);
	}

	@Test
	void playerArgumentType_ResolveBySelector_All_FailsForSingle() throws CommandSyntaxException
	{
		var reader = new StringReader("@a");

		var notch = serverMock.addPlayer("notch");
		var stack = CommandSourceStackMock.from(notch);

		var actual = ArgumentTypes.player().parse(reader, notch).resolve(stack);

		assertEquals(List.of(), actual);
		notch.assertSaid("Only one player is allowed, but the provided selector allows more than one");
	}

	@Test
	void playerArgumentType_ResolveBySelector_All_SucceedsForMultiple() throws CommandSyntaxException
	{
		var reader = new StringReader("@a");

		var player1 = serverMock.addPlayer("Player1");
		var player2 = serverMock.addPlayer("Player2");
		var console = serverMock.getConsoleSender();
		var stack = CommandSourceStackMock.from(console);

		var actual = ArgumentTypes.players().parse(reader, console).resolve(stack);

		assertEquals(List.of(player1, player2), actual);
	}

	@Test
	void playerArgumentType_ResolveBySelector_Self() throws CommandSyntaxException
	{
		var reader = new StringReader("@s");

		var player = serverMock.addPlayer("MockPlayer");
		var stack = CommandSourceStackMock.from(player);

		var actual = ArgumentTypes.player().parse(reader, player).resolve(stack);

		assertEquals(List.of(player), actual);
	}

	@Test
	void playerArgumentType_ResolveBySelector_Nearest_FromConsole() throws CommandSyntaxException
	{
		var reader = new StringReader("@p");

		serverMock.addPlayer("Player1");
		var console = serverMock.getConsoleSender();
		var stack = CommandSourceStackMock.from(console);

		var actual = ArgumentTypes.player().parse(reader, console).resolve(stack);

		assertEquals(List.of(), actual);
		console.assertSaid("No player was found");
	}

	@Test
	void playerArgumentType_ResolveBySelector_Nearest_FromPlayer() throws CommandSyntaxException
	{
		var reader = new StringReader("@p");

		PlayerMock player1 = serverMock.addPlayer("Player1");
		player1.setLocation(new Location(player1.getWorld(), 0, 0, 0));
		PlayerMock player2 = serverMock.addPlayer("Player2");
		player2.setLocation(new Location(player2.getWorld(), 100, 0, 0));
		var stack = CommandSourceStackMock.from(player1);

		var actual = ArgumentTypes.player().parse(reader, player1).resolve(stack);

		assertEquals(List.of(player1), actual);
	}

	@Test
	void playerArgumentType_ResolveBySelector_Random() throws CommandSyntaxException
	{
		var reader = new StringReader("@r");

		serverMock.addPlayer("Player1");
		serverMock.addPlayer("Player2");
		var console = serverMock.getConsoleSender();
		var stack = CommandSourceStackMock.from(console);

		var actual = ArgumentTypes.player().parse(reader, console).resolve(stack);

		assertEquals(1, actual.size());
	}

	@Test
	void playerArgumentType_ResolveByName_NotFound() throws CommandSyntaxException
	{
		var reader = new StringReader("OfflinePlayer");

		var console = serverMock.getConsoleSender();
		var stack = CommandSourceStackMock.from(console);

		var actual = ArgumentTypes.player().parse(reader, console).resolve(stack);

		assertEquals(List.of(), actual);
		console.assertSaid("No player was found");
	}

}
