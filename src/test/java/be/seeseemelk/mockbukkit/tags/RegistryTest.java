package be.seeseemelk.mockbukkit.tags;

import be.seeseemelk.mockbukkit.MockBukkit;
import be.seeseemelk.mockbukkit.RegistryMock;
import org.bukkit.Material;
import org.bukkit.MusicInstrument;
import org.bukkit.Tag;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;

import java.io.FileNotFoundException;
import java.util.HashSet;
import java.util.Locale;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

class RegistryTest
{

	@BeforeEach
	void setUp()
	{
		MockBukkit.mock();
	}

	@AfterEach
	void tearDown()
	{
		MockBukkit.unmock();
	}

	@ParameterizedTest
	@EnumSource(TagRegistry.class)
	void testNotEmpty(@NotNull TagRegistry registry)
	{
		assertFalse(registry.isEmpty());

		for (TagWrapperMock tag : registry.getTags().values())
		{
			assertFalse(tag.getValues().isEmpty(), "Expected Tag \"" + tag + "\" not to be empty");
		}
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
			assertFalse(values.isEmpty());

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

	private boolean isCyclic(@NotNull Set<TagWrapperMock> visiting, @NotNull Set<TagWrapperMock> visited,
			@NotNull TagWrapperMock tag)
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

	@Test
	void locale_independent()
	{
		Locale prevLocale = Locale.getDefault();
		Locale.setDefault(Locale.forLanguageTag("tr"));
		assertDoesNotThrow(() -> RegistryMock.createRegistry(MusicInstrument.class));
		Locale.setDefault(prevLocale);
	}

}
