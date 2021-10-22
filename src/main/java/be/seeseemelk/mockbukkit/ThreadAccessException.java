package be.seeseemelk.mockbukkit;

public class ThreadAccessException extends RuntimeException
{
	private static final long serialVersionUID = 4506968718169636022L;

	public ThreadAccessException()
	{
		super();
	}

	public ThreadAccessException(String message)
	{
		super(message);
	}

	public ThreadAccessException(String message, Exception exception)
	{
		super(message, exception);
	}
}
