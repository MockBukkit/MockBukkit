package org.mockbukkit.mockbukkit.matcher.inventory.meta;

import org.hamcrest.Matcher;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockbukkit.mockbukkit.inventory.meta.ItemMetaMock;
import org.mockbukkit.testutils.matcher.AbstractMatcherTest;

import java.util.List;

import static org.mockbukkit.mockbukkit.matcher.inventory.meta.ItemMetaLoreMatcher.hasLore;

class ItemMetaLoreMatcherTest extends AbstractMatcherTest
{

	private ItemMetaMock itemMeta;

	@BeforeEach
	void setUp()
	{
		this.itemMeta = new ItemMetaMock();
	}

	@Test
	void hasLore_matches()
	{
		this.itemMeta.setLore(List.of("Hello", "world!"));
		assertMatches(hasLore("Hello", "world!"), itemMeta);
	}

	@Test
	void hasLore_wrongLore()
	{
		itemMeta.setLore(List.of("Hello", "wowd!"));
		assertDoesNotMatch(hasLore("Hello", "world!"), itemMeta);
	}

	@Test
	void hasLore_tooLongLore()
	{
		itemMeta.setLore(List.of("Hello", "world!", "extra"));
		assertDoesNotMatch(hasLore("Hello", "world!"), itemMeta);
	}

	@Test
	void hasLore_tooShortLore()
	{
		itemMeta.setLore(List.of("Hello"));
		assertDoesNotMatch(hasLore("Hello", "world!"), itemMeta);
	}

	@Test
	void nullSafe()
	{
		testIsNullSafe();
	}

	@Override
	protected Matcher<?> createMatcher()
	{
		return hasLore("Hello", "world!");
	}

}
