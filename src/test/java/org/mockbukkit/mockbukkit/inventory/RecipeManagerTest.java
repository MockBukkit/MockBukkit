package org.mockbukkit.mockbukkit.inventory;

import com.google.common.base.Preconditions;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ComplexRecipe;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.ShapelessRecipe;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.EnumSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockbukkit.mockbukkit.MockBukkitExtension;

import java.util.EnumMap;
import java.util.Map;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(MockBukkitExtension.class)
class RecipeManagerTest
{

	private static final Material __ = null; // `_` is a keyword
	private final RecipeManager manager = new RecipeManager();

	private static ItemStack[] createCrafting(Material... slots)
	{
		Preconditions.checkArgument(slots.length == 9, "The crafting table should have 9 items");

		return Stream.of(slots)
				.map(m -> (m == null ? ItemStack.empty() : ItemStack.of(m)))
				.toArray(ItemStack[]::new);
	}

	@Nested
	class Reset
	{

		@Test
		void givenNullRecipeType()
		{
			IllegalArgumentException e = assertThrows(IllegalArgumentException.class, () -> manager.reset(null));
			assertEquals("Recipe type cannot be null", e.getMessage());
		}

		@ParameterizedTest
		@EnumSource(RecipeType.class)
		void givenResetToRecipeType(RecipeType type)
		{
			int initialSize = manager.getRecipes(type).size();
			manager.clearRecipes(type);
			assertTrue(manager.getRecipes(type).isEmpty());

			manager.reset(type);

			assertEquals(initialSize, manager.getRecipes(type).size());
		}

		@Test
		void givenGenericReset()
		{
			Map<RecipeType, Integer> initialSizes = new EnumMap<>(RecipeType.class);

			for (RecipeType type : RecipeType.values())
			{
				initialSizes.put(type, manager.getRecipes(type).size());
				manager.clearRecipes(type);
				assertTrue(manager.getRecipes(type).isEmpty());
			}

			manager.reset();

			for (RecipeType type : RecipeType.values())
			{
				assertEquals(initialSizes.get(type), manager.getRecipes(type).size());
			}
		}

	}

	@Nested
	class Clear
	{

		@Test
		void clearOnlyOneSingleRecipeType()
		{
			assertFalse(manager.getRecipes(RecipeType.CRAFTING).isEmpty());

			manager.getRecipes(RecipeType.CRAFTING).clear();

			assertTrue(manager.getRecipes(RecipeType.CRAFTING).isEmpty());
		}

		@Test
		void clearAllRecipeType()
		{
			assertFalse(manager.getRecipes(RecipeType.CRAFTING).isEmpty());

			manager.getRecipes().clear();

			assertTrue(manager.getRecipes(RecipeType.CRAFTING).isEmpty());
		}

	}

	@Nested
	class GetRecipesFor
	{

		@Test
		void testPreconditions()
		{
			ItemStack itemStack = ItemStack.of(Material.AIR);
			assertThrows(
					IllegalArgumentException.class,
					() -> manager.getRecipesFor(null, itemStack)
			);

			assertThrows(
					IllegalArgumentException.class,
					() -> manager.getRecipesFor(RecipeType.CRAFTING, null)
			);
		}

		@Test
		void testShaped()
		{
			var recipes = manager.getRecipesFor(RecipeType.CRAFTING, ItemStack.of(Material.ACACIA_BOAT));

			assertEquals(1, recipes.size());
			assertInstanceOf(ShapedRecipe.class, recipes.getFirst());
		}

		@Test
		void testComplex()
		{
			var recipes = manager.getRecipesFor(RecipeType.CRAFTING, ItemStack.of(Material.AIR));

			assertTrue(recipes.size() > 10);  // It's 11 for now, but likely to change in the future
			assertTrue(recipes.stream().anyMatch(r -> r instanceof ComplexRecipe));
		}

		@Test
		void testShapeless()
		{
			var recipes = manager.getRecipesFor(RecipeType.CRAFTING, ItemStack.of(Material.LIGHT_GRAY_DYE));
			assertTrue(recipes.size() > 2);
			assertTrue(recipes.stream().anyMatch(r -> r instanceof ShapelessRecipe));
		}

	}

	@Nested
	class GetCraftingRecipe
	{

		@Test
		void givenNullCraftMatrix()
		{
			IllegalArgumentException e = assertThrows(IllegalArgumentException.class, () -> manager.getCraftingRecipe(null));
			assertEquals("craftingMatrix must not be null", e.getMessage());
		}

