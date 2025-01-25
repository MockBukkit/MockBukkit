package org.mockbukkit.metaminer.json;

import com.google.gson.JsonObject;
import org.bukkit.Color;
import org.jetbrains.annotations.Nullable;

public class ColorElementFactory
{
	/**
	 * Converts a color into a JsonElement.
	 *
	 * @param color The color to be converted.
	 *
	 * @return The element
	 */
	@Nullable
	public static JsonObject toJson(@Nullable Color color)
	{
		if (color == null)
		{
			return null;
		}

		JsonObject jsonElement = new JsonObject();
		jsonElement.addProperty("alpha", color.getAlpha());
		jsonElement.addProperty("red", color.getRed());
		jsonElement.addProperty("green", color.getGreen());
		jsonElement.addProperty("blue", color.getBlue());
		return jsonElement;
	}

	private ColorElementFactory()
	{
		// Hide the public constructor
	}
}
