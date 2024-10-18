package org.mockbukkit.mockbukkit;

import org.bukkit.util.CachedServerIcon;
import org.jetbrains.annotations.Nullable;

/**
 * Mock implementation of a {@link CachedServerIcon}.
 */
public class CachedServerIconMock implements CachedServerIcon
{

	/**
	 * The prefix used for base 64 images.
	 */
	public static final String PNG_BASE64_PREFIX = "data:image/png;base64,";

	private final @Nullable String data;

	/**
	 * Constructs a new {@link CachedServerIconMock} with the provided data.
	 *
	 * @param data The data to be later retrieved in {@link #getData()}.
	 */
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
