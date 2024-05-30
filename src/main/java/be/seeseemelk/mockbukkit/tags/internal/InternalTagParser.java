package be.seeseemelk.mockbukkit.tags.internal;

import be.seeseemelk.mockbukkit.MockBukkit;
import be.seeseemelk.mockbukkit.tags.TagRegistry;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.Tag;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.EnumSet;
import java.util.Locale;
import java.util.Set;
import java.util.regex.Pattern;

public class InternalTagParser
{

	private static final Pattern MINECRAFT_MATERIAL = Pattern.compile("minecraft:[a-z0-9_]+");
	private static final Pattern MINECRAFT_TAG = Pattern.compile("#minecraft:[a-z_]+");

	public void insertInternalTagValues(InternalTagRegistry internalTagRegistry)
			throws IOException, InternalTagMisconfigurationException
	{
		String path = "/internal_tags/" + internalTagRegistry.name().toLowerCase(Locale.ROOT) + "/";
		for (InternalTag<?> internalTag : internalTagRegistry.getRelatedTags())
		{
			String filePath = path + internalTag.getName().toLowerCase(Locale.ROOT) + ".json";
			try (InputStream inputStream = MockBukkit.class.getResourceAsStream(filePath))
			{
				if (inputStream == null)
				{
					throw new IOException("Could not find resource: " + filePath);
				}
				JsonObject object = (JsonObject) JsonParser.parseReader(new InputStreamReader(inputStream));
				this.parse(object, internalTagRegistry.getTagRegistryEquivalent(), internalTag);
			}
		}
	}

	private <T> void parse(JsonObject json, TagRegistry tagRegistry, InternalTag<T> internalTag)
			throws InternalTagMisconfigurationException
	{
		JsonArray taggedElements = (JsonArray) json.get("values");
		Set<T> included = parseJsonArray(taggedElements, tagRegistry, internalTag.getRelatedClass());
		internalTag.addValues(included);
	}

	private <T> Set<T> parseJsonArray(JsonArray array, TagRegistry tagRegistry, Class<T> targetClass)
			throws InternalTagMisconfigurationException
	{
		if (targetClass == Material.class)
		{
			Set<Material> output = EnumSet.noneOf(Material.class);
			for (JsonElement element : array.asList())
			{
				String aString = element.getAsString();
				if (MINECRAFT_TAG.matcher(aString).matches())
				{
					output.addAll(parseTag(aString.replace("^#", ""), tagRegistry));
				}
				else if (MINECRAFT_MATERIAL.matcher(aString).matches())
				{
					output.add(parseMaterial(aString));
				}
				else
				{
					throw new InternalTagMisconfigurationException("Unexpected value format: " + aString);
				}
			}
			return (Set<T>) output;
		}
		throw new InternalTagMisconfigurationException(
				"Unimplemented materialtype parsing of type name: " + targetClass.getName());
	}

	private Material parseMaterial(String materialString) throws InternalTagMisconfigurationException
	{
		Material material = Material.matchMaterial(materialString);
		if (material == null)
		{
			throw new InternalTagMisconfigurationException("Invalid namespace key " + materialString);
		}
		return material;
	}

	private Set<Material> parseTag(String tagString, TagRegistry tagRegistry)
			throws InternalTagMisconfigurationException
	{
		NamespacedKey namespacedKey = NamespacedKey.minecraft(tagString.split(":")[1]);
		Tag<Material> tag = tagRegistry.getTags().get(namespacedKey);
		if (tag == null)
		{
			throw new InternalTagMisconfigurationException("Invalid tag " + namespacedKey);
		}
		return tag.getValues();
	}

}
