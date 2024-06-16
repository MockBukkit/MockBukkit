package org.mockbukkit.mockbukkit.exception;

import org.jetbrains.annotations.NotNull;

/**
 * Thrown when MockBukkit fails to load a plugin.
 */
public class PluginLoadException extends RuntimeException
{

	/**
	 * Constructs a new runtime exception with the specified detail message.
	 *
	 * @param message The detail message.
	 */
	public PluginLoadException(String message)
	{
		super(message);
	}

	/**
	 * Constructs a new runtime exception with the specified cause and a detail message.
	 *
	 * @param message The detail message.
	 * @param cause   The cause of the exception.
	 */
	public PluginLoadException(String message, Throwable cause)
	{
		super(message, cause);
	}

	/**
	 * Constructs a new runtime exception with the specified cause and a detail message of (cause==null ? null : cause.toString())
	 *
	 * @param cause The cause of the exception.
	 */
	public PluginLoadException(@NotNull Throwable cause)
	{
		super(cause);
	}

}
