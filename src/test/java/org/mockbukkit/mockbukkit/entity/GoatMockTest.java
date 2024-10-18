package org.mockbukkit.mockbukkit.entity;

import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockbukkit.mockbukkit.MockBukkit;
import org.mockbukkit.mockbukkit.ServerMock;

import java.util.UUID;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockbukkit.mockbukkit.matcher.entity.goat.GoatEntityRammedMatcher.hasNotRammed;
import static org.mockbukkit.mockbukkit.matcher.entity.goat.GoatEntityRammedMatcher.hasRammed;

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
		assertThat(goat, hasRammed(entity));
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
		assertThat(goat, hasNotRammed(entity));
	}

	@Test
	void getEyeHeight_GivenDefaultGoat()
	{
		assertEquals(1.105D, goat.getEyeHeight());
	}

	@Test
	void getEyeHeight_GivenBabyGoat()
	{
		goat.setBaby();
		assertEquals(0.5525D, goat.getEyeHeight());
	}

}
