package org.mockbukkit.metaminer.json;

import com.google.gson.JsonObject;
import org.jetbrains.annotations.Nullable;

import java.util.Map;

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
		if (map == null)
		{
			return null;
		}

		JsonObject jsonElement = new JsonObject();

		for (var element : map.entrySet())
		{
			Object key = element.getKey();
			Object value = element.getValue();

			jsonElement.add(String.valueOf(key), ElementFactory.toJson(value));
		}

		return jsonElement.isEmpty() ? null : jsonElement;
	}

	private MapElementFactory()
	{
		// Hide the public constructor
	}
}
