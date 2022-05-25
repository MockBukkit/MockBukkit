package be.seeseemelk.mockbukkit.map;

import org.bukkit.World;
import org.bukkit.map.MapRenderer;
import org.bukkit.map.MapView;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MapViewMock implements MapView
{

	private static final Map<Integer, MapViewMock> mapViews = new HashMap<>();
	private static int nextId = 1;

	public static MapViewMock getMapView(int id)
	{
		return mapViews.get(id);
	}


	private World world;
	private final int id;
	private final List<MapRenderer> renderers = new ArrayList<>();
	private Scale scale;
	private boolean locked;

	public MapViewMock(World world)
	{
		this.world = world;
		this.id = nextId++;
		mapViews.put(this.id, this);
	}

	@Override
	public int getId()
	{
		return this.id;
	}

	@Override
	public boolean isVirtual()
	{
		return this.renderers.size() > 0 && !(this.renderers.get(0) instanceof MapRendererMock);
	}

	@Override
	public @NotNull Scale getScale()
	{
		return scale;
	}

	@Override
	public void setScale(@NotNull Scale scale)
	{
		this.scale = scale;
	}

	@Override
	public int getCenterX()
	{
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException();
	}

	@Override
	public int getCenterZ()
	{
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException();
	}

	@Override
	public void setCenterX(int x)
	{
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException();
	}

	@Override
	public void setCenterZ(int z)
	{
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException();
	}

	@Override
	public @Nullable World getWorld()
	{
		return this.world;
	}

	@Override
	public void setWorld(@NotNull World world)
	{
		this.world = world;
	}

	@Override
	public @NotNull List<MapRenderer> getRenderers()
	{
		return new ArrayList<>(this.renderers);
	}

	@Override
	public void addRenderer(@NotNull MapRenderer renderer)
	{
		this.renderers.add(renderer);
	}

	@Override
	public boolean removeRenderer(@Nullable MapRenderer renderer)
	{
		return this.renderers.remove(renderer);
	}

	@Override
	public boolean isTrackingPosition()
	{
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException();
	}

	@Override
	public void setTrackingPosition(boolean trackingPosition)
	{
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean isUnlimitedTracking()
	{
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException();
	}

	@Override
	public void setUnlimitedTracking(boolean unlimited)
	{
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean isLocked()
	{
		return this.locked;
	}

	@Override
	public void setLocked(boolean locked)
	{
		this.locked = locked;
	}

}
