package org.mockbukkit.mockbukkit.exception;

import org.jetbrains.annotations.NotNull;

/**
 * Thrown when MockBukkit fails to load internal data.
 */
public class InternalDataLoadException extends RuntimeException
{

	/**
	 * Constructs a new runtime exception with the specified detail message.
	 *
	 * @param message The detail message.
	 */
	public InternalDataLoadException(String message)
	{
		super(message);
	}

	/**
	 * Constructs a new runtime exception with the specified cause and a detail message of (cause==null ? null : cause.toString())
	 *
	 * @param cause The cause of the exception.
	 */
	public InternalDataLoadException(@NotNull Throwable cause)
	{
		super(cause);
	}

}
