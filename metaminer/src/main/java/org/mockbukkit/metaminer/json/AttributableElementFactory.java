package org.mockbukkit.metaminer.json;

import com.google.gson.JsonObject;
import org.bukkit.Registry;
import org.bukkit.attribute.Attributable;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeInstance;
import org.jetbrains.annotations.Nullable;

public class AttributableElementFactory
{

	/**
	 * Converts a attributable into a JsonElement.
	 *
	 * @param attributable The attributable to be converted.
	 *
	 * @return The element
	 */
	@Nullable
	public static JsonObject toJson(@Nullable Attributable attributable)
	{
		if (attributable == null)
		{
			return null;
		}

		JsonObject jsonObject = new JsonObject();

		for (Attribute attribute : Registry.ATTRIBUTE)
		{
			@Nullable AttributeInstance value = attributable.getAttribute(attribute);
			if (value == null)
			{
				continue;
			}

			String key = attribute.getKey().asString();
			jsonObject.add(key, AttributeInstanceElementFactory.toJson(value));
		}

		return jsonObject.isEmpty() ? null : jsonObject;
	}

	private AttributableElementFactory()
	{
		// Hide the public constructor
	}

}
