package be.seeseemelk.mockbukkit.command;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class ConsoleCommandSenderMockTest
{
	private ConsoleCommandSenderMock sender;

	@Before
	public void setUp() throws Exception
	{
		sender = new ConsoleCommandSenderMock();
	}

	@Test
	public void getMessage_SomeString_SameString()
	{
		sender.sendMessage("Hello");
		sender.sendMessage("Other");
		assertEquals("Hello", sender.nextMessage());
		assertEquals("Other", sender.nextMessage());
	}

	@Test
	public void assertSaid_CorrectMessage_DoesNotAssert()
	{
		sender.sendMessage("A hello world");
		sender.assertSaid("A hello world");
	}
	
	@Test
	public void assertSaid_WrongMessage_Asserts()
	{
		sender.sendMessage("My message");
		sender.assertSaid("Some other message");
	}
}
