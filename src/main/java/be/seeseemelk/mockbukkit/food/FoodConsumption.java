package be.seeseemelk.mockbukkit.food;

import be.seeseemelk.mockbukkit.MockBukkit;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;
import org.bukkit.Material;
import org.bukkit.inventory.meta.components.FoodComponent;
import org.bukkit.potion.PotionEffect;

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

public record FoodConsumption(Material food, int nutrition, float saturationModifier, boolean canAlwaysEat,
							  int eatDurationTicks, List<FoodEffect> foodEffects)
{

	private static Map<Material, FoodConsumption> ALL_FOODS = null;

	public static Map<Material, FoodConsumption> getOrCreateAllFoods()
	{
		if(ALL_FOODS == null) {
			String path = "/foods/food_properties.json";
			if(MockBukkit.class.getResource(path) == null) {
                try {
                    throw new FileNotFoundException(path);
                } catch (FileNotFoundException e) {
                    throw new RuntimeException(e);
                }
            }
			try(BufferedReader reader = new BufferedReader(new InputStreamReader(MockBukkit.class.getResourceAsStream(path), StandardCharsets.UTF_8))) {
				Gson gson = new Gson();
				List<FoodConsumption> foodsAsList = gson.fromJson(reader, new TypeToken<List<FoodConsumption>>() {});
				ALL_FOODS = foodsAsList.stream().collect(Collectors.toMap(FoodConsumption::food, Function.identity()));
			} catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
		return ALL_FOODS;
	}

	public static FoodConsumption getFor(Material material) {
		return getOrCreateAllFoods().get(material);
	}


	public record FoodEffect(PotionEffect potionEffect, float probability)
	{

	}

}
