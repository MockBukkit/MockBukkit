package org.mockbukkit.mockbukkit.block.data.decoder;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@ApiStatus.Internal
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class IntegerPropertyDecode extends NumberPropertyDecode<Integer>
{
	public static final IntegerPropertyDecode INSTANCE = new IntegerPropertyDecode();

	@Override
	protected @Nullable Integer toNumber(@NotNull Number number)
	{
		return number.intValue();
	}

	@Override
	protected @Nullable Integer toNumber(@NotNull String number)
	{
		return Integer.parseInt(number);
	}

}
