package org.mockbukkit.mockbukkit.inventory;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.bukkit.Material;
import org.bukkit.inventory.ComplexRecipe;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.ShapelessRecipe;
import org.bukkit.inventory.TransmuteRecipe;
import org.bukkit.inventory.recipe.CraftingBookCategory;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockbukkit.mockbukkit.MockBukkitExtension;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(MockBukkitExtension.class)
class CraftingRecipeFactoryTest
{

	@Test
	void createShapedRecipe()
	{
		String text = """
				{
				  		"category": "MISC",
				  		"choiceMap": {
				  			"a": {
				  				"choices": [
				  					"minecraft:acacia_planks"
				  				],
				  				"type": "material"
				  			},
				  			"b": {
				  				"choices": [
				  					"minecraft:stick"
				  				],
				  				"type": "material"
				  			},
				  			"c": {
				  				"choices": [
				  					"minecraft:acacia_planks"
				  				],
				  				"type": "material"
				  			},
				  			"d": {
				  				"choices": [
				  					"minecraft:acacia_planks"
				  				],
				  				"type": "material"
				  			},
				  			"e": {
				  				"choices": [
				  					"minecraft:stick"
				  				],
				  				"type": "material"
				  			},
				  			"f": {
				  				"choices": [
				  					"minecraft:acacia_planks"
				  				],
				  				"type": "material"
				  			}
				  		},
				  		"group": "wooden_fence",
				  		"key": "minecraft:acacia_fence",
				  		"result": {
				  			"amount": 3,
				  			"type": "minecraft:acacia_fence"
				  		},
				  		"shape": [
				  			"abc",
				  			"def"
				  		],
				  		"type": "shaped"
				  	}

				""";
		JsonObject jsonObject = JsonParser.parseString(text).getAsJsonObject();
		ShapedRecipe recipe = CraftingRecipeFactory.createShapedRecipe(jsonObject);

		assertNotNull(recipe);
		assertEquals("minecraft:acacia_fence", recipe.getKey().asString());
		assertEquals(3, recipe.getResult().getAmount());
		assertEquals(Material.ACACIA_FENCE, recipe.getResult().getType());
		assertEquals("wooden_fence", recipe.getGroup());
		assertEquals(CraftingBookCategory.MISC, recipe.getCategory());
		assertArrayEquals(new String[]{ "abc", "def" }, recipe.getShape());
		assertEquals(6, recipe.getChoiceMap().size());
	}

	@Test
	void createShapelessRecipe()
	{
		String text = """
				{
					"category": "BUILDING",
					"choices": [
						{
							"choices": [
								"minecraft:diorite"
							],
							"type": "material"
						},
						{
							"choices": [
								"minecraft:cobblestone"
							],
							"type": "material"
						}
					],
					"group": "",
					"key": "minecraft:andesite",
					"result": {
						"amount": 2,
						"type": "minecraft:andesite"
					},
					"type": "shapeless"
				}
				""";
		JsonObject jsonObject = JsonParser.parseString(text).getAsJsonObject();
		ShapelessRecipe recipe = CraftingRecipeFactory.createShapelessRecipe(jsonObject);

		assertNotNull(recipe);
		assertEquals("minecraft:andesite", recipe.getKey().asString());
		assertEquals(2, recipe.getResult().getAmount());
		assertEquals(Material.ANDESITE, recipe.getResult().getType());
		assertEquals("", recipe.getGroup());
		assertEquals(CraftingBookCategory.BUILDING, recipe.getCategory());
		assertEquals(2, recipe.getChoiceList().size());
	}

	@Test
	void createTransmuteRecipe()
	{
		String text = """
				{
					"category": "MISC",
					"group": "",
					"input": {
						"choices": [
							"minecraft:black_bundle",
							"minecraft:blue_bundle",
							"minecraft:brown_bundle",
							"minecraft:bundle",
							"minecraft:cyan_bundle",
							"minecraft:gray_bundle",
							"minecraft:green_bundle",
							"minecraft:light_blue_bundle",
							"minecraft:light_gray_bundle",
							"minecraft:lime_bundle",
							"minecraft:magenta_bundle",
							"minecraft:orange_bundle",
							"minecraft:pink_bundle",
							"minecraft:purple_bundle",
							"minecraft:red_bundle",
							"minecraft:white_bundle",
							"minecraft:yellow_bundle"
						],
						"type": "material"
					},
					"key": "minecraft:black_bundle",
					"material": {
						"choices": [
							"minecraft:black_dye"
						],
						"type": "material"
					},
					"result": {
						"amount": 1,
						"type": "minecraft:black_bundle"
					},
					"type": "transmute"
				}
				""";
		JsonObject jsonObject = JsonParser.parseString(text).getAsJsonObject();
		TransmuteRecipe recipe = CraftingRecipeFactory.createTransmuteRecipe(jsonObject);

		assertNotNull(recipe);
		assertEquals("minecraft:black_bundle", recipe.getKey().asString());
		assertEquals(1, recipe.getResult().getAmount());
		assertEquals(Material.BLACK_BUNDLE, recipe.getResult().getType());
		assertEquals("", recipe.getGroup());
		assertEquals(CraftingBookCategory.MISC, recipe.getCategory());

		assertTrue(recipe.getInput().test(ItemStack.of(Material.BLUE_BUNDLE)));
		assertFalse(recipe.getInput().test(ItemStack.of(Material.STONE)));

		assertTrue(recipe.getMaterial().test(ItemStack.of(Material.BLACK_DYE)));
		assertFalse(recipe.getMaterial().test(ItemStack.of(Material.CYAN_DYE)));
	}

	@Test
	void createComplexRecipe()
	{
		String text = """
				{
					"category": "MISC",
					"group": "",
					"key": "minecraft:armor_dye",
					"result": {
						"amount": 0,
						"type": "minecraft:air"
					},
					"type": "complex"
				}
				""";
		JsonObject jsonObject = JsonParser.parseString(text).getAsJsonObject();
		ComplexRecipe recipe = CraftingRecipeFactory.createComplexRecipe(jsonObject);

		assertNotNull(recipe);
		assertEquals("minecraft:armor_dye", recipe.getKey().asString());
		assertEquals(0, recipe.getResult().getAmount());
		assertEquals(Material.AIR, recipe.getResult().getType());
	}

}
