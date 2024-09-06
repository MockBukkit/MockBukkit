package org.mockbukkit.mockbukkit.entity;

import org.mockbukkit.mockbukkit.MockBukkit;
import org.mockbukkit.mockbukkit.ServerMock;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ChestedHorseMockTest
{

	private ServerMock server;
	private MuleMock chestedHorse;

	@BeforeEach
	void setup()
	{
		server = MockBukkit.mock();
		chestedHorse = new MuleMock(server, UUID.randomUUID());
	}

	@AfterEach
	void teardown()
	{
		MockBukkit.unmock();
	}

	@Test
	void testIsCarryingChestDefault()
	{
		assertFalse(chestedHorse.isCarryingChest());
	}

	@Test
	void testIsCarryingChest()
	{
		chestedHorse.setCarryingChest(true);
		assertTrue(chestedHorse.isCarryingChest());
	}

	@Test
	void testSetCarryingChestOnAlreadyTrue()
	{
		chestedHorse.setCarryingChest(true);
		chestedHorse.setCarryingChest(true);
		assertTrue(chestedHorse.isCarryingChest());
	}

}
