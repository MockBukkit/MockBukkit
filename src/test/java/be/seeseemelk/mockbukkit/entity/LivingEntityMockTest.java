package be.seeseemelk.mockbukkit.entity;

import be.seeseemelk.mockbukkit.MockBukkit;
import be.seeseemelk.mockbukkit.ServerMock;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class LivingEntityMockTest
{

	private ServerMock server;
	private CowMock livingEntity;

	@BeforeEach
	void setup()
	{
		server = MockBukkit.mock();
		livingEntity = new CowMock(server, UUID.randomUUID());
	}

	@AfterEach
	void tearDown()
	{
		MockBukkit.unmock();
	}

	@Test
	void testIsJumpingDefault()
	{
		assertFalse(livingEntity.isJumping());
	}

	@Test
	void testSetJumping()
	{
		livingEntity.setJumping(true);
		assertTrue(livingEntity.isJumping());
	}

	@Test
	void testGetKillerDefault()
	{
		assertNull(livingEntity.getKiller());
	}

	@Test
	void testSetKiller()
	{
		PlayerMock player = server.addPlayer();

		livingEntity.setKiller(player);
		assertEquals(player, livingEntity.getKiller());
	}

	@Test
	void testSetKillerNullDoesNotThrow()
	{
		livingEntity.setKiller(null);
		assertNull(livingEntity.getKiller());
	}

	@Test
	void testIsInvisibleDefault()
	{
		assertFalse(livingEntity.isInvisible());
	}

	@Test
	void testSetInvisible()
	{
		livingEntity.setInvisible(true);
		assertTrue(livingEntity.isInvisible());
	}

	@Test
	void testSwingMainHand()
	{
		assertDoesNotThrow(() -> livingEntity.swingMainHand());
	}

	@Test
	void testSwingOffHand()
	{
		assertDoesNotThrow(() -> livingEntity.swingOffHand());
	}

}
