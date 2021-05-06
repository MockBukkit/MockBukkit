package be.seeseemelk.mockbukkit.command;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

class CommandResultTest
{
	@Test
	void hasSucceeded_Succeeded_True()
	{
		CommandResult result = new CommandResult(true, null);
		assertTrue(result.hasSucceeded());
	}

	@Test
	void hasSucceeded_Failed_False()
	{
		CommandResult result = new CommandResult(false, null);
		assertFalse(result.hasSucceeded());
	}

	@Test
	void assertSucceed_Succeeded_DoesNotAssert()
	{
		CommandResult result = new CommandResult(true, null);;
		result.assertSucceeded();
	}

	@Test
	void assertSucceed_Failed_Asserts()
	{
		CommandResult result = new CommandResult(false, null);;
		assertThrows(AssertionError.class, () -> result.assertSucceeded());
	}

	@Test
	void assertFailed_Succeeded_Asserts()
	{
		CommandResult result = new CommandResult(true, null);;
		assertThrows(AssertionError.class, () -> result.assertFailed());
	}

	@Test
	void assertFailed_Failed_DoesNotAssert()
	{
		CommandResult result = new CommandResult(false, null);;
		result.assertFailed();
	}

	@Test
	void assertResponse_CorrectResponse_DoesNotAssert()
	{
		ConsoleCommandSenderMock sender = new ConsoleCommandSenderMock();
		sender.sendMessage("Hello world");
		CommandResult result = new CommandResult(true, sender);
		result.assertResponse("Hello world");
	}

	@Test
	void assertResponse_WrongResponse_Asserts()
	{
		ConsoleCommandSenderMock sender = new ConsoleCommandSenderMock();
		sender.sendMessage("Hello world");
		CommandResult result = new CommandResult(true, sender);
		assertThrows(AssertionError.class, () -> result.assertResponse("world Hello"));
	}

	@Test
	void assertResponse_WrongFormattedResponse_Asserts()
	{
		ConsoleCommandSenderMock sender = new ConsoleCommandSenderMock();
		sender.sendMessage("Hello 5 world");
		CommandResult result = new CommandResult(true, sender);
		assertThrows(AssertionError.class, () -> result.assertResponse("Hello %d world", 6));
	}

	@Test
	void assertResponse_NoMessages_Asserts()
	{
		ConsoleCommandSenderMock sender = new ConsoleCommandSenderMock();
		CommandResult result = new CommandResult(true, sender);
		assertThrows(AssertionError.class, () -> result.assertResponse("Hello world"));
	}

	@Test
	void assertNoResponse_NoMoreMessage_DoesNotAssert()
	{
		ConsoleCommandSenderMock sender = new ConsoleCommandSenderMock();
		CommandResult result = new CommandResult(true, sender);
		result.assertNoResponse();
	}

	@Test
	void assertNoResponse_MoreMessage_Asserts()
	{
		ConsoleCommandSenderMock sender = new ConsoleCommandSenderMock();
		sender.sendMessage("More hello world");
		CommandResult result = new CommandResult(true, sender);
		assertThrows(AssertionError.class, () -> result.assertNoResponse());
	}
}
