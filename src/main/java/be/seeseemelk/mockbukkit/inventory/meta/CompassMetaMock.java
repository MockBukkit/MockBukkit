package be.seeseemelk.mockbukkit.inventory.meta;

import com.google.common.base.Preconditions;
import org.bukkit.Location;
import org.bukkit.inventory.meta.CompassMeta;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

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
	public @NotNull CompassMetaMock clone()
	{
		CompassMetaMock clone = (CompassMetaMock) super.clone();
		clone.lodestone = this.lodestone;
		clone.tracked = this.tracked;
		return clone;
	}

}
