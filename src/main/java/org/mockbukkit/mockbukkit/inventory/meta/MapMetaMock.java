package org.mockbukkit.mockbukkit.inventory.meta;

import org.bukkit.Color;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.MapMeta;
import org.bukkit.map.MapView;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.mockbukkit.mockbukkit.exception.UnimplementedOperationException;

import java.util.Map;
import java.util.Objects;

/**
 * Mock implementation of an {@link MapMeta}.
 *
 * @see ItemMetaMock
 */
public class MapMetaMock extends ItemMetaMock implements MapMeta
{

	private static final byte SCALING_EMPTY = (byte) 0;
	private static final byte SCALING_TRUE = (byte) 1;
	private static final byte SCALING_FALSE = (byte) 2;

	private Integer mapId;
	private @Nullable MapView mapView;
	private @Nullable Color color;
	private byte scaling = SCALING_EMPTY;

	/**
	 * Constructs a new {@link MapMetaMock}.
	 */
	public MapMetaMock()
	{
		super();
	}

	/**
	 * Constructs a new {@link MapMetaMock}, cloning the data from another.
	 *
	 * @param meta The meta to clone.
	 */
	public MapMetaMock(@NotNull ItemMeta meta)
	{
		super(meta);

		if(meta instanceof MapMeta mapMeta)
		{
			if (mapMeta.hasMapId())
			{
				this.mapId = mapMeta.getMapId();
			}
			this.mapView = mapMeta.getMapView();
			this.color = mapMeta.getColor();
			if (mapMeta instanceof MapMetaMock metaMock)
			{
				this.scaling = metaMock.scaling;
			}
			else
			{
				if (mapMeta.isScaling()) this.scaling = SCALING_TRUE;
				else this.scaling = SCALING_FALSE;
			}
		}
	}

	@Override
	public boolean hasMapId()
	{
		return this.mapId != null;
	}

	@Override
	public int getMapId()
	{
		if (this.mapId == null)
		{
			throw new IllegalStateException("Map ID is not set. Are you checking #hasMapId() first?");
		}
		return this.mapId;
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
		throw new UnimplementedOperationException();
	}

	@Override
	public @Nullable String getLocationName()
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public void setLocationName(@Nullable String name)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
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
	public int hashCode()
	{
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((color == null) ? 0 : color.hashCode());
		result = prime * result + ((mapId == null) ? 0 : mapId.hashCode());
		result = prime * result + ((mapView == null) ? 0 : mapView.hashCode());
		result = prime * result + (scaling);
		return result;
	}

	@Override
	public boolean equals(Object obj)
	{
		if (!(obj instanceof MapMeta meta))
		{
			return false;
		}
		if (!super.equals(obj) ||
				((this.hasMapId() || meta.hasMapId()) && !Objects.equals(this.mapId, meta.getMapId())) ||
				!Objects.equals(this.mapView, meta.getMapView()) ||
				!Objects.equals(this.color, meta.getColor())) return false;
		if (meta instanceof MapMetaMock mapMeta) return this.scaling == mapMeta.scaling;
		return meta.isScaling() ? this.scaling == 1 : this.scaling == 2;
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

	/**
	 * Required method for Bukkit deserialization.
	 *
	 * @param args A serialized MapMetaMock object in a Map&lt;String, Object&gt; format.
	 * @return A new instance of the MapMetaMock class.
	 */
	public static @NotNull MapMetaMock deserialize(@NotNull Map<String, Object> args)
	{
		MapMetaMock serialMock = new MapMetaMock();
		serialMock.deserializeInternal(args);
		serialMock.mapId = (Integer) args.get("map-id");
		serialMock.mapView = (MapView) args.get("map-view");
		if (args.containsKey("color"))
		{
			serialMock.color = Color.fromARGB((int) args.get("color"));
		}
		serialMock.scaling = (byte) args.get("scaling");
		return serialMock;
	}

	/**
	 * Serializes the properties of an MapMetaMock to a HashMap.
	 * Unimplemented properties are not present in the map.
	 *
	 * @return A HashMap of String, Object pairs representing the MapMetaMock.
	 */
	@Override
	public @NotNull Map<String, Object> serialize()
	{
		final Map<String, Object> serialized = super.serialize();
		if (this.mapId != null)
		{
			serialized.put("map-id", this.mapId);
		}
		if (this.mapView != null)
		{
			serialized.put("map-view", this.mapView);
		}
		if (this.color != null)
		{
			serialized.put("color", this.color.asARGB());
		}
		serialized.put("scaling", this.scaling);

		return serialized;
	}

	@Override
	protected String getTypeName()
	{
		return "MAP";
	}

}
