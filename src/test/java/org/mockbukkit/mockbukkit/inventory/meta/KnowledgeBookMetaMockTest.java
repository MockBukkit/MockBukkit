package org.mockbukkit.mockbukkit.inventory.meta;

import org.mockbukkit.mockbukkit.MockBukkitExtension;
import org.bukkit.NamespacedKey;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(MockBukkitExtension.class)
class KnowledgeBookMetaMockTest
{

	private final int MAX_RECIPES = 32767;

	@NotNull
	@SuppressWarnings("deprecation")
	private NamespacedKey getRandomKey()
	{
		return NamespacedKey.randomKey();
	}

	@Test
	void testRecipesDefaultFalse()
	{
		KnowledgeBookMetaMock meta = new KnowledgeBookMetaMock();
		assertFalse(meta.hasRecipes());
	}

	@Test
	void testAddRecipe()
	{
		KnowledgeBookMetaMock meta = new KnowledgeBookMetaMock();
		NamespacedKey key = getRandomKey();

		assertFalse(meta.hasRecipes());
		meta.addRecipe(key);
		assertTrue(meta.hasRecipes());
	}

	@Test
	void testAddNullRecipeAndFail()
	{
		KnowledgeBookMetaMock meta = new KnowledgeBookMetaMock();
		List<NamespacedKey> recipes = Arrays.asList(null, null, null);

		assertFalse(meta.hasRecipes());
		meta.setRecipes(recipes);
		assertFalse(meta.hasRecipes());
	}

	@Test
	void testSetRecipes()
	{
		KnowledgeBookMetaMock meta = new KnowledgeBookMetaMock();
		List<NamespacedKey> recipes = Arrays.asList(getRandomKey(), getRandomKey());

		assertFalse(meta.hasRecipes());
		meta.setRecipes(recipes);
		assertTrue(meta.hasRecipes());
	}

	@Test
	void testGetRecipes()
	{
		KnowledgeBookMetaMock meta = new KnowledgeBookMetaMock();
		List<NamespacedKey> recipes = Arrays.asList(getRandomKey(), getRandomKey());
		meta.setRecipes(recipes);

		assertEquals(recipes, meta.getRecipes());
	}

	@Test
	void testTooManyRecipes()
	{
		KnowledgeBookMetaMock meta = new KnowledgeBookMetaMock();

		for (int i = 0; i < MAX_RECIPES + 50; i++)
		{
			meta.addRecipe(getRandomKey());
		}

		assertEquals(MAX_RECIPES, meta.getRecipes().size());
	}

	@Test
	void testEquals()
	{
		KnowledgeBookMetaMock meta = new KnowledgeBookMetaMock();
		assertEquals(meta, meta);
		assertNotEquals(meta, new ItemMetaMock());

		KnowledgeBookMetaMock meta2 = new KnowledgeBookMetaMock();
		assertEquals(meta, meta2);

		NamespacedKey recipe = getRandomKey();

		meta.addRecipe(recipe);
		assertNotEquals(meta, meta2);

		meta2.addRecipe(recipe);
		assertEquals(meta, meta2);
	}

}
