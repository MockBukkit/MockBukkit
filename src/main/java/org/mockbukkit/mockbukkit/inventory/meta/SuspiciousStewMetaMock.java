package org.mockbukkit.mockbukkit.inventory.meta;

import com.google.common.collect.ImmutableList;
import io.papermc.paper.potion.SuspiciousEffectEntry;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SuspiciousStewMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.jetbrains.annotations.NotNull;
import org.mockbukkit.mockbukkit.exception.UnimplementedOperationException;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Mock implementation of a {@link SuspiciousStewMeta}.
 *
 * @see ItemMetaMock
 */
public class SuspiciousStewMetaMock extends ItemMetaMock implements SuspiciousStewMeta
{

	private @NotNull List<PotionEffect> effects = new ArrayList<>();

	/**
	 * Constructs a new {@link SuspiciousStewMetaMock}.
	 */
	public SuspiciousStewMetaMock()
	{
		super();
	}

	/**
	 * Constructs a new {@link SuspiciousStewMetaMock}, cloning the data from another.
	 *
	 * @param meta The meta to clone.
	 */
	public SuspiciousStewMetaMock(@NotNull ItemMeta meta)
	{
		super(meta);

		if(meta instanceof SuspiciousStewMeta stewMeta)
		{
			this.effects = new ArrayList<>(stewMeta.getCustomEffects());
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
		if (!(obj instanceof SuspiciousStewMetaMock meta))
		{
			return false;
		}
		return this.effects.equals(meta.effects);
	}

	@Override
	public @NotNull SuspiciousStewMetaMock clone()
	{
		SuspiciousStewMetaMock mock = (SuspiciousStewMetaMock) super.clone();
		mock.effects = new ArrayList<>(effects);
		return mock;
	}

	@Override
	public boolean addCustomEffect(@NotNull PotionEffect effect, boolean overwrite)
	{
		int index = indexOf(effect.getType());

		if (index == -1)
		{
			effects.add(effect);
			return true;
		}

		if (!overwrite)
		{
			return false;
		}

		PotionEffect prev = effects.get(index);
		if (prev.getDuration() == effect.getDuration())
		{
			return false;
		}

		effects.set(index, effect);
		return true;
	}

	@Override
	public boolean addCustomEffect(@NotNull SuspiciousEffectEntry suspiciousEffectEntry, boolean overwrite)
	{
		throw new UnimplementedOperationException();
	}

	@Override
	public boolean clearCustomEffects()
	{
		boolean empty = effects.isEmpty();
		effects.clear();
		return !empty;
	}

	@Override
	public @NotNull List<PotionEffect> getCustomEffects()
	{
		return ImmutableList.copyOf(effects);
	}

	@Override
	public boolean hasCustomEffect(@NotNull PotionEffectType type)
	{
		return indexOf(type) != -1;
	}

	@Override
	public boolean hasCustomEffects()
	{
		return !effects.isEmpty();
	}

	@Override
	public boolean removeCustomEffect(@NotNull PotionEffectType type)
	{
		Iterator<PotionEffect> iterator = effects.iterator();
		boolean changed = false;

		while (iterator.hasNext())
		{
			PotionEffect effect = iterator.next();

			if (type.equals(effect.getType()))
			{
				iterator.remove();
				changed = true;
			}
		}

		return changed;
	}

	private int indexOf(PotionEffectType type)
	{
		for (int i = 0; i < effects.size(); ++i)
		{
			if (effects.get(i).getType().equals(type))
			{
				return i;
			}
		}

		return -1;
	}

	/**
	 * Required method for Bukkit deserialization.
	 *
	 * @param args A serialized SuspiciousStewMetaMock object in a Map&lt;String, Object&gt; format.
	 * @return A new instance of the SuspiciousStewMetaMock class.
	 */
	@SuppressWarnings("unchecked")
	public static @NotNull SuspiciousStewMetaMock deserialize(@NotNull Map<String, Object> args)
	{
		SuspiciousStewMetaMock serialMock = new SuspiciousStewMetaMock();
		serialMock.deserializeInternal(args);
		serialMock.effects = ((List<Map<String, Object>>) args.get("effects")).stream()
				.map(PotionEffect::new).toList();
		return serialMock;
	}

	/**
	 * Serializes the properties of an SuspiciousStewMetaMock to a HashMap.
	 * Unimplemented properties are not present in the map.
	 *
	 * @return A HashMap of String, Object pairs representing the SuspiciousStewMetaMock.
	 */
	@Override
	public @NotNull Map<String, Object> serialize()
	{
		final Map<String, Object> serialized = super.serialize();
		serialized.put("effects", this.effects.stream().map(PotionEffect::serialize).toList());
		return serialized;
	}

	@Override
	protected String getTypeName()
	{
		return "SUSPICIOUS_STEW";
	}

}
