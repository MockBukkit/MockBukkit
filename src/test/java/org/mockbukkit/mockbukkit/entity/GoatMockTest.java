package org.mockbukkit.mockbukkit.entity;

import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockbukkit.mockbukkit.MockBukkitExtension;
import org.mockbukkit.mockbukkit.MockBukkitInject;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockbukkit.mockbukkit.matcher.entity.goat.GoatEntityRammedMatcher.hasNotRammed;
import static org.mockbukkit.mockbukkit.matcher.entity.goat.GoatEntityRammedMatcher.hasRammed;

@ExtendWith(MockBukkitExtension.class)
class GoatMockTest
{

	@MockBukkitInject
	private GoatMock goat;

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
	void testRam(@MockBukkitInject LivingEntity entity)
	{
		goat.ram(entity);
		assertThat(goat, hasRammed(entity));
	}

	@Test
	void testRamNull()
	{
		assertThrows(NullPointerException.class, () -> goat.ram(null));
	}

	@Test
	void testAssertEntityRammedWithNotRammedEntity(@MockBukkitInject LivingEntity entity)
	{
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
