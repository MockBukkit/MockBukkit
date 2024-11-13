package org.mockbukkit.mockbukkit.inventory.meta;

import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableList;
import org.bukkit.FireworkEffect;
import org.bukkit.inventory.meta.FireworkMeta;
import org.bukkit.inventory.meta.ItemMeta;
import org.jetbrains.annotations.NotNull;
import org.mockbukkit.mockbukkit.exception.UnimplementedOperationException;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Mock implementation of an {@link FireworkMeta}.
 *
 * @see ItemMetaMock
 */
public class FireworkMetaMock extends ItemMetaMock implements FireworkMeta
{

	private @NotNull List<FireworkEffect> effects = new ArrayList<>();
	private int power = 0;

	/**
	 * Constructs a new {@link FireworkMetaMock}.
	 */
	public FireworkMetaMock()
	{
		super();
	}

	/**
	 * Constructs a new {@link FireworkMetaMock}, cloning the data from another.
	 *
	 * @param meta The meta to clone.
	 */
	public FireworkMetaMock(@NotNull ItemMeta meta)
	{
		super(meta);

		if(meta instanceof FireworkMeta fireworkMeta)
		{
			this.effects.addAll(fireworkMeta.getEffects());
			this.power = fireworkMeta.getPower();
		}
	}

	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = super.hashCode();
		return prime * result + effects.hashCode();
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
		if (!(obj instanceof FireworkMetaMock other))
		{
			return false;
		}

		return effects.equals(other.effects);
	}

	@Override
	public @NotNull FireworkMetaMock clone()
	{
		FireworkMetaMock mock = (FireworkMetaMock) super.clone();
		mock.effects = new ArrayList<>(this.effects);
		return mock;
	}

	@Override
	public void addEffect(@NotNull FireworkEffect effect)
	{
		Preconditions.checkNotNull(effect, "effect must never be null");
		effects.add(effect);
	}

	@Override
	public void addEffects(@NotNull FireworkEffect @NotNull ... effects)
	{
		Preconditions.checkNotNull(effects, "effects must never be null");

		for (FireworkEffect effect : effects)
		{
			addEffect(effect);
		}
	}

	@Override
	public void addEffects(@NotNull Iterable<FireworkEffect> effects)
	{
		Preconditions.checkNotNull(effects, "effects must never be null");

		for (FireworkEffect effect : effects)
		{
			addEffect(effect);
		}
	}

	@Override
	public @NotNull List<FireworkEffect> getEffects()
	{
		return ImmutableList.copyOf(effects);
	}

	@Override
	public int getEffectsSize()
	{
		return effects.size();
	}

	@Override
	public void removeEffect(int index)
	{
		effects.remove(index);
	}

	@Override
	public void clearEffects()
	{
		effects.clear();
	}

	@Override
	public boolean hasEffects()
	{
		return !effects.isEmpty();
	}

	@Override
	public boolean hasPower()
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public int getPower()
	{
		return power;
	}

	@Override
	public void setPower(int power)
	{
		if (power < 0 || power > 128)
		{
			throw new IllegalArgumentException("Power must be between 0 and 128");
		}

		this.power = power;
	}

	/**
	 * Required method for Bukkit deserialization.
	 *
	 * @param args A serialized FireworkMetaMock object in a Map&lt;String, Object&gt; format.
	 * @return A new instance of the FireworkMetaMock class.
	 */
	@SuppressWarnings("unchecked")
	public static @NotNull FireworkMetaMock deserialize(@NotNull Map<String, Object> args)
	{
		FireworkMetaMock serialMock = new FireworkMetaMock();
		serialMock.deserializeInternal(args);
		serialMock.addEffects(((List<?>) args.get("effects")).stream()
				.map(e -> (FireworkEffect) FireworkEffect.deserialize((Map<String, Object>) e))
				.toList());
		return serialMock;
	}

	/**
	 * Serializes the properties of an FireworkMetaMock to a HashMap.
	 * Unimplemented properties are not present in the map.
	 *
	 * @return A HashMap of String, Object pairs representing the FireworkMetaMock.
	 */
	@Override
	public @NotNull Map<String, Object> serialize()
	{
		final Map<String, Object> serialized = super.serialize();
		serialized.put("effects", effects.stream().map(FireworkEffect::serialize).toList());
		return serialized;
	}

	@Override
	protected String getTypeName()
	{
		return "FIREWORK";
	}

}
