package org.mockbukkit.mockbukkit.food;

import org.mockbukkit.mockbukkit.MockBukkitExtension;
import org.bukkit.Material;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;
import java.util.stream.Stream;

@ExtendWith(MockBukkitExtension.class)
public class FoodConsumptionLoadTest
{

	@Test
	void testLoadingDoesNotThrow()
	{
		assertDoesNotThrow(FoodConsumption::getOrCreateAllFoods);
	}

	@Test
	void testNonConsumableItemReturnsNull()
	{
		assertNull(FoodConsumption.getFor(Material.STONE));
	}

	@Test
	void testConsumableItemReturnsNotNull()
	{
		assertNotNull(FoodConsumption.getFor(Material.GOLDEN_APPLE));
	}

	@ParameterizedTest
	@MethodSource("foodPropertyProvider")
	void testIsCorrectlyLoaded(Material food, List<PotionEffect> inflictedEffects)
	{
		assertEquals(inflictedEffects, foodEffects2PotionEffects(FoodConsumption.getFor(food).foodEffects()));
	}

	private static List<PotionEffect> foodEffects2PotionEffects(List<FoodConsumption.FoodEffect> foodEffects)
	{
		return foodEffects.stream().map(FoodConsumption.FoodEffect::potionEffect).toList();
	}

	private static Stream<Arguments> foodPropertyProvider()
	{
		return Stream.of(
				Arguments.of(Material.ENCHANTED_GOLDEN_APPLE, List.of(
								new PotionEffect(PotionEffectType.REGENERATION, 400, 1, false, true, true),
								new PotionEffect(PotionEffectType.RESISTANCE, 6000, 0, false, true, true),
								new PotionEffect(PotionEffectType.FIRE_RESISTANCE, 6000, 0, false, true, true),
								new PotionEffect(PotionEffectType.ABSORPTION, 2400, 3, false, true, true)
						)
				),
				Arguments.of(Material.POISONOUS_POTATO, List.of(
						new PotionEffect(PotionEffectType.POISON, 100, 0, false, true, true)
				)),
				Arguments.of(Material.ROTTEN_FLESH, List.of(
						new PotionEffect(PotionEffectType.HUNGER, 600, 0, false, true, true)
				)),
				Arguments.of(Material.SALMON, List.of())
		);
	}

}
