package org.mockbukkit.metaminer.json.recipe;

import com.google.gson.JsonObject;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.SmithingRecipe;
import org.jetbrains.annotations.Nullable;
import org.mockbukkit.metaminer.json.ItemStackElementFactory;
import org.mockbukkit.metaminer.json.KeyedElementFactory;

public class SmithingRecipeElementFactory
{

	/**
	 * Converts a smithing recipe into a JsonElement.
	 *
	 * @param smithingRecipe The smithing recipe to be converted.
	 * @return The element
	 */
	@Nullable
	public static JsonObject toJson(@Nullable SmithingRecipe smithingRecipe)
	{
		if (smithingRecipe == null)
		{
			return null;
		}

		JsonObject json = new JsonObject();
		json.add("key", KeyedElementFactory.toJson(smithingRecipe.getKey()));
		json.add("base", RecipeChoiceElementFactory.toJson(smithingRecipe.getBase()));
		json.add("addition", RecipeChoiceElementFactory.toJson(smithingRecipe.getAddition()));
		json.add("result", ItemStackElementFactory.toJson(smithingRecipe.getResult()));
		json.addProperty("copyDataComponents", smithingRecipe.willCopyDataComponents());

		return json;
	}

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

		if (recipe instanceof SmithingRecipe smithingRecipe)
		{
			return toJson(smithingRecipe);
		}

		throw new IllegalArgumentException("Recipe is not a SmithingRecipe");
	}

	private SmithingRecipeElementFactory()
	{
		// Hide the public constructor
	}

}
