package be.seeseemelk.mockbukkit.entity;

import be.seeseemelk.mockbukkit.MockBukkit;
import be.seeseemelk.mockbukkit.ServerMock;
import org.bukkit.entity.Horse;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class SkeletonHorseMockTest
{

	private ServerMock server;
	private SkeletonHorseMock skeletonHorse;

	@BeforeEach
	void setUp()
	{
		server = MockBukkit.mock();
		skeletonHorse = new SkeletonHorseMock(server, UUID.randomUUID());
	}

	@AfterEach
	void tearDown()
	{
		MockBukkit.unmock();
	}

	@Test
	void testVariant()
	{
		assertEquals(Horse.Variant.SKELETON_HORSE, skeletonHorse.getVariant());
	}

	@Test
	void testGetTrappedDefault()
	{
		assertFalse(skeletonHorse.isTrapped());
	}

	@Test
	void testGetTrapTimeDefault()
	{
		assertEquals(0, skeletonHorse.getTrapTime());
	}

	@Test
	void testSetTrapped()
	{
		skeletonHorse.setTrapped(true);
		assertTrue(skeletonHorse.isTrapped());
	}

	@Test
	void testSetTrapTime()
	{
		skeletonHorse.setTrapTime(10);
		assertEquals(10, skeletonHorse.getTrapTime());
	}

	@Test
	void testIsTrapDefault()
	{
		assertFalse(skeletonHorse.isTrap());
	}

	@Test
	void testSetTrap()
	{
		skeletonHorse.setTrap(true);
		assertTrue(skeletonHorse.isTrap());
	}

	@Test
	void getEyeHeight_GivenDefaultSkeletonHorse()
	{
		assertEquals(1.52D, skeletonHorse.getEyeHeight());
	}

	@Test
	void getEyeHeight_GivenBabySkeletonHorse()
	{
		skeletonHorse.setBaby();
		assertEquals(0.76D, skeletonHorse.getEyeHeight());
	}

}
