package org.mockbukkit.mockbukkit.exception;

import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;
import org.mockbukkit.mockbukkit.MockBukkit;
import org.opentest4j.TestAbortedException;

import java.io.Serial;

/**
 * Sometimes your code may use a method that is not yet implemented in MockBukkit. When this happens {@link MockBukkit}
 * will, instead of returning placeholder values or failing your test, throw an {@link UnimplementedOperationException}.
 * <p>
 * This is a {@link TestAbortedException} and causes your Test to be skipped instead of just failing.
 *
 * @author seeseemelk
 */
public class UnimplementedOperationException extends TestAbortedException
{

	@Serial
	private static final long serialVersionUID = 1L;

	/**
	 * Constructs a new  with a default message.
	 */
	@ApiStatus.Internal
	public UnimplementedOperationException()
	{
		this("Not implemented");
	}

	/**
	 * Constructs a new  with the provided message.
	 *
	 * @param message The message.
	 */
	@ApiStatus.Internal
	public UnimplementedOperationException(@NotNull String message)
	{
		super(message);
	}

}
