package be.seeseemelk.mockbukkit.command;

import static org.junit.jupiter.api.Assertions.assertFalse;
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

	@Test(expected = AssertionError.class)
	void assertSucceed_Failed_Asserts()
	{
		CommandResult result = new CommandResult(false, null);;
		result.assertSucceeded();
	}

	@Test(expected = AssertionError.class)
	void assertFailed_Succeeded_Asserts()
	{
		CommandResult result = new CommandResult(true, null);;
		result.assertFailed();
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

	@Test(expected = AssertionError.class)
	void assertResponse_WrongResponse_Asserts()
	{
		ConsoleCommandSenderMock sender = new ConsoleCommandSenderMock();
		sender.sendMessage("Hello world");
		CommandResult result = new CommandResult(true, sender);
		result.assertResponse("world Hello");
	}

	@Test(expected = AssertionError.class)
	void assertResponse_WrongFormattedResponse_Asserts()
	{
		ConsoleCommandSenderMock sender = new ConsoleCommandSenderMock();
		sender.sendMessage("Hello 5 world");
		CommandResult result = new CommandResult(true, sender);
		result.assertResponse("Hello %d world", 6);
	}

	@Test(expected = AssertionError.class)
	void assertResponse_NoMessages_Asserts()
	{
		ConsoleCommandSenderMock sender = new ConsoleCommandSenderMock();
		CommandResult result = new CommandResult(true, sender);
		result.assertResponse("Hello world");
	}

	@Test
	void assertNoResponse_NoMoreMessage_DoesNotAssert()
	{
		ConsoleCommandSenderMock sender = new ConsoleCommandSenderMock();
		CommandResult result = new CommandResult(true, sender);
		result.assertNoResponse();
	}

	@Test(expected = AssertionError.class)
	void assertNoResponse_MoreMessage_Asserts()
	{
		ConsoleCommandSenderMock sender = new ConsoleCommandSenderMock();
		sender.sendMessage("More hello world");
		CommandResult result = new CommandResult(true, sender);
		result.assertNoResponse();
	}
}
