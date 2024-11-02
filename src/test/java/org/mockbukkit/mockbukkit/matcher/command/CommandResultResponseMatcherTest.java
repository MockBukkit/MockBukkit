package org.mockbukkit.mockbukkit.matcher.command;

import org.hamcrest.Matcher;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockbukkit.mockbukkit.MockBukkitExtension;
import org.mockbukkit.mockbukkit.MockBukkitInject;
import org.mockbukkit.mockbukkit.ServerMock;
import org.mockbukkit.mockbukkit.command.CommandResult;
import org.mockbukkit.mockbukkit.entity.PlayerMock;
import org.mockbukkit.testutils.matcher.AbstractMatcherTest;

import static org.mockbukkit.mockbukkit.matcher.command.CommandResultResponseMatcher.hasResponse;

@ExtendWith(MockBukkitExtension.class)
class CommandResultResponseMatcherTest extends AbstractMatcherTest
{

	@MockBukkitInject
	ServerMock serverMock;
	private PlayerMock sender;
	private CommandResult commandResult;
	private static final String MESSAGE = "Hello world!";

	@BeforeEach
	void setUp()
	{
		this.sender = serverMock.addPlayer();
		this.commandResult = new CommandResult(true, sender);
	}

	@Test
	void matches()
	{
		sender.sendMessage(MESSAGE);
		assertMatches(hasResponse(MESSAGE), commandResult);
	}

	@Test
	void doesNotMatch_null()
	{
		assertDoesNotMatch(hasResponse(MESSAGE), commandResult);
	}

	@Test
	void doesNotMatch_differentMessage()
	{
		sender.sendMessage("Hewwo wowd!");
		assertDoesNotMatch(hasResponse(MESSAGE), commandResult);
	}

	@Test
	void nullSafe()
	{
		assertNullSafe(hasResponse(MESSAGE));
	}

	@Test
	void unknownTypeSafe()
	{
		testCopesWithUnknownTypes();
	}

	@Override
	protected Matcher<?> createMatcher()
	{
		return hasResponse(MESSAGE);
	}

}
