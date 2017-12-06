package be.seeseemelk.mockbukkit;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assume.assumeNotNull;

import org.bukkit.Bukkit;
import org.junit.Before;
import org.junit.Test;

public class MockBukkitTest
{
	@Before
	public void init()
	{
		MockBukkit.setServerInstanceToNull();
	}
	
	@Test
	public void setServerInstanceToNull()
	{
		ServerMock server = new ServerMock();
		Bukkit.setServer(server);
		assumeNotNull(Bukkit.getServer());
		MockBukkit.setServerInstanceToNull();
		assertNull(Bukkit.getServer());
	}
	
	@Test
	public void mock()
	{
		ServerMock server = MockBukkit.mock();
		assertNotNull(server);
		assertEquals(server, MockBukkit.getMock());
	}
}
