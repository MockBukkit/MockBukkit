package org.mockbukkit.mockbukkit.entity;

import org.mockbukkit.mockbukkit.MockBukkit;
import org.mockbukkit.mockbukkit.ServerMock;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Skeleton;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class SkeletonMockTest
{

	private ServerMock server;
	private SkeletonMock skeleton;

	@BeforeEach
	void setUp()
	{
		server = MockBukkit.mock();
		skeleton = new SkeletonMock(server, UUID.randomUUID());
	}

	@AfterEach
	void tearDown()
	{
		MockBukkit.unmock();
	}

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