		@ParameterizedTest
		@ValueSource(ints = {
				0, 1, 2, 3, 4, 5, 6, 7, 8, 10, 11, 12, 13, 14, 15
		})
		void givenCraftMatrixWithout9Slots(int itemsAmount)
		{
			IllegalArgumentException e = assertThrows(IllegalArgumentException.class, () -> manager.getCraftingRecipe(new ItemStack[itemsAmount]));
			assertEquals("craftingMatrix must be an array of length 9", e.getMessage());
		}

		@Nested
		class ShapelessRecipe
		{

			@ParameterizedTest
			@CsvSource({
					"OAK_LOG, OAK_PLANKS",
					"SPRUCE_LOG, SPRUCE_PLANKS",
					"BIRCH_LOG, BIRCH_PLANKS",
					"JUNGLE_LOG, JUNGLE_PLANKS",
					"ACACIA_LOG, ACACIA_PLANKS",
					"CHERRY_LOG, CHERRY_PLANKS",
					"PALE_OAK_LOG, PALE_OAK_PLANKS",
					"DARK_OAK_LOG, DARK_OAK_PLANKS",
					"MANGROVE_LOG, MANGROVE_PLANKS"
			})
			void givenLogs(Material log, Material planks)
			{
				Recipe recipe = manager.getCraftingRecipe(createCrafting(
						__, log, __,
						__, __, __,
						__, __, __
				));

				assertNotNull(recipe);
				assertEquals(planks, recipe.getResult().getType());
			}

			@ParameterizedTest
			@CsvSource({
					"OAK_PLANKS, OAK_BUTTON",
					"SPRUCE_PLANKS, SPRUCE_BUTTON",
					"BIRCH_PLANKS, BIRCH_BUTTON",
					"JUNGLE_PLANKS, JUNGLE_BUTTON",
					"ACACIA_PLANKS, ACACIA_BUTTON",
					"CHERRY_PLANKS, CHERRY_BUTTON",
					"DARK_OAK_PLANKS, DARK_OAK_BUTTON",
					"PALE_OAK_PLANKS, PALE_OAK_BUTTON",
					"MANGROVE_PLANKS, MANGROVE_BUTTON",
					"BAMBOO_PLANKS, BAMBOO_BUTTON",
					"CRIMSON_PLANKS, CRIMSON_BUTTON",
					"WARPED_PLANKS, WARPED_BUTTON",
			})
			void givenButton(Material planks, Material button)
			{
				Recipe recipe = manager.getCraftingRecipe(createCrafting(
						__, __, __,
						__, planks, __,
						__, __, __
				));

				assertNotNull(recipe);
				assertEquals(button, recipe.getResult().getType());
			}

			@Test
			void givenSugar()
			{
				Recipe recipe = manager.getCraftingRecipe(createCrafting(
						__, __, __,
						__, __, __,
						__, __, Material.SUGAR_CANE
				));

				assertNotNull(recipe);
				assertEquals(Material.SUGAR, recipe.getResult().getType());
			}

			@Test
			void givenSuspiciousStew()
			{
				Recipe recipe = manager.getCraftingRecipe(createCrafting(
						Material.RED_MUSHROOM, __, Material.BOWL,
						__, __, __,
						Material.ALLIUM, __, Material.BROWN_MUSHROOM
				));

				assertNotNull(recipe);
				assertEquals(Material.SUSPICIOUS_STEW, recipe.getResult().getType());
			}

			@Test
			void givenTntMinecart()
			{
				Recipe recipe = manager.getCraftingRecipe(createCrafting(
						__, __, __,
						Material.TNT, __, __,
						Material.MINECART, __, __
				));

				assertNotNull(recipe);
				assertEquals(Material.TNT_MINECART, recipe.getResult().getType());
			}

			@Test
			void givenTrappedChest()
			{
				Recipe recipe = manager.getCraftingRecipe(createCrafting(
						__, Material.TRIPWIRE_HOOK, Material.CHEST,
						__, __, __,
						__, __, __
				));

				assertNotNull(recipe);
				assertEquals(Material.TRAPPED_CHEST, recipe.getResult().getType());
			}

			@Test
			void givenWaxedCopper()
			{
				Recipe recipe = manager.getCraftingRecipe(createCrafting(
						__, __, __,
						__, Material.COPPER_BLOCK, __,
						Material.HONEYCOMB, __, __
				));

				assertNotNull(recipe);
				assertEquals(Material.WAXED_COPPER_BLOCK, recipe.getResult().getType());
			}

