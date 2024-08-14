package org.mockbukkit.metaminer.internal.potion;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import org.bukkit.Registry;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionType;
import org.mockbukkit.metaminer.DataGenerator;
import org.mockbukkit.metaminer.util.JsonUtil;

import java.io.File;
import java.io.IOException;

public class PotionDataGenerator implements DataGenerator
{

	private final File dataFolder;

	public PotionDataGenerator(File parentDataFolder)
	{
		this.dataFolder = new File(parentDataFolder, "potion");
	}

	@Override
	public void generateData() throws IOException
	{
		for (PotionType potionType : Registry.POTION)
		{
			this.generateIndividualData(potionType);
		}
	}

	private void generateIndividualData(PotionType potionType) throws IOException
	{
		JsonArray effects = new JsonArray();
		for (PotionEffect potionEffect : potionType.getPotionEffects())
		{
			JsonObject potionEffectObject = new JsonObject();
			potionEffectObject.add("type", new JsonPrimitive(potionEffect.getType().getKey().toString()));
			potionEffectObject.add("amplifier", new JsonPrimitive(potionEffect.getAmplifier()));
			potionEffectObject.add("duration", new JsonPrimitive(potionEffect.getDuration()));
			potionEffectObject.add("ambient", new JsonPrimitive(potionEffect.isAmbient()));
			potionEffectObject.add("particles", new JsonPrimitive(potionEffect.hasParticles()));
			potionEffectObject.add("icon", new JsonPrimitive(potionEffect.hasIcon()));
			effects.add(potionEffectObject);
		}

		File destinationFile = new File(dataFolder, potionType.getKey().getKey() + ".json");
		JsonObject rootObject = new JsonObject();
		rootObject.add("effects", effects);
		JsonUtil.dump(rootObject, destinationFile);
	}

}
