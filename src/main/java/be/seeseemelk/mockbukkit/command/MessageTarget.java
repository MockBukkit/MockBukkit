package be.seeseemelk.mockbukkit.command;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

/**
 * Represents an object that can receive messages.
 */
@FunctionalInterface
public interface MessageTarget
{

	/**
	 * Returns the next message that was sent to the target.
	 *
	 * @return The next message sent to the target.
	 */
	@Nullable
	Component nextComponentMessage();

	/**
	 * Returns the next message that was sent to the target.
	 *
	 * @return The next message sent to the target.
	 */
	default @Nullable String nextMessage()
	{
		Component comp = nextComponentMessage();
		if (comp == null)
		{
			return null;
		}
		return LegacyComponentSerializer.legacySection().serialize(comp);
	}

	/**
	 * Asserts that a specific message was not received next by the message target.
	 *
	 * @param expected The message that should have been received by the target.
	 */
	default void assertSaid(@NotNull Component expected)
	{
		Component comp = nextComponentMessage();
		if (comp == null)
		{
			fail("No more messages were sent");
		}
		else
		{
			assertEquals(expected, comp);
		}
	}

	/**
	 * Asserts that a specific message was not received next by the message target.
	 *
	 * @param expected The message that should have been received by the target.
	 */
	default void assertSaid(@NotNull String expected)
	{
		assertSaid(LegacyComponentSerializer.legacySection().deserialize(expected));
	}

	/**
	 * Asserts that more messages were received by the message target.
	 */
	default void assertNoMoreSaid()
	{
		if (nextComponentMessage() != null)
		{
			fail("More messages were available");
		}
	}

}
