package org.mockbukkit.metaminer.json.recipe;

import com.google.gson.JsonObject;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.StonecuttingRecipe;
import org.jetbrains.annotations.Nullable;
import org.mockbukkit.metaminer.json.ItemStackElementFactory;
import org.mockbukkit.metaminer.json.KeyedElementFactory;

public class StoneCuttingRecipeElementFactory
{

	/**
	 * Converts a stonecutting recipe into a JsonElement.
	 *
	 * @param stonecuttingRecipe The stonecutting recipe to be converted.
	 *
	 * @return The element
	 */
	@Nullable
	public static JsonObject toJson(@Nullable StonecuttingRecipe stonecuttingRecipe)
	{
		if (stonecuttingRecipe == null)
		{
			return null;
		}

		JsonObject json = new JsonObject();
		json.add("key", KeyedElementFactory.toJson(stonecuttingRecipe.getKey()));
		json.add("input", RecipeChoiceElementFactory.toJson(stonecuttingRecipe.getInputChoice()));
		json.add("result", ItemStackElementFactory.toJson(stonecuttingRecipe.getResult()));
		json.addProperty("group", stonecuttingRecipe.getGroup());

		return json;
	}

	/**
	 * Converts a recipe into a JsonElement.
	 *
	 * @param recipe The recipe to be converted.
	 *
	 * @return The element
	 */
	@Nullable
	public static JsonObject toJson(@Nullable Recipe recipe)
	{
		if (recipe == null)
		{
			return null;
		}

		if (recipe instanceof StonecuttingRecipe stonecuttingRecipe)
		{
			return toJson(stonecuttingRecipe);
		}

		throw new IllegalArgumentException("Recipe is not a StonecuttingRecipe");
	}

	private StoneCuttingRecipeElementFactory()
	{
		// Hide the public constructor
	}

}