			@ParameterizedTest
			@CsvSource({
					"WHITE_DYE, WHITE_CANDLE",
					"ORANGE_DYE, ORANGE_CANDLE",
					"MAGENTA_DYE, MAGENTA_CANDLE",
					"LIGHT_BLUE_DYE, LIGHT_BLUE_CANDLE",
					"YELLOW_DYE, YELLOW_CANDLE",
					"LIME_DYE, LIME_CANDLE",
					"PINK_DYE, PINK_CANDLE",
					"GRAY_DYE, GRAY_CANDLE",
					"LIGHT_GRAY_DYE, LIGHT_GRAY_CANDLE",
					"CYAN_DYE, CYAN_CANDLE",
					"PURPLE_DYE, PURPLE_CANDLE",
					"BLUE_DYE, BLUE_CANDLE",
					"BROWN_DYE, BROWN_CANDLE",
					"GREEN_DYE, GREEN_CANDLE",
					"RED_DYE, RED_CANDLE",
					"BLACK_DYE, BLACK_CANDLE",
			})
			void givenCandle(Material color, Material expectedOutput)
			{
				Recipe recipe = manager.getCraftingRecipe(createCrafting(
						__, __, color,
						__, __, __,
						Material.CANDLE, __, __
				));

				assertNotNull(recipe);
				assertEquals(expectedOutput, recipe.getResult().getType());
			}

		}

		@Nested
		class ShapedRecipe
		{

			@ParameterizedTest
			@CsvSource({
					"IRON_INGOT, IRON_DOOR",
					"OAK_PLANKS, OAK_DOOR",
					"SPRUCE_PLANKS, SPRUCE_DOOR",
					"BIRCH_PLANKS, BIRCH_DOOR",
					"JUNGLE_PLANKS, JUNGLE_DOOR",
					"ACACIA_PLANKS, ACACIA_DOOR",
					"CHERRY_PLANKS, CHERRY_DOOR",
					"DARK_OAK_PLANKS, DARK_OAK_DOOR",
					"PALE_OAK_PLANKS, PALE_OAK_DOOR",
					"MANGROVE_PLANKS, MANGROVE_DOOR",
					"BAMBOO_PLANKS, BAMBOO_DOOR",
					"CRIMSON_PLANKS, CRIMSON_DOOR",
					"WARPED_PLANKS, WARPED_DOOR",
					"COPPER_INGOT, COPPER_DOOR",
			})
			void givenDoor(Material doorMaterial, Material expectedOutput)
			{
				Recipe recipe = manager.getCraftingRecipe(createCrafting(
						doorMaterial, doorMaterial, __,
						doorMaterial, doorMaterial, __,
						doorMaterial, doorMaterial, __
				));

				assertNotNull(recipe);
				assertEquals(expectedOutput, recipe.getResult().getType());
			}

			@Test
			void givenSticks()
			{
				Recipe recipe = manager.getCraftingRecipe(createCrafting(
						__, __, __,
						__, Material.OAK_PLANKS, __,
						__, Material.OAK_PLANKS, __
				));

				assertNotNull(recipe);
				assertEquals(Material.STICK, recipe.getResult().getType());
			}

		}

		@Test
		void invalidRecipe()
		{
			Recipe recipe = manager.getCraftingRecipe(createCrafting(
					__, __, __,
					__, __, __,
					__, __, __
			));

			assertNull(recipe);
		}

	}

	@Nested
	class Matches
	{

		@Nested
		class Shapeless
		{

			private final ShapelessRecipe recipe = (ShapelessRecipe) Bukkit.getRecipe(NamespacedKey.minecraft("oak_button"));

			@Test
			void givenNullRecipe()
			{
				IllegalArgumentException e = assertThrows(IllegalArgumentException.class, () -> RecipeManager.matches((ShapelessRecipe) null, new ItemStack[0]));
				assertEquals("The recipe cannot be null", e.getMessage());
			}

			@Test
			void givenNullCraftMatrix()
			{
				IllegalArgumentException e = assertThrows(IllegalArgumentException.class, () -> RecipeManager.matches(recipe, null));
				assertEquals("The craftingMatrix cannot be null", e.getMessage());
			}

