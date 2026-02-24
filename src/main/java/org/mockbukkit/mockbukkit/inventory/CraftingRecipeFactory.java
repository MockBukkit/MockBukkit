package org.mockbukkit.mockbukkit.inventory;

import com.google.common.base.Preconditions;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ComplexRecipe;
import org.bukkit.inventory.CraftingRecipe;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.RecipeChoice;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.ShapelessRecipe;
import org.bukkit.inventory.TransmuteRecipe;
import org.bukkit.inventory.recipe.CraftingBookCategory;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.BiFunction;

public class CraftingRecipeFactory
{

	public static final String SHAPED_TYPE = "shaped";
	public static final String SHAPELESS_TYPE = "shapeless";
	public static final String TRANSMUTE_TYPE = "transmute";
	public static final String COMPLEX_TYPE = "complex";

	private static final String FIELD_CATEGORY = "category";
	private static final String FIELD_CHOICE_MAP = "choiceMap";
	private static final String FIELD_CHOICES = "choices";
	private static final String FIELD_GROUP = "group";
	private static final String FIELD_INPUT = "input";
	private static final String FIELD_KEY = "key";
	private static final String FIELD_MATERIAL = "material";
	private static final String FIELD_RESULT = "result";
	private static final String FIELD_SHAPE = "shape";
	private static final String FIELD_TYPE = "type";

	private static final String JSON_RECIPE_IS_NULL = "jsonRecipe is null";
	private static final String JSON_RECIPE_HAS_NO_TYPE = "jsonRecipe has no type";

	@NotNull
	public static ShapedRecipe createShapedRecipe(@NotNull JsonObject jsonRecipe)
	{
		Preconditions.checkNotNull(jsonRecipe, JSON_RECIPE_IS_NULL);

		// Validate type
		Preconditions.checkArgument(jsonRecipe.has(FIELD_TYPE), JSON_RECIPE_HAS_NO_TYPE);
		String type = jsonRecipe.get(FIELD_TYPE).getAsString();
		Preconditions.checkArgument(SHAPED_TYPE.equalsIgnoreCase(type), "jsonRecipe is not a shaped recipe (%s)", type);

		// Materials
		Preconditions.checkArgument(jsonRecipe.has(FIELD_CHOICE_MAP), "jsonRecipe has no choiceMap");
		JsonObject choiceMap = jsonRecipe.get(FIELD_CHOICE_MAP).getAsJsonObject();

		// Shape
		Preconditions.checkArgument(jsonRecipe.has(FIELD_SHAPE), "jsonRecipe has no shape");
		JsonArray shapes = jsonRecipe.get(FIELD_SHAPE).getAsJsonArray();
		List<String> shapesList = shapes.asList().stream()
				.map(JsonElement::getAsString)
				.toList();

		// Create the shaped recipe
		ShapedRecipe shapedRecipe = createRecipe(jsonRecipe, ShapedRecipe::new);
		populateCraftingRecipe(jsonRecipe, shapedRecipe);
		shapedRecipe.shape(shapesList.toArray(String[]::new));

		for (var choice : choiceMap.entrySet())
		{
			String charKey = choice.getKey();
			Preconditions.checkArgument(charKey.length() == 1, "The key should have one character");
			JsonObject value = choice.getValue().getAsJsonObject();
			RecipeChoice recipeChoice = createRecipeChoice(value);
			shapedRecipe.setIngredient(charKey.charAt(0), recipeChoice);
		}

		return shapedRecipe;
	}

	@NotNull
	public static ShapelessRecipe createShapelessRecipe(@NotNull JsonObject jsonRecipe)
	{
		Preconditions.checkNotNull(jsonRecipe, JSON_RECIPE_IS_NULL);

		// Validate type
		Preconditions.checkArgument(jsonRecipe.has(FIELD_TYPE), JSON_RECIPE_HAS_NO_TYPE);
		String type = jsonRecipe.get(FIELD_TYPE).getAsString();
		Preconditions.checkArgument(SHAPELESS_TYPE.equalsIgnoreCase(type), "jsonRecipe is not a shapeless recipe (%s)", type);

		// Create the shaped recipe
		ShapelessRecipe shapelessRecipe = createRecipe(jsonRecipe, ShapelessRecipe::new);
		populateCraftingRecipe(jsonRecipe, shapelessRecipe);

		// Shape
		Preconditions.checkArgument(jsonRecipe.has(FIELD_CHOICES), "jsonRecipe has no choices");
		JsonArray choicesArray = jsonRecipe.get(FIELD_CHOICES).getAsJsonArray();
		createRecipeChoices(choicesArray).forEach(shapelessRecipe::addIngredient);

		return shapelessRecipe;
	}

