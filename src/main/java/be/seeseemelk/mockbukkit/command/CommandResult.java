package be.seeseemelk.mockbukkit.command;

import com.google.common.base.Preconditions;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

/**
 * Represents the result of a command invocation.
 */
public class CommandResult
{

	private final boolean success;
	private final @NotNull MessageTarget sender;

	/**
	 * Constructs a new {@link CommandResult} with the provided parameters.
	 *
	 * @param success Whether the command succeeded (returned true).
	 * @param sender  The message target who executed the command.
	 */
	@ApiStatus.Internal
	public CommandResult(boolean success, @NotNull MessageTarget sender)
	{
		Preconditions.checkNotNull(sender, "Sender cannot be null");
		this.success = success;
		this.sender = sender;
	}

	/**
	 * Check if the command executed successfully.
	 *
	 * @return {@code true} if the command executed successfully, {@code false} if a problem occurred.
	 */
	public boolean hasSucceeded()
	{
		return success;
	}

	/**
	 * Asserts if the returned code of the executed command is not {@code true}.
	 */
	public void assertSucceeded()
	{
		assertTrue(success);
	}

	/**
	 * Asserts if the returned code of the executed command is not {@code false}.
	 */
	public void assertFailed()
	{
		assertFalse(success);
	}

	/**
	 * Assets if the given message was not the next message send to the command sender.
	 *
	 * @param message The message to check for.
	 * @see MessageTarget#nextMessage()
	 */
	public void assertResponse(String message)
	{
		String received = sender.nextMessage();
		if (received != null)
		{
			assertEquals(message, received);
		}
		else
		{
			fail("No more messages");
		}
	}

	/**
	 * Asserts if a given formatted message was not the next message sent to the command sender.
	 *
	 * @param format  The formatted message to check for.
	 * @param objects The objects to place into the formatted message.
	 * @see #assertResponse(String)
	 * @see MessageTarget#nextMessage()
	 */
	public void assertResponse(@NotNull String format, Object... objects)
	{
		assertResponse(String.format(format, objects));
	}

	/**
	 * Asserts if more messages have been sent to the command sender.
	 *
	 * @see MessageTarget#nextMessage()
	 */
	public void assertNoResponse()
	{
		if (sender.nextMessage() != null)
		{
			fail("More messages");
		}
	}

}
