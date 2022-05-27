package be.seeseemelk.mockbukkit.inventory.meta;

import com.destroystokyo.paper.inventory.meta.ArmorStandMeta;
import org.jetbrains.annotations.NotNull;

public class ArmorStandMetaMock extends ItemMetaMock implements ArmorStandMeta
{

	private boolean invisible;
	private boolean noBasePlate;
	private boolean showArms;
	private boolean small;
	private boolean marker;

	public ArmorStandMetaMock()
	{
		super();
	}

	public ArmorStandMetaMock(ArmorStandMeta meta)
	{
		this.invisible = meta.isInvisible();
		this.marker = meta.isMarker();
		this.noBasePlate = meta.hasNoBasePlate();
		this.showArms = meta.shouldShowArms();
		this.small = meta.isSmall();
	}

	@Override
	public boolean isInvisible()
	{
		return invisible;
	}

	@Override
	public boolean hasNoBasePlate()
	{
		return noBasePlate;
	}

	@Override
	public boolean shouldShowArms()
	{
		return showArms;
	}

	@Override
	public boolean isSmall()
	{
		return small;
	}

	@Override
	public boolean isMarker()
	{
		return marker;
	}

	@Override
	public void setInvisible(boolean invisible)
	{
		this.invisible = invisible;
	}

	@Override
	public void setNoBasePlate(boolean noBasePlate)
	{
		this.noBasePlate = noBasePlate;
	}

	@Override
	public void setShowArms(boolean showArms)
	{
		this.showArms = showArms;
	}

	@Override
	public void setSmall(boolean small)
	{
		this.small = small;
	}

	@Override
	public void setMarker(boolean marker)
	{
		this.marker = marker;
	}

	@Override
	public @NotNull ArmorStandMetaMock clone()
	{
		ArmorStandMetaMock clone = (ArmorStandMetaMock) super.clone();

		clone.invisible = this.invisible;
		clone.marker = this.marker;
		clone.noBasePlate = this.noBasePlate;
		clone.showArms = this.showArms;
		clone.small = this.small;

		return clone;
	}

}
