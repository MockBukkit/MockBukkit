package org.mockbukkit.mockbukkit.exception;

/**
 * Thrown when an exception occurs while unmocking.
 */
public class UnmockException extends RuntimeException
{

	/**
	 * Constructs a new runtime exception with the specified cause and a detail message of (cause==null ? null : cause.toString())
	 *
	 * @param cause The cause of the exception.
	 */
	public UnmockException(Throwable cause)
	{
		super(cause);
	}

}
