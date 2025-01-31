package org.mockbukkit.mockbukkit.tags;

import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

import com.google.common.base.Preconditions;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import org.bukkit.NamespacedKey;
import org.bukkit.Tag;
import org.jetbrains.annotations.NotNull;
import org.mockbukkit.mockbukkit.MockBukkit;

public final class TagFactory
{

	@NotNull
	public static Tag<?> createTag(@NotNull TagRegistry registry, @NotNull NamespacedKey key)
	{
		Preconditions.checkNotNull(registry, "registry cannot be null");
		Preconditions.checkNotNull(key, "key cannot be null");

		String path = String.format("/tags/%s/%s.json", registry.getRegistry(), key.value());
		Preconditions.checkArgument(MockBukkit.class.getResource(path) != null, "Resource not found: {}", path);

		try (var resource = MockBukkit.class.getResourceAsStream(path))
		{
			Preconditions.checkNotNull(resource, "Resource cannot be null");
			JsonElement json = JsonParser.parseReader(new InputStreamReader(resource, StandardCharsets.UTF_8));
			Preconditions.checkState(json.isJsonObject(), "Invalid tag JSON");

			JsonElement values = json.getAsJsonObject().get("values");
			Preconditions.checkArgument(values.isJsonArray(), "Invalid tag values");
			return createTag(registry, key, values.getAsJsonArray());
		}
		catch (IOException e)
		{
			throw new IllegalStateException("Error while creating the tag", e);
		}
	}

	static Tag<?> createTag(@NotNull TagRegistry tagRegistry, @NotNull NamespacedKey key, @NotNull JsonArray values)
	{
		return switch (tagRegistry)
		{
			case BLOCKS, ITEMS -> MaterialTagMock.from(key, values);
			case ENTITY_TYPES -> EntityTypeTagMock.from(key, values);
			case FLUIDS -> FluidTagMock.from(key, values);
			case GAME_EVENTS -> GameEventTagMock.from(key, values);
		};
	}

	private TagFactory()
	{
		throw new IllegalStateException("This is a utility class and should not be initialized");
	}

}
