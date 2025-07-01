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
import java.util.Collection;
import java.util.Collections;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;
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
			}
			else
			{
				throw new UnsupportedOperationException("Unknown recipe type: " + recipe.getClass().getName());
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
		Map<Character, RecipeChoice> ingredients = shapedRecipe.getChoiceMap();

		validateShape(shape);

		List<Map<Integer, Character>> possibleCombinations = getShapedRecipePossiblePositions(shape);
		for (Map<Integer, Character> possibleCombination : possibleCombinations)
		{
			boolean found = true;
			for (int index = 0 ; index < 9 ; index++)
			{
				ItemStack item = craftingMatrix[index];
				Character character = possibleCombination.get(index);
				if (character == null)
				{
					if (item.isEmpty())
					{
						continue;
					}

					found = false;
					break;
				}

				RecipeChoice recipeChoice = ingredients.get(character);
				if (recipeChoice == null)
				{
					// If the item does not exist, we can proceed
					continue;
				}

				if (!recipeChoice.test(item))
				{
					found = false;
					break;
				}
			}
			if (found)
			{
				// We found the recipe!
				return true;
			}
		}

		return false;
	}

	public static Character getChoiceAt(String[] shape, int position)
	{
		Preconditions.checkArgument(shape != null, "Must provide a shape");
		Preconditions.checkArgument(position >= 0 && position <= 8, "Position must be between 0 and 8");

		validateShape(shape);

		int rowIndex = position / 3;
		int col = position % 3;

		String row = shape[rowIndex];
		if (row.length() <= col)
		{
			return null;
		}

		return row.charAt(col);
	}

	private static int validateShape(@NotNull String[] shape)
	{
		int lastLen = -1;
		for (String row : shape)
		{
			Preconditions.checkArgument(row != null, "Shape cannot have null rows");
			Preconditions.checkArgument(row.length() > 0 && row.length() < 4, "Crafting rows should be 1, 2, or 3 characters, not ", row.length());

			Preconditions.checkArgument(lastLen == -1 || lastLen == row.length(), "Crafting recipes must be rectangular");
			lastLen = row.length();
		}

		return lastLen;
	}

	public static List<Map<Integer, Character>> getShapedRecipePossiblePositions(String[] shape)
	{
		Preconditions.checkArgument(shape != null, "Must provide a shape");
		Preconditions.checkArgument(shape.length > 0 && shape.length < 4, "Crafting recipes should be 1, 2 or 3 rows, not ", shape.length);

		int shapeHeight = shape.length;
		int shapeWidth = validateShape(shape);

		List<Map<Integer, Character>> results = new ArrayList<>();

		// Map values
		Supplier<Character> pos0 = () -> getChoiceAt(shape, 0);
		Supplier<Character> pos1 = () -> getChoiceAt(shape, 1);
		Supplier<Character> pos2 = () -> getChoiceAt(shape, 2);
		Supplier<Character> pos3 = () -> getChoiceAt(shape, 3);
		Supplier<Character> pos4 = () -> getChoiceAt(shape, 4);
		Supplier<Character> pos5 = () -> getChoiceAt(shape, 5);
		Supplier<Character> pos6 = () -> getChoiceAt(shape, 6);
		Supplier<Character> pos7 = () -> getChoiceAt(shape, 7);
		Supplier<Character> pos8 = () -> getChoiceAt(shape, 8);

		// Shaped with height 3
		if (shapeHeight == 3)
		{
			if (shapeWidth == 3)
			{
				// Normal
				results.add(Map.of(
					0, pos0.get(),
					1, pos1.get(),
					2, pos2.get(),
					3, pos3.get(),
					4, pos4.get(),
					5, pos5.get(),
					6, pos6.get(),
					7, pos7.get(),
					8, pos8.get())
				);
				// Flip Horizontally
				results.add(Map.of(
					0, pos2.get(),
					1, pos1.get(),
					2, pos0.get(),
					3, pos5.get(),
					4, pos4.get(),
					5, pos3.get(),
					6, pos8.get(),
					7, pos7.get(),
					8, pos6.get())
				);
				return results;
			}
			else if (shapeWidth == 2)
			{
				// Normal
				results.add(Map.of(
						0, pos0.get(),
						1, pos1.get(),
						3, pos3.get(),
						4, pos4.get(),
						6, pos6.get(),
						7, pos7.get())
				);
				results.add(Map.of(
						1, pos0.get(),
						2, pos1.get(),
						4, pos3.get(),
						5, pos4.get(),
						7, pos6.get(),
						8, pos7.get())
				);
				// Flip Horizontally
				results.add(Map.of(
						0, pos3.get(),
						1, pos1.get(),
						3, pos0.get(),
						4, pos7.get(),
						6, pos6.get(),
						7, pos4.get())
				);
				results.add(Map.of(
						1, pos3.get(),
						2, pos1.get(),
						4, pos1.get(),
						5, pos7.get(),
						7, pos6.get(),
						8, pos4.get())
				);
				return results;
			}
			else if (shapeWidth == 1)
			{
				// Normal
				results.add(Map.of(
					0, pos0.get(),
					3, pos3.get(),
					6, pos6.get())
				);
				results.add(Map.of(
					1, pos0.get(),
					4, pos3.get(),
					7, pos6.get())
				);
				results.add(Map.of(
					2, pos0.get(),
					5, pos3.get(),
					8, pos6.get())
				);
				// Flip Horizontally
				results.add(Map.of(
						0, pos6.get(),
						3, pos3.get(),
						6, pos0.get())
				);
				results.add(Map.of(
						1, pos6.get(),
						4, pos3.get(),
						7, pos0.get())
				);
				results.add(Map.of(
						2, pos6.get(),
						5, pos3.get(),
						8, pos0.get())
				);
				return results;
			}
		}

		// Shaped with height 2
		if (shapeHeight == 2)
		{
			if (shapeWidth == 3)
			{
				// Normal
				results.add(Map.of(
					0, pos0.get(),
					1, pos1.get(),
					2, pos2.get(),
					3, pos3.get(),
					4, pos4.get(),
					5, pos5.get())
				);
				results.add(Map.of(
						3, pos0.get(),
						4, pos1.get(),
						5, pos2.get(),
						6, pos3.get(),
						7, pos4.get(),
						8, pos5.get())
				);
				// Flip Horizontally
				results.add(Map.of(
						0, pos2.get(),
						1, pos1.get(),
						2, pos0.get(),
						3, pos5.get(),
						4, pos4.get(),
						5, pos3.get())
				);
				results.add(Map.of(
						3, pos2.get(),
						4, pos1.get(),
						5, pos0.get(),
						6, pos5.get(),
						7, pos4.get(),
						8, pos3.get())
				);
				return results;
			}
			else if (shapeWidth == 2)
			{
				// Normal
				results.add(Map.of(
					0, pos0.get(),
					1, pos1.get(),
					3, pos3.get(),
					4, pos4.get())
				);
				results.add(Map.of(
					1, pos0.get(),
					2, pos1.get(),
					4, pos3.get(),
					5, pos4.get())
				);
				results.add(Map.of(
					3, pos0.get(),
					4, pos1.get(),
					6, pos3.get(),
					7, pos4.get())
				);
				results.add(Map.of(
					4, pos0.get(),
					5, pos1.get(),
					7, pos3.get(),
					8, pos4.get())
				);
				// Flip Horizontally
				results.add(Map.of(
						0, pos1.get(),
						1, pos0.get(),
						3, pos4.get(),
						4, pos3.get())
				);
				results.add(Map.of(
						1, pos1.get(),
						2, pos0.get(),
						4, pos4.get(),
						5, pos3.get())
				);
				results.add(Map.of(
						3, pos1.get(),
						4, pos0.get(),
						6, pos4.get(),
						7, pos3.get())
				);
				results.add(Map.of(
						4, pos1.get(),
						5, pos0.get(),
						7, pos4.get(),
						8, pos3.get())
				);
				return results;
			}
			else if (shapeWidth == 1)
			{
				results.add(Map.of(
					0, pos0.get(),
					3, pos3.get())
				);
				results.add(Map.of(
					1, pos0.get(),
					4, pos3.get())
				);
				results.add(Map.of(
					2, pos0.get(),
					5, pos3.get())
				);
				results.add(Map.of(
					3, pos0.get(),
					6, pos3.get())
				);
				results.add(Map.of(
					4, pos0.get(),
					7, pos3.get())
				);
				results.add(Map.of(
					5, pos0.get(),
					8, pos3.get())
				);
				return results;
			}
		}

		// Shaped with 1 height
		if (shapeHeight == 1)
		{
			if (shapeWidth == 3)
			{
				// Normal
				results.add(Map.of(
					0, pos0.get(),
					1, pos1.get(),
					2, pos2.get())
				);
				results.add(Map.of(
						3, pos0.get(),
						4, pos1.get(),
						5, pos2.get())
				);
				results.add(Map.of(
						6, pos0.get(),
						7, pos1.get(),
						8, pos2.get())
				);
				// Flip Horizontally
				results.add(Map.of(
						0, pos2.get(),
						1, pos1.get(),
						2, pos0.get())
				);
				results.add(Map.of(
						3, pos2.get(),
						4, pos1.get(),
						5, pos0.get())
				);
				results.add(Map.of(
						6, pos2.get(),
						7, pos1.get(),
						8, pos0.get())
				);
				return results;
			}
			else if (shapeWidth == 2)
			{
				// Normal
				results.add(Map.of(
					0, pos0.get(),
					1, pos1.get())
				);
				results.add(Map.of(
					1, pos0.get(),
					2, pos1.get())
				);
				results.add(Map.of(
					3, pos0.get(),
					4, pos1.get())
				);
				results.add(Map.of(
					4, pos0.get(),
					5, pos1.get())
				);
				results.add(Map.of(
					6, pos0.get(),
					7, pos1.get())
				);
				results.add(Map.of(
					7, pos0.get(),
					8, pos1.get())
				);
				// Flip Horizontally
				results.add(Map.of(
						0, pos1.get(),
						1, pos0.get())
				);
				results.add(Map.of(
						1, pos1.get(),
						2, pos0.get())
				);
				results.add(Map.of(
						3, pos1.get(),
						4, pos0.get())
				);
				results.add(Map.of(
						4, pos1.get(),
						5, pos0.get())
				);
				results.add(Map.of(
						6, pos1.get(),
						7, pos0.get())
				);
				results.add(Map.of(
						7, pos1.get(),
						8, pos0.get())
				);
				return results;
			}
			else if (shapeWidth == 1)
			{
				results.add(Map.of(
					0, pos0.get())
				);
				results.add(Map.of(
					1, pos0.get())
				);
				results.add(Map.of(
						2, pos0.get())
				);
				results.add(Map.of(
						3, pos0.get())
				);
				results.add(Map.of(
						4, pos0.get())
				);
				results.add(Map.of(
						5, pos0.get())
				);
				results.add(Map.of(
						6, pos0.get())
				);
				results.add(Map.of(
						7, pos0.get())
				);
				results.add(Map.of(
						8, pos0.get())
				);
				return results;
			}
		}

		throw new UnsupportedOperationException("Crafting recipes must be rectangular");
	}

	static boolean matches(@NotNull ComplexRecipe complexRecipe, @NotNull ItemStack @NotNull [] items)
	{
		Preconditions.checkArgument(complexRecipe != null, "The recipe cannot be null");
		Preconditions.checkArgument(items != null, "The craftingMatrix cannot be null");

		// TODO: Logic for complex recipes

		return false;
	}

}
