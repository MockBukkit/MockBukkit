package org.mockbukkit.mockbukkit.exception;

import org.jetbrains.annotations.NotNull;

/**
 * Thrown when item serialization/deserialization fails for any reason.
 */
public class ItemSerializationException extends RuntimeException
{

	/**
	 * Constructs a new runtime exception with the specified cause and a detail message of (cause==null ? null : cause.toString())
	 *
	 * @param cause The cause of the exception.
	 */
	public ItemSerializationException(@NotNull Throwable cause)
	{
		super(cause);
	}

}