			@Test
			void givenValidCraftMatrix()
			{
				ItemStack[] matrix = createCrafting(
						__, __, __,
						__, __, __,
						__, __, Material.OAK_PLANKS
				);
				boolean result = RecipeManager.matches(recipe, matrix);
				assertTrue(result);
			}

			@Test
			void givenInvalidCraftMatrix()
			{
				ItemStack[] matrix = createCrafting(
						__, __, __,
						__, __, __,
						__, __, Material.BIRCH_PLANKS
				);
				boolean result = RecipeManager.matches(recipe, matrix);
				assertFalse(result);
			}

		}

		@Nested
		class Shaped
		{

			private final ShapedRecipe recipe = (ShapedRecipe) Bukkit.getRecipe(NamespacedKey.minecraft("stick"));

			@Test
			void givenNullRecipe()
			{
				IllegalArgumentException e = assertThrows(IllegalArgumentException.class, () -> RecipeManager.matches((ShapedRecipe) null, new ItemStack[0]));
				assertEquals("The recipe cannot be null", e.getMessage());
			}

			@Test
			void givenNullCraftMatrix()
			{
				IllegalArgumentException e = assertThrows(IllegalArgumentException.class, () -> RecipeManager.matches(recipe, null));
				assertEquals("The craftingMatrix cannot be null", e.getMessage());
			}

			@Test
			void givenValidCraftMatrix()
			{
				ItemStack[] matrix = createCrafting(
						__, __, __,
						__, __, Material.OAK_PLANKS,
						__, __, Material.OAK_PLANKS
				);
				boolean result = RecipeManager.matches(recipe, matrix);
				assertTrue(result);
			}

			@Test
			void givenInvalidCraftMatrix()
			{
				ItemStack[] matrix = createCrafting(
						__, __, __,
						__, Material.BIRCH_PLANKS, __,
						__, __, Material.BIRCH_PLANKS
				);
				boolean result = RecipeManager.matches(recipe, matrix);
				assertFalse(result);
			}

			@Test
			void givenRecipeWithSpaces()
			{
				// Create a new recipe with spaces to test space handling
				NamespacedKey key = new NamespacedKey("test", "space_recipe");
				ShapedRecipe newRecipe = new ShapedRecipe(key, new ItemStack(Material.STICK));

				newRecipe.shape("X X", "   ", "X X");
				newRecipe.setIngredient('X', Material.STONE);

				// Test matrix that should match the pattern (spaces should be empty)
				ItemStack[] validMatrix = createCrafting(
						Material.STONE, __, Material.STONE,
						__, __, __,
						Material.STONE, __, Material.STONE
				);

				assertTrue(RecipeManager.matches(newRecipe, validMatrix));

				// Test matrix with items in space positions (should fail)
				ItemStack[] invalidMatrix = createCrafting(
						Material.STONE, Material.DIRT, Material.STONE,
						__, __, __,
						Material.STONE, __, Material.STONE
				);

				assertFalse(RecipeManager.matches(newRecipe, invalidMatrix));
			}

			@Nested
			class GivenValidSamples
			{

				@Test
				void givenSticks()
				{
					ItemStack[] matrix = createCrafting(
							__, __, __,
							__, __, Material.OAK_PLANKS,
							__, __, Material.OAK_PLANKS
					);
					boolean result = RecipeManager.matches(recipe, matrix);
					assertTrue(result);
				}

				@Test
				void givenSticksWithDifferentMaterials()
				{
					ItemStack[] matrix = createCrafting(
							Material.BIRCH_PLANKS, __, __,
							Material.OAK_PLANKS, __, __,
							__, __, __
					);
					boolean result = RecipeManager.matches(recipe, matrix);
					assertTrue(result);
				}

				@Test
				void givenBoat()
				{
					ShapedRecipe boatRecipe = (ShapedRecipe) Bukkit.getRecipe(NamespacedKey.minecraft("oak_boat"));

					ItemStack[] matrix = createCrafting(
							__, __, __,
							Material.OAK_PLANKS, __, Material.OAK_PLANKS,
							Material.OAK_PLANKS, Material.OAK_PLANKS, Material.OAK_PLANKS
					);
					boolean result = RecipeManager.matches(boatRecipe, matrix);
					assertTrue(result);
				}

