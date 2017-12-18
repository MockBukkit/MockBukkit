package be.seeseemelk.mockbukkit;

import static org.junit.Assert.*;

import java.util.Date;

import org.junit.Before;
import org.junit.Test;

public class MockBanList_MockBanEntryListTest
{
	private Date date;
	private MockBanList.MockBanEntry entry;
	
	@Before
	public void setUp()
	{
		date = new Date();
		entry = new MockBanList.MockBanEntry("target", date, "reason", "source");
	}
	
	@Test
	public void constructor()
	{
		assertEquals("target", entry.getTarget());
		assertEquals(date, entry.getExpiration());
		assertEquals("reason", entry.getReason());
		assertEquals("source", entry.getSource());
	}
	
	@Test
	public void setCreationDate_OtherDate_DateSetExactly()
	{
		Date created = entry.getCreated();
		created.setTime(created.getTime() + 1000L);
		entry.setCreated(created);
		assertEquals(created, entry.getCreated());
	}
	
	@Test
	public void setExpirationDate_OtherDate_DateSetExactly()
	{
		Date expiration = entry.getExpiration();
		expiration.setTime(expiration.getTime() + 1000L);
		entry.setExpiration(expiration);
		assertEquals(expiration, entry.getExpiration());
	}
	
	@Test
	public void setSource_OtherSource_SourceSetExactly()
	{
		entry.setSource("other source");
		assertEquals("other source", entry.getSource());
	}
	
	@Test
	public void setReason_LaterDate_DateSetExactly()
	{
		entry.setReason("other reason");
		assertEquals("other reason", entry.getReason());
	}
}
