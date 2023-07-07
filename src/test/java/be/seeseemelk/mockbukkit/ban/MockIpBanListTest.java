package be.seeseemelk.mockbukkit.ban;

import be.seeseemelk.mockbukkit.MockBukkitExtension;
import be.seeseemelk.mockbukkit.MockBukkitInject;
import be.seeseemelk.mockbukkit.ServerMock;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.net.InetAddress;
import java.net.UnknownHostException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(MockBukkitExtension.class)
public class MockIpBanListTest
{

	@MockBukkitInject
	ServerMock server;
	MockIpBanList banList;

	@BeforeEach
	public void setUp()
	{
		banList = new MockIpBanList();
	}

	@Test
	void testAddBan() throws UnknownHostException
	{
		banList.addBan(
				InetAddress.getByName("127.0.0.1"),
				"reason",
				null,
				"source"
		);

		banList.getEntries().stream().anyMatch(banEntry ->
		{
			return banEntry.getBanTarget().getHostAddress().equals("127.0.0.1)");
		});
	}

	@Test
	void testAddBanString()
	{
		banList.addBan("127.0.0.1", "reason", null, "source");

		assertTrue(banList.getEntries().stream().anyMatch(banEntry ->
		{
			return banEntry.getBanTarget().getHostAddress().equals("127.0.0.1");
		}));
	}

	@Test
	void testGetEntries()
	{
		assertTrue(banList.getEntries().isEmpty());
		banList.addBan("127.0.0.1", "reason", null, "source");
		assertEquals(1, banList.getEntries().size());
		assertTrue(banList.getEntries().stream().anyMatch(banEntry ->
		{
			return banEntry.getBanTarget().getHostAddress().equals("127.0.0.1");
		}));
	}

	@Test
	void testGetBanEntries()
	{
		assertTrue(banList.getBanEntries().isEmpty());
		banList.addBan("127.0.0.1", "reason", null, "source");
		assertEquals(1, banList.getBanEntries().size());
		assertTrue(banList.getBanEntries().stream().anyMatch(banEntry ->
		{
			return ((InetAddress) banEntry.getBanTarget()).getHostAddress().equals("127.0.0.1");
		}));

	}

	@Test
	void testGetBanEntry()
	{
		banList.addBan("127.0.0.1", "reason", null, "source");
		assertNotNull(banList.getBanEntry("127.0.0.1"));
	}

	@Test
	void testGetBanEntryInetAddress() throws UnknownHostException
	{
		banList.addBan("127.0.0.1", "reason", null, "source");
		assertNotNull(banList.getBanEntry(InetAddress.getByName("127.0.0.1")));
	}

	@Test
	void testIsBannedString()
	{
		banList.addBan("127.0.0.1", "reason", null, "source");
		assertTrue(banList.isBanned("127.0.0.1"));
	}

	@Test
	void testIsBannedInetAddress() throws UnknownHostException
	{
		banList.addBan("127.0.0.1", "reason", null, "source");
		assertTrue(banList.isBanned(InetAddress.getByName("127.0.0.1")));
	}

	@Test
	void testPardonString() throws UnknownHostException
	{
		banList.addBan("127.0.0.1", "reason", null, "source");
		banList.pardon("127.0.0.1");
		assertFalse(banList.isBanned(InetAddress.getByName("127.0.0.1")));
	}

	@Test
	void testPardonInetAddress() throws UnknownHostException
	{
		banList.addBan("127.0.0.1", "reason", null, "source");
		banList.pardon(InetAddress.getByName("127.0.0.1"));
		assertFalse(banList.isBanned(InetAddress.getByName("127.0.0.1")));
	}

}
