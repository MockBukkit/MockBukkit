package org.mockbukkit.mockbukkit.exception;

import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;
import org.opentest4j.TestAbortedException;

import java.io.Serial;

/**
 * Exception thrown after a failure of a reflective access, usually meaning the MockBukkit`s implementation has to be updated.
 * <p>
 * This is a {@link TestAbortedException} and causes your Test to be skipped instead of just failing.
 *
 * @author NeumimTo
 */
public class ReflectionAccessException extends TestAbortedException
{

	@Serial
	private static final long serialVersionUID = 1L;

	/**
	 * Constructs a new exception with the provided message.
	 *
	 * @param message The message.
	 */
	@ApiStatus.Internal
	public ReflectionAccessException(@NotNull String message)
	{
		super(message);
	}

}
