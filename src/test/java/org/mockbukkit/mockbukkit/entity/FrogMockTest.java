package org.mockbukkit.mockbukkit.entity;

import org.mockbukkit.mockbukkit.MockBukkit;
import org.mockbukkit.mockbukkit.ServerMock;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Frog;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class FrogMockTest
{

	private ServerMock server;
	private FrogMock frog;

	@BeforeEach
	void setup()
	{
		server = MockBukkit.mock();
		frog = new FrogMock(server, UUID.randomUUID());
	}

	@AfterEach
	void tearDown()
	{
		MockBukkit.unmock();
	}

	@Test
	void testGetTongueTargetDefault()
	{
		assertNull(frog.getTongueTarget());
	}

	@Test
	void testSetTongueTarget()
	{
		PlayerMock playerMock = server.addPlayer();
		frog.setTongueTarget(playerMock);
		assertEquals(playerMock, frog.getTongueTarget());
	}

	@Test
	void testGetVariantDefault()
	{
		assertEquals(Frog.Variant.TEMPERATE, frog.getVariant());
	}

	@Test
	void testSetVariant()
	{
		frog.setVariant(Frog.Variant.COLD);
		assertEquals(Frog.Variant.COLD, frog.getVariant());
	}

	@Test
	void testGetType()
	{
		assertEquals(EntityType.FROG, frog.getType());
	}

}