	@NotNull
	public static TransmuteRecipe createTransmuteRecipe(@NotNull JsonObject jsonRecipe)
	{
		Preconditions.checkNotNull(jsonRecipe, JSON_RECIPE_IS_NULL);

		// Validate type
		Preconditions.checkArgument(jsonRecipe.has(FIELD_TYPE), JSON_RECIPE_HAS_NO_TYPE);
		String type = jsonRecipe.get(FIELD_TYPE).getAsString();
		Preconditions.checkArgument(TRANSMUTE_TYPE.equalsIgnoreCase(type), "jsonRecipe is not a shapeless recipe (%s)", type);

		// Input
		Preconditions.checkArgument(jsonRecipe.has(FIELD_INPUT), "jsonRecipe has no input");
		JsonObject inputJson = jsonRecipe.get(FIELD_INPUT).getAsJsonObject();
		RecipeChoice input = createRecipeChoice(inputJson);

		// Material
		Preconditions.checkArgument(jsonRecipe.has(FIELD_MATERIAL), "jsonRecipe has no material");
		JsonObject materialJson = jsonRecipe.get(FIELD_MATERIAL).getAsJsonObject();
		RecipeChoice material = createRecipeChoice(materialJson);

		// Create the shaped recipe
		TransmuteRecipe transmuteRecipe = createRecipe(jsonRecipe, (key, result) -> new TransmuteRecipe(key, result.getType(), input, material));
		populateCraftingRecipe(jsonRecipe, transmuteRecipe);

		return transmuteRecipe;
	}

	@NotNull
	public static ComplexRecipe createComplexRecipe(@NotNull JsonObject jsonRecipe)
	{
		// Validate type
		Preconditions.checkArgument(jsonRecipe.has(FIELD_TYPE), JSON_RECIPE_HAS_NO_TYPE);
		String type = jsonRecipe.get(FIELD_TYPE).getAsString();
		Preconditions.checkArgument(COMPLEX_TYPE.equalsIgnoreCase(type), "jsonRecipe is not a complex recipe (%s)", type);

		return createRecipe(jsonRecipe, ComplexRecipeMock::new);
	}

	private static <T> T createRecipe(@NotNull JsonObject jsonRecipe, @NonNull BiFunction<NamespacedKey, ItemStack, T> factory)
	{
		// Validate key
		Preconditions.checkArgument(jsonRecipe.has(FIELD_KEY), "jsonRecipe has no key");
		NamespacedKey key = NamespacedKey.fromString(jsonRecipe.get(FIELD_KEY).getAsString());
		Preconditions.checkArgument(key != null, "jsonRecipe has no key");

		// Validate result
		Preconditions.checkArgument(jsonRecipe.has(FIELD_RESULT), "jsonRecipe has no result");
		ItemStackMock result = ItemStackMock.fromJson(jsonRecipe.get(FIELD_RESULT).getAsJsonObject());

		return factory.apply(key, result);
	}

	private static RecipeChoice createRecipeChoice(@NotNull JsonObject value)
	{
		Preconditions.checkArgument(value.has(FIELD_TYPE), "Choice map has no type");
		String choiceType = value.get(FIELD_TYPE).getAsString();

		if (FIELD_MATERIAL.equalsIgnoreCase(choiceType))
		{
			List<Material> materials = value.get(FIELD_CHOICES).getAsJsonArray()
					.asList().stream()
					.map(JsonElement::getAsString)
					.map(NamespacedKey::fromString)
					.filter(Objects::nonNull)
					.map(NamespacedKey::getKey)
					.map(String::toUpperCase)
					.map(Material::valueOf)
					.toList();
			return new RecipeChoice.MaterialChoice(materials);
		}

		throw new UnsupportedOperationException("Unsupported choice type: " + choiceType);
	}

	private static List<RecipeChoice> createRecipeChoices(@NotNull JsonArray choicesArray)
	{
		List<RecipeChoice> choices = new ArrayList<>(choicesArray.size());

		for (var element : choicesArray)
		{
			var value = element.getAsJsonObject();
			choices.add(createRecipeChoice(value));
		}

		return choices;
	}

	private static void populateCraftingRecipe(@NonNull JsonObject jsonRecipe, @NonNull CraftingRecipe craftingRecipe)
	{
		// Group
		Preconditions.checkArgument(jsonRecipe.has(FIELD_GROUP), "jsonRecipe has no group");
		String group = jsonRecipe.get(FIELD_GROUP).getAsString();

		// category
		Preconditions.checkArgument(jsonRecipe.has(FIELD_CATEGORY), "jsonRecipe has no category");
		CraftingBookCategory category = CraftingBookCategory.valueOf(jsonRecipe.get(FIELD_CATEGORY).getAsString().toUpperCase());

		craftingRecipe.setGroup(group);
		craftingRecipe.setCategory(category);
	}

	private CraftingRecipeFactory()
	{
		// Hide the public constructor
	}

}
