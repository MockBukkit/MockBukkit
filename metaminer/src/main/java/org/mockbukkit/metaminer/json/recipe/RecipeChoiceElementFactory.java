package org.mockbukkit.metaminer.json.recipe;

import com.google.gson.JsonObject;
import org.bukkit.inventory.RecipeChoice;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.mockbukkit.metaminer.json.CollectionElementFactory;
import org.mockbukkit.metaminer.json.ItemStackElementFactory;

public class RecipeChoiceElementFactory
{

	/**
	 * Converts a recipe choice into a JsonElement.
	 *
	 * @param recipeChoice The recipe choice to be converted.
	 * @return The element
	 */
	@Nullable
	public static JsonObject toJson(@Nullable RecipeChoice recipeChoice)
	{
		if (recipeChoice == null)
		{
			return null;
		}

		if (recipeChoice instanceof RecipeChoice.MaterialChoice materialChoice)
		{
			return getMaterialChoice(materialChoice);
		}

		if (recipeChoice instanceof RecipeChoice.ExactChoice exactChoice)
		{
			return getExactChoice(exactChoice);
		}

		throw new UnsupportedOperationException("Unsupported recipe choice: " + recipeChoice.getClass().getName());
	}

	@NotNull
	private static JsonObject getMaterialChoice(RecipeChoice.MaterialChoice materialChoice)
	{
		JsonObject json = new JsonObject();
		json.addProperty("type", "material");
		json.add("choices", CollectionElementFactory.toJson(materialChoice.getChoices()));
		return json;
	}

	@NotNull
	private static JsonObject getExactChoice(RecipeChoice.ExactChoice exactChoice)
	{
		JsonObject json = new JsonObject();
		json.addProperty("type", "exact");
		json.add("choices", CollectionElementFactory.toJson(exactChoice.getChoices(), ItemStackElementFactory::toJson));
		return json;
	}

	private RecipeChoiceElementFactory()
	{
		// Hide the public constructor
	}

}
