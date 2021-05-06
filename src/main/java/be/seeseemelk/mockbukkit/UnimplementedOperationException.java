package be.seeseemelk.mockbukkit;

import org.jetbrains.annotations.NotNull;
import org.opentest4j.TestAbortedException;

/**
 * Sometimes your code may use a method that is not yet implemented in MockBukkit. When this happens {@link MockBukkit}
 * will, instead of returning placeholder values, throw an {@link UnimplementedOperationException}.
 * <p>
 * This is a {@link TestAbortedException} and causes your Test to be skipped instead of just failing.
 *
 * @author seeseemelk
 *
 */
public class UnimplementedOperationException extends TestAbortedException
{
	private static final long serialVersionUID = 1L;

	public UnimplementedOperationException()
	{
		this("Not implemented");
	}

	public UnimplementedOperationException(@NotNull String message)
	{
		super(message);
	}
}
