package org.mockbukkit.mockbukkit.exception;

import java.io.Serial;

public class UnimplementedOperationFailure extends RuntimeException
{

	@Serial
	private static final long serialVersionUID = 109379017L;

	public UnimplementedOperationFailure(String message)
	{
		super(message);
	}

}
