package be.seeseemelk.mockbukkit.command;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

public class CommandResult
{
	private final boolean success;
	private final MessageTarget sender;

	public CommandResult(boolean success, MessageTarget sender)
	{
		this.success = success;
		this.sender = sender;
	}
	
	/**
	 * Check if the command executed successfully.
	 * @return {@code true} if the command executed successfully, {@code false} if a problem occured.
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
	 * @param message The message to check for.
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
	 * @param format The formatted message to check for.
	 * @param objects The objects to place into the formatted message.
	 */
	public void assertResponse(String format, Object... objects)
	{
		assertResponse(String.format(format, objects));
	}
	
	/**
	 * Asserts if more messages have been sent to the command sender. 
	 */
	public void assertNoResponse()
	{
		if (sender.nextMessage() != null)
		{
			fail("More messages");
		}
	}
}
