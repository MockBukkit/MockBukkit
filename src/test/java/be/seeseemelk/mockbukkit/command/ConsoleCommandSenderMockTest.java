package be.seeseemelk.mockbukkit.command;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ConsoleCommandSenderMockTest
{
	private ConsoleCommandSenderMock sender;

	@BeforeEach
	public void setUp() throws Exception
	{
		sender = new ConsoleCommandSenderMock();
	}

	@Test
	void getMessage_SomeString_SameString()
	{
		sender.sendMessage("Hello");
		sender.sendMessage("Other");
		assertEquals("Hello", sender.nextMessage());
		assertEquals("Other", sender.nextMessage());
	}

	@Test
	void getMessage_NoMessages_Null()
	{
		assertNull(sender.nextMessage());
	}

	@Test
	void sendMessageVararg_SomeStrings_StringsInRightOrder()
	{
		sender.sendMessage(new String[] {"Hello", "world"});
		sender.assertSaid("Hello");
		sender.assertSaid("world");
	}

	@Test
	void getName_IsConsole()
	{
		assertEquals("CONSOLE", sender.getName());
	}

	@Test
	void assertIsOp()
	{
		assertTrue(sender.isOp());
	}

	@Test
	void assertSaid_CorrectMessage_DoesNotAssert()
	{
		sender.sendMessage("A hello world");
		sender.assertSaid("A hello world");
	}

	@Test(expected = AssertionError.class)
	void assertSaid_WrongMessage_Asserts()
	{
		sender.sendMessage("My message");
		sender.assertSaid("Some other message");
	}

	@Test(expected = AssertionError.class)
	void assertSaid_NoMessages_Asserts()
	{
		sender.assertSaid("A message");
	}

	@Test
	void assertNoMore_NoMessages_DoesNotAssert()
	{
		sender.assertNoMoreSaid();
	}

	@Test(expected = AssertionError.class)
	void assertNoMore_MoreMessages_Asserts()
	{
		sender.sendMessage("Some message");
		sender.assertNoMoreSaid();
	}
}
