package be.seeseemelk.mockbukkit.command;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import org.jetbrains.annotations.NotNull;

@FunctionalInterface
public interface MessageTarget
{
	/**
	 * Returns the next message that was sent to the target.
	 * @return The next message sent to the target.
	 */
	String nextMessage();

	/**
	 * Asserts that a specific message was not received next by the message target.
	 * @param expected The message that should have been received by the target.
	 */
	default void assertSaid(@NotNull String expected)
	{
		String message = nextMessage();
		if (message == null)
		{
			fail("No more messages were sent");
		}
		else
		{
			assertEquals(expected, message);
		}
	}

	/**
	 * Asserts that more messages were received by the message target.
	 */
	default void assertNoMoreSaid()
	{
		if (nextMessage() != null)
		{
			fail("More messages were available");
		}
	}
}
