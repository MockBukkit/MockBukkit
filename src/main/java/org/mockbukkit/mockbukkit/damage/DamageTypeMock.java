package org.mockbukkit.mockbukkit.damage;

import com.google.common.base.Preconditions;
import com.google.gson.JsonObject;
import org.bukkit.NamespacedKey;
import org.bukkit.Registry;
import org.bukkit.Sound;
import org.bukkit.damage.DamageEffect;
import org.bukkit.damage.DamageScaling;
import org.bukkit.damage.DamageType;
import org.bukkit.damage.DeathMessageType;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;

public class DamageTypeMock implements DamageType
{

	static final String EXHAUSTION_KEY = "exhaustion";
	static final String DEATH_MESSAGE_TYPE_KEY = "deathMessageType";
	static final String SOUND_KEY = "sound";
	static final String DAMAGE_SCALING_KEY = "damageScaling";
	static final String KEY = "key";
	static final String TRANSLATION_KEY = "translationKey";


	private final DamageScaling damageScaling;
	private final DamageEffectMock damageEffect;
	private final NamespacedKey namespacedKey;
	private final DeathMessageType deathMessageType;
	private final float exhaustion;
	private final String translationKey;

	@ApiStatus.Internal
	public static DamageTypeMock from(JsonObject data)
	{
		Preconditions.checkNotNull(data, "JsonObject can't be null");
		Preconditions.checkArgument(data.has(KEY), "JsonObject does not have a field named 'key'");
		Preconditions.checkArgument(data.has(DAMAGE_SCALING_KEY), "JsonObject does not have a field named 'damageScaling'");
		Preconditions.checkArgument(data.has(SOUND_KEY), "JsonObject does not have a field named 'sound'");
		Preconditions.checkArgument(data.has(DEATH_MESSAGE_TYPE_KEY), "JsonObject does not have a field named 'deathMessageType'");
		Preconditions.checkArgument(data.has(EXHAUSTION_KEY), "JsonObject does not have a field named 'exhaustion'");

		// Retrieve values from JSON
		String keyValue = data.get(KEY).getAsString();
		String damageScalingValue = data.get(DAMAGE_SCALING_KEY).getAsString();
		String soundValue = data.get(SOUND_KEY).getAsString();
		String deathMessageTypeValue = data.get(DEATH_MESSAGE_TYPE_KEY).getAsString();
		String translationKey = data.get(TRANSLATION_KEY).getAsString();
		float exhaustion = data.get(EXHAUSTION_KEY).getAsFloat();

		// Parse values
		NamespacedKey key = NamespacedKey.fromString(keyValue);
		DamageScaling damageScaling = DamageScaling.valueOf(damageScalingValue);
		Sound sound = Registry.SOUNDS.get(NamespacedKey.fromString(soundValue));
		DamageEffectMock damageEffect = new DamageEffectMock(sound);
		DeathMessageType deathMessageType = DeathMessageType.valueOf(deathMessageTypeValue);
		return new DamageTypeMock(damageScaling, damageEffect, key, deathMessageType, exhaustion, translationKey);
	}

	@ApiStatus.Internal
	public DamageTypeMock(@NotNull DamageScaling damageScaling, @NotNull DamageEffectMock damageEffect, @NotNull NamespacedKey namespacedKey, @NotNull DeathMessageType deathMessageType, float exhaustion, @NotNull String translationKey)
	{
		Preconditions.checkNotNull(damageScaling, "DamageScaling cannot be null");
		Preconditions.checkNotNull(damageEffect, "DamageEffectMock cannot be null");
		Preconditions.checkNotNull(namespacedKey, "NamespacedKey cannot be null");
		Preconditions.checkNotNull(deathMessageType, "DeathMessageType cannot be null");
		Preconditions.checkNotNull(translationKey, "Translation key cannot be null");

		this.damageScaling = damageScaling;
		this.damageEffect = damageEffect;
		this.namespacedKey = namespacedKey;
		this.deathMessageType = deathMessageType;
		this.exhaustion = exhaustion;
		this.translationKey = translationKey;
	}

	/**
	 * @deprecated This constructor has been deprecated. Use {@link DamageTypeMock#from(JsonObject)} instead.
	 */
	@Deprecated(forRemoval = true, since = "v3.76.1")
	public DamageTypeMock(JsonObject data)
	{
		this.damageScaling = DamageScaling.valueOf(data.get(DAMAGE_SCALING_KEY).getAsString());
		Sound sound = Registry.SOUNDS.get(NamespacedKey.fromString(data.get(SOUND_KEY).getAsString()));
		this.damageEffect = new DamageEffectMock(sound);
		this.namespacedKey = NamespacedKey.fromString(data.get(KEY).getAsString());
		this.deathMessageType = DeathMessageType.valueOf(data.get(DEATH_MESSAGE_TYPE_KEY).getAsString());
		this.exhaustion = data.get(EXHAUSTION_KEY).getAsFloat();
		this.translationKey = data.get(TRANSLATION_KEY).getAsString();
	}

	@Override
	public @NotNull String getTranslationKey()
	{
		return translationKey;
	}

	@Override
	public @NotNull DamageScaling getDamageScaling()
	{
		return damageScaling;
	}

	@Override
	public @NotNull DamageEffect getDamageEffect()
	{
		return damageEffect;
	}

	@Override
	public @NotNull DeathMessageType getDeathMessageType()
	{
		return deathMessageType;
	}

	@Override
	public float getExhaustion()
	{
		return exhaustion;
	}

	@Override
	public @NotNull NamespacedKey getKey()
	{
		return namespacedKey;
	}

}
