package org.mockbukkit.mockbukkit.conversations;

import org.mockbukkit.mockbukkit.MockBukkit;
import org.mockbukkit.mockbukkit.ServerMock;
import org.mockbukkit.mockbukkit.plugin.TestPlugin;
import org.mockbukkit.mockbukkit.entity.PlayerMock;
import org.bukkit.conversations.Conversation;
import org.bukkit.conversations.ConversationContext;
import org.bukkit.conversations.MessagePrompt;
import org.bukkit.conversations.Prompt;
import org.bukkit.conversations.StringPrompt;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ConversationTrackerTest
{

	private final ConversationTracker tracker = new ConversationTracker();
	private final FirstPrompt firstPrompt = new FirstPrompt();

	private ServerMock serverMock;
	private Plugin plugin;

	private Player player;
	private Conversation conversation;

	@BeforeEach
	void setUp()
	{
		serverMock = MockBukkit.mock();
		plugin = MockBukkit.load(TestPlugin.class);
		player = new PlayerMock(serverMock, "test");
		conversation = new Conversation(plugin, player, firstPrompt);
	}

	@AfterEach
	void tearDown()
	{
		MockBukkit.unmock();
	}

	@Test
	void simpleConversationTest()
	{
		assertFalse(tracker.isConversing());
		assertEquals(Conversation.ConversationState.UNSTARTED, conversation.getState());

		assertTrue(tracker.beginConversation(conversation));
		assertTrue(tracker.isConversing());
		assertEquals(Conversation.ConversationState.STARTED, conversation.getState());

		tracker.acceptConversationInput("First prompt input");
		assertTrue(tracker.isConversing());
		assertEquals(Conversation.ConversationState.ABANDONED, conversation.getState());
	}

	private class FirstPrompt extends StringPrompt
	{


		@Override
		public @NotNull String getPromptText(@NotNull ConversationContext context)
		{
			return "First prompt text";
		}

		@Override
		public @Nullable Prompt acceptInput(@NotNull ConversationContext context, @Nullable String input)
		{
			assertEquals("First prompt input", input);
			context.setSessionData("data", 10);
			return new SecondPrompt();
		}

	}

	private class SecondPrompt extends MessagePrompt
	{

		@Override
		protected @Nullable Prompt getNextPrompt(@NotNull ConversationContext context)
		{
			return Prompt.END_OF_CONVERSATION;
		}

		@Override
		public @NotNull String getPromptText(@NotNull ConversationContext context)
		{
			// Assert that session data passes from one prompt to the next
			assertEquals(10, context.getSessionData("data"));
			return "SecondPrompt";
		}

	}
}
