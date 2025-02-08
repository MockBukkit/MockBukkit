package org.mockbukkit.mockbukkit.inventory;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
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

}
