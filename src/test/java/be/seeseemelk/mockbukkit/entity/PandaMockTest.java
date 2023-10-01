package be.seeseemelk.mockbukkit.entity;

import be.seeseemelk.mockbukkit.MockBukkit;
import be.seeseemelk.mockbukkit.ServerMock;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Panda;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class PandaMockTest
{

	private PandaMock panda;

	@BeforeEach
	public void setUp() {
		ServerMock server = MockBukkit.mock();
		panda = new PandaMock(server, UUID.randomUUID());
	}

	@AfterEach
	public void tearDown() {
		MockBukkit.unmock();
	}

	@Test
	public void testGetMainGene() {
		assertEquals(Panda.Gene.NORMAL, panda.getMainGene());
	}

	@Test
	public void testGetHiddenGene() {
		assertEquals(Panda.Gene.NORMAL, panda.getHiddenGene());
	}

	@Test
	public void testSetMainGene() {
		panda.setMainGene(Panda.Gene.AGGRESSIVE);
		assertEquals(Panda.Gene.AGGRESSIVE, panda.getMainGene());
	}

	@Test
	public void testSetHiddenGene() {
		panda.setHiddenGene(Panda.Gene.AGGRESSIVE);
		assertEquals(Panda.Gene.AGGRESSIVE, panda.getHiddenGene());
	}

	@Test
	public void testEntityType() {
		assertEquals(EntityType.PANDA, panda.getType());
	}

	@Test
	public void testSitting() {
		assertFalse(panda.isSitting());
	}

	@Test
	public void testSetSitting() {
		panda.setSitting(true);
		assertTrue(panda.isSitting());
	}

	@Test
	public void testRolling() {
		assertFalse(panda.isRolling());
	}

	@Test
	public void testSetRolling() {
		panda.setRolling(true);
		assertTrue(panda.isRolling());
	}

	@Test
	public void testSneezing() {
		assertFalse(panda.isSneezing());
	}

	@Test
	public void testSetSneezing() {
		panda.setSneezing(true);
		assertTrue(panda.isSneezing());
	}

	@Test
	public void testEating() {
		assertFalse(panda.isEating());
	}

	@Test
	public void testSetEating() {
		panda.setEating(true);
		assertTrue(panda.isEating());
	}

	@Test
	public void testOnBack() {
		assertFalse(panda.isOnBack());
	}

	@Test
	public void testSetOnBack() {
		panda.setOnBack(true);
		assertTrue(panda.isOnBack());
	}

	@Test
	public void testDefaultScared() {
		assertFalse(panda.isScared());
	}

	@Test
	public void testScared() {
		panda.setMainGene(Panda.Gene.WORRIED);
		assertTrue(panda.isScared());
	}

	@Test
	public void testGetUnHappyTicks() {
		assertEquals(0, panda.getUnhappyTicks());
	}

	@Test
	public void testSetUnHappyTicks() {
		panda.setUnhappyTicks(100);
		assertEquals(100, panda.getUnhappyTicks());
	}

	@Test
	public void testGetSneezeTicks() {
		assertEquals(0, panda.getSneezeTicks());
	}

	@Test
	public void testSetSneezeTicks() {
		panda.setSneezeTicks(100);
		assertEquals(100, panda.getSneezeTicks());
	}

	@Test
	public void testGetEatingTicks() {
		assertEquals(0, panda.getEatingTicks());
	}

	@Test
	public void testSetEatingTicks() {
		panda.setEatingTicks(100);
		assertEquals(100, panda.getEatingTicks());
	}

}
