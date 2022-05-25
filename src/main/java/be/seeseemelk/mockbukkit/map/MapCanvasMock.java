package be.seeseemelk.mockbukkit.map;

import org.bukkit.map.MapCanvas;
import org.bukkit.map.MapCursorCollection;
import org.bukkit.map.MapFont;
import org.bukkit.map.MapView;
import org.jetbrains.annotations.NotNull;

import java.awt.Image;

public class MapCanvasMock implements MapCanvas
{

	@Override
	public @NotNull MapView getMapView()
	{
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException();
	}

	@Override
	public @NotNull MapCursorCollection getCursors()
	{
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException();
	}

	@Override
	public void setCursors(@NotNull MapCursorCollection cursors)
	{
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException();
	}

	@Override
	public void setPixel(int x, int y, byte color)
	{
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException();
	}

	@Override
	public byte getPixel(int x, int y)
	{
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException();
	}

	@Override
	public byte getBasePixel(int x, int y)
	{
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException();
	}

	@Override
	public void drawImage(int x, int y, @NotNull Image image)
	{
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException();
	}

	@Override
	public void drawText(int x, int y, @NotNull MapFont font, @NotNull String text)
	{
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException();
	}

}
