package be.seeseemelk.mockbukkit;

import org.bukkit.util.CachedServerIcon;
import org.jetbrains.annotations.Nullable;

public class CachedServerIconMock implements CachedServerIcon
{

	public static final String PNG_BASE64_PREFIX = "data:image/png;base64,";

	private final @Nullable String data;

	protected CachedServerIconMock(@Nullable String data)
	{
		this.data = data;
	}

	@Override
	public @Nullable String getData()
	{
		return this.data;
	}

}
