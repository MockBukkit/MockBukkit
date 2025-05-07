package org.mockbukkit.mockbukkit.inventory.meta;

import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableList;
import io.papermc.paper.datacomponent.DataComponentType;
import io.papermc.paper.datacomponent.DataComponentTypes;
import io.papermc.paper.datacomponent.item.PotionContents;
import io.papermc.paper.potion.SuspiciousEffectEntry;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SuspiciousStewMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.jetbrains.annotations.ApiStatus;
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

	/**
	 * Constructs a new {@link SuspiciousStewMetaMock}.
	 */
	public SuspiciousStewMetaMock()
	{
		super();
	}

	@ApiStatus.Internal
	public SuspiciousStewMetaMock(Map<DataComponentType, Object> data)
	{
		super(data);
	}

	/**
	 * Constructs a new {@link SuspiciousStewMetaMock}, cloning the data from another.
	 *
	 * @param meta The meta to clone.
	 */
	public SuspiciousStewMetaMock(@NotNull ItemMeta meta)
	{
		super(meta);
	}

	@Override
	public @NotNull SuspiciousStewMetaMock clone()
	{
		return (SuspiciousStewMetaMock) super.clone();
	}

	@Override
	public boolean addCustomEffect(@NotNull PotionEffect effect, boolean overwrite)
	{
		PotionContents potionContents = get(DataComponentTypes.POTION_CONTENTS);
		if (potionContents == null)
		{
			set(DataComponentTypes.POTION_CONTENTS, PotionContents.potionContents().addCustomEffect(effect).build());
			return true;
		}
		List<PotionEffect> effectList = new ArrayList<>(potionContents.customEffects());
		int index = indexOf(effect.getType(), effectList);

		if (index == -1)
		{
			set(DataComponentTypes.POTION_CONTENTS, PotionContents.potionContents()
					.addCustomEffects(effectList)
					.addCustomEffect(effect)
					.potion(potionContents.potion())
					.customName(potionContents.customName())
					.customColor(potionContents.customColor())
					.build());
			return true;
		}
		if (!overwrite)
		{
			return false;
		}

		PotionEffect prev = effectList.get(index);
		if (prev.getDuration() == effect.getDuration())
		{
			return false;
		}
		effectList.set(index, effect);
		set(DataComponentTypes.POTION_CONTENTS, PotionContents.potionContents()
				.addCustomEffects(effectList)
				.potion(potionContents.potion())
				.customName(potionContents.customName())
				.customColor(potionContents.customColor())
				.build());
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
		PotionContents potionContents = get(DataComponentTypes.POTION_CONTENTS);
		if (potionContents == null)
		{
			return false;
		}
		boolean empty = potionContents.customEffects().isEmpty();
		set(DataComponentTypes.POTION_CONTENTS, PotionContents.potionContents().potion(potionContents.potion()).customColor(potionContents.customColor()).customName(potionContents.customName()).build());
		return !empty;
	}

	@Override
	public @NotNull List<PotionEffect> getCustomEffects()
	{
		PotionContents potionContents = get(DataComponentTypes.POTION_CONTENTS);
		if (potionContents == null)
		{
			return List.of();
		}
		return potionContents.customEffects();
	}

	@Override
	public boolean hasCustomEffect(@NotNull PotionEffectType type)
	{
		PotionContents potionContents = get(DataComponentTypes.POTION_CONTENTS);
		if (potionContents == null)
		{
			return false;
		}
		return indexOf(type, potionContents.customEffects()) != -1;
	}

	@Override
	public boolean hasCustomEffects()
	{
		PotionContents potionContents = get(DataComponentTypes.POTION_CONTENTS);
		if (potionContents == null)
		{
			return false;
		}
		return !potionContents.customEffects().isEmpty();
	}

	@Override
	public boolean removeCustomEffect(@NotNull PotionEffectType type)
	{
		Preconditions.checkNotNull(type);
		PotionContents potionContents = get(DataComponentTypes.POTION_CONTENTS);
		if (potionContents == null)
		{
			return false;
		}
		List<PotionEffect> effects = potionContents.customEffects();
		List<PotionEffect> modifiedEffects = effects.stream().filter(effect -> effect.getType().equals(type)).toList();
		set(DataComponentTypes.POTION_CONTENTS, PotionContents.potionContents().addCustomEffects(modifiedEffects)
				.potion(potionContents.potion())
				.customColor(potionContents.customColor())
				.customName(potionContents.customName())
				.build()
		);
		return effects.size() != modifiedEffects.size();
	}

	private int indexOf(PotionEffectType type, List<PotionEffect> effects)
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
		return serialMock;
	}


	@Override
	protected String getTypeName()
	{
		return "SUSPICIOUS_STEW";
	}

}
