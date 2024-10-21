package org.mockbukkit.mockbukkit.map;

import org.bukkit.ChatColor;
import org.bukkit.map.MapCanvas;
import org.bukkit.map.MapCursorCollection;
import org.bukkit.map.MapFont;
import org.bukkit.map.MapPalette;
import org.bukkit.map.MapView;
import org.jetbrains.annotations.NotNull;

import java.awt.Color;
import java.awt.Image;
import java.util.Arrays;
import java.util.function.BiConsumer;

/**
 * Mock implementation of a {@link MapCanvas}.
 */
public class MapCanvasMock implements MapCanvas
{

	private static final int MAP_SIZE = 128;

	private final MapViewMock mapView;
	private final byte[][] pixels = new byte[MAP_SIZE][MAP_SIZE];
	private byte[][] base;
	private @NotNull MapCursorCollection cursors = new MapCursorCollection();

	/**
	 * Constructs a new {@link MapCanvasMock} for the provided {@link MapViewMock}.
	 *
	 * @param mapView The map view this canvas is for.
	 */
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
	public void setPixelColor(int x, int y, @NotNull Color color)
	{
		pixels[x][y] = MapPalette.matchColor(color);
	}

	@Override
	public @NotNull Color getPixelColor(int x, int y)
	{
		return MapPalette.getColor(pixels[x][y]);
	}

	@Override
	public @NotNull Color getBasePixelColor(int x, int y)
	{
		return MapPalette.getColor(base[x][y]);
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

	/**
	 * Sets the base for use in {@link #getBasePixel(int, int)} and {@link #getBasePixelColor(int, int)}.
	 *
	 * @param base The base to set.
	 * @see #getBasePixel(int, int)
	 * @see #getBasePixelColor(int, int)
	 */
	public void setBase(byte[][] base)
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

	/**
	 * Runs a Consumer for each pixel coordinate on a map.
	 *
	 * @param consumer The consumer to run. First parameter is the X coordinate, second is the Y coordinate.
	 */
	public static void executeForAllPixels(@NotNull BiConsumer<Integer, Integer> consumer)
	{
		for (int x = 0; x < MAP_SIZE; ++x)
		{
			for (int y = 0; y < MAP_SIZE; ++y)
			{
				consumer.accept(x, y);
			}
		}
	}

}
