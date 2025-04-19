package org.mockbukkit.mockbukkit.entity;

import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Salmon;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import org.mockbukkit.mockbukkit.MockBukkit;
import org.mockbukkit.mockbukkit.ServerMock;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class SalmonMockTest
{

	private SalmonMock salmon;

	@BeforeEach
	void setup()
	{
		ServerMock server = MockBukkit.mock();
		salmon = new SalmonMock(server, UUID.randomUUID());
	}

	@AfterEach
	void tearDown()
	{
		MockBukkit.unmock();
	}

	@Test
	void testGetBaseBucketItem()
	{
		assertEquals(Material.SALMON_BUCKET, salmon.getBaseBucketItem().getType());
	}

	@Test
	void testGetType()
	{
		assertEquals(EntityType.SALMON, salmon.getType());
	}

	@Nested
	class GetVariant
	{

		@Test
		void givenDefault()
		{
			assertEquals(Salmon.Variant.SMALL, salmon.getVariant());
		}

		@Test
		void givenNullValue()
		{
			IllegalArgumentException e = assertThrows(IllegalArgumentException.class, () -> salmon.setVariant(null));
			assertEquals("Variant cannot be null", e.getMessage());
		}

		@ParameterizedTest
		@EnumSource(Salmon.Variant.class)
		void givenPossibleValues(Salmon.Variant variant)
		{
			salmon.setVariant(variant);
			assertEquals(variant, salmon.getVariant());
		}

	}

}
