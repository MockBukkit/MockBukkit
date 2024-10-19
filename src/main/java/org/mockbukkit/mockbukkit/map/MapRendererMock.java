package org.mockbukkit.mockbukkit.map;

import org.mockbukkit.mockbukkit.exception.UnimplementedOperationException;
import org.bukkit.entity.Player;
import org.bukkit.map.MapCanvas;
import org.bukkit.map.MapRenderer;
import org.bukkit.map.MapView;
import org.jetbrains.annotations.NotNull;

/**
 * Mock implementation of a {@link MapRenderer}.
 */
public class MapRendererMock extends MapRenderer
{

	@Override
	public void render(@NotNull MapView map, @NotNull MapCanvas canvas, @NotNull Player player)
	{
		// TODO Auto-generated method stub
		throw new UnimplementedOperationException();
	}

}
