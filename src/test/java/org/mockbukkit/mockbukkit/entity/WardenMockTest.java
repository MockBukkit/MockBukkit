package org.mockbukkit.mockbukkit.entity;

import org.mockbukkit.mockbukkit.MockBukkit;
import org.mockbukkit.mockbukkit.ServerMock;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class WardenMockTest
{

	private ServerMock server;
	private WardenMock warden;
	private PlayerMock player;

	@BeforeEach
	void setup()
	{
		server = MockBukkit.mock();
		warden = new WardenMock(server, UUID.randomUUID());
		player = server.addPlayer();
	}

	@AfterEach
	void tearDown()
	{
		MockBukkit.unmock();
	}

	@Test
	void testGetAngerDefault()
	{
		assertEquals(0, warden.getAnger(player));
	}

	@Test
	void testGetAngerNullEntityThrows()
	{
		assertThrows(NullPointerException.class, () -> warden.getAnger(null));
	}

	@Test
	void testSetAnger()
	{
		int anger = 42;
		warden.setAnger(player, anger);
		assertEquals(anger, warden.getAnger(player));
	}

	@Test
	void testSetAngerNullEntityThrows()
	{
		assertThrows(NullPointerException.class, () -> warden.setAnger(null, 50));
	}

	@Test
	void testSetAngerBiggerThan150Throws()
	{
		assertThrows(IllegalArgumentException.class, () -> warden.setAnger(player, 420));
	}

	@Test
	void testIncreaseAngerThrowsNullEntity()
	{
		assertThrows(NullPointerException.class, () -> warden.increaseAnger(null, 69));
	}

	@Test
	void testIncreaseAngerThrowsWithUnknownEntityAndToBigValue()
	{
		assertThrows(IllegalStateException.class, () -> warden.increaseAnger(player, 420));
	}

	@Test
	void testIncreaseAngerThrowsWithKnownEntityAndToBigAddedValue()
	{
		warden.setAnger(player, 140);
		assertThrows(IllegalStateException.class, () -> warden.increaseAnger(player, 20));
	}

	@Test
	void testIncreaseAngerWithUnknownPlayer()
	{
		warden.increaseAnger(player, 40);
		assertEquals(40, warden.getAnger(player));
	}

	@Test
	void testIncreaseAngerWithKnownEntity()
	{
		warden.setAnger(player, 29);
		warden.increaseAnger(player, 40);

		assertEquals(69, warden.getAnger(player));
	}

}
