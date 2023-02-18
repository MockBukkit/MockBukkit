package org.mockbukkit.mockbukkit.entity;

import org.mockbukkit.mockbukkit.MockBukkit;
import org.mockbukkit.mockbukkit.ServerMock;
import org.bukkit.entity.Horse;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ZombieHorseMockTest
{

	private ServerMock server;
	private ZombieHorseMock zombieHorse;

	@BeforeEach
	void setUp()
	{
		server = MockBukkit.mock();
		zombieHorse = new ZombieHorseMock(server, UUID.randomUUID());
	}

	@AfterEach
	void tearDown()
	{
		MockBukkit.unmock();
	}

	@Test
	void testVariant()
	{
		assertEquals(Horse.Variant.UNDEAD_HORSE, zombieHorse.getVariant());
	}

}
