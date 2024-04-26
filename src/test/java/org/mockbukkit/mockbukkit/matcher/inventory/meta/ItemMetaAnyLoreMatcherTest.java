package org.mockbukkit.mockbukkit.matcher.inventory.meta;

import org.hamcrest.Matcher;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockbukkit.mockbukkit.inventory.meta.ItemMetaMock;
import org.mockbukkit.testutils.matcher.AbstractMatcherTest;

import java.util.List;

import static org.mockbukkit.mockbukkit.matcher.inventory.meta.ItemMetaAnyLoreMatcher.hasAnyLore;

class ItemMetaAnyLoreMatcherTest extends AbstractMatcherTest
{

	private ItemMetaMock itemMeta;

	@BeforeEach
	void setUp()
	{
		this.itemMeta = new ItemMetaMock();
	}

	@Test
	void hasAnyLore_matches()
	{
		itemMeta.setLore(List.of("Hello", "world!"));
		assertMatches(hasAnyLore(), itemMeta);
	}

	@Test
	void hasAnyLore_noLore()
	{
		assertDoesNotMatch(hasAnyLore(), itemMeta);
	}

	@Test
	void nullSafe()
	{
		testIsNullSafe();
	}

	@Override
	protected Matcher<?> createMatcher()
	{
		return hasAnyLore();
	}

}
