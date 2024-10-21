package org.mockbukkit.mockbukkit.exception;

import java.io.Serial;

/**
 * Thrown when an asynchronous task throws an exception.
 */
public class AsyncTaskException extends RuntimeException
{

	@Serial
	private static final long serialVersionUID = -4501059063243851677L;

	/**
	 * Constructs a new {@link AsyncTaskException} with the specified cause and a detail message of (cause==null ? null : cause.toString())
	 *
	 * @param cause The exception thrown in the asynchronous task.
	 */
	public AsyncTaskException(Exception cause)
	{
		super(cause);
	}

}
