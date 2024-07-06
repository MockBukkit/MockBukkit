package be.seeseemelk.mockbukkit.inventory.meta;

import be.seeseemelk.mockbukkit.UnimplementedOperationException;
import be.seeseemelk.mockbukkit.potion.MockInternalPotionData;
import com.google.common.collect.ImmutableList;
import org.bukkit.Color;
import org.bukkit.UnsafeValues;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.potion.PotionData;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.potion.PotionType;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * Mock implementation of a {@link PotionMeta}.
 *
 * @see ItemMetaMock
 */
public class PotionMetaMock extends ItemMetaMock implements PotionMeta
{

	private @NotNull PotionData basePotionData = new PotionData(PotionType.AWKWARD);
	private @NotNull List<PotionEffect> effects = new ArrayList<>();
	private @Nullable Color color;

	/**
	 * Constructs a new {@link PotionMetaMock}.
	 */
	public PotionMetaMock()
	{
		super();
	}

	/**
	 * Constructs a new {@link PotionMetaMock}, cloning the data from another.
	 *
	 * @param meta The meta to clone.
	 */
	public PotionMetaMock(@NotNull PotionMeta meta)
	{
		super(meta);

		this.effects = new ArrayList<>(meta.getCustomEffects());
		this.basePotionData = meta.getBasePotionData();
		this.color = meta.getColor();
	}

	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + effects.hashCode();
		result = prime * result + basePotionData.hashCode();
		result = prime * result + (color == null ? 0 : color.hashCode());
		return result;
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
		if (!(obj instanceof PotionMetaMock other))
		{
			return false;
		}

		return effects.equals(other.effects) && Objects.equals(color, other.color)
				&& basePotionData.equals(other.basePotionData);
	}

	@Override
	public @NotNull PotionMetaMock clone()
	{
		PotionMetaMock mock = (PotionMetaMock) super.clone();
		mock.effects = new ArrayList<>(effects);
		mock.color = color == null ? null : Color.fromRGB(color.asRGB());
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

	@Override
	public boolean hasColor()
	{
		return color != null;
	}

	@Override
	public @Nullable Color getColor()
	{
		// Return an immutable copy
		return color == null ? null : Color.fromRGB(color.asRGB());
	}

	@Override
	public void setColor(@Nullable Color color)
	{
		this.color = color == null ? null : Color.fromRGB(color.asRGB());
	}

	@Override
	public void setBasePotionData(@Nullable PotionData data)
	{
		this.basePotionData = new PotionData(data.getType(), data.isExtended(), data.isUpgraded());
	}

	@Override
	public @Nullable PotionData getBasePotionData()
	{
		return new PotionData(basePotionData.getType(), basePotionData.isExtended(), basePotionData.isUpgraded());
	}

	@Override
	public void setBasePotionType(@NotNull PotionType type)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public @NotNull PotionType getBasePotionType()
	{
		return basePotionData.getType();
	}

	@Override
	public boolean hasBasePotionType()
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
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
	@SuppressWarnings("unchecked")
	public static @NotNull PotionMetaMock deserialize(@NotNull Map<String, Object> args)
	{
		PotionMetaMock serialMock = new PotionMetaMock();
		serialMock.deserializeInternal(args);
		serialMock.effects = ((List<Map<String, Object>>) args.get("effects")).stream()
				.map(PotionEffect::new).toList();
		return serialMock;
	}

	/**
	 * Serializes the properties of an PotionMetaMock to a HashMap.
	 * Unimplemented properties are not present in the map.
	 *
	 * @return A HashMap of String, Object pairs representing the PotionMetaMock.
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
		return "POTION";
	}

}
