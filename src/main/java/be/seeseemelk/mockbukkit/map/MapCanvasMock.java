package be.seeseemelk.mockbukkit.map;

import org.bukkit.ChatColor;
import org.bukkit.map.MapCanvas;
import org.bukkit.map.MapCursorCollection;
import org.bukkit.map.MapFont;
import org.bukkit.map.MapPalette;
import org.bukkit.map.MapView;
import org.jetbrains.annotations.NotNull;

import java.awt.Image;
import java.util.Arrays;

public class MapCanvasMock implements MapCanvas
{

	private final MapViewMock mapView;
	private final byte[][] pixels = new byte[128][128];
	private byte[][] base;
	private MapCursorCollection cursors = new MapCursorCollection();

	protected MapCanvasMock(MapViewMock mapView)
	{
		this.mapView = mapView;
		Arrays.stream(pixels).forEach(x -> Arrays.fill(x, (byte) -1));
	}

	@Override
	public @NotNull MapView getMapView()
	{
		return mapView;
	}

	@Override
	public @NotNull MapCursorCollection getCursors()
	{
		return cursors;
	}

	@Override
	public void setCursors(@NotNull MapCursorCollection cursors)
	{
		this.cursors = cursors;
	}

	@Override
	public void setPixel(int x, int y, byte color)
	{
		pixels[x][y] = color;
	}

	@Override
	public byte getPixel(int x, int y)
	{
		return pixels[x][y];
	}

	@Override
	public byte getBasePixel(int x, int y)
	{
		return base[x][y];
	}

	protected void setBase(byte[][] base)
	{
		this.base = base;
	}

	@Override
	@SuppressWarnings("deprecation") // Magic values
	public void drawImage(int x, int y, @NotNull Image image)
	{
		byte[] bytes = MapPalette.imageToBytes(image);
		for (int imgX = 0; imgX < image.getWidth(null); ++imgX)
		{
			for (int imgY = 0; imgY < image.getHeight(null); ++imgY)
			{
				this.setPixel(x + imgX, y + imgY, bytes[imgY * image.getWidth(null) + imgX]);
			}
		}
	}

	@Override
	@SuppressWarnings("deprecation") // Magic values
	public void drawText(int x, int y, @NotNull MapFont font, @NotNull String text)
	{
		if (!font.isValid(text))
		{
			throw new IllegalArgumentException("text contains invalid characters");
		}

		int initX = x;
		byte color = MapPalette.DARK_GRAY;

		for (int i = 0; i < text.length(); i++)
		{
			char c = text.charAt(i);
			if (c == '\n')
			{
				x = initX;
				y += font.getHeight() + 1;
				continue;
			}
			else if (c == ChatColor.COLOR_CHAR)
			{
				int idx = text.indexOf(';', i);
				if (idx == -1)
				{
					throw new IllegalArgumentException("Text contains unterminated color string");
				}
				try
				{
					color = Byte.parseByte(text.substring(i + 1, idx));
					i = idx;
					continue;
				}
				catch (NumberFormatException ignored)
				{
				}
			}

			MapFont.CharacterSprite sprite = font.getChar(text.charAt(i));
			for (int h = 0; h < font.getHeight(); h++)
			{
				for (int w = 0; w < sprite.getWidth(); w++)
				{
					if (!sprite.get(h, w))
						continue;
					this.setPixel(x + w, y + h, color);
				}
			}
			x += sprite.getWidth() + 1;
		}
	}

}
