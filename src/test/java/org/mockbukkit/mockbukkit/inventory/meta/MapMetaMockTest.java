package org.mockbukkit.mockbukkit.inventory.meta;

import org.mockbukkit.mockbukkit.MockBukkitExtension;
import org.mockbukkit.mockbukkit.world.WorldMock;
import org.mockbukkit.mockbukkit.map.MapViewMock;
import org.bukkit.Color;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(MockBukkitExtension.class)
class MapMetaMockTest
{

	private MapMetaMock meta;

	@BeforeEach
	void setUp()
	{
		meta = new MapMetaMock();
	}

	@Test
	void cloneConstructor_CopiesValues()
	{
		MapViewMock mapView = new MapViewMock(new WorldMock(), 1);
		meta.setMapView(mapView);

		MapMetaMock otherMeta = new MapMetaMock(meta);

		assertEquals(mapView, otherMeta.getMapView());
	}

	@Test
	void getColor_Constructor_IsNull()
	{
		assertNull(meta.getColor());
	}

	@Test
	void setColor_Sets()
	{
		meta.setColor(Color.AQUA);

		assertEquals(Color.AQUA, meta.getColor());
	}

	@Test
	void hasId_Constructor_False()
	{
		assertFalse(meta.hasMapId());
	}

	@Test
	void hasId_True_HasId()
	{
		meta.setMapId(1);

		assertTrue(meta.hasMapId());
	}

	@Test
	void getId_CorrectValue()
	{
		meta.setMapId(1);

		assertEquals(1, meta.getMapId());
	}

	@Test
	void getMapView_Constructor_IsNull()
	{
		assertNull(meta.getMapView());
	}

	@Test
	void setMapView_Sets()
	{
		MapViewMock mapView = new MapViewMock(new WorldMock(), 1);

		meta.setMapView(mapView);

		assertEquals(mapView, meta.getMapView());
	}

	@Test
	void isScaling_Constructor_False()
	{
		assertFalse(meta.isScaling());
	}

	@Test
	void setScaling_Sets()
	{
		meta.setScaling(true);

		assertTrue(meta.isScaling());
	}

	@Test
	void equals_SameInstance_ReturnsTrue()
	{
		assertEquals(meta, meta);
	}

	@Test
	void equals_DifferentInstance_SameValues_True()
	{
		MapMetaMock clone = meta.clone();
		assertEquals(meta, clone);
	}

	@Test
	void equals_DifferentInstance_DifferentValues_False()
	{
		MapMetaMock clone = meta.clone();
		clone.setMapId(2);
		assertNotEquals(meta, clone);
	}

	@Test
	void clone_CopiesValues()
	{
		MapViewMock mapView = new MapViewMock(new WorldMock(), 1);
		meta.setMapView(mapView);

		MapMetaMock otherMeta = meta.clone();

		assertEquals(mapView, otherMeta.getMapView());
	}

}
