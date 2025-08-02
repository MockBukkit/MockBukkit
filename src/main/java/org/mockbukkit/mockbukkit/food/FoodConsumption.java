package org.mockbukkit.mockbukkit.food;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import org.bukkit.Material;
import org.bukkit.potion.PotionEffect;
import org.jetbrains.annotations.ApiStatus;
import org.mockbukkit.mockbukkit.potion.InternalPotionDataMock;
import org.mockbukkit.mockbukkit.util.ResourceLoader;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@ApiStatus.Internal
public record FoodConsumption(Material name, int nutrition, float saturationModifier, boolean canAlwaysEat,
							  int eatDurationTicks, List<FoodEffect> foodEffects)
{

	private static Map<Material, FoodConsumption> ALL_FOODS = null;

	private static FoodConsumption loadFoodConsumptionFrom(JsonObject jsonObject)
	{
		return new FoodConsumption(
				Material.matchMaterial(jsonObject.get("name").getAsString()),
				jsonObject.get("nutrition").getAsInt(),
				jsonObject.get("saturationModifier").getAsFloat(),
				jsonObject.get("canAlwaysEat").getAsBoolean(),
				jsonObject.get("eatDurationTicks").getAsInt(),
				loadFoodEffectsFrom(jsonObject.get("effects").getAsJsonArray())
		);
	}

	private static List<FoodConsumption.FoodEffect> loadFoodEffectsFrom(JsonArray jsonArray)
	{
		return jsonArray.asList().stream()
				.map(jsonElement -> loadFoodEffectFrom(jsonElement.getAsJsonObject()))
				.toList();
	}

	private static FoodConsumption.FoodEffect loadFoodEffectFrom(JsonObject jsonObject)
	{
		return new FoodConsumption.FoodEffect(InternalPotionDataMock.getPotionEffectFromData(jsonObject), jsonObject.get("probability").getAsFloat());
	}

	@ApiStatus.Internal
	public static Map<Material, FoodConsumption> getOrCreateAllFoods()
	{
		if (ALL_FOODS == null)
		{
			JsonArray jsonArray = ResourceLoader.loadResource("/foods/food_properties.json").getAsJsonArray();
			ALL_FOODS = jsonArray.asList().stream()
					.map(jsonElement -> loadFoodConsumptionFrom(jsonElement.getAsJsonObject()))
					.collect(Collectors.toMap(FoodConsumption::name, Function.identity()));
		}
		return ALL_FOODS;
	}

	@ApiStatus.Internal
	public static FoodConsumption getFor(Material material)
	{
		return getOrCreateAllFoods().get(material);
	}

	@ApiStatus.Internal
	public record FoodEffect(PotionEffect potionEffect, float probability)
	{

	}

}
