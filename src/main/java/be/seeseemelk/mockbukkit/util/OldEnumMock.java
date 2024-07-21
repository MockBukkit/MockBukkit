package be.seeseemelk.mockbukkit.util;

import org.bukkit.util.OldEnum;
import org.jetbrains.annotations.NotNull;

public abstract class OldEnumMock<T extends OldEnum<T>> implements OldEnum<T>
{

	@Override
	public int compareTo(@NotNull T other)
	{
		return Integer.compare(this.ordinal(), other.ordinal());
	}

}
