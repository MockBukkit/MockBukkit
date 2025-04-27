package org.mockbukkit.mockbukkit.entity;

import io.papermc.paper.registry.RegistryAccess;
import io.papermc.paper.registry.RegistryKey;
import org.bukkit.entity.Cow;
import org.bukkit.entity.EntityType;
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
import static org.junit.jupiter.api.Assertions.assertThrows;

class CowMockTest
{

	private ServerMock server;
	private CowMock cow;

	@BeforeEach
	void setUp()
	{
		server = MockBukkit.mock();
		cow = new CowMock(server, UUID.randomUUID());
	}

	@AfterEach
	void tearDown()
	{
		MockBukkit.unmock();
	}

	@Test
	void testGetType()
	{
		assertEquals(EntityType.COW, cow.getType());
	}

	@Test
	void getEyeHeight_GivenDefaultCow()
	{
		assertEquals(1.3D, cow.getEyeHeight());
	}

	@Test
	void getEyeHeight_GivenBabyCow()
	{
		cow.setBaby();
		assertEquals(0.65D, cow.getEyeHeight());
	}

	@Nested
	class GetVariant
	{

		@Test
		void givenDefault()
		{
			assertEquals(Cow.Variant.TEMPERATE, cow.getVariant());
		}

		@Test
		void givenNullValue()
		{
			IllegalArgumentException e = assertThrows(IllegalArgumentException.class, () -> cow.setVariant(null));
			assertEquals("Variant cannot be null", e.getMessage());
		}

		@ParameterizedTest
		@MethodSource("getCowVariants")
		void givenPossibleValues(Cow.Variant variant)
		{
			cow.setVariant(variant);
			assertEquals(variant, cow.getVariant());
		}

		public static Stream<Arguments> getCowVariants()
		{
			return RegistryAccess.registryAccess()
					.getRegistry(RegistryKey.COW_VARIANT)
					.stream()
					.map(Arguments::of);
		}

	}

}
