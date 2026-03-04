package org.mockbukkit.mockbukkit.entity;

import io.papermc.paper.registry.RegistryAccess;
import io.papermc.paper.registry.RegistryKey;
import org.bukkit.Material;
import org.bukkit.entity.Chicken;
import org.bukkit.entity.EntityType;
import org.bukkit.inventory.ItemStack;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockbukkit.mockbukkit.MockBukkitExtension;
import org.mockbukkit.mockbukkit.MockBukkitInject;
import org.mockbukkit.mockbukkit.inventory.ItemStackMock;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(MockBukkitExtension.class)
class ChickenMockTest
{

	@MockBukkitInject
	private ChickenMock chicken;

	@Test
	void testGetType()
	{
		assertEquals(EntityType.CHICKEN, chicken.getType());
	}

	@Test
	void testIsBreedItemItemStack()
	{
		assertTrue(chicken.isBreedItem(new ItemStackMock(Material.WHEAT_SEEDS)));
	}

	@Test
	void testIsBreedItemMaterial()
	{
		assertTrue(chicken.isBreedItem(Material.WHEAT_SEEDS));
	}

	@Test
	void testIsBreedItemItemStackFalse()
	{
		assertFalse(chicken.isBreedItem(new ItemStackMock(Material.STONE)));
	}

	@Test
	void testIsBreedItemMaterialFalse()
	{
		assertFalse(chicken.isBreedItem(Material.STONE));
	}

	@Test
	void testIsBreedItemNull()
	{
		assertThrows(NullPointerException.class, () -> chicken.isBreedItem((ItemStack) null));
	}

	@Test
	void testIsBreedItemNullWithMaterial()
	{
		assertThrows(NullPointerException.class, () -> chicken.isBreedItem((Material) null));
	}

	@Test
	void testIsChickenJockeyDefault()
	{
		assertFalse(chicken.isChickenJockey());
	}

	@Test
	void testSetIsChickenJockey()
	{
		chicken.setIsChickenJockey(true);
		assertTrue(chicken.isChickenJockey());
	}

	@Test
	void testGetEggLayTimeDefault()
	{
		assertTrue(chicken.getEggLayTime() >= 6000);
	}

	@Test
	void testSetEggLayTime()
	{
		chicken.setEggLayTime(100);
		assertEquals(100, chicken.getEggLayTime());
	}

	@Test
	void getEyeHeight_GivenDefaultChicken()
	{
		assertEquals(0.644D, chicken.getEyeHeight());
	}

	@Test
	void getEyeHeight_GivenBabyChicken()
	{
		chicken.setBaby();
		assertEquals(0.175D, chicken.getEyeHeight());
	}

	@Nested
	class GetVariant
	{

		@Test
		void givenDefault()
		{
			assertEquals(Chicken.Variant.TEMPERATE, chicken.getVariant());
		}

		@Test
		void givenNullValue()
		{
			IllegalArgumentException e = assertThrows(IllegalArgumentException.class, () -> chicken.setVariant(null));
			assertEquals("Variant cannot be null", e.getMessage());
		}

		@ParameterizedTest
		@MethodSource("getChickenVariants")
		void givenPossibleValues(Chicken.Variant variant)
		{
			chicken.setVariant(variant);
			assertEquals(variant, chicken.getVariant());
		}

		public static Stream<Arguments> getChickenVariants()
		{
			return RegistryAccess.registryAccess()
					.getRegistry(RegistryKey.CHICKEN_VARIANT)
					.stream()
					.map(Arguments::of);
		}

	}

}
