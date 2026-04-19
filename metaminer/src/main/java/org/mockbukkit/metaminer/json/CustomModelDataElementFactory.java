package org.mockbukkit.metaminer.json;

import com.google.gson.JsonObject;
import org.bukkit.inventory.meta.components.CustomModelDataComponent;
import org.jetbrains.annotations.Nullable;

/**
 * Factory for converting {@link CustomModelDataComponent} to JSON.
 */
public class CustomModelDataElementFactory
{

	/**
	 * Converts a {@link CustomModelDataComponent} into a {@link JsonObject}.
	 *
	 * @param component The component to be converted.
	 * @return The JSON object.
	 */
	@Nullable
	public static JsonObject toJson(@Nullable CustomModelDataComponent component)
	{
		if (component == null)
		{
			return null;
		}

		JsonObject json = new JsonObject();
		json.add("floats", CollectionElementFactory.toJson(component.getFloats(), PrimitiveElementFactory::toJson));
		json.add("flags", CollectionElementFactory.toJson(component.getFlags(), PrimitiveElementFactory::toJson));
		json.add("strings", CollectionElementFactory.toJson(component.getStrings(), PrimitiveElementFactory::toJson));
		json.add("colors", CollectionElementFactory.toJson(component.getColors(), ColorElementFactory::toJson));

		return json;
	}

	private CustomModelDataElementFactory()
	{
		// Hide the public constructor
	}

}
