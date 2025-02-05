package org.mockbukkit.metaminer.json.recipe;

import com.google.gson.JsonObject;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.SmokingRecipe;
import org.jetbrains.annotations.Nullable;

public class SmokingRecipeElementFactory
{

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

		if (recipe instanceof SmokingRecipe smokingRecipe)
		{
			return CookingRecipeElementFactory.toJson(smokingRecipe);
		}

		throw new IllegalArgumentException("Recipe is not a SmokingRecipe");
	}

	private SmokingRecipeElementFactory()
	{
		// Hide the public constructor
	}

}
