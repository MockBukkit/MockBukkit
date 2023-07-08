package be.seeseemelk.mockbukkit.entity;

import be.seeseemelk.mockbukkit.MockBukkit;
import be.seeseemelk.mockbukkit.ServerMock;
import org.bukkit.entity.AnimalTamer;
import org.bukkit.entity.Horse;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class AbstractHorseMockTest
{

	private ServerMock server;
	private HorseMock horse;

	@BeforeEach
	void setUp()
	{
		server = MockBukkit.mock();
		horse = new HorseMock(server, UUID.randomUUID());
	}

	@AfterEach
	void tearDown()
	{
		MockBukkit.unmock();
	}

	@Test
	void testSetVariant()
	{
		assertThrows(UnsupportedOperationException.class, () -> horse.setVariant(Horse.Variant.HORSE));
	}

	@Test
	void testGetDomesticationDefault()
	{
		assertEquals(0, horse.getDomestication());
	}

	@Test
	void testGetMaxDomesticationDefault()
	{
		assertEquals(100, horse.getMaxDomestication());
	}

	@Test
	void testSetDomestication()
	{
		horse.setDomestication(50);
		assertEquals(50, horse.getDomestication());
	}

	@Test
	void testSetDomesticationTooLow()
	{
		assertThrows(IllegalArgumentException.class, () -> horse.setDomestication(-1));
	}

	@Test
	void testSetDomesticationTooHigh()
	{
		assertThrows(IllegalArgumentException.class, () -> horse.setDomestication(101));
	}

	@Test
	void testSetMaxDomestication()
	{
		horse.setMaxDomestication(50);
		assertEquals(50, horse.getMaxDomestication());
	}

	@Test
	void testSetMaxDomesticationTooLow()
	{
		assertThrows(IllegalArgumentException.class, () -> horse.setMaxDomestication(0));
	}

	@Test
	void testSetMaxDomesticationInfluencingSetDomestication()
	{
		horse.setMaxDomestication(50);
		assertThrows(IllegalArgumentException.class, () -> horse.setDomestication(51));
	}

	@Test
	void testGetOwnerUniqueIdDefault()
	{
		assertNull(horse.getOwnerUniqueId());
	}

	@Test
	void testSetOwnerUUID()
	{
		UUID owner = UUID.randomUUID();
		horse.setOwnerUUID(owner);
		assertEquals(owner, horse.getOwnerUniqueId());
	}

	@Test
	void testGetOwnerDefault()
	{
		assertNull(horse.getOwner());
	}

	@Test
	void testSetOwner()
	{
		AnimalTamer owner = server.addPlayer();
		horse.setOwner(owner);
		assertEquals(owner, horse.getOwner());
		assertTrue(horse.isTamed());
	}

	@Test
	void testSetOwnerNull()
	{
		AnimalTamer owner = server.addPlayer();
		horse.setOwner(owner);
		assertNotNull(horse.getOwner());
		horse.setOwner(null);
		assertNull(horse.getOwner());
		assertFalse(horse.isTamed());
	}

	@Test
	void testIsEatingHayStackDefault()
	{
		assertFalse(horse.isEatingHaystack());
	}

	@Test
	void testSetEatingHayStack()
	{
		horse.setEatingHaystack(true);
		assertTrue(horse.isEatingHaystack());
	}

	@Test
	void testIsEatingGrassDefault()
	{
		assertFalse(horse.isEatingGrass());
	}

	@Test
	void testSetEatingGrass()
	{
		horse.setEatingGrass(true);
		assertTrue(horse.isEatingGrass());
	}

	@Test
	void testIsEatingDefault()
	{
		assertFalse(horse.isEating());
	}

	@Test
	void testSetEating()
	{
		horse.setEating(true);
		assertTrue(horse.isEating());
	}

	@Test
	void testIsRearingDefault()
	{
		assertFalse(horse.isRearing());
	}

	@Test
	void testSetRearing()
	{
		horse.setRearing(true);
		assertTrue(horse.isRearing());
	}

	@Test
	void testGetJumpStrengthDefault()
	{
		assertEquals(0.7, horse.getJumpStrength());
	}

	@Test
	void testSetJumpStrength()
	{
		horse.setJumpStrength(0.5);
		assertEquals(0.5, horse.getJumpStrength());
	}

	@Test
	void testSetJumpStrengthTooLow()
	{
		assertThrows(IllegalArgumentException.class, () -> horse.setJumpStrength(-0.1));
	}

}
