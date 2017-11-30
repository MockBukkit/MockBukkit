package be.seeseemelk.mockbukkit;

import org.bukkit.Bukkit;

public class MockBukkit
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
