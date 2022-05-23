package be.seeseemelk.mockbukkit;

import org.bukkit.Bukkit;

public class AsyncCatcher
{

	public static void catchOp(String reason)
	{
		if (Bukkit.getServer().isPrimaryThread())
			return;
		throw new IllegalStateException("Asynchronous " + reason + "!");
	}

}
