package org.mockbukkit.metaminer.json;

import com.google.gson.JsonPrimitive;
import net.kyori.adventure.key.Keyed;
import org.jetbrains.annotations.Nullable;

public class KeyedElementFactory
{

	/**
	 * Converts a Keyed into a JsonElement.
	 *
	 * @param keyed The keyed to be converted.
	 *
	 * @return The element
	 */
	@Nullable
	public static JsonPrimitive toJson(@Nullable Keyed keyed)
	{
		if (keyed == null)
		{
			return null;
		}

		return new JsonPrimitive(keyed.key().asString());
	}

	private KeyedElementFactory()
	{
		// Hide the public constructor
	}

}
