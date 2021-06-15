package be.seeseemelk.mockbukkit;


import org.junit.internal.AssumptionViolatedException;

public class UnimplementedOperationException extends AssumptionViolatedException
{
	private static final long serialVersionUID = 1L;

	public UnimplementedOperationException()
	{
		super("Not implemented");
	}
	
	public UnimplementedOperationException(String message)
	{
		super(message);
	}
}
