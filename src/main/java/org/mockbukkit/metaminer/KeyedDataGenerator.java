package org.mockbukkit.metaminer;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import net.kyori.adventure.text.serializer.gson.GsonComponentSerializer;
import org.bukkit.GameEvent;
import org.bukkit.Keyed;
import org.bukkit.MusicInstrument;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.generator.structure.Structure;
import org.bukkit.generator.structure.StructureType;
import org.bukkit.inventory.meta.trim.TrimMaterial;
import org.bukkit.inventory.meta.trim.TrimPattern;
import org.bukkit.potion.PotionEffectType;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.lang.reflect.Field;
import java.util.List;

public class KeyedDataGenerator implements DataGenerator
{

	private final File dataFolder;

	public KeyedDataGenerator(@NotNull File dataFolder)
	{
		this.dataFolder = new File(dataFolder, "keyed");
	}

	@Override
	public void generateData() throws IOException
	{
		if (!this.dataFolder.exists() && !this.dataFolder.mkdirs())
		{
			throw new IOException("Could not make directory: " + this.dataFolder);
		}

		List<Class<? extends Keyed>> keyedClasses = List.of(Structure.class,
				StructureType.class, TrimMaterial.class, TrimPattern.class,
				MusicInstrument.class, GameEvent.class, Enchantment.class,
				PotionEffectType.class);
		for (Class<? extends Keyed> tClass : keyedClasses)
		{
			JsonArray array = new JsonArray();
			for (Field field : tClass.getDeclaredFields())
			{
				JsonObject jsonObject = new JsonObject();
				try
				{
					Object object = field.get(null);
					if (tClass.isInstance(object))
					{
						Keyed keyedObject = (Keyed) object;
						jsonObject.add("key", new JsonPrimitive(keyedObject.getKey().toString()));
						if (keyedObject instanceof Structure structure)
						{
							jsonObject.add("type", new JsonPrimitive(structure.getStructureType().getKey().toString()));
						}
						if (keyedObject instanceof Enchantment enchantment)
						{
							addEnchantmentProperties(jsonObject, enchantment);
						}
						if (keyedObject instanceof PotionEffectType potionEffectType)
						{
							addPotionEffectTypeProperties(jsonObject, potionEffectType);
						}
					}
					array.add(jsonObject);
				}
				catch (NullPointerException | IllegalAccessException ignored)
				{
				}
			}
			File destinationFile = new File(dataFolder, tClass.getSimpleName().toLowerCase() + ".json");
			JsonObject rootObject = new JsonObject();
			rootObject.add("values", array);
			if (!destinationFile.exists() && !destinationFile.createNewFile())
			{
				throw new IOException("Could not create file: " + destinationFile);
			}
			try (Writer writer = new FileWriter(destinationFile))
			{
				Gson gson = new GsonBuilder().setPrettyPrinting().create();
				gson.toJson(rootObject, writer);
			}
		}
	}

	private void addPotionEffectTypeProperties(JsonObject jsonObject, PotionEffectType potionEffectType)
	{
		jsonObject.add("id", new JsonPrimitive(potionEffectType.getId()));
		jsonObject.add("name", new JsonPrimitive(potionEffectType.getName()));
		jsonObject.add("instant", new JsonPrimitive(potionEffectType.isInstant()));
		jsonObject.add("rgb", new JsonPrimitive(potionEffectType.getColor().asRGB()));
		jsonObject.add("category", new JsonPrimitive(potionEffectType.getEffectCategory().toString()));
	}

	/**
	 * Currently not taking the following properties into consideration:
	 * -
	 *
	 * @param jsonObject  <p>The JsonObject to modify</p>
	 * @param enchantment <p>The enchantment to get the properties from</p>
	 */
	private void addEnchantmentProperties(JsonObject jsonObject, Enchantment enchantment)
	{
		jsonObject.add("itemTarget", new JsonPrimitive(enchantment.getItemTarget().toString()));
		jsonObject.add("treasure", new JsonPrimitive(enchantment.isTreasure()));
		jsonObject.add("cursed", new JsonPrimitive(enchantment.isCursed()));
		jsonObject.add("maxLevel", new JsonPrimitive(enchantment.getMaxLevel()));
		jsonObject.add("startLevel", new JsonPrimitive(enchantment.getStartLevel()));
		jsonObject.add("name", new JsonPrimitive(enchantment.getName()));
		JsonArray displayNames = new JsonArray();
		JsonArray minModifiedCosts = new JsonArray();
		JsonArray maxModifiedCosts = new JsonArray();
		for (int i = 1; i <= enchantment.getMaxLevel(); i++)
		{
			GsonComponentSerializer serializer = GsonComponentSerializer.builder().build();
			JsonObject displayName = new JsonObject();
			displayName.add("level", new JsonPrimitive(i));
			displayName.add("text", serializer.serializeToTree(enchantment.displayName(i)));
			displayNames.add(displayName);

			JsonObject minModifiedCost = new JsonObject();
			minModifiedCost.add("level", new JsonPrimitive(i));
			minModifiedCost.add("cost", new JsonPrimitive(enchantment.getMinModifiedCost(i)));
			minModifiedCosts.add(minModifiedCost);

			JsonObject maxModifiedCost = new JsonObject();
			maxModifiedCost.add("level", new JsonPrimitive(i));
			maxModifiedCost.add("cost", new JsonPrimitive(enchantment.getMaxModifiedCost(i)));
			maxModifiedCosts.add(maxModifiedCost);
		}
		jsonObject.add("displayNames", displayNames);
		jsonObject.add("minModifiedCosts", minModifiedCosts);
		jsonObject.add("maxModifiedCosts", maxModifiedCosts);
		jsonObject.add("tradeable", new JsonPrimitive(enchantment.isTradeable()));
		jsonObject.add("discoverable", new JsonPrimitive(enchantment.isDiscoverable()));
		jsonObject.add("rarity", new JsonPrimitive(enchantment.getRarity().toString()));

		JsonArray conflicts = new JsonArray();
		for (Enchantment otherEnchantment : Enchantment.values())
		{
			if (enchantment.conflictsWith(otherEnchantment))
			{
				conflicts.add(otherEnchantment.getKey().toString());
			}
		}
		jsonObject.add("conflicts", conflicts);
	}

}
