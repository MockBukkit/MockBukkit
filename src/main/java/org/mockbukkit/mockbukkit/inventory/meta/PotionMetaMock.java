package org.mockbukkit.mockbukkit.inventory.meta;

import com.google.common.base.Preconditions;
import io.papermc.paper.datacomponent.DataComponentType;
import io.papermc.paper.datacomponent.DataComponentTypes;
import io.papermc.paper.datacomponent.item.PotionContents;
import org.bukkit.Color;
import org.bukkit.NamespacedKey;
import org.bukkit.Registry;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.potion.PotionData;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.potion.PotionType;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.annotations.Unmodifiable;
import org.mockbukkit.mockbukkit.exception.UnimplementedOperationException;
import org.mockbukkit.mockbukkit.potion.PotionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Mock implementation of a {@link PotionMeta}.
 *
 * @see ItemMetaMock
 */
public class PotionMetaMock extends ItemMetaMock implements PotionMeta
{

	/**
	 * Constructs a new {@link PotionMetaMock}.
	 */
	public PotionMetaMock()
	{
		super();
	}

	@ApiStatus.Internal
	public PotionMetaMock(Map<DataComponentType, Object> data)
	{
		super(data);
	}

	/**
	 * Constructs a new {@link PotionMetaMock}, cloning the data from another.
	 *
	 * @param meta The meta to clone.
	 */
	public PotionMetaMock(@NotNull ItemMeta meta)
	{
		super(meta);
	}

	@Override
	public @NotNull PotionMetaMock clone()
	{
		return (PotionMetaMock) super.clone();
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
	public @NotNull @Unmodifiable List<PotionEffect> getAllEffects()
	{
		throw new UnimplementedOperationException();
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

	@Override
	public boolean hasColor()
	{
		PotionContents potionContents = get(DataComponentTypes.POTION_CONTENTS);
		if (potionContents == null)
		{
			return false;
		}
		return potionContents.customColor() != null;
	}

	@Override
	public @Nullable Color getColor()
	{
		PotionContents potionContents = get(DataComponentTypes.POTION_CONTENTS);
		if (potionContents == null)
		{
			return null;
		}
		return potionContents.customColor();
	}

	@Override
	public void setColor(@Nullable Color color)
	{
		PotionContents potionContents = get(DataComponentTypes.POTION_CONTENTS);
		if (potionContents == null)
		{
			if (color != null)
			{
				set(DataComponentTypes.POTION_CONTENTS, PotionContents.potionContents().customColor(color).build());
			}
			return;
		}
		set(DataComponentTypes.POTION_CONTENTS, PotionContents.potionContents()
				.customName(potionContents.customName())
				.potion(potionContents.potion())
				.customColor(color)
				.addCustomEffects(potionContents.customEffects())
				.build()
		);
	}

	@Override
	public @NotNull Color computeEffectiveColor()
	{
		throw new UnimplementedOperationException();
	}

	@Override
	public boolean hasCustomPotionName()
	{
		PotionContents potionContents = get(DataComponentTypes.POTION_CONTENTS);
		if (potionContents == null)
		{
			return false;
		}
		return potionContents.customName() != null;
	}

	@Override
	public @Nullable String getCustomPotionName()
	{
		PotionContents potionContents = get(DataComponentTypes.POTION_CONTENTS);
		if (potionContents == null)
		{
			return null;
		}
		return potionContents.customName();
	}

	@Override
	public void setCustomPotionName(@Nullable String customName)
	{
		PotionContents potionContents = get(DataComponentTypes.POTION_CONTENTS);
		if (potionContents == null)
		{
			if (customName != null)
			{
				set(DataComponentTypes.POTION_CONTENTS, PotionContents.potionContents().customName(customName).build());
			}
			return;
		}
		set(DataComponentTypes.POTION_CONTENTS, PotionContents.potionContents()
				.customName(customName)
				.potion(potionContents.potion())
				.customColor(potionContents.customColor())
				.addCustomEffects(potionContents.customEffects())
				.build()
		);
	}

	@Override
	public void setBasePotionData(@Nullable PotionData data)
	{
		setBasePotionType(PotionUtils.fromBukkit(data));
	}

	@Override
	public @Nullable PotionData getBasePotionData()
	{
		return PotionUtils.toBukkit(getBasePotionType());
	}

	@Override
	public void setBasePotionType(@Nullable PotionType type)
	{
		PotionContents potionContents = get(DataComponentTypes.POTION_CONTENTS);
		if (potionContents == null)
		{
			if (type != null)
			{
				set(DataComponentTypes.POTION_CONTENTS, PotionContents.potionContents().potion(type).build());
			}
			return;
		}
		set(DataComponentTypes.POTION_CONTENTS, PotionContents.potionContents()
				.customName(potionContents.customName())
				.potion(type)
				.customColor(potionContents.customColor())
				.addCustomEffects(potionContents.customEffects())
				.build()
		);
	}

	@Override
	public @Nullable PotionType getBasePotionType()
	{
		PotionContents potionContents = get(DataComponentTypes.POTION_CONTENTS);
		if (potionContents == null)
		{
			return null;
		}
		return potionContents.potion();
	}

	@Override
	public boolean hasBasePotionType()
	{
		PotionContents potionContents = get(DataComponentTypes.POTION_CONTENTS);
		if (potionContents == null)
		{
			return false;
		}
		return potionContents.potion() != null;
	}

	@Override
	@Deprecated(since = "1.9")
	public boolean setMainEffect(@NotNull PotionEffectType type)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	/**
	 * Required method for Bukkit deserialization.
	 *
	 * @param args A serialized PotionMetaMock object in a Map&lt;String, Object&gt; format.
	 * @return A new instance of the PotionMetaMock class.
	 */
	public static @NotNull PotionMetaMock deserialize(@NotNull Map<String, Object> args)
	{
		PotionMetaMock serialMock = new PotionMetaMock();
		serialMock.deserializeInternal(args);
		return serialMock;
	}

	@Override
	protected String getTypeName()
	{
		return "POTION";
	}

}
