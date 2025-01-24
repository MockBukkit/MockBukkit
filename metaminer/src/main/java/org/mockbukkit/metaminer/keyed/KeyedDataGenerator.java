package org.mockbukkit.metaminer.keyed;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import io.papermc.paper.datacomponent.DataComponentType;
import io.papermc.paper.registry.RegistryAccess;
import io.papermc.paper.registry.RegistryKey;
import net.kyori.adventure.text.serializer.gson.GsonComponentSerializer;
import org.bukkit.Keyed;
import org.bukkit.Material;
import org.bukkit.Registry;
import org.bukkit.damage.DamageType;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.generator.structure.Structure;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ItemType;
import org.bukkit.potion.PotionEffectType;
import org.jetbrains.annotations.NotNull;
import org.mockbukkit.metaminer.DataGenerator;
import org.mockbukkit.metaminer.util.JsonUtil;

import java.io.File;
import java.io.IOException;

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

		for (RegistryKey<? extends Keyed> registryKey : KeyedClassTracker.CLASS_REGISTRY_KEY_RELATION.keySet())
		{
			Registry<? extends Keyed> registry = RegistryAccess.registryAccess().getRegistry(registryKey);
			JsonArray array = new JsonArray();
			for (Keyed keyed : registry)
			{
				JsonObject keyedObjectData = MethodDataScanner.findMethodData(keyed);
				addNonTrivialData(keyedObjectData, keyed);
				array.add(keyedObjectData);
			}
			File destinationFile = new File(dataFolder, registryKey.key().value() + ".json");
			JsonObject rootObject = new JsonObject();
			rootObject.add("values", array);
			JsonUtil.dump(rootObject, destinationFile);
		}
	}

	private void addNonTrivialData(JsonObject jsonObject, Keyed keyed)
	{
		if (keyed instanceof ItemType itemType)
		{
			addItemTypeProperties(jsonObject, itemType);
		}
		if (keyed instanceof DamageType damageType)
		{
			addDamageTypeProperties(jsonObject, damageType);
		}
		if (keyed instanceof PotionEffectType potionEffectType)
		{
			addPotionEffectTypeProperties(jsonObject, potionEffectType);
		}
		if (keyed instanceof Enchantment enchantment)
		{
			addEnchantmentProperties(jsonObject, enchantment);
		}
		if (keyed instanceof Structure structure)
		{
			jsonObject.add("type", new JsonPrimitive(structure.getStructureType().getKey().toString()));
		}
		if (keyed instanceof DataComponentType dataComponentType)
		{
			jsonObject.add("valued", new JsonPrimitive(dataComponentType instanceof DataComponentType.Valued<?>));
		}
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
		jsonObject.add("material", new JsonPrimitive(itemType.asMaterial().name()));
		jsonObject.add("rarity", new JsonPrimitive(itemType.getItemRarity().toString()));
		jsonObject.add("creativeCategory", new JsonPrimitive(itemType.getCreativeCategory().toString()));
		jsonObject.add("compostable", new JsonPrimitive(itemType.isCompostable()));
		if (itemType.isCompostable())
		{
			jsonObject.add("compostChance", new JsonPrimitive("%sF".formatted(itemType.getCompostChance())));
		}
		if (itemType != ItemType.AIR)
		{
			jsonObject.add("metaClass", new JsonPrimitive(itemType.getItemMetaClass().getSimpleName()));
		}
	}

	private void addDamageTypeProperties(JsonObject jsonObject, DamageType damageType)
	{
		jsonObject.add("damageScaling", new JsonPrimitive(damageType.getDamageScaling().toString()));
		jsonObject.add("sound", new JsonPrimitive(damageType.getDamageEffect().getSound().getKey().toString()));
		jsonObject.add("deathMessageType", new JsonPrimitive(damageType.getDeathMessageType().toString()));
	}

	private void addPotionEffectTypeProperties(JsonObject jsonObject, PotionEffectType potionEffectType)
	{
		jsonObject.add("rgb", new JsonPrimitive(potionEffectType.getColor().asRGB()));
		jsonObject.add("category", new JsonPrimitive(potionEffectType.getEffectCategory().toString()));
	}

	/**
	 * Add the given enchantment's properties to the given jsonObject
	 *
	 * @param jsonObject  the jsonObject to add the enchantment's properties to
	 * @param enchantment the enchantment to add the properties of
	 */
	private void addEnchantmentProperties(JsonObject jsonObject, Enchantment enchantment)
	{
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

		JsonArray conflicts = new JsonArray();
		for (Enchantment otherEnchantment : Enchantment.values())
		{
			if (enchantment.conflictsWith(otherEnchantment))
			{
				conflicts.add(otherEnchantment.getKey().toString());
			}
		}
		jsonObject.add("conflicts", conflicts);
		JsonArray enchantables = new JsonArray();
		for (Material material : Material.values())
		{
			if (material.isItem())
			{
				ItemStack itemStack = new ItemStack(material);
				if (enchantment.canEnchantItem(itemStack))
				{
					enchantables.add(material.getKey().toString());
				}
			}
		}
		jsonObject.add("enchantables", enchantables);
	}

}
