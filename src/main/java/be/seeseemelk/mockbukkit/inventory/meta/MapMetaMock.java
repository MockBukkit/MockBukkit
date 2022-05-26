package be.seeseemelk.mockbukkit.inventory.meta;

import org.bukkit.Color;
import org.bukkit.inventory.meta.MapMeta;
import org.bukkit.map.MapView;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class MapMetaMock extends ItemMetaMock implements MapMeta
{

	private static final byte SCALING_EMPTY = (byte) 0;
	private static final byte SCALING_TRUE = (byte) 1;
	private static final byte SCALING_FALSE = (byte) 2;

	private Integer mapId;
	private MapView mapView;
	private Color color;
	private byte scaling = SCALING_EMPTY;

	public MapMetaMock()
	{
		super();
	}

	public MapMetaMock(MapMeta meta)
	{
		super(meta);

		try
		{
			this.mapId = meta.getMapId();
		} // If no map ID is set, it will throw a NPE when trying to convert the Integer to an int.
		catch (NullPointerException ignored)
		{
		}
		this.mapView = meta.getMapView();
		this.color = meta.getColor();
		this.scaling = meta instanceof MapMetaMock metaMock ? metaMock.scaling : meta.isScaling() ? SCALING_TRUE : SCALING_FALSE;
	}

	@Override
	public boolean hasMapId()
	{
		return mapId != null;
	}

	@Override
	public int getMapId()
	{
		return mapId;
	}

	@Override
	public void setMapId(int id)
	{
		this.mapId = id;
	}

	@Override
	public boolean hasMapView()
	{
		return this.mapView != null;
	}

	@Override
	public @Nullable MapView getMapView()
	{
		return mapView;
	}

	@Override
	public void setMapView(MapView map)
	{
		this.mapView = map;
	}

	@Override
	public boolean isScaling()
	{
		return this.scaling == SCALING_TRUE;
	}

	@Override
	public void setScaling(boolean scaling)
	{
		this.scaling = scaling ? SCALING_TRUE : SCALING_FALSE;
	}

	@Override
	public boolean hasLocationName()
	{
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException();
	}

	@Override
	public @Nullable String getLocationName()
	{
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException();
	}

	@Override
	public void setLocationName(@Nullable String name)
	{
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean hasColor()
	{
		return this.color != null;
	}

	@Override
	public @Nullable Color getColor()
	{
		return this.color;
	}

	@Override
	public void setColor(@Nullable Color color)
	{
		this.color = color;
	}

	@Override
	public @NotNull MapMetaMock clone()
	{
		MapMetaMock clone = (MapMetaMock) super.clone();
		clone.color = this.color;
		clone.mapId = this.mapId;
		clone.mapView = this.mapView;
		clone.scaling = this.scaling;
		return clone;
	}

}
