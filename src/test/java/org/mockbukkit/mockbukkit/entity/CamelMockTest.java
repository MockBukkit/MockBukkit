package org.mockbukkit.mockbukkit.entity;

import org.bukkit.entity.Camel;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Horse;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockbukkit.mockbukkit.MockBukkitExtension;
import org.mockbukkit.mockbukkit.MockBukkitInject;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(MockBukkitExtension.class)
class CamelMockTest
{

	@MockBukkitInject
	private Camel camel;

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
