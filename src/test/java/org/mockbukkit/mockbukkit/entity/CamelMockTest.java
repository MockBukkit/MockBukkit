package org.mockbukkit.mockbukkit.entity;

import org.mockbukkit.mockbukkit.MockBukkitExtension;
import org.mockbukkit.mockbukkit.MockBukkitInject;
import org.mockbukkit.mockbukkit.ServerMock;
import org.bukkit.entity.Camel;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Horse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(MockBukkitExtension.class)
class CamelMockTest
{

	private Camel camel;
	@MockBukkitInject
	private ServerMock server;

	@BeforeEach
	void setUp()
	{
		camel = new CamelMock(server, UUID.randomUUID());
	}

	@Test
	void testGetVariant()
	{
		assertEquals(Horse.Variant.CAMEL, camel.getVariant());
	}

	@Test
	void testGetType()
	{
		assertEquals(EntityType.CAMEL, camel.getType());
	}

	@Test
	void testIsDashingDefault()
	{
		assertFalse(camel.isDashing());
	}

	@Test
	void testIsDashing()
	{
		camel.setDashing(true);
		assertTrue(camel.isDashing());
	}

	@Test
	void testIsSittingDefault()
	{
		assertFalse(camel.isSitting());
	}

	@Test
	void testSetSitting()
	{
		camel.setSitting(true);
		assertTrue(camel.isSitting());
	}

}
