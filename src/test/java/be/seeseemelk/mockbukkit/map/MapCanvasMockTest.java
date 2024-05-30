package be.seeseemelk.mockbukkit.map;

import be.seeseemelk.mockbukkit.MockBukkit;
import org.bukkit.map.MinecraftFont;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.ObjectInputStream;
import java.util.concurrent.ThreadLocalRandom;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

class MapCanvasMockTest
{

	private MapCanvasMock canvas;

	@BeforeEach
	void setUp()
	{
		MockBukkit.mock();
		canvas = new MapCanvasMock(null);
	}

	@AfterEach
	void tearDown()
	{
		MockBukkit.unmock();
	}

	@Test
	void getMapView_Constructor_Null()
	{
		assertNull(new MapCanvasMock(null).getMapView());
	}

	@Test
	void getMapView_Constructor_NotNull()
	{
		assertNotNull(new MapCanvasMock(new MapViewMock(null, 1)).getMapView());
	}

	@Test
	void getPixel_Constructor_NegativeOne()
	{
		MapCanvasMock.executeForAllPixels((x, y) -> assertEquals(-1, canvas.getPixel(x, y)));
	}

	@Test
	void setPixel_SetsPixel()
	{
		MapCanvasMock.executeForAllPixels((x, y) -> {
			byte b = (byte) ThreadLocalRandom.current().nextInt(Byte.MAX_VALUE);
			canvas.setPixel(x, y, b);
			assertEquals(b, canvas.getPixel(x, y));
		});
	}

	@Test
	void drawImage() throws Exception
	{
		byte[][] bytes;
		try (ObjectInputStream in = new ObjectInputStream(ClassLoader.getSystemResourceAsStream("map/img.ser")))
		{
			bytes = (byte[][]) in.readObject();
		}
		BufferedImage image = new BufferedImage(128, 128, BufferedImage.TYPE_INT_ARGB);
		Graphics g = image.createGraphics();
		g.setColor(Color.CYAN);
		g.fillOval(0, 0, 128, 128);
		g.dispose();

		canvas.drawImage(0, 0, image);

		MapCanvasMock.executeForAllPixels((x, y) -> assertEquals(bytes[x][y], canvas.getPixel(x, y)));
	}

	@Test
	void drawText() throws Exception
	{
		byte[][] bytes;
		try (ObjectInputStream in = new ObjectInputStream(ClassLoader.getSystemResourceAsStream("map/img_text.ser")))
		{
			bytes = (byte[][]) in.readObject();
		}

		canvas.drawText(0, 0, MinecraftFont.Font, "Hello World!");

		MapCanvasMock.executeForAllPixels((x, y) -> assertEquals(bytes[x][y], canvas.getPixel(x, y)));
	}

}
