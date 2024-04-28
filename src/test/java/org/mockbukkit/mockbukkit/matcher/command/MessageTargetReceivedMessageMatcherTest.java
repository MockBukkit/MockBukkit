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

import static org.mockbukkit.mockbukkit.matcher.command.MessageTargetReceivedMessageMatcher.hasReceived;

@ExtendWith(MockBukkitExtension.class)
class MessageTargetReceivedMessageMatcherTest extends AbstractMatcherTest
{

	@MockBukkitInject
	private ServerMock serverMock;
	private PlayerMock messageTarget;
	private static final String MESSAGE = "Hello world!";

	@BeforeEach
	void setUp()
	{
		this.messageTarget = serverMock.addPlayer();
	}

	@Test
	void receivedMessage()
	{
		messageTarget.sendMessage(MESSAGE);
		assertMatches(hasReceived(MESSAGE), messageTarget);
	}

	@Test
	void notReceivedMessage_noMessage()
	{
		assertDoesNotMatch(hasReceived(MESSAGE), messageTarget);
	}

	@Test
	void notReceivedMessage_wrongMessage()
	{
		messageTarget.sendMessage("HELLO WORLD!!");
		assertDoesNotMatch(hasReceived(MESSAGE), messageTarget);
	}

	@Test
	void nullCompatible()
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
		return hasReceived(MESSAGE);
	}

}
