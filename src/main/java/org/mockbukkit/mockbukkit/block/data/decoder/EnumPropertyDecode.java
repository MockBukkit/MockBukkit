package org.mockbukkit.mockbukkit.block.data.decoder;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@ApiStatus.Internal
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public class EnumPropertyDecode<T extends Enum<?>> implements PropertyDecoder<T>
{
	public static <T extends Enum<?>> EnumPropertyDecode<T> of(Class<T> enumClass)
	{
		return new EnumPropertyDecode<>(enumClass);
	}

	private final @NotNull Class<T> enumClass;

	@Override
	public @Nullable T decode(@Nullable Object value)
	{
		if (value == null)
		{
			return null;
		}

		if (enumClass.isInstance(value))
		{
			return enumClass.cast(value);
		}

		if (value instanceof String string)
		{
			for (T enumValue : enumClass.getEnumConstants())
			{
				if (enumValue.name().equalsIgnoreCase(string))
				{
					return enumValue;
				}
			}

			return null;
		}

		throw new UnsupportedOperationException("Unsupported value " + value);
	}

}
