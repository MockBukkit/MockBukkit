package org.mockbukkit.mockbukkit.inventory.meta;

import com.google.common.base.Preconditions;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.OminousBottleMeta;
import org.jetbrains.annotations.NotNull;

import java.util.Map;

public class OminousBottleMetaMock extends ItemMetaMock implements OminousBottleMeta
{

	private Integer amplifier;
	private static final String AMPLIFIER_KEY = "amplifier";

	/**
	 * Constructs a new {@link OminousBottleMetaMock}.
	 */
	public OminousBottleMetaMock()
	{
		super();
	}

	/**
	 * Constructs a new {@link OminousBottleMetaMock}, cloning the data from another.
	 *
	 * @param meta The meta to clone.
	 */
	public OminousBottleMetaMock(ItemMeta meta)
	{
		super(meta);

		if(meta instanceof OminousBottleMeta bottleMeta)
		{
			this.amplifier = bottleMeta.hasAmplifier() ? bottleMeta.getAmplifier() : null;
		}
	}

	@Override
	public boolean hasAmplifier()
	{
		return this.amplifier != null;
	}

	@Override
	public int getAmplifier()
	{
		Preconditions.checkState(this.hasAmplifier(),
				"'ominous_bottle_amplifier' data component is absent. Check hasAmplifier first!");
		return this.amplifier;
	}

	@Override
	public void setAmplifier(int amplifier)
	{
		Preconditions.checkArgument(0 <= amplifier && amplifier <= 4, "Amplifier must be in range [0, 4]");
		this.amplifier = amplifier;
	}

	@Override
	public @NotNull OminousBottleMetaMock clone()
	{
		OminousBottleMetaMock clone = (OminousBottleMetaMock) super.clone();
		clone.amplifier = this.amplifier;
		return clone;
	}

	@Override
	protected void deserializeInternal(@NotNull Map<String, Object> args)
	{
		super.deserializeInternal(args);
		if (args.containsKey(AMPLIFIER_KEY))
		{
			this.amplifier = (Integer) args.get(AMPLIFIER_KEY);
		}
	}

	public static @NotNull OminousBottleMetaMock deserialize(@NotNull Map<String, Object> args)
	{
		OminousBottleMetaMock serialMock = new OminousBottleMetaMock();
		serialMock.deserializeInternal(args);
		return serialMock;
	}

	@Override
	public @NotNull Map<String, Object> serialize()
	{
		final Map<String, Object> serialized = super.serialize();
		if (this.hasAmplifier())
		{
			serialized.put(AMPLIFIER_KEY, amplifier);
		}
		return serialized;
	}

}
