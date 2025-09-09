package org.mockbukkit.mockbukkit.block.data.decoder;

import org.jetbrains.annotations.ApiStatus;

import javax.annotation.Nullable;

@ApiStatus.Internal
public interface PropertyDecoder<T>
{

	@Nullable
	T decode(@Nullable Object value);

}
