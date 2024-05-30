package be.seeseemelk.mockbukkit.potion;

import be.seeseemelk.mockbukkit.MockBukkit;
import be.seeseemelk.mockbukkit.UnimplementedOperationException;
import com.google.common.base.Preconditions;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.bukkit.NamespacedKey;
import org.bukkit.Registry;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.potion.PotionType;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class MockInternalPotionData implements PotionType.InternalPotionData
{

	private final NamespacedKey namespacedKey;
	private final boolean upgradeable;
	private final boolean extendable;
	private final int maxLevel;
	private final List<PotionEffect> potionEffects;

	public MockInternalPotionData(NamespacedKey namespacedKey)
	{
		List<PotionEffect> tempPotionEffects;
		this.namespacedKey = namespacedKey;
		try
		{
			JsonObject data = loadData(namespacedKey);
			tempPotionEffects = getPotionEffectFromData(data);
		}
		catch (IOException e)
		{
			tempPotionEffects = null;
		}
		this.potionEffects = tempPotionEffects;
		this.upgradeable = Registry.POTION
				.get(new NamespacedKey(namespacedKey.getNamespace(), "strong_" + namespacedKey.getKey())) != null;
		this.extendable = Registry.POTION
				.get(new NamespacedKey(namespacedKey.getNamespace(), "long_" + namespacedKey.getKey())) != null;
		this.maxLevel = this.isUpgradeable() ? 2 : 1;
	}

	private JsonObject loadData(NamespacedKey namespacedKey) throws IOException
	{
		String path = "/potion/" + namespacedKey.getKey() + ".json";
		if (MockBukkit.class.getResource(path) == null)
		{
			throw new FileNotFoundException(path);
		}

		try (BufferedReader reader = new BufferedReader(
				new InputStreamReader(MockBukkit.class.getResourceAsStream(path), StandardCharsets.UTF_8)))
		{
			JsonElement jsonElement = JsonParser.parseReader(reader);
			return jsonElement.getAsJsonObject();
		}
	}

	@Override
	public PotionEffectType getEffectType()
	{
		return this.getPotionEffects().isEmpty() ? null : this.getPotionEffects().get(0).getType();
	}

	@Override
	public List<PotionEffect> getPotionEffects()
	{
		if (potionEffects == null)
		{
			throw new UnimplementedOperationException("Unimplemented potion: " + namespacedKey);
		}
		return potionEffects;
	}

	@Override
	public boolean isInstant()
	{
		throw new UnimplementedOperationException();
	}

	@Override
	public boolean isUpgradeable()
	{
		return upgradeable;
	}

	@Override
	public boolean isExtendable()
	{
		return extendable;
	}

	@Override
	public int getMaxLevel()
	{
		return maxLevel;
	}

	private List<PotionEffect> getPotionEffectFromData(JsonObject data)
	{
		List<PotionEffect> potionEffectsList = new ArrayList<>();
		for (JsonElement potionEffectData : data.get("effects").getAsJsonArray())
		{
			JsonObject potionEffectDataObject = potionEffectData.getAsJsonObject();
			NamespacedKey potionEffectTypeKey = Preconditions
					.checkNotNull(NamespacedKey.fromString(potionEffectDataObject.get("type").getAsString()));
			PotionEffectType potionEffectType = Registry.POTION_EFFECT_TYPE.get(potionEffectTypeKey);
			int duration = potionEffectDataObject.get("duration").getAsInt();
			int amplifier = potionEffectDataObject.get("amplifier").getAsInt();
			boolean ambient = potionEffectDataObject.get("ambient").getAsBoolean();
			boolean particles = potionEffectDataObject.get("particles").getAsBoolean();
			boolean icon = potionEffectDataObject.get("icon").getAsBoolean();
			PotionEffect potionEffect = new PotionEffect(potionEffectType, duration, amplifier, ambient, particles,
					icon);
			potionEffectsList.add(potionEffect);
		}
		return potionEffectsList;
	}

}
