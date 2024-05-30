package be.seeseemelk.mockbukkit.damage;

import be.seeseemelk.mockbukkit.UnimplementedOperationException;
import com.google.common.base.Preconditions;
import com.google.gson.JsonObject;
import org.bukkit.NamespacedKey;
import org.bukkit.Registry;
import org.bukkit.Sound;
import org.bukkit.damage.DamageEffect;
import org.bukkit.damage.DamageScaling;
import org.bukkit.damage.DamageType;
import org.bukkit.damage.DeathMessageType;
import org.jetbrains.annotations.NotNull;

public class DamageTypeMock implements DamageType
{

	static final String EXHAUSTION = "exhaustion";
	static final String DEATH_MESSAGE_TYPE = "deathMessageType";
	static final String SOUND = "sound";
	static final String DAMAGE_SCALING = "damageScaling";
	static final String KEY = "key";

	/**
	 * Create a {@link DamageTypeMock} from a {@link JsonObject}.
	 * <p>
	 * Example:
	 * <pre>
	 * {
	 *       "key": "minecraft:in_fire",
	 *       "damageScaling": "WHEN_CAUSED_BY_LIVING_NON_PLAYER",
	 *       "sound": "minecraft:entity.player.hurt_on_fire",
	 *       "deathMessageType": "DEFAULT",
	 *       "exhaustion": 0.1
	 * }
	 * </pre>
	 *
	 * @param data The json data.
	 * @return The damage type created from the JSON
	 */
	public static DamageTypeMock from(JsonObject data)
	{
		Preconditions.checkArgument(data != null, "JsonObject can't be null");
		Preconditions.checkArgument(data.has(KEY), "JsonObject does not have a field named 'key'");
		Preconditions.checkArgument(data.has(DAMAGE_SCALING), "JsonObject does not have a field named 'damageScaling'");
		Preconditions.checkArgument(data.has(SOUND), "JsonObject does not have a field named 'sound'");
		Preconditions.checkArgument(data.has(DEATH_MESSAGE_TYPE),
				"JsonObject does not have a field named 'deathMessageType'");
		Preconditions.checkArgument(data.has(EXHAUSTION), "JsonObject does not have a field named 'exhaustion'");

		// Retrieve values from JSON
		String keyValue = data.get(KEY).getAsString();
		String damageScalingValue = data.get(DAMAGE_SCALING).getAsString();
		String soundValue = data.get(SOUND).getAsString();
		String deathMessageTypeValue = data.get(DEATH_MESSAGE_TYPE).getAsString();
		float exhaustion = data.get(EXHAUSTION).getAsFloat();

		// Parse values
		NamespacedKey key = NamespacedKey.fromString(keyValue);
		DamageScaling damageScaling = DamageScaling.valueOf(damageScalingValue);
		Sound sound = Registry.SOUNDS.get(NamespacedKey.fromString(soundValue));
		DamageEffectMock damageEffect = new DamageEffectMock(sound);
		DeathMessageType deathMessageType = DeathMessageType.valueOf(deathMessageTypeValue);

		// Create object
		return new DamageTypeMock(damageScaling, damageEffect, key, deathMessageType, exhaustion);
	}

	private final DamageScaling damageScaling;
	private final DamageEffectMock damageEffect;
	private final NamespacedKey key;
	private final DeathMessageType deathMessageType;
	private final float exhaustion;

	public DamageTypeMock(@NotNull DamageScaling damageScaling, @NotNull DamageEffectMock damageEffect,
			@NotNull NamespacedKey key, @NotNull DeathMessageType deathMessageType, float exhaustion)
	{
		Preconditions.checkArgument(damageScaling != null, "DamageScaling cannot be null");
		Preconditions.checkArgument(damageEffect != null, "DamageEffectMock cannot be null");
		Preconditions.checkArgument(key != null, "NamespacedKey cannot be null");
		Preconditions.checkArgument(deathMessageType != null, "DeathMessageType cannot be null");

		this.damageScaling = damageScaling;
		this.damageEffect = damageEffect;
		this.key = key;
		this.deathMessageType = deathMessageType;
		this.exhaustion = exhaustion;
	}

	/**
	 * @deprecated Will be replaced with {{@link #from(JsonObject)}}.
	 */
	@Deprecated(forRemoval = true)
	public DamageTypeMock(JsonObject data)
	{
		this.damageScaling = DamageScaling.valueOf(data.get("damageScaling").getAsString());
		Sound sound = Registry.SOUNDS.get(NamespacedKey.fromString(data.get("sound").getAsString()));
		this.damageEffect = new DamageEffectMock(sound);
		this.key = NamespacedKey.fromString(data.get("key").getAsString());
		this.deathMessageType = DeathMessageType.valueOf(data.get("deathMessageType").getAsString());
		this.exhaustion = data.get("exhaustion").getAsFloat();
	}

	@Override
	public @NotNull String getTranslationKey()
	{
		throw new UnimplementedOperationException();
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
		return key;
	}

}
