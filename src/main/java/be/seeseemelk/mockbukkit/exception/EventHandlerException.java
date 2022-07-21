package be.seeseemelk.mockbukkit.exception;

/**
 * Thrown when an event handler throws a non-{@link RuntimeException}
 */
public class EventHandlerException extends RuntimeException
{

	public EventHandlerException(Throwable cause)
	{
		super(cause);
	}

}
