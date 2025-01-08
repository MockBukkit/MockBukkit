package org.mockbukkit.mockbukkit.exception;

import org.jetbrains.annotations.NotNull;

/**
 * Thrown when MockBukkit fails to initialize BlockState.
 */
public class BlockStateInitException extends RuntimeException
{

	/**
	 * Constructs a new runtime exception with the specified cause and a detail message.
	 *
	 * @param message The detail message.
	 * @param cause   The cause of the exception.
	 */
	public BlockStateInitException(String message, Throwable cause)
	{
		super(message, cause);
	}

	/**
	 * Constructs a new runtime exception with the specified cause and a detail message of (cause==null ? null : cause.toString())
	 *
	 * @param cause The cause of the exception.
	 */
	public BlockStateInitException(@NotNull Throwable cause)
	{
		super(cause);
	}

}
