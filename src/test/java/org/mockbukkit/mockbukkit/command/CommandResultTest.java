package org.mockbukkit.mockbukkit.command;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockbukkit.mockbukkit.MockBukkit;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockbukkit.mockbukkit.matcher.command.CommandResultAnyResponseMatcher.hasAnyResponse;
import static org.mockbukkit.mockbukkit.matcher.command.CommandResultAnyResponseMatcher.hasNoResponse;
import static org.mockbukkit.mockbukkit.matcher.command.CommandResultResponseMatcher.doesNotHaveResponse;
import static org.mockbukkit.mockbukkit.matcher.command.CommandResultResponseMatcher.hasResponse;
import static org.mockbukkit.mockbukkit.matcher.command.CommandResultSucceedMatcher.hasFailed;
import static org.mockbukkit.mockbukkit.matcher.command.CommandResultSucceedMatcher.hasSucceeded;

class CommandResultTest
{

	private MessageTarget target;

	@BeforeEach
	void setUp()
	{
		this.target = MockBukkit.mock().getConsoleSender();
	}

	@AfterEach
	void teardown()
	{
		MockBukkit.unmock();
	}

	@Test
	void hasSucceeded_Succeeded_True()
	{
		CommandResult result = new CommandResult(true, target);
		assertTrue(result.hasSucceeded());
	}

	@Test
	void hasSucceeded_Failed_False()
	{
		CommandResult result = new CommandResult(false, target);
		assertFalse(result.hasSucceeded());
	}

	@Test
	void assertSucceed_Succeeded_DoesNotAssert()
	{
		CommandResult result = new CommandResult(true, target);
		assertThat(result, hasSucceeded());
	}

	@Test
	void assertSucceed_Failed_Asserts()
	{
		CommandResult result = new CommandResult(false, target);
		assertThat(result, hasFailed());
	}

	@Test
	void assertFailed_Succeeded_Asserts()
	{
		CommandResult result = new CommandResult(true, target);
		assertThrows(AssertionError.class, result::assertFailed);
	}

	@Test
	void assertFailed_Failed_DoesNotAssert()
	{
		CommandResult result = new CommandResult(false, target);
		result.assertFailed();
	}

	@Test
	void assertResponse_CorrectResponse_DoesNotAssert()
	{
		ConsoleCommandSenderMock sender = new ConsoleCommandSenderMock();
		sender.sendMessage("Hello world");
		CommandResult result = new CommandResult(true, sender);
		assertThat(result, hasResponse("Hello world"));
	}

	@Test
	void assertResponse_WrongResponse_Asserts()
	{
		ConsoleCommandSenderMock sender = new ConsoleCommandSenderMock();
		sender.sendMessage("Hello world");
		CommandResult result = new CommandResult(true, sender);
		assertThat(result, doesNotHaveResponse("world Hello"));
	}

	@Test
	void assertResponse_WrongFormattedResponse_Asserts()
	{
		ConsoleCommandSenderMock sender = new ConsoleCommandSenderMock();
		sender.sendMessage("Hello 5 world");
		CommandResult result = new CommandResult(true, sender);
		assertThat(result, doesNotHaveResponse(String.format("Hello %d world", 6)));
	}

	@Test
	void assertResponse_NoMessages_Asserts()
	{
		ConsoleCommandSenderMock sender = new ConsoleCommandSenderMock();
		CommandResult result = new CommandResult(true, sender);
		assertThat(result, doesNotHaveResponse("Hello world"));
	}

	@Test
	void assertNoResponse_NoMoreMessage_DoesNotAssert()
	{
		ConsoleCommandSenderMock sender = new ConsoleCommandSenderMock();
		CommandResult result = new CommandResult(true, sender);
		assertThat(result, hasNoResponse());
	}

	@Test
	void assertNoResponse_MoreMessage_Asserts()
	{
		ConsoleCommandSenderMock sender = new ConsoleCommandSenderMock();
		sender.sendMessage("More hello world");
		CommandResult result = new CommandResult(true, sender);
		assertThat(result, hasAnyResponse());
	}

}
