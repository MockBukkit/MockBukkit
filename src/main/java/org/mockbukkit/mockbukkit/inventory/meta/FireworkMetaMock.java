package org.mockbukkit.mockbukkit.inventory.meta;

import com.google.common.base.Preconditions;
import io.papermc.paper.datacomponent.DataComponentType;
import io.papermc.paper.datacomponent.DataComponentTypes;
import io.papermc.paper.datacomponent.item.Fireworks;
import org.bukkit.FireworkEffect;
import org.bukkit.inventory.meta.FireworkMeta;
import org.bukkit.inventory.meta.ItemMeta;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;

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

	/**
	 * Constructs a new {@link FireworkMetaMock}.
	 */
	public FireworkMetaMock()
	{
		super();
	}

	@ApiStatus.Internal
	public FireworkMetaMock(Map<DataComponentType, Object> data)
	{
		super(data);
	}

	/**
	 * Constructs a new {@link FireworkMetaMock}, cloning the data from another.
	 *
	 * @param meta The meta to clone.
	 */
	public FireworkMetaMock(@NotNull ItemMeta meta)
	{
		super(meta);
	}

	@Override
	public @NotNull FireworkMetaMock clone()
	{
		return (FireworkMetaMock) super.clone();
	}

	@Override
	public void addEffect(@NotNull FireworkEffect effect)
	{
		Preconditions.checkNotNull(effect, "effect must never be null");
		Fireworks fireworks = get(DataComponentTypes.FIREWORKS);
		if (fireworks == null)
		{
			set(DataComponentTypes.FIREWORKS, Fireworks.fireworks().addEffect(effect).build());
		}
		else
		{
			List<FireworkEffect> effects = new ArrayList<>(fireworks.effects());
			effects.add(effect);
			set(DataComponentTypes.FIREWORKS, Fireworks.fireworks().addEffects(effects).build());
		}
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
		Fireworks fireworks = get(DataComponentTypes.FIREWORKS);
		if (fireworks == null)
		{
			return List.of();
		}
		return fireworks.effects();
	}

	@Override
	public int getEffectsSize()
	{
		Fireworks fireworks = get(DataComponentTypes.FIREWORKS);
		if (fireworks == null)
		{
			return 0;
		}
		return fireworks.effects().size();
	}

	@Override
	public void removeEffect(int index)
	{
		Fireworks fireworks = get(DataComponentTypes.FIREWORKS);
		if (fireworks == null)
		{
			return;
		}
		List<FireworkEffect> effects = new ArrayList<>(fireworks.effects());
		effects.remove(index);
		set(DataComponentTypes.FIREWORKS, Fireworks.fireworks(effects, fireworks.flightDuration()));
	}

	@Override
	public void clearEffects()
	{
		Fireworks fireworks = get(DataComponentTypes.FIREWORKS);
		if (fireworks == null)
		{
			return;
		}
		set(DataComponentTypes.FIREWORKS, Fireworks.fireworks(List.of(), fireworks.flightDuration()));
	}

	@Override
	public boolean hasEffects()
	{
		return !getEffects().isEmpty();
	}

	@Override
	public boolean hasPower()
	{
		return has(DataComponentTypes.FIREWORKS);
	}

	@Override
	public int getPower()
	{
		Fireworks fireworks = get(DataComponentTypes.FIREWORKS);
		if (fireworks == null)
		{
			return 0;
		}
		return fireworks.flightDuration();
	}

	@Override
	public void setPower(int power)
	{
		Preconditions.checkArgument(power >= 0, "power cannot be less than zero: %s", power);
		Preconditions.checkArgument(power <= 255, "power cannot be more than 255: %s", power);

		Fireworks fireworks = get(DataComponentTypes.FIREWORKS);
		if (fireworks == null)
		{
			set(DataComponentTypes.FIREWORKS, Fireworks.fireworks().flightDuration(power).build());
		}
		else
		{
			set(DataComponentTypes.FIREWORKS, Fireworks.fireworks(fireworks.effects(), power));
		}
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
		return serialMock;
	}

	@Override
	protected String getTypeName()
	{
		return "FIREWORK";
	}

}
