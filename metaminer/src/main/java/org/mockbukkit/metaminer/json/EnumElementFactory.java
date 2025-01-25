package org.mockbukkit.metaminer.json;

import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import org.jetbrains.annotations.Nullable;

public class EnumElementFactory
{

	/**
	 * Converts a enum element into a JsonElement.
	 *
	 * @param enumElement The enumElement to be converted.
	 *
	 * @return The element
	 */
	@Nullable
	public static JsonElement toJson(@Nullable Enum<?> enumElement)
	{
		if (enumElement == null)
		{
			return null;
		}

		return new JsonPrimitive(enumElement.name());
	}

	private EnumElementFactory()
	{
		// Hide the public constructor
	}

}
