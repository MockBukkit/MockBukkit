package org.mockbukkit.mockbukkit.inventory;

import com.google.common.base.Preconditions;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
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
import org.mockbukkit.mockbukkit.util.ResourceLoader;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

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
	 * @param recipeType The recipe type to get the recipes.
	 * @param recipeKey  The recipe key to get the recipes.
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
	 * @return The list of recipes available to create.
	 */
	@NotNull
	public List<Recipe> getRecipesFor(@NotNull RecipeType recipeType, @NotNull ItemStack itemStack)
	{
		Preconditions.checkArgument(recipeType != null, "Recipe type cannot be null");
		Preconditions.checkArgument(itemStack != null, "Item stack cannot be null");

		return getRecipes(recipeType).stream()
				.filter(recipe -> itemStack.isSimilar(recipe.getResult()))
				.toList();
	}

	@Nullable
	public Recipe getCraftingRecipe(@NotNull ItemStack @NotNull [] craftingMatrix)
	{
		Preconditions.checkArgument(craftingMatrix != null, "craftingMatrix must not be null");
		Preconditions.checkArgument(craftingMatrix.length == 9, "craftingMatrix must be an array of length 9");

		List<Recipe> possibleRecipes = getRecipes(RecipeType.CRAFTING);
		for (Recipe recipe : possibleRecipes)
		{
			switch (recipe)
			{
			case ShapelessRecipe shapelessRecipe ->
			{
				if (matches(shapelessRecipe, craftingMatrix))
				{
					return recipe;
				}
			}
			case ShapedRecipe shapedRecipe ->
			{
				if (matches(shapedRecipe, craftingMatrix))
				{
					return recipe;
				}
			}
			case ComplexRecipe complexRecipe ->
			{
				if (matches(complexRecipe, craftingMatrix))
				{
					return recipe;
				}
			}
			default -> throw new UnsupportedOperationException("Unknown recipe type: " + recipe.getClass().getName());
			}
		}

		return null;
	}

	/**
	 * Add a recipe to the list of recipes.
	 *
	 * @param recipeType The recipe type.
	 * @param recipe     The recipe to be added.
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
	 * @param recipeType The recipe type.
	 * @param recipe     The recipe to be removed.
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
			case RecipeType.BLASTING, RecipeType.CAMPFIRE_COOKING, RecipeType.SMELTING, RecipeType.SMITHING,
				 RecipeType.SMOKING, RecipeType.STONECUTTING -> Collections.emptyList();
			case RecipeType.CRAFTING -> loadCraftingRecipes();
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

	private static @NotNull List<Recipe> loadCraftingRecipes()
	{
		List<Recipe> recipesList = new ArrayList<>();
		JsonArray recipes = ResourceLoader.loadResource("recipes/crafting.json").getAsJsonArray();
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

		String[] shape = shapedRecipe.getShape();
		String[] mirroredShape = mirrorRecipeHorizontally(shapedRecipe.getShape());
		if (Arrays.equals(shape, mirroredShape))
		{
			mirroredShape = null;
		}

		Map<Character, RecipeChoice> ingredientMap = shapedRecipe.getChoiceMap();

		int recipeHeight = shape.length;
		int recipeWidth = shape[0].length();

		// Try all possible positions in the 3x3 crafting grid
		for (int startRow = 0; startRow <= 3 - recipeHeight; startRow++)
		{
			for (int startCol = 0; startCol <= 3 - recipeWidth; startCol++)
			{
				// Validate the recipe
				if (matchesAtPosition(shape, ingredientMap, craftingMatrix, startRow, startCol))
				{
					return true;
				}

				// Validate the recipe mirrored
				if (mirroredShape != null && matchesAtPosition(mirroredShape, ingredientMap, craftingMatrix, startRow, startCol))
				{
					return true;
				}
			}
		}

		return false;
	}

	/**
	 * Mirror the recipe in the horizontal axis.
	 * <p>
	 * Example: ["abc","def"] will become ["cba", "fed"].
	 *
	 * @param shape The recipe to be mirrored.
	 * @return The mirrored recipe.
	 */
	private static @NotNull String @NotNull [] mirrorRecipeHorizontally(@NotNull String @NotNull [] shape)
	{
		String[] flippedShape = shape.clone();
		flippedShape[0] = new StringBuilder(flippedShape[0]).reverse().toString(); // Should always be at least 1 row
		if (flippedShape.length > 1)
		{
			flippedShape[1] = new StringBuilder(flippedShape[1]).reverse().toString();
		}
		if (flippedShape.length > 2)
		{
			flippedShape[2] = new StringBuilder(flippedShape[2]).reverse().toString();
		}
		return flippedShape;
	}

	private static boolean matchesAtPosition(String[] shape, Map<Character, RecipeChoice> ingredientMap,
											 ItemStack[] craftingMatrix, int startRow, int startCol)
	{
		for (int row = 0; row < 3; row++)
		{
			for (int col = 0; col < 3; col++)
			{
				if (!matchesSlot(shape, ingredientMap, craftingMatrix, startRow, startCol, row, col))
				{
					return false;
				}
			}
		}
		return true;
	}

	private static boolean matchesSlot(String[] shape, Map<Character, RecipeChoice> ingredientMap,
									   ItemStack[] craftingMatrix, int startRow, int startCol, int row, int col)
	{
		int index = row * 3 + col;
		boolean isInRecipe = (row >= startRow && row < startRow + shape.length) &&
				(col >= startCol && col < startCol + shape[0].length());

		ItemStack itemInSlot = craftingMatrix[index];
		if (!isInRecipe)
		{
			return itemInSlot.isEmpty();
		}

		int recipeRow = row - startRow;
		int recipeCol = col - startCol;
		char recipeChar = shape[recipeRow].charAt(recipeCol);

		if (recipeChar == ' ')
		{
			return itemInSlot.isEmpty();
		}

		// A choice can be null when there's no item in that position. An example of this is minecraft:acacia_boat at position "b"
		@Nullable RecipeChoice choice = ingredientMap.get(recipeChar);
		if (choice == null)
		{
			return itemInSlot.isEmpty();
		}
		else
		{
			return choice.test(itemInSlot);
		}
	}

	static boolean matches(@NotNull ComplexRecipe complexRecipe, @NotNull ItemStack @NotNull [] items)
	{
		Preconditions.checkArgument(complexRecipe != null, "The recipe cannot be null");
		Preconditions.checkArgument(items != null, "The craftingMatrix cannot be null");

		// TODO: Logic for complex recipes

		return false;
	}

}
