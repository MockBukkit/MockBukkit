package be.seeseemelk.mockbukkit.ban;

import com.google.common.net.InetAddresses;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class MockIpBanEntryTest
{

	MockIpBanEntry entry;

	@BeforeEach
	void setUp() throws Exception
	{
		entry = new MockIpBanEntry("127.0.0.1", "reason", null, "source");
	}

	@Test
	void testGetTarget()
	{
		assertEquals("127.0.0.1", entry.getTarget());
	}

	@Test
	void testGetBanTarget()
	{
		assertEquals(InetAddresses.forString("127.0.0.1"), entry.getBanTarget());
	}

	@Test
	void testGetCreated()
	{
		Date created = new Date();
		assertTrue(created.getTime() >= entry.getCreated().getTime());
	}

	@Test
	void testSetCreated()
	{
		Date created = new Date();
		entry.setCreated(created);
		assertEquals(created, entry.getCreated());
	}

	@Test
	void testSetCreated_Null()
	{
		NullPointerException nullPointerException = assertThrows(NullPointerException.class, () -> {
			entry.setCreated(null);
		});

		assertEquals("Created date cannot be null", nullPointerException.getMessage());
	}

	@Test
	void testGetSource()
	{
		assertEquals("source", entry.getSource());
	}

	@Test
	void testSetSource()
	{
		entry.setSource("new source");
		assertEquals("new source", entry.getSource());
	}

	@Test
	void testGetReason()
	{
		assertEquals("reason", entry.getReason());
	}

	@Test
	void testSetReason()
	{
		entry.setReason("new reason");
		assertEquals("new reason", entry.getReason());
	}

	@Test
	void testGetExpiration()
	{
		assertNull(entry.getExpiration());
	}

	@Test
	void testSetExpiration()
	{
		Date expiration = new Date();
		entry.setExpiration(expiration);
		assertEquals(expiration, entry.getExpiration());
	}

}
