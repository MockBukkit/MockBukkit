package org.mockbukkit.mockbukkit.inventory.meta;

import com.google.common.base.Preconditions;
import io.papermc.paper.datacomponent.DataComponentType;
import io.papermc.paper.datacomponent.DataComponentTypes;
import io.papermc.paper.datacomponent.item.OminousBottleAmplifier;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.OminousBottleMeta;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;

import java.util.Map;

public class OminousBottleMetaMock extends ItemMetaMock implements OminousBottleMeta
{

	private static final String AMPLIFIER_KEY = "amplifier";

	/**
	 * Constructs a new {@link OminousBottleMetaMock}.
	 */
	public OminousBottleMetaMock()
	{
		super();
	}

	@ApiStatus.Internal
	public OminousBottleMetaMock(Map<DataComponentType, Object> data)
	{
		super(data);
	}

	/**
	 * Constructs a new {@link OminousBottleMetaMock}, cloning the data from another.
	 *
	 * @param meta The meta to clone.
	 */
	public OminousBottleMetaMock(ItemMeta meta)
	{
		super(meta);
	}

	@Override
	public boolean hasAmplifier()
	{
		return has(DataComponentTypes.OMINOUS_BOTTLE_AMPLIFIER);
	}

	@Override
	public int getAmplifier()
	{
		Preconditions.checkState(this.hasAmplifier(),
				"'ominous_bottle_amplifier' data component is absent. Check hasAmplifier first!");
		return get(DataComponentTypes.OMINOUS_BOTTLE_AMPLIFIER).amplifier();
	}

	@Override
	public void setAmplifier(int amplifier)
	{
		Preconditions.checkArgument(0 <= amplifier && amplifier <= 4, "Amplifier must be in range [0, 4]");
		set(DataComponentTypes.OMINOUS_BOTTLE_AMPLIFIER, OminousBottleAmplifier.amplifier(amplifier));
	}

	@Override
	public @NotNull OminousBottleMetaMock clone()
	{
		return (OminousBottleMetaMock) super.clone();
	}

	public static @NotNull OminousBottleMetaMock deserialize(@NotNull Map<String, Object> args)
	{
		OminousBottleMetaMock serialMock = new OminousBottleMetaMock();
		serialMock.deserializeInternal(args);
		return serialMock;
	}

}
