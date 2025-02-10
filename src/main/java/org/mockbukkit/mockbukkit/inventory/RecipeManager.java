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
import java.util.stream.Stream;

import com.google.common.base.Preconditions;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.bukkit.Keyed;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ComplexRecipe;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.RecipeChoice;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.ShapelessRecipe;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.mockbukkit.mockbukkit.MockBukkit;

public class RecipeManager
{

	/**
	 * This field is used as cache. The values are lazy loaded with method {@link #getRecipes()}.
	 * This field should not be accessed directly, it's preferred to use the method {@link #getRecipes()} instead.
	 */
	private @Nullable Map<RecipeType, List<Recipe>> recipes = null;

	/**
	 * Resets the list of recipes to the default.
	 */
	public void reset()
	{
		this.recipes = new EnumMap<>(RecipeManager.loadDefaultRecipes());
	}

	/**
	 * Resets the list of recipes to the default for a given recipe type.
	 *
	 * @param recipeType The recipe type to reset.
	 */
	public void reset(@NotNull RecipeType recipeType)
	{
		Preconditions.checkArgument(recipeType != null, "Recipe type cannot be null");
		Preconditions.checkState(this.recipes != null, "Recipes has not been initialized yet.");
		this.recipes.put(recipeType, RecipeManager.loadDefaultRecipes(recipeType));
	}

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
			this.reset();
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
		Preconditions.checkArgument(recipeType != null, "Recipe type cannot be null");
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
		Preconditions.checkArgument(recipeType != null, "Recipe type cannot be null");
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
		Preconditions.checkArgument(recipeType != null, "Recipe type cannot be null");
		return getRecipes(recipeType).stream()
				.filter(recipe -> itemStack.isSimilar(recipe.getResult()))
				.toList();
	}

	@Nullable
	public Recipe getCraftingRecipe(@NotNull ItemStack[] craftingMatrix)
	{
		Preconditions.checkArgument(craftingMatrix != null, "craftingMatrix must not be null");
		Preconditions.checkArgument(craftingMatrix.length == 9, "craftingMatrix must be an array of length 9");

		List<Recipe> possibleRecipes = getRecipes(RecipeType.CRAFTING);
		for (Recipe recipe : possibleRecipes)
		{
			if (recipe instanceof ShapelessRecipe shapelessRecipe)
			{
				if (matches(shapelessRecipe, craftingMatrix))
				{
					return recipe;
				}
			}
			else if (recipe instanceof ShapedRecipe shapedRecipe)
			{
				if (matches(shapedRecipe, craftingMatrix))
				{
					return recipe;
				}
			}
			else if (recipe instanceof ComplexRecipe complexRecipe)
			{
				if (matches(complexRecipe, craftingMatrix))
				{
					return recipe;
				}
			} else
			{
				throw new UnsupportedOperationException("Unknown recipe type: " + recipe.getClass().getName());
			}
		}

		return null;
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

	static boolean matches(@NotNull ShapelessRecipe shapelessRecipe, @NotNull ItemStack @NotNull [] craftingMatrix)
	{
		Preconditions.checkArgument(shapelessRecipe != null, "The recipe cannot be null");
		Preconditions.checkArgument(craftingMatrix != null, "The craftingMatrix cannot be null");

		long itemCount = Stream.of(craftingMatrix).filter(item -> !item.isEmpty()).count();

		@NotNull List<RecipeChoice> choices = shapelessRecipe.getChoiceList();
		if (choices.size() != itemCount)
		{
			// If number of items in the recipe does not match the amount of items required, we skip
			return false;
		}

		for (RecipeChoice choice : choices)
		{
			boolean anyMatches = Stream.of(craftingMatrix).anyMatch(choice);
			if (!anyMatches)
			{
				// If at least one item does not have matching items we exit
				return false;
			}
		}

		return true;
	}

	static boolean matches(@NotNull ShapedRecipe shapedRecipe, @NotNull ItemStack @NotNull [] craftingMatrix)
	{
		Preconditions.checkArgument(shapedRecipe != null, "The recipe cannot be null");
		Preconditions.checkArgument(craftingMatrix != null, "The craftingMatrix cannot be null");

		// TODO: Logic for shaped recipes

		return false;
	}

	static boolean matches(@NotNull ComplexRecipe complexRecipe, @NotNull ItemStack @NotNull [] items)
	{
		Preconditions.checkArgument(complexRecipe != null, "The recipe cannot be null");
		Preconditions.checkArgument(items != null, "The craftingMatrix cannot be null");

		// TODO: Logic for complex recipes

		return false;
	}

}
