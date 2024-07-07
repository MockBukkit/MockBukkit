package be.seeseemelk.mockbukkit.entity;

import be.seeseemelk.mockbukkit.MockBukkit;
import be.seeseemelk.mockbukkit.ServerMock;
import org.bukkit.Color;
import org.bukkit.entity.Display;
import org.joml.Matrix4f;
import org.joml.Vector3f;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

class DisplayMockTest
{

	private DisplayMock display;

	@BeforeEach
	void setUp()
	{
		ServerMock serverMock = MockBukkit.mock();
		this.display = new DisplayMock(serverMock, UUID.randomUUID());
	}

	@AfterEach
	void tearDown()
	{
		MockBukkit.unmock();
	}

	@Test
	void setTransformation_null()
	{
		assertThrows(IllegalArgumentException.class, () -> display.setTransformation(null));
	}

	@Test
	void setTransformationMatrix()
	{
		Matrix4f matrix4f = new Matrix4f();
		Vector3f scale = new Vector3f(3.0f, 7.0f, 9.0f);
		matrix4f.scale(scale);
		display.setTransformationMatrix(matrix4f);
		assertEquals(scale, display.getTransformation().getScale());
	}

	@Test
	void setTransformationMatrix_null()
	{
		assertThrows(IllegalArgumentException.class, () -> display.setTransformationMatrix(null));
	}

	@Test
	void getInterpolationDuration_default()
	{
		assertEquals(0, display.getInterpolationDuration());
	}

	@Test
	void setInterpolationDuration()
	{
		display.setInterpolationDuration(10);
		assertEquals(10, display.getInterpolationDuration());
	}

	@Test
	void getTeleportDuration_default()
	{
		assertEquals(0, display.getTeleportDuration());
	}

	@Test
	void setTeleportDuration()
	{
		display.setTeleportDuration(14);
		assertEquals(14, display.getTeleportDuration());
	}

	@Test
	void setTeleportDuration_negative()
	{
		assertThrows(IllegalArgumentException.class, () -> display.setTeleportDuration(-14));
	}

	@Test
	void setTeleportDuration_large()
	{
		assertThrows(IllegalArgumentException.class, () -> display.setTeleportDuration(60));
	}

	@Test
	void getViewRange_default()
	{
		assertEquals(1.0f, display.getViewRange());
	}

	@Test
	void setViewRange()
	{
		display.setViewRange(2.0f);
		assertEquals(2.0f, display.getViewRange());
	}

	@Test
	void getShadowRadius_default()
	{
		assertEquals(0.0f, display.getShadowRadius());
	}

	@Test
	void setShadowRadius()
	{
		display.setShadowRadius(1.0f);
		assertEquals(1.0f, display.getShadowRadius());
	}

	@Test
	void getShadowStrength_default()
	{
		assertEquals(1.0f, display.getShadowStrength());
	}

	@Test
	void setShadowStrength()
	{
		display.setShadowStrength(3.0f);
		assertEquals(3.0f, display.getShadowStrength());
	}

	@Test
	void getDisplayWidth_default()
	{
		assertEquals(0.0f, display.getDisplayWidth());
	}

	@Test
	void setDisplayWidth()
	{
		display.setDisplayWidth(8.0f);
		assertEquals(8.0f, display.getDisplayWidth());
	}

	@Test
	void getDisplayHeight()
	{
		assertEquals(0.0f, display.getDisplayHeight());
	}

	@Test
	void setDisplayHeight()
	{
		display.setDisplayHeight(9.0f);
		assertEquals(9.0f, display.getDisplayHeight());
	}

	@Test
	void getInterpolationDelay_default()
	{
		assertEquals(0, display.getInterpolationDelay());
	}

	@Test
	void setInterpolationDelay()
	{
		display.setInterpolationDelay(1);
		assertEquals(1, display.getInterpolationDelay());
	}

	@Test
	void getGlowColorOverride_default()
	{
		assertNull(display.getGlowColorOverride());
	}

	@Test
	void setGlowColorOverride()
	{
		Color color = Color.BLUE;
		display.setGlowColorOverride(color);
		assertEquals(color, display.getGlowColorOverride());
	}

	@Test
	void getBrightness_default()
	{
		assertNull(display.getBrightness());
	}

	@Test
	void setBrightness()
	{
		Display.Brightness brightness = new Display.Brightness(10, 5);
		display.setBrightness(brightness);
		assertEquals(brightness, display.getBrightness());
	}

}
