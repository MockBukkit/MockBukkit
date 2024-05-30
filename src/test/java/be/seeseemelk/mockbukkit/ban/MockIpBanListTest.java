package be.seeseemelk.mockbukkit.ban;

import be.seeseemelk.mockbukkit.MockBukkitExtension;
import be.seeseemelk.mockbukkit.MockBukkitInject;
import be.seeseemelk.mockbukkit.ServerMock;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(MockBukkitExtension.class)
class MockIpBanListTest
{

	@MockBukkitInject
	ServerMock server;
	MockIpBanList banList;

	@BeforeEach
	void setUp()
	{
		banList = new MockIpBanList();
	}

	@Test
	void testAddBan() throws UnknownHostException
	{
		banList.addBan(InetAddress.getByName("127.0.0.1"), "reason", (Date) null, "source");

		assertTrue(banList.getEntries().stream()
				.anyMatch(banEntry -> banEntry.getBanTarget().getHostAddress().equals("127.0.0.1")));
	}

	@Test
	void testAddBanString()
	{
		banList.addBan("127.0.0.1", "reason", null, "source");

		assertTrue(banList.getEntries().stream()
				.anyMatch(banEntry -> banEntry.getBanTarget().getHostAddress().equals("127.0.0.1")));
	}

	@Test
	void testGetEntries()
	{
		assertTrue(banList.getEntries().isEmpty());
		banList.addBan("127.0.0.1", "reason", null, "source");
		assertEquals(1, banList.getEntries().size());
		assertTrue(banList.getEntries().stream()
				.anyMatch(banEntry -> banEntry.getBanTarget().getHostAddress().equals("127.0.0.1")));
	}

	@Test
	@SuppressWarnings("deprecation")
	void testGetBanEntries()
	{
		assertTrue(banList.getBanEntries().isEmpty());
		banList.addBan("127.0.0.1", "reason", null, "source");
		assertEquals(1, banList.getBanEntries().size());
		assertTrue(banList.getBanEntries().stream()
				.anyMatch(banEntry -> ((InetAddress) banEntry.getBanTarget()).getHostAddress().equals("127.0.0.1")));
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
	void testGetBanEntryStringNullThrows()
	{
		NullPointerException nullPointerException = assertThrows(NullPointerException.class, () -> {
			banList.getBanEntry((String) null);
		});

		assertEquals("Target cannot be null", nullPointerException.getMessage());
	}

	@Test
	void testGetBanEntryInetAddressNullThrows()
	{
		NullPointerException nullPointerException = assertThrows(NullPointerException.class, () -> {
			banList.getBanEntry((InetAddress) null);
		});

		assertEquals("Target cannot be null", nullPointerException.getMessage());
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

	@Test
	void testPardonStringNull()
	{
		NullPointerException nullPointerException = assertThrows(NullPointerException.class, () -> {
			banList.pardon((String) null);
		});

		assertEquals("Target cannot be null", nullPointerException.getMessage());

	}

	@Test
	void testPardonInetAddressNull()
	{
		NullPointerException nullPointerException = assertThrows(NullPointerException.class, () -> {
			banList.pardon((InetAddress) null);
		});

		assertEquals("Target cannot be null", nullPointerException.getMessage());
	}

}
