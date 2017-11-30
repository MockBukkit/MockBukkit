package be.seeseemelk.bukkitmock;

import org.bukkit.Bukkit;

public class BukkitMock
{
	/**
	 * Start mocking the <code>Bukkit</code> singleton.
	 */
	public static void mock()
	{
		ServerMock mock = new ServerMock();
		Bukkit.setServer(mock);
	}
}
