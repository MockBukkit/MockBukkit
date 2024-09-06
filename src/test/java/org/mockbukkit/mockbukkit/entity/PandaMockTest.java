package org.mockbukkit.mockbukkit.entity;

import org.mockbukkit.mockbukkit.MockBukkit;
import org.mockbukkit.mockbukkit.ServerMock;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Panda;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class PandaMockTest
{

	private PandaMock panda;

	@BeforeEach
	void setUp()
	{
		ServerMock server = MockBukkit.mock();
		panda = new PandaMock(server, UUID.randomUUID());
	}

	@AfterEach
	void tearDown()
	{
		MockBukkit.unmock();
	}

	@Test
	void testGetMainGene()
	{
		assertEquals(Panda.Gene.NORMAL, panda.getMainGene());
	}

	@Test
	void testGetHiddenGene()
	{
		assertEquals(Panda.Gene.NORMAL, panda.getHiddenGene());
	}

	@Test
	void testSetMainGene()
	{
		panda.setMainGene(Panda.Gene.AGGRESSIVE);
		assertEquals(Panda.Gene.AGGRESSIVE, panda.getMainGene());
	}

	@Test
	void testSetHiddenGene()
	{
		panda.setHiddenGene(Panda.Gene.AGGRESSIVE);
		assertEquals(Panda.Gene.AGGRESSIVE, panda.getHiddenGene());
	}

	@Test
	void testEntityType()
	{
		assertEquals(EntityType.PANDA, panda.getType());
	}

	@Test
	void testSitting()
	{
		assertFalse(panda.isSitting());
	}

	@Test
	void testSetSitting()
	{
		panda.setSitting(true);
		assertTrue(panda.isSitting());
	}

	@Test
	void testRolling()
	{
		assertFalse(panda.isRolling());
	}

	@Test
	void testSetRolling()
	{
		panda.setRolling(true);
		assertTrue(panda.isRolling());
	}

	@Test
	void testSneezing()
	{
		assertFalse(panda.isSneezing());
	}

	@Test
	void testSetSneezing()
	{
		panda.setSneezing(true);
		assertTrue(panda.isSneezing());
	}

	@Test
	void testEating()
	{
		assertFalse(panda.isEating());
	}

	@Test
	void testSetEating()
	{
		panda.setEating(true);
		assertTrue(panda.isEating());
	}

	@Test
	void testOnBack()
	{
		assertFalse(panda.isOnBack());
	}

	@Test
	void testSetOnBack()
	{
		panda.setOnBack(true);
		assertTrue(panda.isOnBack());
	}

	@Test
	void testDefaultScared()
	{
		assertFalse(panda.isScared());
	}

	@Test
	void testScared()
	{
		panda.setMainGene(Panda.Gene.WORRIED);
		assertTrue(panda.isScared());
	}

	@Test
	void testGetUnHappyTicks()
	{
		assertEquals(0, panda.getUnhappyTicks());
	}

	@Test
	void testSetUnHappyTicks()
	{
		panda.setUnhappyTicks(100);
		assertEquals(100, panda.getUnhappyTicks());
	}

	@Test
	void testGetSneezeTicks()
	{
		assertEquals(0, panda.getSneezeTicks());
	}

	@Test
	void testSetSneezeTicks()
	{
		panda.setSneezeTicks(100);
		assertEquals(100, panda.getSneezeTicks());
	}

	@Test
	void testGetEatingTicks()
	{
		assertEquals(0, panda.getEatingTicks());
	}

	@Test
	void testSetEatingTicks()
	{
		panda.setEatingTicks(100);
		assertEquals(100, panda.getEatingTicks());
	}

	@Test
	void getEyeHeight_GivenDefaultPanda()
	{
		assertEquals(1.0625D, panda.getEyeHeight());
	}

	@Test
	void getEyeHeight_GivenBabyPanda()
	{
		panda.setBaby();
		assertEquals(0.53125D, panda.getEyeHeight());
	}

}
