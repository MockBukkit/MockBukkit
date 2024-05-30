package be.seeseemelk.mockbukkit.tags;

import be.seeseemelk.mockbukkit.MockBukkit;
import com.google.common.base.Preconditions;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonParser;
import com.google.gson.JsonPrimitive;
import org.bukkit.Keyed;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.Tag;
import org.jetbrains.annotations.NotNull;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.HashSet;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * The {@link TagParser} is responsible for parsing a JSON input into a {@link TagWrapperMock}.
 *
 * @author TheBusyBiscuit
 */
public class TagParser implements Keyed
{

	private static final Pattern COLON = Pattern.compile(":");
	private static final Pattern MINECRAFT_MATERIAL = Pattern.compile("minecraft:[a-z0-9_]+");
	private static final Pattern MINECRAFT_TAG = Pattern.compile("#minecraft:[a-z_]+");

	private final @NotNull TagRegistry registry;
	private final @NotNull NamespacedKey key;

	/**
	 * This constructs a new {@link TagParser}.
	 *
	 * @param registry The {@link TagRegistry} for the resulting {@link Tag}
	 * @param key      The {@link NamespacedKey} of the resulting {@link Tag}
	 */
	public TagParser(@NotNull TagRegistry registry, @NotNull NamespacedKey key)
	{
		this.registry = registry;
		this.key = key;
	}

	/**
	 * This constructs a new {@link TagParser}.
	 *
	 * @param tag The {@link TagWrapperMock}
	 */
	TagParser(@NotNull TagWrapperMock tag)
	{
		this.registry = tag.getRegistry();
		this.key = tag.getKey();
	}

	void parse(@NotNull BiConsumer<Set<Material>, Set<TagWrapperMock>> callback)
			throws TagMisconfigurationException, FileNotFoundException
	{
		String path = "/tags/" + registry.getRegistry() + '/' + getKey().getKey() + ".json";

		if (MockBukkit.class.getResource(path) == null)
		{
			throw new FileNotFoundException(path);
		}

		try (BufferedReader reader = new BufferedReader(
				new InputStreamReader(MockBukkit.class.getResourceAsStream(path), StandardCharsets.UTF_8)))
		{
			parse(reader.lines().collect(Collectors.joining("")), callback);
		}
		catch (IOException x)
		{
			throw new TagMisconfigurationException(key, x.getMessage());
		}
	}

	/**
	 * This will parse the given JSON {@link String} and run the provided callback with {@link Set Sets} of matched
	 * {@link Material Materials} and {@link Tag Tags}.
	 *
	 * @param json     The JSON {@link String} to parse
	 * @param callback A callback to run after successfully parsing the input
	 * @throws TagMisconfigurationException This is thrown whenever the given input is malformed or no adequate
	 *                                      {@link Material} or {@link Tag} could be found
	 */
	public void parse(@NotNull String json, @NotNull BiConsumer<Set<Material>, Set<TagWrapperMock>> callback)
			throws TagMisconfigurationException
	{
		Preconditions.checkNotNull(json, "Cannot parse a null String");

		try
		{
			Set<Material> materials = new HashSet<>();
			Set<TagWrapperMock> tags = new HashSet<>();

			JsonObject root = JsonParser.parseString(json).getAsJsonObject();
			JsonElement child = root.get("values");

			if (child instanceof JsonArray)
			{
				JsonArray values = child.getAsJsonArray();

				for (JsonElement element : values)
				{
					if (element instanceof JsonPrimitive && ((JsonPrimitive) element).isString())
					{
						// Strings will be parsed directly
						parsePrimitiveValue(element.getAsString(), materials, tags);
					}
					else if (element instanceof JsonObject)
					{
						// JSONObjects can have a "required" property which can make
						// it optional to resolve the underlying value
						parseComplexValue(element.getAsJsonObject(), materials, tags);
					}
					else
					{
						throw new TagMisconfigurationException(key,
								"Unexpected value format: " + element.getClass().getSimpleName() + " - " + element);
					}
				}

				// Run the callback with the filled-in materials and tags
				callback.accept(materials, tags);
			}
			else
			{
				// The JSON seems to be empty yet valid
				throw new TagMisconfigurationException(key, "No values array specified");
			}
		}
		catch (IllegalStateException | JsonParseException x)
		{
			throw new TagMisconfigurationException(key, x.getMessage());
		}
	}

	private void parsePrimitiveValue(@NotNull String value, @NotNull Set<Material> materials,
			@NotNull Set<TagWrapperMock> tags) throws TagMisconfigurationException
	{
		if (MINECRAFT_MATERIAL.matcher(value).matches())
		{
			// Match the NamespacedKey against Materials
			Material material = Material.matchMaterial(value);

			if (material != null)
			{
				// If the Material could be matched, simply add it to our Set
				materials.add(material);
			}
			else
			{
				throw new TagMisconfigurationException(key, "Minecraft Material '" + value + "' seems to not exist!");
			}
		}
		else if (MINECRAFT_TAG.matcher(value).matches())
		{
			NamespacedKey tagKey = NamespacedKey.minecraft(COLON.split(value)[1]);
			TagWrapperMock tag = registry.getTags().get(tagKey);

			if (tag != null)
			{
				tags.add(tag);
			}
			else
			{
				throw new TagMisconfigurationException(key,
						"There is no '" + value + "' tag in Minecraft:" + registry.getRegistry());
			}
		}
		else
		{
			// If no RegEx pattern matched, it's malformed.
			throw new TagMisconfigurationException(key, "Could not recognize value '" + value + "'");
		}
	}

	private void parseComplexValue(@NotNull JsonObject entry, @NotNull Set<Material> materials,
			@NotNull Set<TagWrapperMock> tags) throws TagMisconfigurationException
	{
		JsonElement id = entry.get("id");
		JsonElement required = entry.get("required");

		// Check if the entry contains elements of the correct type
		if (id instanceof JsonPrimitive && ((JsonPrimitive) id).isString() && required instanceof JsonPrimitive
				&& ((JsonPrimitive) required).isBoolean())
		{
			if (required.getAsBoolean())
			{
				// If this entry is required, parse it like normal
				parsePrimitiveValue(id.getAsString(), materials, tags);
			}
			else
			{
				// If the entry is not required, validation will be optional
				try
				{
					parsePrimitiveValue(id.getAsString(), materials, tags);
				}
				catch (TagMisconfigurationException x)
				{
					// This is an optional entry, so we will ignore the validation here
				}
			}
		}
		else
		{
			throw new TagMisconfigurationException(key, "Found a JSON Object value without an id!");
		}
	}

	@NotNull
	@Override
	public NamespacedKey getKey()
	{
		return key;
	}

}
