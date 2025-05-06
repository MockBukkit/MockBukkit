package org.mockbukkit.mockbukkit.inventory.meta;

import io.papermc.paper.datacomponent.DataComponentType;
import io.papermc.paper.datacomponent.DataComponentTypes;
import org.bukkit.FireworkEffect;
import org.bukkit.inventory.meta.FireworkEffectMeta;
import org.bukkit.inventory.meta.ItemMeta;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Map;

/**
 * Mock implementation of an {@link FireworkEffectMeta}.
 *
 * @see ItemMetaMock
 */
public class FireworkEffectMetaMock extends ItemMetaMock implements FireworkEffectMeta
{

	/**
	 * Constructs a new {@link FireworkEffectMetaMock}.
	 */
	public FireworkEffectMetaMock()
	{
		super();
	}

	@ApiStatus.Internal
	public FireworkEffectMetaMock(Map<DataComponentType, Object> data)
	{
		super(data);
	}

	/**
	 * Constructs a new {@link FireworkEffectMetaMock}, cloning the data from another.
	 *
	 * @param meta The meta to clone.
	 */
	public FireworkEffectMetaMock(@NotNull ItemMeta meta)
	{
		super(meta);
	}


	@Override
	public @NotNull FireworkEffectMetaMock clone()
	{
		return (FireworkEffectMetaMock) super.clone();
	}

	@Override
	public void setEffect(@Nullable FireworkEffect effect)
	{
		if (effect == null)
		{
			unset(DataComponentTypes.FIREWORK_EXPLOSION);
		}
		else
		{
			set(DataComponentTypes.FIREWORK_EXPLOSION, effect);
		}
	}

	@Override
	public boolean hasEffect()
	{
		return has(DataComponentTypes.FIREWORK_EXPLOSION);
	}

	@Override
	public @Nullable FireworkEffect getEffect()
	{
		return get(DataComponentTypes.FIREWORK_EXPLOSION);
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
		return serialMock;
	}

	@Override
	protected String getTypeName()
	{
		return "FIREWORK_EFFECT";
	}

}
