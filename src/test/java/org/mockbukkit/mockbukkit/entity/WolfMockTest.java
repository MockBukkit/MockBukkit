package org.mockbukkit.mockbukkit.entity;

import org.mockbukkit.mockbukkit.MockBukkit;
import org.mockbukkit.mockbukkit.ServerMock;
import org.bukkit.DyeColor;
import org.bukkit.entity.EntityType;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class WolfMockTest
{

	private WolfMock wolf;

	@BeforeEach
	void setup()
	{
		ServerMock server = MockBukkit.mock();
		wolf = new WolfMock(server, UUID.randomUUID());
	}

	@AfterEach
	void tearDown()
	{
		MockBukkit.unmock();
	}

	@Test
	void testGetType()
	{
		assertEquals(EntityType.WOLF, wolf.getType());
	}

	@Test
	void testIsAngryDefault()
	{
		assertFalse(wolf.isAngry());
	}

	@Test
	void testSetAngry()
	{
		wolf.setAngry(true);
		assertTrue(wolf.isAngry());
	}

	@Test
	void testGetCollarColorDefault()
	{
		assertEquals(DyeColor.RED, wolf.getCollarColor());
	}

	@Test
	void testSetCollarColor()
	{
		wolf.setCollarColor(DyeColor.BLUE);
		assertEquals(DyeColor.BLUE, wolf.getCollarColor());
	}

	@Test
	void testIsWetDefault()
	{
		assertFalse(wolf.isWet());
	}

	@Test
	void testSetWet()
	{
		wolf.setWet(true);
		assertTrue(wolf.isWet());
	}

	@Test
	void testIsInterestedDefault()
	{
		assertFalse(wolf.isInterested());
	}

	@Test
	void testSetInterested()
	{
		wolf.setInterested(true);
		assertTrue(wolf.isInterested());
	}

	@Test
	void testGetTailAngle()
	{
		assertEquals(0.62831855F, wolf.getTailAngle(), 0.00001);
	}

	@Test
	void getEyeHeight_GivenDefaultWolf()
	{
		assertEquals(0.68D, wolf.getEyeHeight());
	}

	@Test
	void getEyeHeight_GivenBabyWolf()
	{
		wolf.setBaby();
		assertEquals(0.34D, wolf.getEyeHeight());
	}

}
