package org.mockbukkit.mockbukkit.inventory.meta;

import com.google.common.base.Preconditions;
import io.papermc.paper.datacomponent.DataComponentTypes;
import io.papermc.paper.datacomponent.item.LodestoneTracker;
import org.bukkit.Location;
import org.bukkit.inventory.meta.CompassMeta;
import org.bukkit.inventory.meta.ItemMeta;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.mockbukkit.mockbukkit.exception.UnimplementedOperationException;

import java.util.Map;
import java.util.Objects;

/**
 * Mock implementation of a {@link CompassMeta}.
 *
 * @see ItemMetaMock
 */
public class CompassMetaMock extends ItemMetaMock implements CompassMeta
{

	/**
	 * Constructs a new {@link CompassMetaMock}.
	 */
	public CompassMetaMock()
	{
		super();
	}

	/**
	 * Constructs a new {@link CompassMetaMock}, cloning the data from another.
	 *
	 * @param meta The meta to clone.
	 */
	public CompassMetaMock(@NotNull ItemMeta meta)
	{
		super(meta);
	}

	@Override
	public boolean hasLodestone()
	{
		LodestoneTracker lodestoneTracker = get(DataComponentTypes.LODESTONE_TRACKER);
		if (lodestoneTracker == null)
		{
			return false;
		}
		return lodestoneTracker.location() != null;
	}

	@Override
	public @Nullable Location getLodestone()
	{
		LodestoneTracker lodestoneTracker = get(DataComponentTypes.LODESTONE_TRACKER);
		if (lodestoneTracker == null)
		{
			return null;
		}
		return lodestoneTracker.location();
	}

	@Override
	public void setLodestone(@Nullable Location lodestone)
	{
		Preconditions.checkArgument(lodestone == null || lodestone.getWorld() != null, "world is null");
		LodestoneTracker lodestoneTracker = get(DataComponentTypes.LODESTONE_TRACKER);
		if (lodestoneTracker == null)
		{
			set(DataComponentTypes.LODESTONE_TRACKER, LodestoneTracker.lodestoneTracker().location(lodestone).build());
		}
		else
		{
			set(DataComponentTypes.LODESTONE_TRACKER, LodestoneTracker.lodestoneTracker(lodestone, lodestoneTracker.tracked()));
		}
	}

	@Override
	public boolean isLodestoneTracked()
	{
		LodestoneTracker lodestoneTracker = get(DataComponentTypes.LODESTONE_TRACKER);
		if (lodestoneTracker == null)
		{
			return false;
		}
		return lodestoneTracker.tracked();
	}

	@Override
	public void setLodestoneTracked(boolean tracked)
	{
		LodestoneTracker lodestoneTracker = get(DataComponentTypes.LODESTONE_TRACKER);
		if (lodestoneTracker == null)
		{
			set(DataComponentTypes.LODESTONE_TRACKER, LodestoneTracker.lodestoneTracker().tracked(tracked).build());
		}
		else
		{
			set(DataComponentTypes.LODESTONE_TRACKER, LodestoneTracker.lodestoneTracker(lodestoneTracker.location(), tracked));
		}
	}

	@Override
	public boolean isLodestoneCompass()
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public void clearLodestone()
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public @NotNull CompassMetaMock clone()
	{
		return (CompassMetaMock) super.clone();
	}

	/**
	 * Required method for Bukkit deserialization.
	 *
	 * @param args A serialized CompassMetaMock object in a Map&lt;String, Object&gt; format.
	 * @return A new instance of the CompassMetaMock class.
	 */
	public static @NotNull CompassMetaMock deserialize(@NotNull Map<String, Object> args)
	{
		CompassMetaMock serialMock = new CompassMetaMock();
		serialMock.deserializeInternal(args);
		return serialMock;
	}

	@Override
	protected String getTypeName()
	{
		return "COMPASS";
	}

}
