package org.mockbukkit.metaminer.json;

import com.google.gson.JsonElement;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.serializer.gson.GsonComponentSerializer;
import org.jetbrains.annotations.Nullable;

public class ComponentElementFactory
{

	/**
	 * Converts a component into a JsonElement.
	 *
	 * @param component The component to be converted.
	 *
	 * @return The element
	 */
	@Nullable
	public static JsonElement toJson(@Nullable Component component)
	{
		if (component == null)
		{
			return null;
		}

		return GsonComponentSerializer.gson().serializeToTree(component);
	}

	private ComponentElementFactory()
	{
		// Hide the public constructor
	}

}
