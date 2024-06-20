package org.mockbukkit.mockbukkit.matcher.command;

import org.hamcrest.Matcher;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockbukkit.mockbukkit.MockBukkitExtension;
import org.mockbukkit.mockbukkit.MockBukkitInject;
import org.mockbukkit.mockbukkit.ServerMock;
import org.mockbukkit.mockbukkit.entity.PlayerMock;
import org.mockbukkit.testutils.matcher.AbstractMatcherTest;

import static org.mockbukkit.mockbukkit.matcher.command.MessageTargetReceivedAnyMessageMatcher.hasReceivedAny;

@ExtendWith(MockBukkitExtension.class)
class MessageTargetReceivedAnyMessageMatcherTest extends AbstractMatcherTest
{

	@MockBukkitInject
	ServerMock serverMock;
	private PlayerMock messageTarget;
	private static final String MESSAGE = "Hello world!";

	@BeforeEach
	void setUp()
	{
		this.messageTarget = serverMock.addPlayer();
	}

	@Test
	void receivedAny()
	{
		messageTarget.sendMessage(MESSAGE);
		assertMatches(hasReceivedAny(), messageTarget);
	}

	@Test
	void notReceivedAny()
	{
		assertDoesNotMatch(hasReceivedAny(), messageTarget);
	}

	@Test
	void description()
	{
		assertDescription("to receive any message", hasReceivedAny());
	}

	@Test
	void nullSafe()
	{
		testIsNullSafe();
	}

	@Test
	void unknownTypeSafe()
	{
		testCopesWithUnknownTypes();
	}

	@Override
	protected Matcher<?> createMatcher()
	{
		return hasReceivedAny();
	}

}
