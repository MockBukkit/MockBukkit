package be.seeseemelk.mockbukkit.inventory.meta;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import be.seeseemelk.mockbukkit.MockBukkit;
import be.seeseemelk.mockbukkit.entity.OfflinePlayerMock;

class SkullMetaMockTest
{

	@BeforeEach
	public void setUp()
	{
		MockBukkit.mock();
	}

	@AfterEach
	public void tearDown()
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
	void testClone()
	{
		SkullMetaMock meta = new SkullMetaMock();

		assertTrue(meta.setOwner("TheBusyBiscuit"));

		SkullMetaMock clone = meta.clone();
		assertEquals(meta, clone);
		assertEquals("TheBusyBiscuit", clone.getOwner());
	}

}
