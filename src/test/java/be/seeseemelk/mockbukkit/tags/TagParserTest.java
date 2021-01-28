package be.seeseemelk.mockbukkit.tags;

import static org.junit.Assert.assertEquals;

import org.bukkit.NamespacedKey;
import org.jetbrains.annotations.NotNull;
import org.junit.Assert;
import org.junit.Test;

public class TagParserTest
{

	@Test
	public void testKeyed()
	{
		NamespacedKey key = NamespacedKey.minecraft("i_dont_exist");
		TagParser parser = new TagParser(TagRegistry.BLOCKS, key);
		assertEquals(key, parser.getKey());
	}

	@Test(expected = IllegalArgumentException.class)
	public void testNullability() throws TagMisconfigurationException
	{
		NamespacedKey key = NamespacedKey.minecraft("i_dont_exist");
		TagParser parser = new TagParser(TagRegistry.BLOCKS, key);
		parser.parse(null, (a, b) ->
		{
		});
	}

	@Test
	public void testInvalidJson()
	{
		assertMisconfiguration("");
		assertMisconfiguration("hello world");
	}

	@Test
	public void testMissingArray()
	{
		assertMisconfiguration("{}");
		assertMisconfiguration("{\"values\":\"derp\"}");
	}

	@Test
	public void testInvalidMaterial()
	{
		assertMisconfiguration("{\"values\":[123456]}");
	}

	@Test
	public void testInvalidMaterials()
	{
		assertMisconfiguration("{\"values\":[\"NO\"]}");
		assertMisconfiguration("{\"values\":[\"lol:jk\"]}");
		assertMisconfiguration("{\"values\":[\"minecraft:no\"]}");
	}

	@Test
	public void testInvalidMinecraftTags()
	{
		assertMisconfiguration("{\"values\":[\"#minecraft:never_gonna_give_you_up\"]}");
	}

	@Test
	public void testInvalidJSONObjects()
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
			parser.parse(json, (a, b) ->
			{
			});

			Assert.fail("JSON was misconfigured, should have thrown an exception");
		}
		catch (TagMisconfigurationException x)
		{
			// This was to be expected
		}
	}

}
