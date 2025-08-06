package org.mockbukkit.mockbukkit.matcher.inventory.meta;

import org.hamcrest.Matcher;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockbukkit.mockbukkit.MockBukkitExtension;
import org.mockbukkit.mockbukkit.MockBukkitInject;
import org.mockbukkit.mockbukkit.inventory.meta.ItemMetaMock;
import org.mockbukkit.testutils.matcher.AbstractMatcherTest;

import java.util.List;

import static org.mockbukkit.mockbukkit.matcher.inventory.meta.ItemMetaAnyLoreMatcher.hasAnyLore;

@ExtendWith(MockBukkitExtension.class)
class ItemMetaAnyLoreMatcherTest extends AbstractMatcherTest
{

	@MockBukkitInject
	private ItemMetaMock itemMeta;

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
