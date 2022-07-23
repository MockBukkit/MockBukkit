package be.seeseemelk.mockbukkit.exception;

import org.jetbrains.annotations.NotNull;

import java.io.Serial;

/**
 * Thrown when an event handler throws a non-{@link RuntimeException}
 */
public class EventHandlerException extends RuntimeException
{

	@Serial
	private static final long serialVersionUID = 6093700474770834429L;

	public EventHandlerException(@NotNull Throwable cause)
	{
		super(cause);
	}

}
