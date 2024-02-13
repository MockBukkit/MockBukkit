package be.seeseemelk.mockbukkit.damage;

import be.seeseemelk.mockbukkit.UnimplementedOperationException;
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

	private final DamageScaling damageScaling;
	private final DamageEffectMock damageEffect;
	private final NamespacedKey key;
	private final DeathMessageType deathMessageType;
	private final float exhaustion;

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
