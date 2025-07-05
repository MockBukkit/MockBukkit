package org.mockbukkit.metaminer.json.recipe;

import com.google.common.base.Preconditions;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import org.bukkit.inventory.ComplexRecipe;
import org.bukkit.inventory.CraftingRecipe;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.ShapelessRecipe;
import org.bukkit.inventory.TransmuteRecipe;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.jetbrains.annotations.Nullable;
import org.mockbukkit.metaminer.json.CollectionElementFactory;
import org.mockbukkit.metaminer.json.EnumElementFactory;
import org.mockbukkit.metaminer.json.ItemStackElementFactory;
import org.mockbukkit.metaminer.json.KeyedElementFactory;
import org.mockbukkit.metaminer.json.MapElementFactory;

import java.util.stream.Stream;

public class CraftingRecipeElementFactory
{

	/**
	 * Converts a crafting recipe into a JsonElement.
	 *
	 * @param craftingRecipe The crafting recipe to be converted.
	 * @return The element
	 */
	@Nullable
	public static JsonObject toJson(@Nullable CraftingRecipe craftingRecipe)
	{
		if (craftingRecipe == null)
		{
			return null;
		}

		JsonObject json = new JsonObject();

		json.add("key", KeyedElementFactory.toJson(craftingRecipe.getKey()));
		json.add("category", EnumElementFactory.toJson(craftingRecipe.getCategory()));
		json.add("result", ItemStackElementFactory.toJson(craftingRecipe.getResult()));
		json.addProperty("group", craftingRecipe.getGroup());
		populateWithCraftingShape(json, craftingRecipe);

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

		if (recipe instanceof CraftingRecipe craftingRecipe)
		{
			return toJson(craftingRecipe);
		}

		throw new IllegalArgumentException("Recipe is not a CraftingRecipe");
	}

	private static void populateWithCraftingShape(@NonNull JsonObject json, @Nullable CraftingRecipe craftingRecipe)
	{
		Preconditions.checkNotNull(json, "The json cannot be null");
		switch (craftingRecipe)
		{
		case null ->
		{
			// Nothing to do here
		}
		case ShapelessRecipe shapelessRecipe ->
		{
			json.addProperty("type", "shapeless");
			json.add("choices", CollectionElementFactory.toJson(shapelessRecipe.getChoiceList()));
		}
		case ShapedRecipe shapedRecipe ->
		{
			json.addProperty("type", "shaped");
			json.add("choiceMap", MapElementFactory.toJson(shapedRecipe.getChoiceMap()));

			JsonArray shapes = new JsonArray();
			Stream.of(shapedRecipe.getShape()).forEachOrdered(shapes::add);
			json.add("shape", shapes);
		}
		case TransmuteRecipe transmuteRecipe ->
		{
			json.addProperty("type", "transmute");
			json.add("input", RecipeChoiceElementFactory.toJson(transmuteRecipe.getInput()));
			json.add("material", RecipeChoiceElementFactory.toJson(transmuteRecipe.getMaterial()));
		}
		case ComplexRecipe complexRecipe -> json.addProperty("type", "complex");
		default ->
				throw new UnsupportedOperationException(String.format("Unknown recipe type: %s", craftingRecipe.getClass().getName()));
		}
	}

	private CraftingRecipeElementFactory()
	{
		// Hide the public constructor
	}

}
