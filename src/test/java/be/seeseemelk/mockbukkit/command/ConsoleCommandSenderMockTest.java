package be.seeseemelk.mockbukkit.command;

import be.seeseemelk.mockbukkit.MockBukkit;
import be.seeseemelk.mockbukkit.ServerMock;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.format.TextColor;
import net.md_5.bungee.api.chat.BaseComponent;
import org.bukkit.permissions.Permission;
import org.bukkit.permissions.PermissionAttachment;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertThrowsExactly;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ConsoleCommandSenderMockTest
{

	private ServerMock server;
	private ConsoleCommandSenderMock sender;

	@BeforeEach
	void setUp()
	{
		server = MockBukkit.mock();
		sender = server.getConsoleSender();
	}

	@AfterEach
	void teardown()
	{
		MockBukkit.unmock();
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
	void assertSetOp_ThrowsException()
	{
		assertThrowsExactly(UnsupportedOperationException.class, () -> sender.setOp(false));
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
	void sendMessage_NoMessage_ThrowsException()
	{
		assertThrowsExactly(NullPointerException.class, () -> sender.sendMessage((String) null));
	}

	@Test
	void addAttachment_True_Has()
	{
		sender.addAttachment(MockBukkit.createMockPlugin(), "test.permission", true);
		assertTrue(sender.hasPermission("test.permission"));
	}

	@Test
	void addAttachment_False_DoesntHave()
	{
		sender.addAttachment(MockBukkit.createMockPlugin(), "test.permission", false);
		assertFalse(sender.hasPermission("test.permission"));
	}

	@Test
	void addAttachment_RemovedAfterTicks()
	{
		sender.addAttachment(MockBukkit.createMockPlugin(), "test.permission", true, 10);
		assertTrue(sender.isPermissionSet("test.permission"));
		server.getScheduler().performTicks(9);
		assertTrue(sender.isPermissionSet("test.permission"));
		server.getScheduler().performTicks(10);
		assertFalse(sender.isPermissionSet("test.permission"));
	}

	@Test
	void removeAttachment_RemovesAttachment()
	{
		PermissionAttachment att = sender.addAttachment(MockBukkit.createMockPlugin(), "test.permission", false);
		assertFalse(sender.hasPermission("test.permission"));
		sender.removeAttachment(att);
		assertTrue(sender.hasPermission("test.permission"));
	}

	@Test
	void isPermissionSet_String_IsSet_True()
	{
		sender.addAttachment(MockBukkit.createMockPlugin(), "test.permission", true);
		assertTrue(sender.isPermissionSet("test.permission"));
	}

	@Test
	void isPermissionSet_String_IsntSet_False()
	{
		assertFalse(sender.isPermissionSet("test.permission"));
	}

	@Test
	void isPermissionSet_Permission_IsSet_True()
	{
		sender.addAttachment(MockBukkit.createMockPlugin(), "test.permission", true);
		assertTrue(sender.isPermissionSet(new Permission("test.permission")));
	}

	@Test
	void isPermissionSet_Permission_IsntSet_False()
	{
		assertFalse(sender.isPermissionSet(new Permission("test.permission")));
	}

	@Test
	void hasPermission_String_SetTrue_True()
	{
		sender.addAttachment(MockBukkit.createMockPlugin(), "test.permission", true);
		assertTrue(sender.hasPermission("test.permission"));
	}

	@Test
	void hasPermission_String_SetFalse_True()
	{
		sender.addAttachment(MockBukkit.createMockPlugin(), "test.permission", false);
		assertFalse(sender.hasPermission("test.permission"));
	}

	@Test
	void hasPermission_String_NotSet_True()
	{
		assertTrue(sender.hasPermission("test.permission"));
	}

	@Test
	void hasPermission_Permission_SetTrue_True()
	{
		sender.addAttachment(MockBukkit.createMockPlugin(), "test.permission", true);
		assertTrue(sender.hasPermission(new Permission("test.permission")));
	}

	@Test
	void hasPermission_Permission_SetFalse_True()
	{
		sender.addAttachment(MockBukkit.createMockPlugin(), "test.permission", false);
		assertFalse(sender.hasPermission(new Permission("test.permission")));
	}

	@Test
	void hasPermission_Permission_NotSet_True()
	{
		assertTrue(sender.hasPermission(new Permission("test.permission")));
	}

	@Test
	void sendMessage_StoredAsComponent()
	{
		TextComponent comp = Component.text().content("hi").color(TextColor.color(11141120)).build();
		sender.sendMessage(comp);
		sender.assertSaid(comp);
	}

	@Test
	void spigot_sendMessage_SingleComponent()
	{
		sender.spigot().sendMessage(new net.md_5.bungee.api.chat.TextComponent("Howdy"));
		sender.assertSaid("Howdy");
		sender.assertNoMoreSaid();
	}

	@Test
	void spigot_sendMessage_SingleComponent_Null_ThrowsException()
	{
		assertThrowsExactly(NullPointerException.class, () -> sender.spigot().sendMessage((BaseComponent) null));
	}

	@Test
	void spigot_sendMessage_MultipleComponents()
	{
		sender.spigot().sendMessage(new net.md_5.bungee.api.chat.TextComponent("Hello,"),
				new net.md_5.bungee.api.chat.TextComponent("world!"));
		sender.assertSaid("Hello,world!");
		sender.assertNoMoreSaid();
	}

	@Test
	void spigot_sendMessage_MultipleComponents_Null_ThrowsException()
	{
		assertThrowsExactly(NullPointerException.class, () -> sender.spigot().sendMessage((BaseComponent[]) null));
	}

}
