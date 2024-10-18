package org.mockbukkit.mockbukkit.entity;

import org.mockbukkit.mockbukkit.MockBukkit;
import org.mockbukkit.mockbukkit.ServerMock;
import org.bukkit.entity.EntityType;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class FoxMockTest
{

	private ServerMock server;
	private FoxMock fox;

	@BeforeEach
	void setup()
	{
		server = MockBukkit.mock();
		fox = new FoxMock(server, UUID.randomUUID());
	}

	@AfterEach
	void tearDown()
	{
		MockBukkit.unmock();
	}

	@Test
	void testGetFoxTypeDefault()
	{
		assertEquals(FoxMock.Type.RED, fox.getFoxType());
	}

	@Test
	void testSetFoxType()
	{
		fox.setFoxType(FoxMock.Type.SNOW);
		assertEquals(FoxMock.Type.SNOW, fox.getFoxType());
	}

	@Test
	void testSetFoxTypeNullThrows()
	{
		assertThrows(NullPointerException.class, () -> fox.setFoxType(null));
	}

	@Test
	void testIsCrouchingDefault()
	{
		assertFalse(fox.isCrouching());
	}

	@Test
	void testSetCrouching()
	{
		fox.setCrouching(true);
		assertTrue(fox.isCrouching());
	}

	@Test
	void testIsSleepingDefault()
	{
		assertFalse(fox.isSleeping());
	}

	@Test
	void testSetSleeping()
	{
		fox.setSleeping(true);
		assertTrue(fox.isSleeping());
	}

	@Test
	void testIsLeapingDefault()
	{
		assertFalse(fox.isLeaping());
	}

	@Test
	void testSetLeaping()
	{
		fox.setLeaping(true);
		assertTrue(fox.isLeaping());
	}

	@Test
	void testIsDefendingDefault()
	{
		assertFalse(fox.isDefending());
	}

	@Test
	void testSetDefending()
	{
		fox.setDefending(true);
		assertTrue(fox.isDefending());
	}

	@Test
	void testGetFirstTrustedPlayerDefault()
	{
		assertNull(fox.getFirstTrustedPlayer());
	}

	@Test
	void testSetFirstTrustedPlayer()
	{
		PlayerMock player = server.addPlayer();
		fox.setFirstTrustedPlayer(player);
		assertEquals(player, fox.getFirstTrustedPlayer());
	}

	@Test
	void testSetFirstTrustedPlayerNull()
	{
		fox.setFirstTrustedPlayer(null);
		assertNull(fox.getFirstTrustedPlayer());
	}

	@Test
	void testGetSecondTrustedPlayerDefault()
	{
		assertNull(fox.getSecondTrustedPlayer());
	}

	@Test
	void testSetSecondTrustedPlayer()
	{
		PlayerMock player = server.addPlayer();
		fox.setSecondTrustedPlayer(player);
		assertEquals(player, fox.getSecondTrustedPlayer());
	}

	@Test
	void testSetSecondTrustedPlayerNull()
	{
		fox.setSecondTrustedPlayer(null);
		assertNull(fox.getSecondTrustedPlayer());
	}

	@Test
	void testGetFaceplantedDefault()
	{
		assertFalse(fox.isFaceplanted());
	}

	@Test
	void testSetFaceplanted()
	{
		fox.setFaceplanted(true);
		assertTrue(fox.isFaceplanted());
	}

	@Test
	void testGetInterestedDefault()
	{
		assertFalse(fox.isInterested());
	}

	@Test
	void testSetInterested()
	{
		fox.setInterested(true);
		assertTrue(fox.isInterested());
	}

	@Test
	void testIsSittingDefault()
	{
		assertFalse(fox.isSitting());
	}

	@Test
	void testSetSitting()
	{
		fox.setSitting(true);
		assertTrue(fox.isSitting());
	}

	@Test
	void testGetType()
	{
		assertEquals(EntityType.FOX, fox.getType());
	}

	@Test
	void getEyeHeight_GivenDefaultFox()
	{
		assertEquals(0.4D, fox.getEyeHeight());
	}

	@Test
	void getEyeHeight_GivenBabyFox()
	{
		fox.setBaby();
		assertEquals(0.2D, fox.getEyeHeight());
	}


}
