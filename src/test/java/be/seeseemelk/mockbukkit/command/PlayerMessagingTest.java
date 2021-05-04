package be.seeseemelk.mockbukkit.command;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertThrows;

import be.seeseemelk.mockbukkit.MockBukkit;
import be.seeseemelk.mockbukkit.ServerMock;
import be.seeseemelk.mockbukkit.entity.PlayerMock;
import net.md_5.bungee.api.chat.TextComponent;

class PlayerMessagingTest
{

	private ServerMock server;
	private PlayerMock sender;

	@BeforeEach
	public void setUp()
	{
		server = MockBukkit.mock();
		sender = server.addPlayer();
	}

	@AfterEach
	public void tearDown()
	{
		MockBukkit.unmock();
	}

	@Test
	void assertSaid_CorrectMessage_spigot_api_DoesNotAssert()
	{
		sender.spigot().sendMessage(TextComponent.fromLegacyText("Spigot message"));
		sender.assertSaid("Spigot message");
	}

	@Test
	void assertSaid_WrongMessage_spigot_api_Asserts()
	{
		sender.sendMessage("Spigot message");
		assertThrows(AssertionError.class, () -> sender.assertSaid("Some other message"));
	}
}
