package org.mockbukkit.mockbukkit.exception;

import org.jetbrains.annotations.NotNull;

/**
 * Thrown when an internal IO operation requested by a plugin fails.
 */
public class PluginIOException extends RuntimeException
{

	/**
	 * Constructs a new runtime exception with the specified cause and a detail message of (cause==null ? null : cause.toString())
	 *
	 * @param cause The cause of the exception.
	 */
	public PluginIOException(@NotNull Throwable cause)
	{
		super(cause);
	}

}
