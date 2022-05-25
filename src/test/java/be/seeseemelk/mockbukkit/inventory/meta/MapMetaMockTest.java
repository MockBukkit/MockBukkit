package be.seeseemelk.mockbukkit.inventory.meta;

import be.seeseemelk.mockbukkit.MockBukkit;
import be.seeseemelk.mockbukkit.ServerMock;
import be.seeseemelk.mockbukkit.WorldMock;
import be.seeseemelk.mockbukkit.map.MapViewMock;
import org.bukkit.Color;
import org.bukkit.inventory.meta.MapMeta;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class MapMetaMockTest
{

	private ServerMock server;

	@BeforeEach
	void setUp()
	{
		server = MockBukkit.mock();
	}

	@AfterEach
	void tearDown()
	{
		MockBukkit.unmock();
	}

	@Test
	void getColor_Constructor_IsNull()
	{
		MapMeta meta = new MapMetaMock();

		assertNull(meta.getColor());
	}

	@Test
	void setColor_Sets()
	{
		MapMeta meta = new MapMetaMock();

		meta.setColor(Color.AQUA);

		assertEquals(Color.AQUA, meta.getColor());
	}

	@Test
	void hasId_Constructor_False()
	{
		MapMeta meta = new MapMetaMock();

		assertFalse(meta.hasMapId());
	}

	@Test
	void hasId_True_HasId()
	{
		MapMeta meta = new MapMetaMock();

		meta.setMapId(1);

		assertTrue(meta.hasMapId());
	}

	@Test
	void getId_CorrectValue()
	{
		MapMeta meta = new MapMetaMock();

		meta.setMapId(1);

		assertEquals(1, meta.getMapId());
	}

	@Test
	void getMapView_Constructor_IsNull()
	{
		MapMeta meta = new MapMetaMock();
		assertNull(meta.getMapView());
	}

	@Test
	void setMapView_Sets()
	{
		MapMeta meta = new MapMetaMock();
		MapViewMock mapView = new MapViewMock(new WorldMock(), 1);

		meta.setMapView(mapView);

		assertEquals(mapView, meta.getMapView());
	}

	@Test
	void isScaling_Constructor_False()
	{
		MapMeta meta = new MapMetaMock();

		assertFalse(meta.isScaling());
	}

	@Test
	void setScaling_Sets()
	{
		MapMeta meta = new MapMetaMock();

		meta.setScaling(true);

		assertTrue(meta.isScaling());
	}

}
