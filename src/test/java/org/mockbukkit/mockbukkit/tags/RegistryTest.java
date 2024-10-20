package org.mockbukkit.mockbukkit.tags;

import org.mockbukkit.mockbukkit.MockBukkitExtension;
import org.bukkit.Material;
import org.bukkit.Tag;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import org.mockbukkit.mockbukkit.exception.TagMisconfigurationException;

import java.io.FileNotFoundException;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

@ExtendWith(MockBukkitExtension.class)
class RegistryTest
{

	@ParameterizedTest
	@EnumSource(TagRegistry.class)
	void testNotEmpty(@NotNull TagRegistry registry)
	{
		assertFalse(registry.isEmpty());

		for (TagWrapperMock tag : registry.getTags().values())
		{
			if(getEmptyTags().noneMatch(tag::equals))
			{
				assertFalse(tag.getValues().isEmpty(), "Expected Tag \"" + tag + "\" not to be empty");
			}
		}
	}

	static Stream<Tag<?>> getEmptyTags()
	{
		return Stream.of(Tag.INCORRECT_FOR_NETHERITE_TOOL, Tag.INCORRECT_FOR_DIAMOND_TOOL);
	}

	@ParameterizedTest
	@EnumSource(TagRegistry.class)
	void testForInfiniteLoops(@NotNull TagRegistry registry) throws TagMisconfigurationException
	{
		for (TagWrapperMock tag : registry.getTags().values())
		{
			assertNotCyclic(tag);
		}
	}

	@ParameterizedTest
	@EnumSource(TagRegistry.class)
	void testGetValues(@NotNull TagRegistry registry) throws TagMisconfigurationException, FileNotFoundException
	{
		for (TagWrapperMock tag : registry.getTags().values())
		{
			tag.reload();
		}

		for (TagWrapperMock tag : registry.getTags().values())
		{
			Set<Material> values = tag.getValues();
			if (getEmptyTags().noneMatch(tag::equals)) // Currently these tags are empty
			{
				assertFalse(values.isEmpty(), "Expected values in tag: " + tag.getKey());
			}
			for (Material value : tag.getValues())
			{
				// All values of our tag must be tagged
				assertTrue(tag.isTagged(value));
			}

			for (Tag<Material> sub : tag.getSubTags())
			{
				for (Material value : sub.getValues())
				{
					// All values of sub tags should be tagged by our tag too
					assertTrue(tag.isTagged(value));
				}
			}
		}
	}

	private void assertNotCyclic(@NotNull TagWrapperMock tag)
	{
		Set<TagWrapperMock> visiting = new HashSet<>();
		Set<TagWrapperMock> visited = new HashSet<>();

		if (isCyclic(visiting, visited, tag))
		{
			System.out.println("Currently visiting: " + visiting);
			System.out.println("Previously visited" + visiting);
			fail("Tag '" + tag.getKey() + "' is cyclic!");
		}
	}

	private boolean isCyclic(@NotNull Set<TagWrapperMock> visiting, @NotNull Set<TagWrapperMock> visited, @NotNull TagWrapperMock tag)
	{
		visiting.add(tag);

		for (TagWrapperMock sub : tag.getSubTags())
		{
			if (visiting.contains(sub))
			{
				return true;
			}
			else if (!visited.contains(sub) && isCyclic(visiting, visited, sub))
			{
				return true;
			}
		}

		visiting.remove(tag);
		visited.add(tag);
		return false;
	}

}
