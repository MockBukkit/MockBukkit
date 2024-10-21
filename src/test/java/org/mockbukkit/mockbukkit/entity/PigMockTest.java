package org.mockbukkit.mockbukkit.entity;

import org.mockbukkit.mockbukkit.MockBukkit;
import org.mockbukkit.mockbukkit.ServerMock;
import org.bukkit.Material;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class PigMockTest
{

	private PigMock pig;

	@BeforeEach
	void setUp()
	{
		ServerMock server = MockBukkit.mock();
		pig = new PigMock(server, UUID.randomUUID());
	}

	@AfterEach
	void tearDown()
	{
		MockBukkit.unmock();
	}

	@Test
	void testHasSaddleDefault()
	{
		assertFalse(pig.hasSaddle());
	}

	@Test
	void testSetSaddle()
	{
		pig.setSaddle(true);
		assertTrue(pig.hasSaddle());
	}

	@Test
	void testGetBoostTicksDefault()
	{
		assertTrue(pig.getBoostTicks() >= 0);
	}

	@Test
	void testSetBoostTicks()
	{
		pig.setBoostTicks(100);
		assertEquals(100, pig.getBoostTicks());
	}

	@Test
	void testSetBoosTicksNegativeThrows()
	{
		assertThrows(IllegalArgumentException.class, () -> pig.setBoostTicks(-1));
	}

	@Test
	void testGetCurrentBoostTicksDefault()
	{
		assertEquals(0, pig.getCurrentBoostTicks());
	}

	@Test
	void testSetCurrentBoostTicks()
	{
		pig.setSaddle(true);
		pig.setCurrentBoostTicks(100);
		assertEquals(100, pig.getCurrentBoostTicks());
	}

	@Test
	void testSetCurrentBoostTicksNegativeThrows()
	{
		pig.setSaddle(true);
		assertThrows(IllegalArgumentException.class, () -> pig.setCurrentBoostTicks(-1));
	}

	@Test
	void testSetCurrentBoostTicksGreaterThanBoostTicksThrows()
	{
		pig.setSaddle(true);
		pig.setBoostTicks(1);
		assertThrows(IllegalArgumentException.class, () -> pig.setCurrentBoostTicks(101));
	}

	@Test
	void testSetCurrentBoostTicksWithNoSaddle()
	{
		pig.setCurrentBoostTicks(100);
		assertEquals(0, pig.getCurrentBoostTicks());
	}

	@Test
	void testGetSteerMaterial()
	{
		assertEquals(Material.CARROT_ON_A_STICK, pig.getSteerMaterial());
	}


	@Test
	void getEyeHeight_GivenDefaultPig()
	{
		assertEquals(0.765D, pig.getEyeHeight());
	}

	@Test
	void getEyeHeight_GivenBabyPig()
	{
		pig.setBaby();
		assertEquals(0.3825D, pig.getEyeHeight());
	}

}
