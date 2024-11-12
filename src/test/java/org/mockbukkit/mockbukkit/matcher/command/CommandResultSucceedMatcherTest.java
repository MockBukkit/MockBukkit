package org.mockbukkit.mockbukkit.matcher.command;

import org.hamcrest.Matcher;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockbukkit.mockbukkit.MockBukkitExtension;
import org.mockbukkit.mockbukkit.MockBukkitInject;
import org.mockbukkit.mockbukkit.ServerMock;
import org.mockbukkit.mockbukkit.command.CommandResult;
import org.mockbukkit.testutils.matcher.AbstractMatcherTest;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockbukkit.mockbukkit.matcher.command.CommandResultSucceedMatcher.hasFailed;
import static org.mockbukkit.mockbukkit.matcher.command.CommandResultSucceedMatcher.hasSucceeded;

@ExtendWith(MockBukkitExtension.class)
class CommandResultSucceedMatcherTest extends AbstractMatcherTest
{

	@MockBukkitInject
	ServerMock serverMock;
	private CommandResult commandResult;

	@BeforeEach
	void setUp()
	{
		this.commandResult = new CommandResult(true, serverMock.addPlayer());
	}

	@Test
	void succeeded()
	{
		assertThat(commandResult, hasSucceeded());
	}

	@Test
	void notSucceeded()
	{
		CommandResult commandResultFail = new CommandResult(false, serverMock.addPlayer());
		assertThat(commandResultFail, hasFailed());
	}

	@Test
	void description()
	{
		assertDescription("to have a success code", hasSucceeded());
	}

	@Test
	void nullSafe()
	{
		assertNullSafe(hasSucceeded());
	}

	@Test
	void unknownTypeSafe()
	{
		testCopesWithUnknownTypes();
	}

	@Override
	protected Matcher<?> createMatcher()
	{
		return hasSucceeded();
	}

}
