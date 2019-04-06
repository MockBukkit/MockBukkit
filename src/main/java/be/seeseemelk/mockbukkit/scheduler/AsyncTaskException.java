package be.seeseemelk.mockbukkit.scheduler;

public class AsyncTaskException extends RuntimeException
{
	private static final long serialVersionUID = -4501059063243851677L;

	public AsyncTaskException(Exception e)
	{
		super(e);
	}
}
