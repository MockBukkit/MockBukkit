package org.mockbukkit.mockbukkit.util;

import io.papermc.paper.registry.RegistryKey;
import org.jetbrains.annotations.Nullable;

/**
 * Utility class for {@link io.papermc.paper.registry.Registry} related operations.
 */
public final class RegistryUtils
{

	private RegistryUtils()
	{
	}

	/**
	 * Gets the plural name of a registry key.
	 *
	 * @param key The registry key.
	 * @return The plural name, or {@code null} if the key is null.
	 */
	public static @Nullable String getPlural(RegistryKey<?> key)
	{
		if (key == null)
		{
			return null;
		}
		return switch (key.key().value())
		{
			case "attribute" -> "attributes";
			case "banner_pattern" -> "banner_patterns";
			case "biome" -> "biomes";
			case "block" -> "blocks";
			case "cat_variant" -> "cat_variants";
			case "cat_sound_variant" -> "cat_sound_variants";
			case "chicken_variant" -> "chicken_variants";
			case "chicken_sound_variant" -> "chicken_sound_variants";
			case "cow_variant" -> "cow_variants";
			case "cow_sound_variant" -> "cow_sound_variants";
			case "damage_type" -> "damage_types";
			case "data_component_type" -> "data_component_types";
			case "dialog" -> "dialogs";
			case "enchantment" -> "enchantments";
			case "entity_type" -> "entity_types";
			case "fluid" -> "fluids";
			case "frog_variant" -> "frog_variants";
			case "game_event" -> "game_events";
			case "game_rule" -> "game_rules";
			case "instrument" -> "instruments";
			case "item" -> "items";
			case "jukebox_song" -> "jukebox_songs";
			case "map_decoration_type" -> "map_decoration_types";
			case "memory_module_type" -> "memory_module_types";
			case "menu" -> "menus";
			case "mob_effect" -> "mob_effects";
			case "painting_variant" -> "painting_variants";
			case "particle_type" -> "particle_types";
			case "pig_variant" -> "pig_variants";
			case "pig_sound_variant" -> "pig_sound_variants";
			case "potion" -> "potions";
			case "sound_event" -> "sound_events";
			case "structure" -> "structures";
			case "structure_type" -> "structure_types";
			case "trim_material" -> "trim_materials";
			case "trim_pattern" -> "trim_patterns";
			case "villager_profession" -> "villager_professions";
			case "villager_type" -> "villager_types";
			case "wolf_variant" -> "wolf_variants";
			case "wolf_sound_variant" -> "wolf_sound_variants";
			case "zombie_nautilus_variant" -> "zombie_nautilus_variants";
			default -> key.key().value() + "s";
		};
	}

}
