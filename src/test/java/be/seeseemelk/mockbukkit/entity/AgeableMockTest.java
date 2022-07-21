package be.seeseemelk.mockbukkit.entity;

import be.seeseemelk.mockbukkit.MockBukkit;
import be.seeseemelk.mockbukkit.ServerMock;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class AgeableMockTest
{

	private ServerMock serverMock;
	private AgeableMock ageable;

	@BeforeEach
	void setUp()
	{
		serverMock = MockBukkit.mock();
		ageable = new AgeableMock(serverMock, UUID.randomUUID());
	}

	@AfterEach
	void tearDown()
	{
		MockBukkit.unmock();
	}

	@Test
	void testGetAgeDefault()
	{
		assertEquals(0, ageable.getAge());
	}

	@Test
	void testSetAge()
	{
		ageable.setAge(10);
		assertEquals(10, ageable.getAge());
	}


	@Test
	void testGetAgeLock()
	{
		assertFalse(ageable.getAgeLock());
	}

	@Test
	void testSetAgeLock()
	{
		ageable.setAgeLock(true);
		assertTrue(ageable.getAgeLock());
	}

	@Test
	void testSetBaby()
	{
		ageable.setBaby();
		assertFalse(ageable.isAdult());
	}

	@Test
	void testSetAdult()
	{
		ageable.setAdult();
		assertTrue(ageable.isAdult());
	}

	@Test
	void testCanBreed()
	{
		assertTrue(ageable.canBreed());
	}

	@Test
	void testCanBreedFalse()
	{
		ageable.setAge(1);
		assertFalse(ageable.canBreed());
	}

	@Test
	void testSetBreed()
	{
		ageable.setBreed(true);
		assertTrue(ageable.isAdult());
	}

	@Test
	void testSetBreedWithAdult()
	{
		ageable.setAdult();
		assertTrue(ageable.isAdult());
		ageable.setBreed(false);
		assertEquals(6000, ageable.getAge());
	}

	@Test
	void testSetBreedFalseWithAdult()
	{
		ageable.setAge(0);
		ageable.setBreed(false);
		assertEquals(6000, ageable.getAge());
	}

	@Test
	void testToString()
	{
		assertEquals("AgeableMock", ageable.toString());
	}

}
