package org.mockbukkit.mockbukkit.inventory;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockbukkit.mockbukkit.MockBukkitExtension;

@ExtendWith(MockBukkitExtension.class)
class RecipeManagerTest
{
	private final RecipeManager manager = new RecipeManager();

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
	class GetCraftingRecipe
	{

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
				Recipe recipe = manager.getCraftingRecipe(new ItemStack[]
						{
								ItemStack.empty(), ItemStack.of(log), ItemStack.empty(),
								ItemStack.empty(), ItemStack.empty(), ItemStack.empty(),
								ItemStack.empty(), ItemStack.empty(), ItemStack.empty()
						});

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
				Recipe recipe = manager.getCraftingRecipe(new ItemStack[]
						{
								ItemStack.empty(), ItemStack.empty(), 		ItemStack.empty(),
								ItemStack.empty(), ItemStack.of(planks), 	ItemStack.empty(),
								ItemStack.empty(), ItemStack.empty(), 		ItemStack.empty()
						});

				assertNotNull(recipe);
				assertEquals(button, recipe.getResult().getType());
			}

			@Test
			void givenSugar()
			{
				Recipe recipe = manager.getCraftingRecipe(new ItemStack[]
						{
								ItemStack.empty(), ItemStack.empty(), ItemStack.empty(),
								ItemStack.empty(), ItemStack.empty(), ItemStack.empty(),
								ItemStack.empty(), ItemStack.empty(), ItemStack.of(Material.SUGAR_CANE)
						});

				assertNotNull(recipe);
				assertEquals(Material.SUGAR, recipe.getResult().getType());
			}

			@Test
			void givenSuspiciousStew()
			{
				Recipe recipe = manager.getCraftingRecipe(new ItemStack[]
						{
								ItemStack.of(Material.RED_MUSHROOM), 	ItemStack.empty(), ItemStack.of(Material.BOWL),
								ItemStack.empty(), 						ItemStack.empty(), ItemStack.empty(),
								ItemStack.of(Material.ALLIUM), 			ItemStack.empty(), ItemStack.of(Material.BROWN_MUSHROOM)
						});

				assertNotNull(recipe);
				assertEquals(Material.SUSPICIOUS_STEW, recipe.getResult().getType());
			}

			@Test
			void givenTntMinecart()
			{
				Recipe recipe = manager.getCraftingRecipe(new ItemStack[]
						{
								ItemStack.empty(), 				ItemStack.empty(), ItemStack.empty(),
								ItemStack.of(Material.TNT), 	ItemStack.empty(), ItemStack.empty(),
								ItemStack.of(Material.MINECART),ItemStack.empty(), ItemStack.empty()
						});

				assertNotNull(recipe);
				assertEquals(Material.TNT_MINECART, recipe.getResult().getType());
			}

			@Test
			void givenTrappedChest()
			{
				Recipe recipe = manager.getCraftingRecipe(new ItemStack[]
						{
								ItemStack.empty(), ItemStack.of(Material.TRIPWIRE_HOOK),ItemStack.of(Material.CHEST),
								ItemStack.empty(), ItemStack.empty(), 					ItemStack.empty(),
								ItemStack.empty(), ItemStack.empty(), 					ItemStack.empty()
						});

				assertNotNull(recipe);
				assertEquals(Material.TRAPPED_CHEST, recipe.getResult().getType());
			}

			@Test
			void givenWaxedCopper()
			{
				Recipe recipe = manager.getCraftingRecipe(new ItemStack[]
						{
								ItemStack.empty(), 					ItemStack.empty(), 						ItemStack.empty(),
								ItemStack.empty(), 					ItemStack.of(Material.COPPER_BLOCK), 	ItemStack.empty(),
								ItemStack.of(Material.HONEYCOMB),	ItemStack.empty(), 						ItemStack.empty()
						});

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
				Recipe recipe = manager.getCraftingRecipe(new ItemStack[]
						{
								ItemStack.empty(), 					ItemStack.empty(), 	ItemStack.of(color),
								ItemStack.empty(), 					ItemStack.empty(), 	ItemStack.empty(),
								ItemStack.of(Material.CANDLE),		ItemStack.empty(), 	ItemStack.empty()
						});

				assertNotNull(recipe);
				assertEquals(expectedOutput, recipe.getResult().getType());
			}

		}

		@Nested
		@Disabled("Shaped recipes have not been implemented yet")
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
				"EXPOSED_COPPER, EXPOSED_COPPER_DOOR",
				"WEATHERED_COPPER, WEATHERED_COPPER_DOOR",
				"OXIDIZED_COPPER, OXIDIZED_COPPER_DOOR",
				"WAXED_EXPOSED_COPPER, WAXED_EXPOSED_COPPER_DOOR",
				"WAXED_WEATHERED_COPPER, WAXED_WEATHERED_COPPER_DOOR",
				"WAXED_OXIDIZED_COPPER, WAXED_OXIDIZED_COPPER_DOOR"
			})
			void givenDoor(Material doorMaterial, Material expectedOutput)
			{
				Recipe recipe = manager.getCraftingRecipe(new ItemStack[]
						{
								ItemStack.of(doorMaterial), ItemStack.of(doorMaterial), ItemStack.empty(),
								ItemStack.of(doorMaterial), ItemStack.of(doorMaterial), ItemStack.empty(),
								ItemStack.of(doorMaterial), ItemStack.of(doorMaterial), ItemStack.empty()
						});

				assertNotNull(recipe);
				assertEquals(expectedOutput, recipe.getResult().getType());
			}

			@Test
			void givenSticks()
			{
				Recipe recipe = manager.getCraftingRecipe(new ItemStack[]
						{
								ItemStack.empty(), ItemStack.empty(), 					ItemStack.empty(),
								ItemStack.empty(), ItemStack.of(Material.OAK_PLANKS), 	ItemStack.empty(),
								ItemStack.empty(), ItemStack.of(Material.OAK_PLANKS), 	ItemStack.empty()
						});

				assertNotNull(recipe);
				assertEquals(Material.STICK, recipe.getResult().getType());
			}

		}

	}

}
