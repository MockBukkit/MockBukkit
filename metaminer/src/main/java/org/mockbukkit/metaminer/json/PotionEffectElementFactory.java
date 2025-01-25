package org.mockbukkit.metaminer.json;

import com.google.gson.JsonObject;
import org.bukkit.potion.PotionEffect;
import org.jetbrains.annotations.Nullable;

public class PotionEffectElementFactory
{
	/**
	 * Converts a potionEffect into a JsonElement.
	 *
	 * @param potionEffect The potionEffect to be converted.
	 *
	 * @return The element
	 */
	@Nullable
	public static JsonObject toJson(@Nullable PotionEffect potionEffect)
	{
		if (potionEffect == null)
		{
			return null;
		}

		JsonObject jsonElement = new JsonObject();
		jsonElement.addProperty("amplifier", potionEffect.getAmplifier());
		jsonElement.addProperty("duration", potionEffect.getDuration());
		jsonElement.add("type", ElementFactory.toJson(potionEffect.getType()));
		jsonElement.addProperty("ambient", potionEffect.isAmbient());
		jsonElement.addProperty("particles", potionEffect.hasParticles());
		jsonElement.addProperty("icon", potionEffect.hasIcon());
		jsonElement.add("hiddenEffect", toJson(potionEffect.getHiddenPotionEffect()));
		return jsonElement;
	}

	private PotionEffectElementFactory()
	{
		// Hide the public constructor
	}
}
