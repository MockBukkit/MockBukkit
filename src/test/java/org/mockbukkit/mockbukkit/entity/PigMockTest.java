package org.mockbukkit.mockbukkit.entity;

import io.papermc.paper.registry.RegistryAccess;
import io.papermc.paper.registry.RegistryKey;
import org.bukkit.Material;
import org.bukkit.entity.Pig;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockbukkit.mockbukkit.MockBukkit;
import org.mockbukkit.mockbukkit.ServerMock;

import java.util.UUID;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class PigMockTest
{

	private PigMock pig;

	@BeforeEach
	void setUp()
	{
		ServerMock server = MockBukkit.mock();
		pig = new PigMock(server, UUID.randomUUID());
	}

	@AfterEach
	void tearDown()
	{
		MockBukkit.unmock();
	}

	@Test
	void testHasSaddleDefault()
	{
		assertFalse(pig.hasSaddle());
	}

	@Test
	void testSetSaddle()
	{
		pig.setSaddle(true);
		assertTrue(pig.hasSaddle());
	}

	@Test
	void testGetBoostTicksDefault()
	{
		assertTrue(pig.getBoostTicks() >= 0);
	}

	@Test
	void testSetBoostTicks()
	{
		pig.setBoostTicks(100);
		assertEquals(100, pig.getBoostTicks());
	}

	@Test
	void testSetBoosTicksNegativeThrows()
	{
		assertThrows(IllegalArgumentException.class, () -> pig.setBoostTicks(-1));
	}

	@Test
	void testGetCurrentBoostTicksDefault()
	{
		assertEquals(0, pig.getCurrentBoostTicks());
	}

	@Test
	void testSetCurrentBoostTicks()
	{
		pig.setSaddle(true);
		pig.setCurrentBoostTicks(100);
		assertEquals(100, pig.getCurrentBoostTicks());
	}

	@Test
	void testSetCurrentBoostTicksNegativeThrows()
	{
		pig.setSaddle(true);
		assertThrows(IllegalArgumentException.class, () -> pig.setCurrentBoostTicks(-1));
	}

	@Test
	void testSetCurrentBoostTicksGreaterThanBoostTicksThrows()
	{
		pig.setSaddle(true);
		pig.setBoostTicks(1);
		assertThrows(IllegalArgumentException.class, () -> pig.setCurrentBoostTicks(101));
	}

	@Test
	void testSetCurrentBoostTicksWithNoSaddle()
	{
		pig.setCurrentBoostTicks(100);
		assertEquals(0, pig.getCurrentBoostTicks());
	}

	@Test
	void testGetSteerMaterial()
	{
		assertEquals(Material.CARROT_ON_A_STICK, pig.getSteerMaterial());
	}


	@Test
	void getEyeHeight_GivenDefaultPig()
	{
		assertEquals(0.765D, pig.getEyeHeight());
	}

	@Test
	void getEyeHeight_GivenBabyPig()
	{
		pig.setBaby();
		assertEquals(0.3825D, pig.getEyeHeight());
	}

	@Nested
	class GetVariant
	{

		@Test
		void givenDefault()
		{
			assertEquals(Pig.Variant.TEMPERATE, pig.getVariant());
		}

		@Test
		void givenNullValue()
		{
			IllegalArgumentException e = assertThrows(IllegalArgumentException.class, () -> pig.setVariant(null));
			assertEquals("Variant cannot be null", e.getMessage());
		}

		@ParameterizedTest
		@MethodSource("getPigVariants")
		void givenPossibleValues(Pig.Variant variant)
		{
			pig.setVariant(variant);
			assertEquals(variant, pig.getVariant());
		}

		public static Stream<Arguments> getPigVariants()
		{
			return RegistryAccess.registryAccess()
					.getRegistry(RegistryKey.PIG_VARIANT)
					.stream()
					.map(Arguments::of);
		}

	}

}
