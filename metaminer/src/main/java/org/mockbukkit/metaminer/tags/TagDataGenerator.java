package org.mockbukkit.metaminer.tags;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import io.papermc.paper.registry.RegistryAccess;
import io.papermc.paper.registry.RegistryKey;
import org.bukkit.Keyed;
import org.bukkit.Registry;
import org.mockbukkit.metaminer.DataGenerator;
import org.mockbukkit.metaminer.keyed.KeyedClassTracker;
import org.mockbukkit.metaminer.util.JsonUtil;

import java.io.File;
import java.io.IOException;
import java.util.Map;

public class TagDataGenerator implements DataGenerator
{

	private final File dataFolder;

	public TagDataGenerator(File dataFolder)
	{
		this.dataFolder = new File(dataFolder, "tags");
	}

	@Override
	public void generateData() throws IOException
	{
		for (Map.Entry<RegistryKey<? extends Keyed>, Class<?>> entry : KeyedClassTracker.CLASS_REGISTRY_KEY_RELATION.entrySet())
		{
			@SuppressWarnings("unchecked")
			RegistryKey<Keyed> registryKey = (RegistryKey<Keyed>) entry.getKey();
			Registry<Keyed> registry = RegistryAccess.registryAccess().getRegistry(registryKey);
			String tagType = getPlural(registryKey);

			try
			{
				for (io.papermc.paper.registry.tag.Tag<? extends Keyed> tag : registry.getTags())
				{
					writeTag(tag, tagType);
				}
			}
			catch (UnsupportedOperationException ignored)
			{
				// This registry does not support tags.
			}
		}
	}

	private void writeTag(io.papermc.paper.registry.tag.Tag<? extends Keyed> tag, String tagTypeName) throws IOException
	{
		JsonArray jsonArray = new JsonArray();
		tag.values().forEach(tagValue -> jsonArray.add(tagValue.key().toString()));
		JsonObject rootObject = new JsonObject();
		rootObject.add("replace", new JsonPrimitive(false));
		rootObject.add("values", jsonArray);

		File destinationFile = new File(new File(this.dataFolder, tagTypeName), tag.tagKey().key().value() + ".json");
		JsonUtil.dump(rootObject, destinationFile);
	}

	private String getPlural(RegistryKey<?> key)
	{
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
