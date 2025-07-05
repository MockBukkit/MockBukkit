package org.mockbukkit.metaminer.json.recipe;

import com.google.gson.JsonObject;
import org.bukkit.inventory.CampfireRecipe;
import org.bukkit.inventory.Recipe;
import org.jetbrains.annotations.Nullable;

public class CampfireRecipeElementFactory
{

	/**
	 * Converts a recipe into a JsonElement.
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

		if (recipe instanceof CampfireRecipe campfireRecipe)
		{
			return CookingRecipeElementFactory.toJson(campfireRecipe);
		}

		throw new IllegalArgumentException("Recipe is not a CampfireRecipe");
	}

	private CampfireRecipeElementFactory()
	{
		// Hide the public constructor
	}

}
