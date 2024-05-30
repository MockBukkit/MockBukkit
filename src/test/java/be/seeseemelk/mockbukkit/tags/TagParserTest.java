package be.seeseemelk.mockbukkit.tags;

import org.bukkit.NamespacedKey;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.fail;

class TagParserTest
{

	@Test
	void testKeyed()
	{
		NamespacedKey key = NamespacedKey.minecraft("i_dont_exist");
		TagParser parser = new TagParser(TagRegistry.BLOCKS, key);
		assertEquals(key, parser.getKey());
	}

	@Test
	void testNullability() throws TagMisconfigurationException
	{
		NamespacedKey key = NamespacedKey.minecraft("i_dont_exist");
		TagParser parser = new TagParser(TagRegistry.BLOCKS, key);

		assertThrows(NullPointerException.class, () -> parser.parse(null, (a, b) -> {
		}));
	}

	@Test
	void testInvalidJson()
	{
		assertMisconfiguration("");
		assertMisconfiguration("hello world");
	}

	@Test
	void testMissingArray()
	{
		assertMisconfiguration("{}");
		assertMisconfiguration("{\"values\":\"derp\"}");
	}

	@Test
	void testInvalidMaterial()
	{
		assertMisconfiguration("{\"values\":[123456]}");
	}

	@Test
	void testInvalidMaterials()
	{
		assertMisconfiguration("{\"values\":[\"NO\"]}");
		assertMisconfiguration("{\"values\":[\"lol:jk\"]}");
		assertMisconfiguration("{\"values\":[\"minecraft:no\"]}");
	}

	@Test
	void testInvalidMinecraftTags()
	{
		assertMisconfiguration("{\"values\":[\"#minecraft:never_gonna_give_you_up\"]}");
	}

	@Test
	void testInvalidJSONObjects()
	{
		assertMisconfiguration("{\"values\":[{}]}");
		assertMisconfiguration("{\"values\":[{\"id\":123}]}");
		assertMisconfiguration("{\"values\":[{\"id\":\"wooh\"}]}");
		assertMisconfiguration("{\"values\":[{\"required\":false}]}");
		assertMisconfiguration("{\"values\":[{\"id\":\"wooh\",\"required\":\"wooh\"}]}");
		assertMisconfiguration("{\"values\":[{\"id\":\"wooh\",\"required\":true}]}");
	}

	private void assertMisconfiguration(@NotNull String json)
	{
		NamespacedKey key = NamespacedKey.minecraft("i_dont_exist");
		TagParser parser = new TagParser(TagRegistry.BLOCKS, key);

		try
		{
			parser.parse(json, (a, b) -> {
			});

			fail("JSON was misconfigured, should have thrown an exception");
		}
		catch (TagMisconfigurationException x)
		{
			// This was to be expected
		}
	}

}
