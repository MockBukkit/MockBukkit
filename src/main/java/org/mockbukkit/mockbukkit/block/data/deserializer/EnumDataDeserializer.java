package org.mockbukkit.mockbukkit.block.data.deserializer;

import com.google.common.base.Preconditions;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import java.util.function.Function;

@ApiStatus.Internal
public record EnumDataDeserializer<T extends Enum<?>>(@NotNull Class<T> enumClass) implements Function<String, Object>
{

	public static <T extends Enum<?>> EnumDataDeserializer<T> of(@NotNull Class<T> enumClass)
	{
		Preconditions.checkNotNull(enumClass, "enumClass cannot be null");
		return new EnumDataDeserializer<>(enumClass);
	}

	@Override
	public @Nullable Object apply(@Nullable String value)
	{
		if (value == null)
		{
			return null;
		}

		for (T enumValue : enumClass.getEnumConstants())
		{
			if (enumValue.name().equalsIgnoreCase(value))
			{
				return enumValue;
			}
		}

		return null;
	}

}
