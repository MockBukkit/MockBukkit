package be.seeseemelk.mockbukkit.command;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.format.TextColor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ConsoleCommandSenderMockTest
{

	private ConsoleCommandSenderMock sender;

	@BeforeEach
	void setUp() throws Exception
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
		sender.sendMessage("Hello", "world");
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

	@Test
	void assertSaid_WrongMessage_Asserts()
	{
		sender.sendMessage("My message");
		assertThrows(AssertionError.class, () -> sender.assertSaid("Some other message"));
	}

	@Test
	void assertSaid_NoMessages_Asserts()
	{
		assertThrows(AssertionError.class, () -> sender.assertSaid("A message"));
	}

	@Test
	void assertNoMore_NoMessages_DoesNotAssert()
	{
		sender.assertNoMoreSaid();
	}

	@Test
	void assertNoMore_MoreMessages_Asserts()
	{
		sender.sendMessage("Some message");
		assertThrows(AssertionError.class, () -> sender.assertNoMoreSaid());
	}

	@Test
	void sendMessage_StoredAsComponent()
	{
		TextComponent comp = Component.text().content("hi").color(TextColor.color(11141120)).build();
		sender.sendMessage(comp);
		sender.assertSaid(comp);
	}

}
