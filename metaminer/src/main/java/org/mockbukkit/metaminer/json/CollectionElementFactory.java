package org.mockbukkit.metaminer.json;

import com.google.common.base.Preconditions;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Collection;
import java.util.function.Function;

public class CollectionElementFactory
{

	/**
	 * Converts a collection into a JsonElement.
	 *
	 * @param collection The collection to be converted.
	 * @return The element
	 */
	@Nullable
	public static <T> JsonArray toJson(@Nullable Collection<T> collection)
	{
		return toJson(collection, ElementFactory::toJson);
	}

	/**
	 * Converts a collection into a JsonElement.
	 *
	 * @param collection The collection to be converted.
	 * @param toJson     The function to apply.
	 * @return The element
	 */
	@Nullable
	public static <T> JsonArray toJson(@Nullable Collection<T> collection, @NotNull Function<T, JsonElement> toJson)
	{
		Preconditions.checkArgument(toJson != null, "toJson must not be null");

		if (collection == null)
		{
			return null;
		}

		JsonArray jsonElements = new JsonArray(collection.size());

		for (T element : collection)
		{
			@Nullable JsonElement jsonElement = toJson.apply(element);
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
