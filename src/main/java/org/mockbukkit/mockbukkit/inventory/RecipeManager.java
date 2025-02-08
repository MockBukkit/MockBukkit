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
import org.bukkit.Keyed;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.mockbukkit.mockbukkit.MockBukkit;

public class RecipeManager
{

	private static final String RECIPE_TYPE_CANNOT_BE_NULL = "Recipe type cannot be null";

	/**
	 * This field is used as cache. The values are lazy loaded with method {@link #getRecipes()}.
	 * This field should not be accessed directly, it's preferred to use the method {@link #getRecipes()} instead.
	 */
	private Map<RecipeType, List<Recipe>> recipes = null;

	/**
	 * Get the list of recipes available.
	 *
	 * @return The list of recipes available.
	 */
	@NotNull
	public Map<RecipeType, List<Recipe>> getRecipes()
	{
		if (this.recipes == null)
		{
			this.recipes = new EnumMap<>(RecipeManager.loadDefaultRecipes());
		}

		return this.recipes;
	}

	/**
	 * Get the list of recipes available for that recipe type.
	 *
	 * @param recipeType The recipe type.
	 *
	 * @return The list of recipes available.
	 */
	@NotNull
	public List<Recipe> getRecipes(@NotNull RecipeType recipeType)
	{
		Preconditions.checkArgument(recipeType != null, RECIPE_TYPE_CANNOT_BE_NULL);
		return getRecipes().getOrDefault(recipeType, Collections.emptyList());
	}

	/**
	 * Helper function to lazy load the recipes.
	 *
	 * @param recipeType 	The recipe type to get the recipes.
	 * @param recipeKey 	The recipe key to get the recipes.
	 *
	 * @return The server recipes.
	 */
	@Nullable
	public Recipe getRecipeByKey(@NotNull RecipeType recipeType, @NotNull NamespacedKey recipeKey)
	{
		Preconditions.checkArgument(recipeType != null, RECIPE_TYPE_CANNOT_BE_NULL);
		Preconditions.checkArgument(recipeKey != null, "Recipe key cannot be null");

		List<Recipe> recipesToSearch = getRecipes().get(recipeType);
		for (Recipe recipe : recipesToSearch)
		{
			if (recipe instanceof Keyed keyed && recipeKey.equals(keyed.getKey()))
			{
				return recipe;
			}
		}

		return null;
	}

	/**
	 * Get the list of recipes available to create a desired item.
	 *
	 * @param recipeType The recipe type.
	 * @param itemStack  The desired item.
	 *
	 * @return The list of recipes available to create.
	 */
	@NotNull
	public List<Recipe> getRecipesFor(@NotNull RecipeType recipeType, @NotNull ItemStack itemStack)
	{
		Preconditions.checkArgument(recipeType != null, RECIPE_TYPE_CANNOT_BE_NULL);
		return getRecipes(recipeType).stream()
				.filter(recipe -> itemStack.isSimilar(recipe.getResult()))
				.toList();
	}

	/**
	 * Add a recipe to the list of recipes.
	 *
	 * @param recipeType 	The recipe type.
	 * @param recipe		The recipe to be added.
	 *
	 * @return {@code true} if added, otherwise {@code false}.
	 */
	public boolean addRecipe(@NotNull RecipeType recipeType, @NotNull Recipe recipe)
	{
		Preconditions.checkArgument(recipeType != null, "The recipe type cannot be null");
		Preconditions.checkArgument(recipe != null, "The recipe cannot be null");
		return getRecipes(recipeType).add(recipe);
	}

	/**
	 * Remove a recipe to the list of recipes.
	 *
	 * @param recipeType 	The recipe type.
	 * @param recipe		The recipe to be removed.
	 *
	 * @return {@code true} if removed, otherwise {@code false}.
	 */
	public boolean removeRecipe(@NotNull RecipeType recipeType, @NotNull Recipe recipe)
	{
		Preconditions.checkArgument(recipeType != null, "The recipe type cannot be null");
		Preconditions.checkArgument(recipe != null, "The recipe cannot be null");
		return getRecipes(recipeType).remove(recipe);
	}

	/**
	 * Clears the list of recipes.
	 */
	public void clearRecipes(RecipeType recipeType)
	{
		getRecipes(recipeType).clear();
	}

	// Static methods

	public static List<Recipe> loadDefaultRecipes(RecipeType recipeType)
	{
		return switch (recipeType)
		{
			case RecipeType.BLASTING -> Collections.emptyList();
			case RecipeType.CAMPFIRE_COOKING -> Collections.emptyList();
			case RecipeType.CRAFTING -> loadCraftingRecipes();
			case RecipeType.SMELTING -> Collections.emptyList();
			case RecipeType.SMITHING -> Collections.emptyList();
			case RecipeType.SMOKING -> Collections.emptyList();
			case RecipeType.STONECUTTING -> Collections.emptyList();
		};
	}

	public static Map<RecipeType, List<Recipe>> loadDefaultRecipes()
	{
		Map<RecipeType, List<Recipe>> recipesMap = new EnumMap<>(RecipeType.class);
		for (RecipeType recipeType : RecipeType.values())
		{
			var recipes = RecipeManager.loadDefaultRecipes(recipeType);
			recipesMap.put(recipeType, recipes);
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
