package org.mockbukkit.mockbukkit.map;

import org.mockbukkit.mockbukkit.ServerMock;
import org.mockbukkit.mockbukkit.exception.UnimplementedOperationException;
import org.mockbukkit.mockbukkit.entity.PlayerMock;
import org.bukkit.World;
import org.bukkit.map.MapRenderer;
import org.bukkit.map.MapView;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Mock implementation of a {@link MapView}.
 */
public class MapViewMock implements MapView
{

	private World world;
	private final int id;
	private final @NotNull List<MapRenderer> renderers;
	private final Map<MapRenderer, Map<PlayerMock, MapCanvasMock>> canvases = new HashMap<>();
	private Scale scale;
	private boolean locked;

	/**
	 * Constructs a new {@link MapViewMock} for the given world with the specified ID.
	 * This is for internal use only, please use {@link ServerMock#createMap(World)} for creating maps.
	 *
	 * @param world The world this map is for.
	 * @param id    The ID of the mop.
	 * @see ServerMock#createMap(World)
	 */
	@ApiStatus.Internal
	public MapViewMock(World world, int id)
	{
		this.world = world;
		this.id = id;
		this.renderers = new ArrayList<>();
		this.scale = Scale.NORMAL;
		this.locked = false;
	}

	@Override
	public int getId()
	{
		return this.id;
	}

	@Override
	public boolean isVirtual()
	{
		return !this.renderers.isEmpty() && !(this.renderers.get(0) instanceof MapRendererMock);
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
		throw new UnimplementedOperationException();
	}

	@Override
	public int getCenterZ()
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public void setCenterX(int x)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public void setCenterZ(int z)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
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
		if (!this.renderers.contains(renderer))
		{
			this.renderers.add(renderer);
			this.canvases.put(renderer, new HashMap<>());
			renderer.initialize(this);
		}
	}

	@Override
	public boolean removeRenderer(@Nullable MapRenderer renderer)
	{
		if (!this.renderers.contains(renderer))
			return false;

		this.renderers.remove(renderer);

		// canvases should always be in sync with renderers.
		for (MapCanvasMock canvas : this.canvases.get(renderer).values())
		{
			MapCanvasMock.executeForAllPixels((x, y) -> canvas.setPixel(x, y, (byte) -1));
		}

		this.canvases.remove(renderer);
		return true;
	}

	/**
	 * Renders the map for the given player.
	 *
	 * @param player Player to render for.
	 */
	public void render(@NotNull PlayerMock player)
	{
		for (MapRenderer renderer : this.renderers)
		{
			MapCanvasMock canvas = this.canvases.get(renderer).get(renderer.isContextual() ? player : null);
			if (canvas == null)
			{
				canvas = new MapCanvasMock(this);
				this.canvases.get(renderer).put(renderer.isContextual() ? player : null, canvas);
			}

			try
			{
				renderer.render(this, canvas, player);
			}
			catch (Throwable ignored)
			{
			}
		}
	}

	@Override
	public boolean isTrackingPosition()
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public void setTrackingPosition(boolean trackingPosition)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public boolean isUnlimitedTracking()
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

	@Override
	public void setUnlimitedTracking(boolean unlimited)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
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
