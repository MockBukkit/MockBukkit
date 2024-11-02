package org.mockbukkit.mockbukkit.inventory.meta;

import org.bukkit.FireworkEffect;
import org.bukkit.inventory.meta.FireworkEffectMeta;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Map;
import java.util.Objects;

/**
 * Mock implementation of an {@link FireworkEffectMeta}.
 *
 * @see ItemMetaMock
 */
public class FireworkEffectMetaMock extends ItemMetaMock implements FireworkEffectMeta
{

	private @Nullable FireworkEffect effect;

	/**
	 * Constructs a new {@link FireworkEffectMetaMock}.
	 */
	public FireworkEffectMetaMock()
	{
		super();
	}

	/**
	 * Constructs a new {@link FireworkEffectMetaMock}, cloning the data from another.
	 *
	 * @param meta The meta to clone.
	 */
	public FireworkEffectMetaMock(@NotNull FireworkEffectMeta meta)
	{
		super(meta);

		this.effect = meta.getEffect();
	}

	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = super.hashCode();
		return prime * result + (effect != null ? effect.hashCode() : 0);
	}

	@Override
	public boolean equals(Object obj)
	{
		if (this == obj)
		{
			return true;
		}
		if (!super.equals(obj))
		{
			return false;
		}
		if (!(obj instanceof FireworkEffectMetaMock other))
		{
			return false;
		}

		return Objects.equals(effect, other.effect);
	}

	@Override
	public @NotNull FireworkEffectMetaMock clone()
	{
		FireworkEffectMetaMock mock = (FireworkEffectMetaMock) super.clone();
		mock.effect = this.effect;
		return mock;
	}

	@Override
	public void setEffect(@Nullable FireworkEffect effect)
	{
		this.effect = effect;
	}

	@Override
	public boolean hasEffect()
	{
		return effect != null;
	}

	@Override
	public @Nullable FireworkEffect getEffect()
	{
		return effect;
	}

	/**
	 * Required method for Bukkit deserialization.
	 *
	 * @param args A serialized FireworkEffectMetaMock object in a Map&lt;String, Object&gt; format.
	 * @return A new instance of the FireworkEffectMetaMock class.
	 */
	@SuppressWarnings("unchecked")
	public static @NotNull FireworkEffectMetaMock deserialize(@NotNull Map<String, Object> args)
	{
		FireworkEffectMetaMock serialMock = new FireworkEffectMetaMock();
		serialMock.deserializeInternal(args);
		if (args.containsKey("effect"))
		{
			serialMock.effect = (FireworkEffect) FireworkEffect.deserialize((Map<String, Object>) args.get("effect"));
		}
		return serialMock;
	}

	/**
	 * Serializes the properties of an FireworkEffectMetaMock to a HashMap.
	 * Unimplemented properties are not present in the map.
	 *
	 * @return A HashMap of String, Object pairs representing the FireworkEffectMetaMock.
	 */
	@Override
	public @NotNull Map<String, Object> serialize()
	{
		final Map<String, Object> serialized = super.serialize();
		if (effect != null)
		{
			serialized.put("effect", effect.serialize());
		}
		return serialized;
	}

	@Override
	protected String getTypeName()
	{
		return "FIREWORK_EFFECT";
	}

}
