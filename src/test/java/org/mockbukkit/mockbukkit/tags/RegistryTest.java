package org.mockbukkit.mockbukkit.tags;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertFalse;

import org.bukkit.Tag;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import org.mockbukkit.mockbukkit.MockBukkitExtension;

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
			if (getEmptyTags().noneMatch(tag::equals))
			{
				assertFalse(tag.getValues().isEmpty(), "Expected Tag \"" + tag + "\" not to be empty");
			}
		}
	}

	static Stream<Tag<?>> getEmptyTags()
	{
		return Stream.of(Tag.INCORRECT_FOR_NETHERITE_TOOL, Tag.INCORRECT_FOR_DIAMOND_TOOL);
	}

}
