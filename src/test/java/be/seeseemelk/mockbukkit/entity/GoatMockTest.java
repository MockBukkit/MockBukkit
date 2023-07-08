package be.seeseemelk.mockbukkit.entity;

import be.seeseemelk.mockbukkit.MockBukkit;
import be.seeseemelk.mockbukkit.ServerMock;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class GoatMockTest
{

	private ServerMock server;
	private GoatMock goat;

	@BeforeEach
	void setUp()
	{
		server = MockBukkit.mock();
		goat = new GoatMock(server, UUID.randomUUID());
	}

	@AfterEach
	void tearDown()
	{
		MockBukkit.unmock();
	}

	@Test
	void testGetType()
	{
		assertEquals(EntityType.GOAT, goat.getType());
	}

	@Test
	void testHasLeftHornDefault()
	{
		assertTrue(goat.hasLeftHorn());
	}

	@Test
	void testSetLeftHorn()
	{
		goat.setLeftHorn(false);
		assertFalse(goat.hasLeftHorn());
	}

	@Test
	void testHasRightHornDefault()
	{
		assertTrue(goat.hasRightHorn());
	}

	@Test
	void testSetRightHorn()
	{
		goat.setRightHorn(false);
		assertFalse(goat.hasRightHorn());
	}

	@Test
	void testIsScreamingDefault()
	{
		assertFalse(goat.isScreaming());
	}

	@Test
	void testSetScreaming()
	{
		goat.setScreaming(true);
		assertTrue(goat.isScreaming());
	}

	@Test
	void testRam()
	{
		LivingEntity entity = server.addPlayer();
		goat.ram(entity);
		goat.assertEntityRammed(entity);
	}

	@Test
	void testRamNull()
	{
		assertThrows(NullPointerException.class, () -> goat.ram(null));
	}

	@Test
	void testAssertEntityRammedWithNotRammedEntity()
	{
		LivingEntity entity = server.addPlayer();
		AssertionError assertionError = assertThrows(AssertionError.class, () -> goat.assertEntityRammed(entity));
		assertEquals("Expected Goat to have rammed Player0 but it did not!", assertionError.getMessage());
	}

}
