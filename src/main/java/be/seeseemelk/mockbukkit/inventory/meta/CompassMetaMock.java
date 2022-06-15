package be.seeseemelk.mockbukkit.inventory.meta;

import com.google.common.base.Preconditions;
import org.bukkit.Location;
import org.bukkit.inventory.meta.CompassMeta;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;

public class CompassMetaMock extends ItemMetaMock implements CompassMeta
{

	private Location lodestone;
	private boolean tracked;

	public CompassMetaMock()
	{
		super();
	}

	public CompassMetaMock(CompassMeta meta)
	{
		super(meta);

		this.lodestone = meta.getLodestone();
		this.tracked = meta.isLodestoneTracked();
	}

	@Override
	public boolean hasLodestone()
	{
		return this.lodestone != null;
	}

	@Override
	public @Nullable Location getLodestone()
	{
		return this.lodestone;
	}

	@Override
	public void setLodestone(@Nullable Location lodestone)
	{
		Preconditions.checkArgument(lodestone == null || lodestone.getWorld() != null, "world is null");
		this.lodestone = lodestone;
	}

	@Override
	public boolean isLodestoneTracked()
	{
		return this.tracked;
	}

	@Override
	public void setLodestoneTracked(boolean tracked)
	{
		this.tracked = tracked;
	}

	@Override
	public int hashCode()
	{
		final int prime = 31;
		int hash = super.hashCode();
		hash = prime * hash + (this.lodestone != null ? this.lodestone.hashCode() : 0);
		hash = prime * hash + (this.tracked ? 1 : 0);
		return hash;
	}

	@Override
	public boolean equals(Object obj)
	{
		if (!(obj instanceof CompassMeta meta))
			return false;
		return super.equals(obj) && Objects.equals(this.lodestone, meta.getLodestone()) && this.tracked == meta.isLodestoneTracked();
	}

	@Override
	public @NotNull CompassMetaMock clone()
	{
		CompassMetaMock clone = (CompassMetaMock) super.clone();
		clone.lodestone = this.lodestone == null ? null : this.lodestone.clone();
		clone.tracked = this.tracked;
		return clone;
	}

}
