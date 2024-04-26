package org.mockbukkit.mockbukkit.matcher.inventory;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.hamcrest.Matcher;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockbukkit.mockbukkit.MockBukkitExtension;
import org.mockbukkit.testutils.matcher.AbstractMatcherTest;

import static org.mockbukkit.mockbukkit.matcher.inventory.ItemSimilarityMatcher.similarTo;

@ExtendWith(MockBukkitExtension.class)
class ItemSimilarityMatcherTest extends AbstractMatcherTest
{

	private ItemStack item;

	@BeforeEach
	void setUp()
	{
		this.item = new ItemStack(Material.POTATO);
	}

	@Test
	void similarTo_matchesSame()
	{
		assertMatches(similarTo(item), item);
	}

	@Test
	void similarTo_matchesSimilar()
	{
		assertMatches(similarTo(new ItemStack(Material.POTATO, 30)), item);
	}

	@Test
	void similarTo_notSimilar()
	{
		assertDoesNotMatch(similarTo(Material.ITEM_FRAME), item);
	}

	@Test
	void nullSafe()
	{
		testIsNullSafe();
	}

	@Override
	protected Matcher<?> createMatcher()
	{
		return similarTo(Material.APPLE);
	}

}
