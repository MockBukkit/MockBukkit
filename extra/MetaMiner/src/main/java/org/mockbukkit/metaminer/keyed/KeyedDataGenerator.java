package org.mockbukkit.metaminer.keyed;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import io.papermc.paper.registry.RegistryAccess;
import io.papermc.paper.registry.RegistryKey;
import net.kyori.adventure.text.serializer.gson.GsonComponentSerializer;
import org.bukkit.Keyed;
import org.bukkit.Registry;
import org.bukkit.generator.structure.Structure;
import org.bukkit.damage.DamageType;
import org.bukkit.enchantments.Enchantment;
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
		if (keyed instanceof Structure structure){
			jsonObject.add("type", new JsonPrimitive(structure.getStructureType().getKey().toString()));
		}
	}

	private void addItemTypeProperties(JsonObject jsonObject, ItemType itemType)
	{
		jsonObject.add("material", new JsonPrimitive(itemType.asMaterial().name()));
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
	}

}
