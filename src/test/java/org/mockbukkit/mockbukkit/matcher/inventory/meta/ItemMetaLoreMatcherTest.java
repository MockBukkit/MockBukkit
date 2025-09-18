package org.mockbukkit.mockbukkit.matcher.inventory.meta;

import net.kyori.adventure.text.Component;
import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockbukkit.mockbukkit.MockBukkitExtension;
import org.mockbukkit.mockbukkit.MockBukkitInject;
import org.mockbukkit.mockbukkit.inventory.meta.ItemMetaMock;
import org.mockbukkit.testutils.matcher.AbstractMatcherTest;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockbukkit.mockbukkit.matcher.inventory.meta.ItemMetaLoreMatcher.doesNotHaveLore;
import static org.mockbukkit.mockbukkit.matcher.inventory.meta.ItemMetaLoreMatcher.hasLore;

@ExtendWith(MockBukkitExtension.class)
class ItemMetaLoreMatcherTest extends AbstractMatcherTest
{

	@MockBukkitInject
	private ItemMetaMock itemMeta;

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

	@Test
	void doesntHaveLore_String()
	{
		itemMeta.setLore(List.of("Hello"));

		assertDoesNotMatch(doesNotHaveLore("Hello"), itemMeta);
		assertMatches(doesNotHaveLore("World"), itemMeta);
		assertMatches(doesNotHaveLore("Hello", "World"), itemMeta);
	}

	@Test
	void doesntHaveLore_Component()
	{
		itemMeta.setLore(List.of("Hello"));

		assertDoesNotMatch(doesNotHaveLore(Component.text("Hello")), itemMeta);

		assertMatches(doesNotHaveLore(Component.text("World")), itemMeta);
		assertMatches(doesNotHaveLore(Component.text("Hello"), Component.text("World")), itemMeta);
		assertMatches(doesNotHaveLore(List.of(Component.text("Hello"), Component.text("World"))), itemMeta);
	}

	@Test
	void doesntMatchNullLore()
	{
		itemMeta.setLore(null);
		assertMatches(doesNotHaveLore(Component.text("Hello")), itemMeta);
	}

	@Nested
	class DescriptionTests
	{

		// Test implementation of Description to capture what gets written
		private static class TestDescription implements Description
		{

			private final StringBuilder text = new StringBuilder();

			@Override
			public Description appendText(String text)
			{
				this.text.append(text);
				return this;
			}

			@Override
			public Description appendDescriptionOf(org.hamcrest.SelfDescribing value)
			{
				return this;
			}

			@Override
			public Description appendValue(Object value)
			{
				text.append(value);
				return this;
			}

			@SafeVarargs
			@Override
			public final <T> Description appendValueList(String start, String separator, String end, T... values)
			{
				return appendValueList(start, separator, end, Arrays.stream(values).toList());
			}

			@Override
			public <T> Description appendValueList(String start, String separator, String end, Iterable<T> values)
			{
				text.append(start);
				boolean first = true;
				for (T value : values)
				{
					if (!first)
					{
						text.append(separator);
					}
					text.append(value);
					first = false;
				}
				text.append(end);
				return this;
			}

			@Override
			public Description appendList(String start, String separator, String end, Iterable<? extends org.hamcrest.SelfDescribing> values)
			{
				return this;
			}

			String getText()
			{
				return text.toString();
			}

		}

		@Test
		void describeTo_shouldAppendExpectedLoreDescription()
		{
			List<Component> targetLore = List.of(Component.text("First line"), Component.text("Second line"));
			ItemMetaLoreMatcher matcher = new ItemMetaLoreMatcher(targetLore);
			TestDescription description = new TestDescription();

			matcher.describeTo(description);

			assertTrue(description.getText().startsWith("to have the following lore ["));
			assertTrue(description.getText().contains("First line"));
			assertTrue(description.getText().contains("Second line"));
			assertTrue(description.getText().endsWith("]"));
		}

		@Test
		void describeMismatchSafely_shouldAppendActualLoreDescription()
		{
			List<Component> targetLore = List.of(Component.text("Expected"));
			ItemMetaLoreMatcher matcher = new ItemMetaLoreMatcher(targetLore);
			TestDescription description = new TestDescription();

			itemMeta.setLore(List.of("Actual", "Different"));

			matcher.describeMismatchSafely(itemMeta, description);

			assertTrue(description.getText().startsWith("had lore ["));
			assertTrue(description.getText().contains("Actual"));
			assertTrue(description.getText().contains("Different"));
			assertTrue(description.getText().endsWith("]"));
		}

		@Test
		void hasLore_Component_matches()
		{
			itemMeta.setLore(List.of("Hello", "world!"));
			assertTrue(hasLore(Component.text("Hello"), Component.text("world!")).matches(itemMeta));
		}

		@Test
		void hasLore_Component_wrongLore()
		{
			itemMeta.setLore(List.of("Hello", "different!"));
			assertFalse(hasLore(Component.text("Hello"), Component.text("world!")).matches(itemMeta));
		}

		@Test
		void hasLore_List_matches()
		{
			List<Component> lore = List.of(Component.text("Hello"), Component.text("world!"));
			itemMeta.setLore(List.of("Hello", "world!"));
			assertTrue(hasLore(lore).matches(itemMeta));
		}

		@Test
		void hasLore_List_wrongLore()
		{
			List<Component> lore = List.of(Component.text("Hello"), Component.text("world!"));
			itemMeta.setLore(List.of("Hello", "different!"));
			assertFalse(hasLore(lore).matches(itemMeta));
		}

		@Test
		void doesNotHaveLore_List_matches()
		{
			List<Component> lore = List.of(Component.text("Hello"), Component.text("world!"));
			itemMeta.setLore(List.of("Different", "content"));
			assertTrue(doesNotHaveLore(lore).matches(itemMeta));
		}

	}

}
