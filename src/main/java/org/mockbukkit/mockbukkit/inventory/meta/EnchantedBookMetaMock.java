package org.mockbukkit.mockbukkit.inventory.meta;

import org.bukkit.inventory.meta.EnchantmentStorageMeta;
import org.jetbrains.annotations.NotNull;

/**
 * Mock implementation of an {@link EnchantmentStorageMeta}.
 *
 * @deprecated use {@link EnchantmentStorageMetaMock}
 * @see ItemMetaMock
 */
@Deprecated(forRemoval = true)
public class EnchantedBookMetaMock extends EnchantmentStorageMetaMock {

	/**
	 * Constructs a new {@link EnchantedBookMetaMock}.
	 */
	public EnchantedBookMetaMock()
	{
		super();
	}

	/**
	 * Constructs a new {@link EnchantedBookMetaMock}, cloning the data from another.
	 *
	 * @param meta The meta to clone.
	 */
	public EnchantedBookMetaMock(@NotNull EnchantmentStorageMeta meta)
	{
		super(meta);
	}

}