				@Test
				void givenDoor()
				{
					ShapedRecipe doorRecipe = (ShapedRecipe) Bukkit.getRecipe(NamespacedKey.minecraft("acacia_door"));

					ItemStack[] matrix = createCrafting(
							__, Material.ACACIA_PLANKS, Material.ACACIA_PLANKS,
							__, Material.ACACIA_PLANKS, Material.ACACIA_PLANKS,
							__, Material.ACACIA_PLANKS, Material.ACACIA_PLANKS
					);
					boolean result = RecipeManager.matches(doorRecipe, matrix);
					assertTrue(result);
				}

				@Test
				void givenFence()
				{
					ShapedRecipe fenceRecipe = (ShapedRecipe) Bukkit.getRecipe(NamespacedKey.minecraft("acacia_fence"));

					ItemStack[] matrix = createCrafting(
							Material.ACACIA_PLANKS, Material.STICK, Material.ACACIA_PLANKS,
							Material.ACACIA_PLANKS, Material.STICK, Material.ACACIA_PLANKS,
							__, __, __
					);
					boolean result = RecipeManager.matches(fenceRecipe, matrix);
					assertTrue(result);
				}

				@Test
				void givenBow()
				{
					ShapedRecipe fenceRecipe = (ShapedRecipe) Bukkit.getRecipe(NamespacedKey.minecraft("bow"));

					ItemStack[] matrix = createCrafting(
							__, Material.STICK, Material.STRING,
							Material.STICK, __, Material.STRING,
							__, Material.STICK, Material.STRING
					);
					boolean result = RecipeManager.matches(fenceRecipe, matrix);
					assertTrue(result);
				}

				@Test
				void givenBowFlipped()
				{
					ShapedRecipe fenceRecipe = (ShapedRecipe) Bukkit.getRecipe(NamespacedKey.minecraft("bow"));

					ItemStack[] matrix = createCrafting(
							Material.STRING, Material.STICK, __,
							Material.STRING, __, Material.STICK,
							Material.STRING, Material.STICK, __
					);
					boolean result = RecipeManager.matches(fenceRecipe, matrix);
					assertTrue(result);
				}

				@Test
				void givenStairs()
				{
					ShapedRecipe fenceRecipe = (ShapedRecipe) Bukkit.getRecipe(NamespacedKey.minecraft("stone_stairs"));

					ItemStack[] matrix = createCrafting(
							Material.STONE, __, __,
							Material.STONE, Material.STONE, __,
							Material.STONE, Material.STONE, Material.STONE
					);
					boolean result = RecipeManager.matches(fenceRecipe, matrix);
					assertTrue(result);
				}

				@Test
				void givenStairsFlipped()
				{
					ShapedRecipe fenceRecipe = (ShapedRecipe) Bukkit.getRecipe(NamespacedKey.minecraft("stone_stairs"));

					ItemStack[] matrix = createCrafting(
							__, __, Material.STONE,
							__, Material.STONE, Material.STONE,
							Material.STONE, Material.STONE, Material.STONE
					);
					boolean result = RecipeManager.matches(fenceRecipe, matrix);
					assertTrue(result);
				}

			}

			@Nested
			class GivenInvalidSamples
			{

				@Test
				void givenInvalidStick()
				{
					ItemStack[] matrix = createCrafting(
							__, __, __,
							__, __, Material.STONE,
							__, __, Material.BIRCH_PLANKS
					);
					boolean result = RecipeManager.matches(recipe, matrix);
					assertFalse(result);
				}

				@Test
				void givenValidRecipeButWithExtraMaterial()
				{
					ItemStack[] matrix = createCrafting(
							__, __, Material.STONE,
							__, __, Material.OAK_PLANKS,
							__, __, Material.OAK_PLANKS
					);
					boolean result = RecipeManager.matches(recipe, matrix);
					assertFalse(result);
				}

			}

		}

		@Nested
		class Complex
		{

			private final ComplexRecipe recipe = (ComplexRecipe) Bukkit.getRecipe(NamespacedKey.minecraft("shield_decoration"));

			@Test
			void givenNullRecipe()
			{
				IllegalArgumentException e = assertThrows(IllegalArgumentException.class, () -> RecipeManager.matches((ComplexRecipe) null, new ItemStack[0]));
				assertEquals("The recipe cannot be null", e.getMessage());
			}

			@Test
			void givenNullCraftMatrix()
			{
				IllegalArgumentException e = assertThrows(IllegalArgumentException.class, () -> RecipeManager.matches(recipe, null));
				assertEquals("The craftingMatrix cannot be null", e.getMessage());
			}

		}

	}

}
