package org.mockbukkit.metaminer.recipes;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

import com.google.common.base.Preconditions;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import org.bukkit.Bukkit;
import org.bukkit.inventory.BlastingRecipe;
import org.bukkit.inventory.CampfireRecipe;
import org.bukkit.inventory.CraftingRecipe;
import org.bukkit.inventory.FurnaceRecipe;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.SmithingRecipe;
import org.bukkit.inventory.SmokingRecipe;
import org.bukkit.inventory.StonecuttingRecipe;
import org.jetbrains.annotations.Nullable;
import org.mockbukkit.metaminer.DataGenerator;
import org.mockbukkit.metaminer.json.recipe.BlastingRecipeElementFactory;
import org.mockbukkit.metaminer.json.recipe.CampfireRecipeElementFactory;
import org.mockbukkit.metaminer.json.recipe.CraftingRecipeElementFactory;
import org.mockbukkit.metaminer.json.recipe.SmeltingRecipeElementFactory;
import org.mockbukkit.metaminer.json.recipe.SmithingRecipeElementFactory;
import org.mockbukkit.metaminer.json.recipe.SmokingRecipeElementFactory;
import org.mockbukkit.metaminer.json.recipe.StoneCuttingRecipeElementFactory;
import org.mockbukkit.metaminer.util.JsonUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RecipeDataGenerator implements DataGenerator
{
	private static final Logger LOGGER = LoggerFactory.getLogger(RecipeDataGenerator.class);

	public static final String CRAFTING = "crafting";
	public static final String SMELTING = "smelting";
	public static final String BLASTING = "blasting";
	public static final String SMOKING = "smoking";
	public static final String CAMPFIRE_COOKING = "campfire_cooking";
	public static final String STONECUTTING = "stonecutting";
	public static final String SMITHING = "smithing";

	private static final Map<Class<? extends Recipe>, String> RECIPE_CLASS_MAP = Map.of(
			CraftingRecipe.class, CRAFTING,
			FurnaceRecipe.class, SMELTING,
			BlastingRecipe.class, BLASTING,
			SmokingRecipe.class, SMOKING,
			CampfireRecipe.class, CAMPFIRE_COOKING,
			StonecuttingRecipe.class, STONECUTTING,
			SmithingRecipe.class, SMITHING
	);

	private static final Map<String, Function<Recipe, JsonElement>> RECIPE_FACTORY_MAP = Map.of(
			CRAFTING, CraftingRecipeElementFactory::toJson,
			SMELTING, SmeltingRecipeElementFactory::toJson,
			BLASTING, BlastingRecipeElementFactory::toJson,
			SMOKING, SmokingRecipeElementFactory::toJson,
			CAMPFIRE_COOKING, CampfireRecipeElementFactory::toJson,
			STONECUTTING, StoneCuttingRecipeElementFactory::toJson,
			SMITHING, SmithingRecipeElementFactory::toJson
	);

	private final File dataFolder;

	public RecipeDataGenerator(File parentDataFolder)
	{
		this.dataFolder = new File(parentDataFolder, "recipes");
	}

	@Override
	public void generateData() throws IOException
	{
		Map<String, List<Recipe>> recipes = new HashMap<>();

		// Group all the existing recipes into groups
		Iterator<Recipe> iterator = Bukkit.recipeIterator();
		while (iterator.hasNext())
		{
			Recipe recipe = iterator.next();
			String recipeType = getRecipeType(recipe);

			recipes.computeIfAbsent(recipeType, k -> new ArrayList<>()).add(recipe);
		}

		// Process each groups
		LOGGER.info("Recipe summary:");
		int totalRecipes = 0;
		for (Map.Entry<String, List<Recipe>> entry : recipes.entrySet())
		{
			String recipeType = entry.getKey();
			List<Recipe> recipeList = entry.getValue();

			totalRecipes += recipeList.size();

			LOGGER.info(" - {}: {}", recipeType, recipeList.size());

			Function<Recipe, JsonElement> factory = RECIPE_FACTORY_MAP.get(recipeType);
			Preconditions.checkNotNull(factory, "Recipe with type '%s' does not have a factory.", recipeType);

			JsonArray recipeListJson = new JsonArray(recipeList.size());
			recipeList.stream().map(factory).forEach(recipeListJson::add);
			JsonUtil.dump(recipeListJson, new File(dataFolder, String.format("%s.json", recipeType)));
		}

		LOGGER.info(" - TOTAL: {}", totalRecipes);
	}

	@Nullable
	private String getRecipeType(Recipe recipe)
	{
		for (var mapEntry : RECIPE_CLASS_MAP.entrySet())
		{
			Class<?> clazz = mapEntry.getKey();

			Preconditions.checkArgument(Recipe.class.isAssignableFrom(clazz), "The class %s is not a Recipe", clazz.getName());
			if (clazz.isInstance(recipe))
			{
				return mapEntry.getValue();
			}
		}

		throw new IllegalArgumentException("Unknown recipe type for class " + recipe.getClass().getName());
	}

}
