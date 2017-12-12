package be.seeseemelk.mockbukkit.command;

import static org.junit.Assert.*;

import org.junit.Test;

public class CommandResultTest
{
	@Test
	public void hasSucceeded_Succeeded_True()
	{
		CommandResult result = new CommandResult(true, null);
		assertTrue(result.hasSucceeded());
	}
	
	@Test
	public void hasSucceeded_Failed_False()
	{
		CommandResult result = new CommandResult(false, null);
		assertFalse(result.hasSucceeded());
	}
	
	@Test
	public void assertSucceed_Succeeded_DoesNotAssert()
	{
		CommandResult result = new CommandResult(true, null);;
		result.assertSucceeded();
	}
	
	@Test(expected = AssertionError.class)
	public void assertSucceed_Failed_Asserts()
	{
		CommandResult result = new CommandResult(false, null);;
		result.assertSucceeded();
	}
	
	@Test(expected = AssertionError.class)
	public void assertFailed_Succeeded_Asserts()
	{
		CommandResult result = new CommandResult(true, null);;
		result.assertFailed();
	}
	
	@Test
	public void assertFailed_Failed_DoesNotAssert()
	{
		CommandResult result = new CommandResult(false, null);;
		result.assertFailed();
	}
	
	@Test
	public void assertResponse_CorrectResponse_DoesNotAssert()
	{
		ConsoleCommandSenderMock sender = new ConsoleCommandSenderMock();
		sender.sendMessage("Hello world");
		CommandResult result = new CommandResult(true, sender);
		result.assertResponse("Hello world");
	}
	
	@Test(expected = AssertionError.class)
	public void assertResponse_WrongResponse_Asserts()
	{
		ConsoleCommandSenderMock sender = new ConsoleCommandSenderMock();
		sender.sendMessage("Hello world");
		CommandResult result = new CommandResult(true, sender);
		result.assertResponse("world Hello");
	}
	
	@Test(expected = AssertionError.class)
	public void assertResponse_WrongFormattedResponse_Asserts()
	{
		ConsoleCommandSenderMock sender = new ConsoleCommandSenderMock();
		sender.sendMessage("Hello 5 world");
		CommandResult result = new CommandResult(true, sender);
		result.assertResponse("Hello %d world", 6);
	}
	
	@Test(expected = AssertionError.class)
	public void assertResponse_NoMessages_Asserts()
	{
		ConsoleCommandSenderMock sender = new ConsoleCommandSenderMock();
		CommandResult result = new CommandResult(true, sender);
		result.assertResponse("Hello world");
	}
	
	@Test
	public void assertNoResponse_NoMoreMessage_DoesNotAssert()
	{
		ConsoleCommandSenderMock sender = new ConsoleCommandSenderMock();
		CommandResult result = new CommandResult(true, sender);
		result.assertNoResponse();
	}
	
	@Test(expected = AssertionError.class)
	public void assertNoResponse_MoreMessage_Asserts()
	{
		ConsoleCommandSenderMock sender = new ConsoleCommandSenderMock();
		sender.sendMessage("More hello world");
		CommandResult result = new CommandResult(true, sender);
		result.assertNoResponse();
	}
}



























