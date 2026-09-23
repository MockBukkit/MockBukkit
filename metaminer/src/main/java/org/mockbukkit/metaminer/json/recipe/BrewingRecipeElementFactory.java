package org.mockbukkit.metaminer.json.recipe;

import com.google.gson.JsonObject;
import org.bukkit.inventory.BrewingRecipe;
import org.bukkit.inventory.Recipe;
import org.jetbrains.annotations.Nullable;
import org.mockbukkit.metaminer.json.ItemStackElementFactory;
import org.mockbukkit.metaminer.json.KeyedElementFactory;

public class BrewingRecipeElementFactory
{

	/**
	 * Converts a brewing recipe into a JsonElement.
	 *
	 * @param recipe The recipe to be converted.
	 * @return The element
	 */
	@Nullable
	public static JsonObject toJson(@Nullable Recipe recipe)
	{
		if (recipe == null)
		{
			return null;
		}

		if (!(recipe instanceof BrewingRecipe brewingRecipe))
		{
			throw new IllegalArgumentException("Recipe is not a BrewingRecipe");
		}

		JsonObject json = new JsonObject();
		json.add("key", KeyedElementFactory.toJson(brewingRecipe.getKey()));
		json.add("ingredient", RecipeChoiceElementFactory.toJson(brewingRecipe.getIngredient()));
		json.add("input", RecipeChoiceElementFactory.toJson(brewingRecipe.getInput()));
		json.add("result", ItemStackElementFactory.toJson(brewingRecipe.getResult()));

		return json;
	}

	private BrewingRecipeElementFactory()
	{
		// Hide the public constructor
	}

}
