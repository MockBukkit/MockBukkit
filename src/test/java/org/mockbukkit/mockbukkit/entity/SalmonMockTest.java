package org.mockbukkit.mockbukkit.entity;

import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Salmon;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import org.mockbukkit.mockbukkit.MockBukkitExtension;
import org.mockbukkit.mockbukkit.MockBukkitInject;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockBukkitExtension.class)
class SalmonMockTest
{

	@MockBukkitInject
	private SalmonMock salmon;

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
