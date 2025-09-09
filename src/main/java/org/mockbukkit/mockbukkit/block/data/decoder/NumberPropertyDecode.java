package org.mockbukkit.mockbukkit.block.data.decoder;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@ApiStatus.Internal
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public abstract class NumberPropertyDecode<A extends Number> implements PropertyDecoder<A>
{

	@Override
	public @Nullable A decode(@Nullable Object value)
	{
		return switch (value)
		{
			case null -> null;
			case Number number -> toNumber(number);
			case String string -> toNumber(string);
			default -> throw new UnsupportedOperationException("Unable to decode boolean");
		};

	}

	protected abstract @Nullable A toNumber(@NotNull Number number);

	protected abstract @Nullable A toNumber(@NotNull String number);

}
