package org.mockbukkit.metaminer.json;

import com.google.gson.JsonObject;
import org.bukkit.attribute.AttributeModifier;
import org.jetbrains.annotations.Nullable;

public class AttributeModifierElementFactory
{

	/**
	 * Converts a attributeModifier into a JsonElement.
	 *
	 * @param attributeModifier The attributeModifier to be converted.
	 *
	 * @return The element
	 */
	@Nullable
	public static JsonObject toJson(@Nullable AttributeModifier attributeModifier)
	{
		if (attributeModifier == null)
		{
			return null;
		}

		JsonObject jsonObject = new JsonObject();
		jsonObject.addProperty("name", attributeModifier.getName());
		jsonObject.add("key", ElementFactory.toJson(attributeModifier.getKey()));
		jsonObject.addProperty("amount", attributeModifier.getAmount());
		jsonObject.add("operation", ElementFactory.toJson(attributeModifier.getOperation()));
		jsonObject.add("slotGroup", ElementFactory.toJson(attributeModifier.getSlotGroup()));
		return jsonObject;
	}

	private AttributeModifierElementFactory()
	{
		// Hide the public constructor
	}

}
