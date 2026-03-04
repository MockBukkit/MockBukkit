package org.mockbukkit.mockbukkit.exception;

import org.jetbrains.annotations.NotNull;

import java.io.Serial;

/**
 * Thrown when an event handler throws a non-{@link RuntimeException}
 */
public class EventHandlerException extends RuntimeException
{

	@Serial
	private static final long serialVersionUID = 6093700474770834429L;

	/**
	 * Constructs a new runtime exception with the specified cause and a detail message of (cause==null ? null : cause.toString())
	 *
	 * @param cause The cause of the exception.
	 */
	public EventHandlerException(@NotNull Throwable cause)
	{
		super(cause);
	}

}
