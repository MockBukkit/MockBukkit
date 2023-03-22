package be.seeseemelk.mockbukkit.inventory.meta;

import com.google.common.collect.ImmutableList;
import org.bukkit.inventory.meta.SuspiciousStewMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Mock implementation of a {@link SuspiciousStewMeta}.
 *
 * @see ItemMetaMock
 */
public class SuspiciousStewMetaMock extends ItemMetaMock implements SuspiciousStewMeta
{

	private @NotNull List<PotionEffect> effects = new ArrayList<>();

	/**
	 * Constructs a new {@link ArmorStandMetaMock}.
	 */
	public SuspiciousStewMetaMock()
	{
		super();
	}

	/**
	 * Constructs a new {@link ArmorStandMetaMock}, cloning the data from another.
	 *
	 * @param meta The meta to clone.
	 */
	public SuspiciousStewMetaMock(@NotNull SuspiciousStewMeta meta)
	{
		super(meta);

		this.effects = new ArrayList<>(meta.getCustomEffects());
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
			return false;
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

}
