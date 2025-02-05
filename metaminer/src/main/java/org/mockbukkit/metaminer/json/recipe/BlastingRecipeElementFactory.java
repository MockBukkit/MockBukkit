package org.mockbukkit.metaminer.json.recipe;

import com.google.gson.JsonObject;
import org.bukkit.inventory.BlastingRecipe;
import org.bukkit.inventory.Recipe;
import org.jetbrains.annotations.Nullable;

public class BlastingRecipeElementFactory
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

		if (recipe instanceof BlastingRecipe blastingRecipe)
		{
			return CookingRecipeElementFactory.toJson(blastingRecipe);
		}

		throw new IllegalArgumentException("Recipe is not a BlastingRecipe");
	}

	private BlastingRecipeElementFactory()
	{
		// Hide the public constructor
	}

}
