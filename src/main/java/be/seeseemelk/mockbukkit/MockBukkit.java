package be.seeseemelk.mockbukkit;

import org.bukkit.Bukkit;

public class MockBukkit
{
	private static ServerMock mock = null;
	
	/**
	 * Start mocking the <code>Bukkit</code> singleton.
	 */
	public static void mock()
	{
		if (mock == null)
		{
			mock = new ServerMock();
			Bukkit.setServer(mock);
		}
		else
		{
			throw new UnsupportedOperationException("Already mocking");
		}
	}
	
	/**
	 * Get the mock server instance.
	 * @return The {@link ServerMock} instance or {@code null} if none is set up yet.
	 */
	public static ServerMock getMock()
	{
		return mock;
	}
}
