package org.mockbukkit.mockbukkit;

import com.google.common.base.Preconditions;
import org.bukkit.Bukkit;

/**
 * A class used to prevent synchronous-only methods from being run asynchronously.
 */
public class AsyncCatcher
{

	private AsyncCatcher()
	{
		throw new UnsupportedOperationException("Utility class");
	}

	/**
	 * Throws an {@link IllegalStateException} if not called from the primary thread.
	 *
	 * @param reason The reason for the exception.
	 */
	public static void catchOp(String reason)
	{
		Preconditions.checkState(Bukkit.getServer().isPrimaryThread(), "Asynchronous %s!", reason);
	}

}
