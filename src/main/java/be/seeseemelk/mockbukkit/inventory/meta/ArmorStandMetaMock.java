package be.seeseemelk.mockbukkit.inventory.meta;

import com.destroystokyo.paper.inventory.meta.ArmorStandMeta;
import org.jetbrains.annotations.NotNull;

/**
 * Mock implementation of an {@link ArmorStandMeta}.
 *
 * @see ItemMetaMock
 */
public class ArmorStandMetaMock extends ItemMetaMock implements ArmorStandMeta
{

	private boolean invisible;
	private boolean noBasePlate;
	private boolean showArms;
	private boolean small;
	private boolean marker;

	/**
	 * Constructs a new {@link ArmorStandMetaMock}.
	 */
	public ArmorStandMetaMock()
	{
		super();
	}

	/**
	 * Constructs a new {@link ArmorStandMetaMock}, cloning the data from another.
	 *
	 * @param meta The meta to clone.
	 */
	public ArmorStandMetaMock(@NotNull ArmorStandMeta meta)
	{
		super(meta);

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
	public int hashCode()
	{
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + (invisible ? 1 : 0);
		result = prime * result + (noBasePlate ? 1 : 0);
		result = prime * result + (showArms ? 1 : 0);
		result = prime * result + (small ? 1 : 0);
		result = prime * result + (marker ? 1 : 0);
		return result;
	}

	@Override
	public boolean equals(Object obj)
	{
		if (!(obj instanceof ArmorStandMeta meta))
			return false;
		return super.equals(obj) && this.isInvisible() == meta.isInvisible()
				&& this.hasNoBasePlate() == meta.hasNoBasePlate() && this.shouldShowArms() == meta.shouldShowArms()
				&& this.isSmall() == meta.isSmall() && this.isMarker() == meta.isMarker();
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
