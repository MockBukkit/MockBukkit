package org.mockbukkit.metaminer.json.recipe;

import com.google.gson.JsonObject;
import org.bukkit.inventory.CookingRecipe;
import org.jetbrains.annotations.Nullable;
import org.mockbukkit.metaminer.json.EnumElementFactory;
import org.mockbukkit.metaminer.json.ItemStackElementFactory;
import org.mockbukkit.metaminer.json.KeyedElementFactory;

public class CookingRecipeElementFactory
{

	/**
	 * Converts a cooking recipe into a JsonElement.
	 *
	 * @param cookingRecipe The cooking recipe to be converted.
	 * @return The element
	 */
	@Nullable
	public static JsonObject toJson(@Nullable CookingRecipe<?> cookingRecipe)
	{
		if (cookingRecipe == null)
		{
			return null;
		}

		JsonObject json = new JsonObject();
		json.add("key", KeyedElementFactory.toJson(cookingRecipe.getKey()));
		json.add("category", EnumElementFactory.toJson(cookingRecipe.getCategory()));
		json.add("input", RecipeChoiceElementFactory.toJson(cookingRecipe.getInputChoice()));
		json.add("result", ItemStackElementFactory.toJson(cookingRecipe.getResult()));
		json.addProperty("group", cookingRecipe.getGroup());
		json.addProperty("experience", cookingRecipe.getExperience());
		json.addProperty("cookingTime", cookingRecipe.getCookingTime());

		return json;
	}

	private CookingRecipeElementFactory()
	{
		// Hide the public constructor
	}

}
