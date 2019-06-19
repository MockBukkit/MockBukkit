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
	public void getMessage_NoMessages_Null()
	{
		assertNull(sender.nextMessage());
	}
	
	@Test
	public void sendMessageVararg_SomeStrings_StringsInRightOrder()
	{
		sender.sendMessage(new String[]{"Hello", "world"});
		sender.assertSaid("Hello");
		sender.assertSaid("world");
	}

	@Test
	public void assertSaid_CorrectMessage_DoesNotAssert()
	{
		sender.sendMessage("A hello world");
		sender.assertSaid("A hello world");
	}
	
	@Test(expected = AssertionError.class)
	public void assertSaid_WrongMessage_Asserts()
	{
		sender.sendMessage("My message");
		sender.assertSaid("Some other message");
	}
	
	@Test(expected = AssertionError.class)
	public void assertSaid_NoMessages_Asserts()
	{
		sender.assertSaid("A message");
	}
	
	@Test
	public void assertNoMore_NoMessages_DoesNotAssert()
	{
		sender.assertNoMoreSaid();
	}
	
	@Test(expected = AssertionError.class)
	public void assertNoMore_MoreMessages_Asserts()
	{
		sender.sendMessage("Some message");
		sender.assertNoMoreSaid();
	}
}


















