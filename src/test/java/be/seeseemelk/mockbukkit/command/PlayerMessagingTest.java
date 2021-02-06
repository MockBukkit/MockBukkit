package be.seeseemelk.mockbukkit.command;

import be.seeseemelk.mockbukkit.MockBukkit;
import be.seeseemelk.mockbukkit.ServerMock;
import be.seeseemelk.mockbukkit.entity.PlayerMock;
import net.md_5.bungee.api.chat.TextComponent;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class PlayerMessagingTest
{

	private ServerMock server;
	private PlayerMock sender;

	@Before
	public void setUp()
	{
		server = MockBukkit.mock();
		sender = server.addPlayer();
	}

	@After
	public void tearDown()
	{
		MockBukkit.unmock();
	}

	@Test
	public void assertSaid_CorrectMessage_spigot_api_DoesNotAssert()
	{
		sender.spigot().sendMessage(TextComponent.fromLegacyText("Spigot message"));
		sender.assertSaid("Spigot message");
	}

	@Test(expected = AssertionError.class)
	public void assertSaid_WrongMessage_spigot_api_Asserts()
	{
		sender.sendMessage("Spigot message");
		sender.assertSaid("Some other message");
	}
}
