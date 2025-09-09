package org.mockbukkit.mockbukkit.block.data.encoder;

import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;

@ApiStatus.Internal
public interface PropertyEncoder<T>
{

	@Nullable
	Object encode(@Nullable T value);

	default Object encodeIfPossible(@Nullable Object value)
	{
		Class<T> valueType = getValueType();
		return valueType.isInstance(value) ? encode(valueType.cast(value)) : value;
	}

	@NotNull
	Class<T> getValueType();

}
