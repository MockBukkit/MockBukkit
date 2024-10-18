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

import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockbukkit.mockbukkit.matcher.command.CommandResultAnyResponseMatcher.hasAnyResponse;
import static org.mockbukkit.mockbukkit.matcher.command.CommandResultAnyResponseMatcher.hasNoResponse;

@ExtendWith(MockBukkitExtension.class)
class CommandResultAnyResponseMatcherTest extends AbstractMatcherTest
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
	void anyResponse()
	{
		sender.sendMessage(MESSAGE);
		assertThat(commandResult, hasAnyResponse());
	}

	@Test
	void notAnyResponse()
	{
		assertThat(commandResult, hasNoResponse());
	}

	@Test
	void description()
	{
		assertDescription("to have any messages sent to command sender", hasAnyResponse());
	}

	@Test
	void nullSafe()
	{
		assertNullSafe(hasAnyResponse());
	}

	@Test
	void unknownTypeSafe()
	{
		testCopesWithUnknownTypes();
	}

	@Override
	protected Matcher<?> createMatcher()
	{
		return hasAnyResponse();
	}

}
