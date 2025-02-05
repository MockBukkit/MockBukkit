package org.mockbukkit.metaminer.json;

import java.util.Map;
import java.util.function.Function;

import com.google.common.base.Preconditions;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class MapElementFactory
{
	/**
	 * Converts a map into a JsonElement.
	 *
	 * @param map The map to be converted.
	 *
	 * @return The element
	 */
	@Nullable
	public static JsonObject toJson(@Nullable Map<?, ?> map)
	{
		return toJson(map, String::valueOf, ElementFactory::toJson);
	}

	/**
	 * Converts a map into a JsonElement.
	 *
	 * @param map The map to be converted.
	 *
	 * @return The element
	 */
	@Nullable
	public static <K, V> JsonObject toJson(@Nullable Map<K, V> map,
										   @NotNull Function<K, String> keyFactory,
										   @NotNull Function<V, JsonElement> valueFactory)
	{
		Preconditions.checkArgument(keyFactory != null, "keyFactory must not be null");
		Preconditions.checkArgument(valueFactory != null, "valueFactory must not be null");

		if (map == null)
		{
			return null;
		}

		JsonObject jsonElement = new JsonObject();

		for (var element : map.entrySet())
		{
			K key = element.getKey();
			V value = element.getValue();

			String jsonKey = keyFactory.apply(key);
			JsonElement jsonValue = valueFactory.apply(value);
			jsonElement.add(jsonKey, jsonValue);
		}

		return jsonElement.isEmpty() ? null : jsonElement;
	}

	private MapElementFactory()
	{
		// Hide the public constructor
	}
}
