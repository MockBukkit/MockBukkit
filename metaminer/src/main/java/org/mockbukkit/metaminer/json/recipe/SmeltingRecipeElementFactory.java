package org.mockbukkit.metaminer.json.recipe;

import com.google.gson.JsonObject;
import org.bukkit.inventory.FurnaceRecipe;
import org.bukkit.inventory.Recipe;
import org.jetbrains.annotations.Nullable;

public class SmeltingRecipeElementFactory
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

		if (recipe instanceof FurnaceRecipe smeltingRecipe)
		{
			return CookingRecipeElementFactory.toJson(smeltingRecipe);
		}

		throw new IllegalArgumentException("Recipe is not a FurnaceRecipe");
	}

	private SmeltingRecipeElementFactory()
	{
		// Hide the public constructor
	}

}
