package org.mockbukkit.mockbukkit.inventory.meta;

import org.mockbukkit.mockbukkit.MockBukkit;
import org.mockbukkit.mockbukkit.ServerMock;
import org.mockbukkit.mockbukkit.entity.OfflinePlayerMock;
import org.mockbukkit.mockbukkit.entity.PlayerMock;
import org.bukkit.profile.PlayerProfile;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class SkullMetaMockTest
{

	private ServerMock server;

	@BeforeEach
	void setUp()
	{
		server = MockBukkit.mock();
	}

	@AfterEach
	void tearDown()
	{
		MockBukkit.unmock();
	}

	@Test
	void testDefaultNoOwner()
	{
		SkullMetaMock meta = new SkullMetaMock();

		assertFalse(meta.hasOwner());
		assertNull(meta.getOwner());
		assertNull(meta.getOwningPlayer());
	}

	@Test
	void testSetOwner()
	{
		SkullMetaMock meta = new SkullMetaMock();

		assertTrue(meta.setOwner("TheBusyBiscuit"));
		assertTrue(meta.hasOwner());
		assertEquals("TheBusyBiscuit", meta.getOwner());
		assertEquals("TheBusyBiscuit", meta.getOwningPlayer().getName());
	}

	@Test
	void testSetOwningPlayer()
	{
		SkullMetaMock meta = new SkullMetaMock();

		assertTrue(meta.setOwningPlayer(new OfflinePlayerMock("TheBusyBiscuit")));
		assertTrue(meta.hasOwner());
		assertEquals("TheBusyBiscuit", meta.getOwner());
		assertEquals("TheBusyBiscuit", meta.getOwningPlayer().getName());
	}

	@Test
	void testNoPlayerProfile()
	{
		SkullMetaMock meta = new SkullMetaMock();

		assertNull(meta.getPlayerProfile());
	}

	@Test
	void testSetNullPlayerProfile()
	{
		SkullMetaMock meta = new SkullMetaMock();

		assertNull(meta.getPlayerProfile());
		meta.setPlayerProfile(null);
		assertNull(meta.getPlayerProfile());
	}

	@Test
	void testSetPlayerProfile()
	{
		SkullMetaMock meta = new SkullMetaMock();
		PlayerMock playerMock = server.addPlayer();

		meta.setPlayerProfile(playerMock.getPlayerProfile());
		assertEquals(playerMock.getPlayerProfile(), meta.getPlayerProfile());
	}

	@Test
	void testClone()
	{
		SkullMetaMock meta = new SkullMetaMock();

		assertTrue(meta.setOwner("TheBusyBiscuit"));

		SkullMetaMock clone = meta.clone();
		assertEquals(meta, clone);
		assertEquals("TheBusyBiscuit", clone.getOwner());
	}

	@Test
	void testSetOwnerProfile()
	{
		SkullMetaMock meta = new SkullMetaMock();
		PlayerMock playerMock = server.addPlayer();
		PlayerProfile profile = server.createPlayerProfile(UUID.randomUUID(), "Test");

		meta.setOwnerProfile(profile);

		assertNotNull(meta.getOwnerProfile());
		assertEquals(profile, meta.getOwnerProfile());
	}

}
