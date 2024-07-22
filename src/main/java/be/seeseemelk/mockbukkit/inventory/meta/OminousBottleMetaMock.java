package be.seeseemelk.mockbukkit.inventory.meta;

import com.google.common.base.Preconditions;
import org.bukkit.inventory.meta.OminousBottleMeta;
import org.jetbrains.annotations.NotNull;

public class OminousBottleMetaMock extends ItemMetaMock implements OminousBottleMeta
{

	private Integer amplifier;

	public OminousBottleMetaMock()
	{
		super();
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
}
