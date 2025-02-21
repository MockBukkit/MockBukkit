package org.mockbukkit.mockbukkit.tags.internal;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.Tag;
import org.jetbrains.annotations.NotNull;
import org.mockbukkit.mockbukkit.tags.TagRegistry;
import org.mockbukkit.mockbukkit.util.ResourceLoader;

import java.util.Collection;
import java.util.EnumSet;
import java.util.Locale;
import java.util.Set;
import java.util.regex.Pattern;

public class InternalTagParser
{

	private static final Pattern MINECRAFT_MATERIAL = Pattern.compile("minecraft:[a-z0-9_]+");
	private static final Pattern MINECRAFT_TAG = Pattern.compile("#minecraft:[a-z_]+");

	public void insertInternalTagValues(@NotNull InternalTagRegistry internalTagRegistry) throws InternalTagMisconfigurationException
	{
		String path = "/internal_tags/" + internalTagRegistry.name().toLowerCase(Locale.ROOT) + "/";
		for (InternalTag<?> internalTag : internalTagRegistry.getRelatedTags())
		{
			String filePath = path + internalTag.getName().toLowerCase(Locale.ROOT) + ".json";
			JsonObject object = ResourceLoader.loadResource(filePath).getAsJsonObject();
			this.parse(object, internalTagRegistry.getTagRegistryEquivalent(), internalTag);
		}
	}

	private <T> void parse(JsonObject json, TagRegistry tagRegistry, InternalTag<T> internalTag) throws InternalTagMisconfigurationException
	{
		JsonArray taggedElements = (JsonArray) json.get("values");
		Set<T> included = parseJsonArray(taggedElements, tagRegistry, internalTag.getRelatedClass());
		internalTag.addValues(included);
	}

	private <T> Set<T> parseJsonArray(JsonArray array, TagRegistry tagRegistry, Class<T> targetClass) throws InternalTagMisconfigurationException
	{
		if (targetClass == Material.class)
		{
			Set<Material> output = EnumSet.noneOf(Material.class);
			for (JsonElement element : array.asList())
			{
				String aString = element.getAsString();
				if (MINECRAFT_TAG.matcher(aString).matches())
				{
					output.addAll((Collection<Material>) parseTag(aString.replace("^#", ""), tagRegistry));
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
		throw new InternalTagMisconfigurationException("Unimplemented materialtype parsing of type name: " + targetClass.getName());
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

	private Set<?> parseTag(String tagString, TagRegistry tagRegistry) throws InternalTagMisconfigurationException
	{
		NamespacedKey namespacedKey = NamespacedKey.minecraft(tagString.split(":")[1]);
		Tag<?> tag = tagRegistry.getTags().get(namespacedKey);
		if (tag == null)
		{
			throw new InternalTagMisconfigurationException("Invalid tag " + namespacedKey);
		}
		return tag.getValues();
	}

}
