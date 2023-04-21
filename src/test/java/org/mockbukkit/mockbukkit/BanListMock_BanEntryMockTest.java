package org.mockbukkit.mockbukkit;

import org.mockbukkit.mockbukkit.BanListMock;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;

class BanListMock_BanEntryMockTest
{

	private Date date;
	private BanListMock.BanEntryMock entry;

	@BeforeEach
	void setUp()
	{
		date = new Date();
		entry = new BanListMock.BanEntryMock("target", date, "reason", "source");
	}

	@Test
	void constructor()
	{
		assertEquals("target", entry.getTarget());
		assertEquals(date, entry.getExpiration());
		assertEquals("reason", entry.getReason());
		assertEquals("source", entry.getSource());
	}

	@Test
	void setCreationDate_OtherDate_DateSetExactly()
	{
		Date created = entry.getCreated();
		created.setTime(created.getTime() + 1000L);
		entry.setCreated(created);
		assertEquals(created, entry.getCreated());
	}

	@Test
	void setExpirationDate_OtherDate_DateSetExactly()
	{
		Date expiration = entry.getExpiration();
		expiration.setTime(expiration.getTime() + 1000L);
		entry.setExpiration(expiration);
		assertEquals(expiration, entry.getExpiration());
	}

	@Test
	void setSource_OtherSource_SourceSetExactly()
	{
		entry.setSource("other source");
		assertEquals("other source", entry.getSource());
	}

	@Test
	void setReason_LaterDate_DateSetExactly()
	{
		entry.setReason("other reason");
		assertEquals("other reason", entry.getReason());
	}

}
