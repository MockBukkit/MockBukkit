package be.seeseemelk.mockbukkit;

import static org.junit.Assert.*;

import java.util.Date;
import java.util.Set;

import org.bukkit.BanEntry;
import org.junit.Before;
import org.junit.Test;

public class MockBanListTest
{
	private MockBanList banList;
	
	@Before
	public void setUp() throws Exception
	{
		banList = new MockBanList();
	}
	
	@Test
	public void isBanned_NotBanned_False()
	{
		assertFalse(banList.isBanned("target"));
	}
	
	@Test
	public void isBanned_Banned_True()
	{
		banList.addBan("target", "reason", new Date(), "source");
		assertTrue(banList.isBanned("target"));
	}
	
	@Test
	public void isBanned_PardonedPerson_False()
	{
		banList.addBan("target", "reason", new Date(), "source");
		banList.pardon("target");
		assertFalse(banList.isBanned("target"));
	}
	
	@Test
	public void getBanEntries_OnePersonBanned_SetOfOnePerson()
	{
		banList.addBan("target", "reason", new Date(), "source");
		Set<BanEntry> entries = banList.getBanEntries();
		assertEquals(1, entries.size());
		assertEquals("target", entries.iterator().next().getTarget());
	}
	
	@Test
	public void getBanEntry_NotBannedPerson_Null()
	{
		assertNull(banList.getBanEntry("target"));
	}
	
	@Test
	public void getBanEntry_BannedPerson_AllValuesCorrect()
	{
		String target = "target";
		String reason = "reason";
		Date date = new Date();
		String source = "source";
		banList.addBan(target, reason, date, source);
		assertNotNull(banList.getBanEntry(target));
		assertEquals(target, banList.getBanEntry(target).getTarget());
		assertEquals(reason, banList.getBanEntry(target).getReason());
		assertEquals(date, banList.getBanEntry(target).getExpiration());
		assertEquals(source, banList.getBanEntry(target).getSource());
	}
	
}









