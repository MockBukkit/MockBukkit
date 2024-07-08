package org.mockbukkit.metaminer.keyed;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import io.papermc.paper.registry.RegistryKey;
import net.kyori.adventure.text.serializer.gson.GsonComponentSerializer;
import org.bukkit.JukeboxSong;
import org.bukkit.Keyed;
import org.bukkit.block.BlockType;
import org.bukkit.damage.DamageType;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.generator.structure.Structure;
import org.bukkit.inventory.ItemType;
import org.bukkit.inventory.meta.trim.TrimMaterial;
import org.bukkit.inventory.meta.trim.TrimPattern;
import org.bukkit.potion.PotionEffectType;
import org.jetbrains.annotations.NotNull;
import org.mockbukkit.metaminer.DataGenerator;
import org.mockbukkit.metaminer.util.JsonUtil;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.lang.reflect.Field;
import java.util.Map;

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

		for (Map.Entry<RegistryKey<?>, Class<?>> entry : KeyedClassTracker.CLASS_REGISTRY_KEY_RELATION.entrySet())
		{

			JsonArray array = new JsonArray();
			Class<?> tClass = entry.getValue();
			RegistryKey<?> key = entry.getKey();

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
						if (keyedObject instanceof DamageType damageType)
						{
							addDamageTypeProperties(jsonObject, damageType);
						}
						if (keyedObject instanceof TrimPattern trimPattern)
						{
							addTrimPatternProperties(jsonObject, trimPattern);
						}
						if (keyedObject instanceof TrimMaterial trimMaterial)
						{
							addTrimMaterialProperties(jsonObject, trimMaterial);
						}
						if (keyedObject instanceof ItemType itemType)
						{
							addItemTypeProperties(jsonObject, itemType);
						}
						if (keyedObject instanceof BlockType blockType)
						{
							addBlockTypeProperties(jsonObject, blockType);
						}
						if (keyedObject instanceof JukeboxSong jukeboxSong)
						{
							addJukeboxSongProperties(jsonObject, jukeboxSong);
						}
						array.add(jsonObject);
					}
				}
				catch (NullPointerException | IllegalAccessException | IllegalArgumentException ignored)
				{
				}
			}
			File destinationFile = new File(dataFolder, key.key().value() + ".json");
			JsonObject rootObject = new JsonObject();
			rootObject.add("values", array);
			JsonUtil.dump(rootObject, destinationFile);
		}
	}

	private void addBlockTypeProperties(JsonObject jsonObject, BlockType blockType)
	{
		jsonObject.add("itemType", new JsonPrimitive(blockType.hasItemType()));
		jsonObject.add("solid", new JsonPrimitive(blockType.isSolid()));
		jsonObject.add("flammable", new JsonPrimitive(blockType.isFlammable()));
		jsonObject.add("burnable", new JsonPrimitive(blockType.isBurnable()));
		jsonObject.add("occluding", new JsonPrimitive(blockType.isOccluding()));
		jsonObject.add("gravity", new JsonPrimitive(blockType.hasGravity()));
		jsonObject.add("interactable", new JsonPrimitive(blockType.isInteractable()));
		jsonObject.add("hardness", new JsonPrimitive(blockType.getHardness()));
		jsonObject.add("slipperiness", new JsonPrimitive(blockType.getSlipperiness()));
		jsonObject.add("blastResistance", new JsonPrimitive(blockType.getBlastResistance()));
		jsonObject.add("air", new JsonPrimitive(blockType.isAir()));
		jsonObject.add("translationKey", new JsonPrimitive(blockType.getTranslationKey()));
	}

	private void addItemTypeProperties(JsonObject jsonObject, ItemType itemType)
	{
		jsonObject.add("maxStackSize", new JsonPrimitive(itemType.getMaxStackSize()));
		jsonObject.add("maxDurability", new JsonPrimitive(itemType.getMaxDurability()));
		jsonObject.add("edible", new JsonPrimitive(itemType.isEdible()));
		jsonObject.add("record", new JsonPrimitive(itemType.isRecord()));
		jsonObject.add("fuel", new JsonPrimitive(itemType.isFuel()));
		jsonObject.add("blockType", new JsonPrimitive(itemType.hasBlockType()));
		jsonObject.add("translationKey", new JsonPrimitive(itemType.getTranslationKey()));
		jsonObject.add("material",new JsonPrimitive(itemType.asMaterial().name()));
		if (itemType != ItemType.AIR)
		{
			jsonObject.add("metaClass", new JsonPrimitive(itemType.getItemMetaClass().getSimpleName()));
		}
	}

	private void addTrimMaterialProperties(JsonObject jsonObject, TrimMaterial trimMaterial)
	{
		jsonObject.add("description", GsonComponentSerializer.gson().serializeToTree(trimMaterial.description()));
	}

	private void addTrimPatternProperties(JsonObject jsonObject, TrimPattern trimPattern)
	{
		jsonObject.add("description", GsonComponentSerializer.gson().serializeToTree(trimPattern.description()));
	}

	private void addDamageTypeProperties(JsonObject jsonObject, DamageType damageType)
	{
		jsonObject.add("damageScaling", new JsonPrimitive(damageType.getDamageScaling().toString()));
		jsonObject.add("sound", new JsonPrimitive(damageType.getDamageEffect().getSound().getKey().toString()));
		jsonObject.add("deathMessageType", new JsonPrimitive(damageType.getDeathMessageType().toString()));
		jsonObject.add("exhaustion", new JsonPrimitive(damageType.getExhaustion()));
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

	private void addJukeboxSongProperties(JsonObject jsonObject, JukeboxSong jukeboxSong)
	{
		JsonObject description = new JsonObject();
		description.add("translate", new JsonPrimitive(jukeboxSong.getTranslationKey()));
		jsonObject.add("description", description);
	}

}
