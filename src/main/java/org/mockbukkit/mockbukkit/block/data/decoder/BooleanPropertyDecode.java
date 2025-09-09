package org.mockbukkit.mockbukkit.block.data.decoder;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.Nullable;

@ApiStatus.Internal
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class BooleanPropertyDecode implements PropertyDecoder<Boolean>
{
	public static final BooleanPropertyDecode INSTANCE = new BooleanPropertyDecode();

	@Override
	public @Nullable Boolean decode(@Nullable Object value)
	{
		return switch (value)
		{
			case null -> null;
			case Boolean bool -> bool;
			case String string -> Boolean.parseBoolean(string);
			default -> throw new UnsupportedOperationException("Unable to decode boolean from value: " + value);
		};

	}

}
