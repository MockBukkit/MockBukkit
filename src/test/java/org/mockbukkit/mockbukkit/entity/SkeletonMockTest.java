package org.mockbukkit.mockbukkit.entity;

import org.bukkit.entity.EntityType;
import org.bukkit.entity.Skeleton;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockbukkit.mockbukkit.MockBukkitExtension;
import org.mockbukkit.mockbukkit.MockBukkitInject;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(MockBukkitExtension.class)
class SkeletonMockTest
{

	@MockBukkitInject
	private SkeletonMock skeleton;

	@Test
	void testGetSkeletonType()
	{
		assertEquals(Skeleton.SkeletonType.NORMAL, skeleton.getSkeletonType());
	}

	@Test
	void testIsConvertingDefault()
	{
		assertFalse(skeleton.isConverting());
	}

	@Test
	void testSetConverting()
	{
		skeleton.setConverting(true);
		assertTrue(skeleton.isConverting());
	}

	@Test
	void testGetConversionTimeDefault()
	{
		skeleton.setConverting(true);
		assertEquals(0, skeleton.getConversionTime());
	}

	@Test
	void testGetConversionTimeConvertingFalseThrows()
	{
		assertThrows(IllegalStateException.class, () -> skeleton.getConversionTime());
	}

	@Test
	void testSetConversionTime()
	{
		skeleton.setConverting(true);
		skeleton.setConversionTime(69);
		assertEquals(69, skeleton.getConversionTime());
	}

	@Test
	void tesSetConversionTimeNegative()
	{
		skeleton.setConverting(true);
		skeleton.setConversionTime(-69);
		assertEquals(-1, skeleton.getConversionTime());
	}

	@Test
	void testInPowderedSnowTimeDefault()
	{
		assertEquals(0, skeleton.inPowderedSnowTime());
	}

	@Test
	void testSetInPowderedSnowTime()
	{
		skeleton.setInPowderedSnowTime(5);
		assertEquals(5, skeleton.inPowderedSnowTime());
	}

	@Test
	void testInPowderedSnowTimeNegativeValueThrows()
	{
		assertThrows(IllegalArgumentException.class, () -> skeleton.setInPowderedSnowTime(-5));
	}

	@Test
	void testGetEntityType()
	{
		assertEquals(EntityType.SKELETON, skeleton.getType());
	}

}
