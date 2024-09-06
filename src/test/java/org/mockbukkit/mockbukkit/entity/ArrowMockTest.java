package org.mockbukkit.mockbukkit.entity;

import org.mockbukkit.mockbukkit.MockBukkit;
import org.mockbukkit.mockbukkit.ServerMock;
import org.bukkit.entity.EntityType;
import org.bukkit.potion.PotionType;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class ArrowMockTest
{

	private ArrowMock arrow;

	@BeforeEach
	void setUp()
	{
		ServerMock serverMock = MockBukkit.mock();
		this.arrow = new ArrowMock(serverMock, UUID.randomUUID());
	}

	@AfterEach
	void tearDown()
	{
		MockBukkit.unmock();
	}

	@Test
	void getType()
	{
		assertEquals(EntityType.ARROW, arrow.getType());
	}

	@Test
	void getDamage_default()
	{
		assertEquals(6.0, arrow.getDamage());
	}

	@Test
	void testBasePotionType()
	{
		assertNull(arrow.getBasePotionType());

		arrow.setBasePotionType(PotionType.HEALING);
		assertEquals(PotionType.HEALING, arrow.getBasePotionType());

		arrow.setBasePotionType(null);
		assertNull(arrow.getBasePotionType());
	}
}
