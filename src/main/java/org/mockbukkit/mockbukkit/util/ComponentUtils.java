package org.mockbukkit.mockbukkit.util;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.serializer.plain.PlainTextComponentSerializer;
import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.Nullable;

@NullMarked
public class ComponentUtils
{

	/**
	 * Serializes an Adventure {@link Component} into a plain text string.
	 *
	 * @param component the component to serialize, or {@code null}
	 * @return the plain text representation of the component, or {@code null} if the input component is {@code null}
	 *
	 * @see PlainTextComponentSerializer#plainText()
	 */
	public static @Nullable String asPlainString(@Nullable Component component)
	{
		if (component == null)
		{
			return null;
		}

		return PlainTextComponentSerializer.plainText().serialize(component);
	}

}
