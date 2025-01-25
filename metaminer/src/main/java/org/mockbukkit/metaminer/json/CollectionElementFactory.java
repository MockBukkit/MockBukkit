package org.mockbukkit.metaminer.json;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import org.jetbrains.annotations.Nullable;

import java.util.Collection;

public class CollectionElementFactory
{

	/**
	 * Converts a collection into a JsonElement.
	 *
	 * @param collection The collection to be converted.
	 *
	 * @return The element
	 */
	@Nullable
	public static JsonArray toJson(@Nullable Collection<?> collection)
	{
		if (collection == null)
		{
			return null;
		}

		JsonArray jsonElements = new JsonArray();

		for (Object element : collection)
		{
			@Nullable JsonElement jsonElement = ElementFactory.toJson(element);
			if (jsonElement != null)
			{
				jsonElements.add(jsonElement);
			}
		}

		return jsonElements.isEmpty() ? null : jsonElements;
	}

	private CollectionElementFactory()
	{
		// Hide the public constructor
	}

}
