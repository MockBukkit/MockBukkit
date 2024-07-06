package be.seeseemelk.mockbukkit.food;

import be.seeseemelk.mockbukkit.MockBukkit;
import be.seeseemelk.mockbukkit.potion.MockInternalPotionData;
import com.google.common.base.Preconditions;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.Registry;
import org.bukkit.potion.PotionEffect;
import org.jetbrains.annotations.ApiStatus;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
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
		return new FoodConsumption.FoodEffect(MockInternalPotionData.getPotionEffectFromData(jsonObject), jsonObject.get("probability").getAsFloat());
	}

	@ApiStatus.Internal
	public static Map<Material, FoodConsumption> getOrCreateAllFoods()
	{
		if (ALL_FOODS == null)
		{
			String path = "/foods/food_properties.json";
			if (MockBukkit.class.getResource(path) == null)
			{
				try
				{
					throw new FileNotFoundException(path);
				}
				catch (FileNotFoundException e)
				{
					throw new RuntimeException(e);
				}
			}
			try (BufferedReader reader = new BufferedReader(new InputStreamReader(MockBukkit.class.getResourceAsStream(path), StandardCharsets.UTF_8)))
			{

				JsonArray jsonArray = JsonParser.parseReader(reader).getAsJsonArray();
				ALL_FOODS = jsonArray.asList().stream()
						.map(jsonElement -> loadFoodConsumptionFrom(jsonElement.getAsJsonObject()))
						.collect(Collectors.toMap(FoodConsumption::name, Function.identity()));
			}
			catch (IOException e)
			{
				throw new RuntimeException(e);
			}
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
