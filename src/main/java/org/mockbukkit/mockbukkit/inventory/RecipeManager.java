package org.mockbukkit.mockbukkit.inventory;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;

import com.google.common.base.Preconditions;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.bukkit.inventory.Recipe;
import org.jetbrains.annotations.NotNull;
import org.mockbukkit.mockbukkit.MockBukkit;

public enum RecipeManager
{
	BLASTING("blasting"),
	CAMPFIRE_COOKING("campfire_cooking"),
	CRAFTING("crafting"),
	SMELTING("smelting"),
	SMITHING("smithing"),
	SMOKING("smoking"),
	STONECUTTING("stonecutting");

	private final String name;

	RecipeManager(@NotNull String name)
	{
		this.name = Preconditions.checkNotNull(name, "name cannot be null");
	}

	public static List<Recipe> loadDefaultRecipes(RecipeManager recipeType)
	{
		return switch (recipeType)
		{
			case BLASTING -> Collections.emptyList();
			case CAMPFIRE_COOKING -> Collections.emptyList();
			case CRAFTING -> loadCraftingRecipes();
			case SMELTING -> Collections.emptyList();
			case SMITHING -> Collections.emptyList();
			case SMOKING -> Collections.emptyList();
			case STONECUTTING -> Collections.emptyList();
		};
	}

	public static Map<RecipeManager, List<Recipe>> loadDefaultRecipes()
	{
		Map<RecipeManager, List<Recipe>> recipesMap = new EnumMap<>(RecipeManager.class);
		for (RecipeManager recipeManager : RecipeManager.values())
		{
			var recipes = RecipeManager.loadDefaultRecipes(recipeManager);
			recipesMap.put(recipeManager, recipes);
		}
		return recipesMap;
	}

	public static List<Recipe> loadDefaultRecipesAsLists()
	{
		return loadDefaultRecipes().values().stream()
				.flatMap(Collection::stream)
				.toList();
	}

	private static List<Recipe> loadCraftingRecipes()
	{
		List<Recipe> recipesList = new ArrayList<>();
		URL resource = MockBukkit.class.getClassLoader().getResource("recipes/crafting.json");
		try
		{
			File file = new File(resource.toURI());
			JsonArray recipes = JsonParser.parseReader(new FileReader(file)).getAsJsonArray();

			for (JsonElement recipeElement : recipes)
			{
				Preconditions.checkArgument(recipeElement.isJsonObject(), "The recipe is not a JSON object");
				JsonObject recipe = recipeElement.getAsJsonObject();
				String recipeTypeString = recipe.get("type").getAsString();
				if (CraftingRecipeFactory.SHAPED_TYPE.equalsIgnoreCase(recipeTypeString))
				{
					recipesList.add(CraftingRecipeFactory.createShapedRecipe(recipe));
				}
				else if (CraftingRecipeFactory.SHAPELESS_TYPE.equalsIgnoreCase(recipeTypeString))
				{
					recipesList.add(CraftingRecipeFactory.createShapelessRecipe(recipe));
				}
				else if (CraftingRecipeFactory.TRANSMUTE_TYPE.equalsIgnoreCase(recipeTypeString))
				{
					recipesList.add(CraftingRecipeFactory.createTransmuteRecipe(recipe));
				}
				else if (CraftingRecipeFactory.COMPLEX_TYPE.equalsIgnoreCase(recipeTypeString))
				{
					recipesList.add(CraftingRecipeFactory.createComplexRecipe(recipe));
				}
				else
				{
					throw new IllegalArgumentException("Unknown recipe type: " + recipeTypeString);
				}
			}
		}
		catch (URISyntaxException | FileNotFoundException e)
		{
			throw new IllegalArgumentException("Error while loading crafting recipes", e);
		}

		return recipesList;
	}

}
