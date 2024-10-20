package org.mockbukkit.mockbukkit.exception;

import org.jetbrains.annotations.NotNull;

/**
 * Thrown when MockBukkit fails find a requested class from a plugin.
 */
public class PluginClassNotFoundException extends RuntimeException
{

	/**
	 * Constructs a new runtime exception with the specified cause and a detail message of (cause==null ? null : cause.toString())
	 *
	 * @param cause The cause of the exception.
	 */
	public PluginClassNotFoundException(@NotNull Throwable cause)
	{
		super(cause);
	}

}
