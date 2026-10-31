package org.mockbukkit.mockbukkit.util;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.serializer.gson.GsonComponentSerializer;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.chat.ComponentSerializer;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;

/**
 * Utility for converting between BungeeCord {@link BaseComponent}s and Adventure {@link Component}s.
 * <p>
 * This replaces {@code net.kyori.adventure.text.serializer.bungeecord.BungeeComponentSerializer}, which is
 * binary-incompatible with the Adventure release bundled with Paper (its static initializer calls
 * {@code GsonComponentSerializer.Builder.downsampleColors()}, removed in Adventure 5). The conversion is routed
 * through the JSON chat representation instead, using BungeeCord's own {@link ComponentSerializer} and Adventure's
 * gson serializer, both of which are always on the classpath.
 */
@ApiStatus.Internal
public final class BungeeComponentUtils
{

	private BungeeComponentUtils()
	{
		throw new UnsupportedOperationException("Utility class");
	}

	/**
	 * Converts BungeeCord {@link BaseComponent}s into an Adventure {@link Component}.
	 *
	 * @param components The BungeeCord components to convert.
	 * @return The equivalent Adventure component.
	 */
	public static @NotNull Component deserialize(@NotNull BaseComponent @NotNull ... components)
	{
		return GsonComponentSerializer.gson().deserialize(ComponentSerializer.toString(components));
	}

	/**
	 * Converts an Adventure {@link Component} into BungeeCord {@link BaseComponent}s.
	 *
	 * @param component The Adventure component to convert.
	 * @return The equivalent BungeeCord components.
	 */
	public static @NotNull BaseComponent @NotNull [] serialize(@NotNull Component component)
	{
		return ComponentSerializer.parse(GsonComponentSerializer.gson().serialize(component));
	}

}
