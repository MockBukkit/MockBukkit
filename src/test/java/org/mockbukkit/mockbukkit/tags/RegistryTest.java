package org.mockbukkit.mockbukkit.tags;

import org.bukkit.Tag;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import org.mockbukkit.mockbukkit.MockBukkitExtension;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(MockBukkitExtension.class)
class RegistryTest
{

	@ParameterizedTest
	@EnumSource(TagRegistry.class)
	void testNotEmpty(@NotNull TagRegistry registry)
	{
		assertFalse(registry.isEmpty());

		for (Tag<?> tag : registry.getTags().values())
		{
			Stream<Tag<?>> emptyTags = getEmptyTags();
			boolean shouldBeEmpty = emptyTags.anyMatch(tag::equals);
			boolean isEmpty = tag.getValues().isEmpty();
			if (shouldBeEmpty)
			{
				assertTrue(isEmpty, "Expected Tag \"" + tag.key().asString() + "\" was expected to be empty");
			}
			else
			{
				assertFalse(isEmpty, "Expected Tag \"" + tag.key().asString() + "\" not to be empty");
			}
		}
	}

	static Stream<Tag<?>> getEmptyTags()
	{
		return Stream.of(
				Tag.INCORRECT_FOR_NETHERITE_TOOL,
				Tag.INCORRECT_FOR_DIAMOND_TOOL,
				Tag.SUPPORTS_FROGSPAWN,
				Tag.DEFAULT_IMMUNE_TO);
	}

}
