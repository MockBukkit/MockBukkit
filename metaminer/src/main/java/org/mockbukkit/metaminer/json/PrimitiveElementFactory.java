package org.mockbukkit.metaminer.json;

import com.google.gson.JsonPrimitive;
import org.jetbrains.annotations.Nullable;

public class PrimitiveElementFactory
{
	/**
	 * Converts a boolean into a JsonElement.
	 *
	 * @param bool The bool to be converted.
	 *
	 * @return The element
	 */
	@Nullable
	public static JsonPrimitive toJson(@Nullable Boolean bool)
	{
		if (bool == null)
		{
			return null;
		}

		return new JsonPrimitive(bool);
	}

	/**
	 * Converts an number into a JsonElement.
	 *
	 * @param number The number to be converted.
	 *
	 * @return The element
	 */
	@Nullable
	public static JsonPrimitive toJson(@Nullable Number number)
	{
		if (number == null)
		{
			return null;
		}

		return new JsonPrimitive(number);
	}

	/**
	 * Converts a character into a JsonElement.
	 *
	 * @param character The character to be converted.
	 *
	 * @return The element
	 */
	@Nullable
	public static JsonPrimitive toJson(@Nullable Character character)
	{
		if (character == null)
		{
			return null;
		}

		return new JsonPrimitive(character);
	}

	/**
	 * Converts an string into a JsonElement.
	 *
	 * @param string The string to be converted.
	 *
	 * @return The element
	 */
	@Nullable
	public static JsonPrimitive toJson(@Nullable String string)
	{
		if (string == null)
		{
			return null;
		}

		return new JsonPrimitive(string);
	}

	private PrimitiveElementFactory()
	{
		// Hide the public constructor
	}

}
