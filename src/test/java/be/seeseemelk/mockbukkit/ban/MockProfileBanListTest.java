package be.seeseemelk.mockbukkit.ban;

import be.seeseemelk.mockbukkit.MockBukkitExtension;
import be.seeseemelk.mockbukkit.MockBukkitInject;
import be.seeseemelk.mockbukkit.ServerMock;
import be.seeseemelk.mockbukkit.profile.PlayerProfileMock;
import org.bukkit.BanEntry;
import org.bukkit.Bukkit;
import org.bukkit.profile.PlayerProfile;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.util.Date;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(MockBukkitExtension.class)
class MockProfileBanListTest
{

	@MockBukkitInject
	ServerMock server;
	MockProfileBanList banList;

	@BeforeEach
	void setUp()
	{
		banList = new MockProfileBanList();
	}

	@Test
	void testGetBanEntries()
	{
		assertEquals(0, banList.getBanEntries().size());
		PlayerProfileMock profileMock = new PlayerProfileMock("TestPlayer", UUID.randomUUID());
		banList.addBan(profileMock, "TestReason", (Date) null, null);

		assertEquals(1, banList.getBanEntries().size());
		assertTrue(banList.getBanEntries().stream().anyMatch(banEntry -> banEntry.getBanTarget().equals(profileMock)));
	}

	@Test
	void testGetBanEntry()
	{
		PlayerProfileMock profileMock = new PlayerProfileMock("TestPlayer", UUID.randomUUID());
		banList.addBan(profileMock, "TestReason", (Date) null, null);

		assertEquals("TestReason", banList.getBanEntry(profileMock).getReason());
	}

	@Test
	void testGetBanEntryString()
	{
		PlayerProfileMock profileMock = new PlayerProfileMock("TestPlayer", UUID.randomUUID());
		banList.addBan(profileMock, "TestReason", (Date) null, null);

		assertEquals("TestReason", banList.getBanEntry("TestPlayer").getReason());
	}

	@Test
	void testGetBanEntryNullThrows()
	{
		NullPointerException nullPointerException = assertThrows(NullPointerException.class, () -> {
			banList.getBanEntry((String) null);
		});

		assertEquals("Target cannot be null", nullPointerException.getMessage());
	}

	@Test
	void testGetBanEntryNullThrowsPlayerProfile()
	{
		NullPointerException nullPointerException = assertThrows(NullPointerException.class, () -> {
			banList.getBanEntry((PlayerProfileMock) null);
		});

		assertEquals("Target cannot be null", nullPointerException.getMessage());
	}

	@Test
	void testIsBanned()
	{
		PlayerProfileMock profileMock = new PlayerProfileMock("TestPlayer", UUID.randomUUID());
		banList.addBan(profileMock, "TestReason", (Date) null, null);

		assertTrue(banList.isBanned(profileMock));
	}

	@Test
	void testIsBannedString()
	{
		PlayerProfileMock profileMock = new PlayerProfileMock("TestPlayer", UUID.randomUUID());
		banList.addBan(profileMock, "TestReason", (Date) null, null);

		assertTrue(banList.isBanned("TestPlayer"));
	}

	@Test
	void testPardon()
	{
		PlayerProfileMock profileMock = new PlayerProfileMock("TestPlayer", UUID.randomUUID());
		banList.addBan(profileMock, "TestReason", (Date) null, null);

		assertTrue(banList.isBanned(profileMock));
		banList.pardon(profileMock);
		assertFalse(banList.isBanned(profileMock));
	}

	@Test
	void testPardonString()
	{
		PlayerProfileMock profileMock = new PlayerProfileMock("TestPlayer", UUID.randomUUID());
		banList.addBan(profileMock, "TestReason", (Date) null, null);

		assertTrue(banList.isBanned(profileMock));
		banList.pardon("TestPlayer");
		assertFalse(banList.isBanned(profileMock));
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
	void testPardonProfileNull()
	{
		NullPointerException nullPointerException = assertThrows(NullPointerException.class, () -> {
			banList.pardon((PlayerProfileMock) null);
		});

		assertEquals("Target cannot be null", nullPointerException.getMessage());

	}

	@Test
	void testAddBanBukkitProfile()
	{
		PlayerProfile testPlayer = Bukkit.createPlayerProfile(UUID.randomUUID(), "TestPlayer");
		banList.addBan(testPlayer, "TestReason", (Date) null, null);

		assertTrue(banList.isBanned(testPlayer));
	}

	@Test
	void testGetBanEntryBukkit()
	{
		PlayerProfile testPlayer = Bukkit.createPlayerProfile(UUID.randomUUID(), "TestPlayer");
		BanEntry<? super com.destroystokyo.paper.profile.PlayerProfile> banEntry = banList.addBan(testPlayer,
				"TestReason", (Date) null, null);

		assertEquals("TestReason", banList.getBanEntry(testPlayer).getReason());
	}

	@Test
	void testPardonBukkit()
	{
		PlayerProfile testPlayer = Bukkit.createPlayerProfile(UUID.randomUUID(), "TestPlayer");
		banList.addBan(testPlayer, "TestReason", (Date) null, null);

		assertTrue(banList.isBanned(testPlayer));
		banList.pardon(testPlayer);
		assertFalse(banList.isBanned(testPlayer));
	}

}
