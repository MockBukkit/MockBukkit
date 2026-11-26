package org.mockbukkit.mockbukkit.entity;

import net.kyori.adventure.text.Component;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.UUID;

/**
 * A resource pack applied to a {@link PlayerMock}. Bukkit has no type for this -- packs are only ever pushed at a
 * player, never read back -- so a test needs something to assert against.
 *
 * @param id       The pack id.
 * @param url      Where the pack is served from.
 * @param hash     The pack hash, or null if none was given.
 * @param prompt   The prompt shown to the player, or null for the default.
 * @param required Whether the player must accept it.
 */
public record ResourcePackEntryMock(@NotNull UUID id, @NotNull String url, @Nullable String hash,
									@Nullable Component prompt, boolean required)
{
}
