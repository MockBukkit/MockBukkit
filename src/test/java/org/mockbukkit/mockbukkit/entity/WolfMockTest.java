package org.mockbukkit.mockbukkit.entity;

import io.papermc.paper.registry.RegistryAccess;
import io.papermc.paper.registry.RegistryKey;
import org.bukkit.DyeColor;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Wolf;
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

class WolfMockTest
{

	private WolfMock wolf;

	@BeforeEach
	void setup()
	{
		ServerMock server = MockBukkit.mock();
		wolf = new WolfMock(server, UUID.randomUUID());
	}

	@AfterEach
	void tearDown()
	{
		MockBukkit.unmock();
	}

	@Test
	void testGetType()
	{
		assertEquals(EntityType.WOLF, wolf.getType());
	}

	@Test
	void testIsAngryDefault()
	{
		assertFalse(wolf.isAngry());
	}

	@Test
	void testSetAngry()
	{
		wolf.setAngry(true);
		assertTrue(wolf.isAngry());
	}

	@Test
	void testGetCollarColorDefault()
	{
		assertEquals(DyeColor.RED, wolf.getCollarColor());
	}

	@Test
	void testSetCollarColor()
	{
		wolf.setCollarColor(DyeColor.BLUE);
		assertEquals(DyeColor.BLUE, wolf.getCollarColor());
	}

	@Test
	void testIsWetDefault()
	{
		assertFalse(wolf.isWet());
	}

	@Test
	void testSetWet()
	{
		wolf.setWet(true);
		assertTrue(wolf.isWet());
	}

	@Test
	void testIsInterestedDefault()
	{
		assertFalse(wolf.isInterested());
	}

	@Test
	void testSetInterested()
	{
		wolf.setInterested(true);
		assertTrue(wolf.isInterested());
	}

	@Test
	void testGetTailAngle()
	{
		assertEquals(0.62831855F, wolf.getTailAngle(), 0.00001);
	}

	@Test
	void getEyeHeight_GivenDefaultWolf()
	{
		assertEquals(0.68D, wolf.getEyeHeight());
	}

	@Test
	void getEyeHeight_GivenBabyWolf()
	{
		wolf.setBaby();
		assertEquals(0.34D, wolf.getEyeHeight());
	}

	@Nested
	class GetVariant
	{

		@Test
		void givenDefault()
		{
			assertEquals(Wolf.Variant.PALE, wolf.getVariant());
		}

		@Test
		void givenNullValue()
		{
			IllegalArgumentException e = assertThrows(IllegalArgumentException.class, () -> wolf.setVariant(null));
			assertEquals("Variant cannot be null", e.getMessage());
		}

		@ParameterizedTest
		@MethodSource("getWolfVariants")
		void givenPossibleValues(Wolf.Variant variant)
		{
			wolf.setVariant(variant);
			assertEquals(variant, wolf.getVariant());
		}

		public static Stream<Arguments> getWolfVariants()
		{
			return RegistryAccess.registryAccess()
					.getRegistry(RegistryKey.WOLF_VARIANT)
					.stream()
					.map(Arguments::of);
		}

	}

	@Nested
	class GetSoundVariant
	{

		@Test
		void givenDefault()
		{
			assertEquals(Wolf.SoundVariant.CLASSIC, wolf.getSoundVariant());
		}

		@ParameterizedTest
		@MethodSource("getWolfSoundVariants")
		void givenPossibleValues(Wolf.SoundVariant variant)
		{
			wolf.setSoundVariant(variant);
			assertEquals(variant, wolf.getSoundVariant());
		}

		public static Stream<Arguments> getWolfSoundVariants()
		{
			return RegistryAccess.registryAccess()
					.getRegistry(RegistryKey.WOLF_SOUND_VARIANT)
					.stream()
					.map(Arguments::of);
		}

	}

}
