package org.mockbukkit.metaminer.json;

import com.google.gson.JsonObject;
import org.bukkit.attribute.AttributeInstance;
import org.jetbrains.annotations.Nullable;

public class AttributeInstanceElementFactory
{

	/**
	 * Converts a attributeInstance into a JsonElement.
	 *
	 * @param attributeInstance The attributeInstance to be converted.
	 *
	 * @return The element
	 */
	@Nullable
	public static JsonObject toJson(@Nullable AttributeInstance attributeInstance)
	{
		if (attributeInstance == null)
		{
			return null;
		}

		JsonObject jsonObject = new JsonObject();
		jsonObject.add("baseValue", PrimitiveElementFactory.toJson(attributeInstance.getBaseValue()));
		jsonObject.add("defaultValue", PrimitiveElementFactory.toJson(attributeInstance.getDefaultValue()));
		jsonObject.add("modifiers", CollectionElementFactory.toJson(attributeInstance.getModifiers()));
		return jsonObject;
	}

	private AttributeInstanceElementFactory()
	{
		// Hide the public constructor
	}

}
