package org.mockbukkit.mockbukkit.map;

import org.mockbukkit.mockbukkit.MockBukkit;
import org.mockbukkit.mockbukkit.ServerMock;
import org.mockbukkit.mockbukkit.world.WorldMock;
import org.mockbukkit.mockbukkit.entity.PlayerMock;
import org.bukkit.entity.Player;
import org.bukkit.map.MapCanvas;
import org.bukkit.map.MapRenderer;
import org.bukkit.map.MapView;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class MapViewMockTest
{

	private ServerMock server;
	private WorldMock world;
	private MapViewMock mapView;

	@BeforeEach
	void setUp()
	{
		server = MockBukkit.mock();
		world = new WorldMock();
		server.addWorld(world);
		mapView = new MapViewMock(world, 1);
	}

	@AfterEach
	void tearDown()
	{
		MockBukkit.unmock();
	}

	@Test
	void getWorld_Constructor_NotNull()
	{
		assertNotNull(mapView.getWorld());
	}

	@Test
	void getId()
	{
		assertEquals(1, mapView.getId());
	}

	@Test
	void isVirtual_NoRenderers_False()
	{
		assertFalse(mapView.isVirtual());
	}

	@Test
	void isVirtual_MockRenderer_False()
	{
		mapView.addRenderer(new MapRendererMock());
		assertFalse(mapView.isVirtual());
	}

	@Test
	void isVirtual_NotMockRenderer_True()
	{
		mapView.addRenderer(new MapRenderer()
		{
			@Override
			public void render(@NotNull MapView map, @NotNull MapCanvas canvas, @NotNull Player player)
			{
			}
		});
		assertTrue(mapView.isVirtual());
	}

	@Test
	void isScale_Constructor_Normal()
	{
		assertEquals(MapView.Scale.NORMAL, mapView.getScale());
	}

	@Test
	void setScale_Sets()
	{
		mapView.setScale(MapView.Scale.FARTHEST);

		assertEquals(MapView.Scale.FARTHEST, mapView.getScale());
	}

	@Test
	void getWorld_Constructor_SameWorld()
	{
		assertEquals(world, mapView.getWorld());
	}

	@Test
	void setWorld_SetsWorld()
	{
		WorldMock newWorld = new WorldMock();

		mapView.setWorld(newWorld);

		assertEquals(newWorld, mapView.getWorld());
	}

	@Test
	void getRenderers_Constructor_Empty()
	{
		assertTrue(mapView.getRenderers().isEmpty());
	}

	@Test
	void getRenderers_ReturnsClone()
	{
		mapView.addRenderer(new MapRendererMock());

		mapView.getRenderers().clear();

		assertEquals(1, mapView.getRenderers().size());
	}

	@Test
	void addRenderer_AddsRenderer()
	{
		MapRendererMock renderer1 = new MapRendererMock();
		MapRendererMock renderer2 = new MapRendererMock();

		mapView.addRenderer(renderer1);
		mapView.addRenderer(renderer2);

		assertEquals(2, mapView.getRenderers().size());
		assertEquals(renderer1, mapView.getRenderers().get(0));
		assertEquals(renderer2, mapView.getRenderers().get(1));
	}

	@Test
	void addRenderer_Duplicate_DoesntAdd()
	{
		MapRendererMock renderer = new MapRendererMock();

		mapView.addRenderer(renderer);
		mapView.addRenderer(renderer);

		assertEquals(1, mapView.getRenderers().size());
		assertEquals(renderer, mapView.getRenderers().get(0));
	}

	@Test
	void removeRenderer_Exists_Removes_ReturnsTrue()
	{
		MapRendererMock renderer1 = new MapRendererMock();
		MapRendererMock renderer2 = new MapRendererMock();
		mapView.addRenderer(renderer1);
		mapView.addRenderer(renderer2);

		assertTrue(mapView.removeRenderer(renderer1));

		assertEquals(1, mapView.getRenderers().size());
		assertEquals(renderer2, mapView.getRenderers().get(0));
	}

	@Test
	void removeRenderer_DoesntExist_ReturnsFalse()
	{
		MapRendererMock renderer = new MapRendererMock();

		assertFalse(mapView.removeRenderer(renderer));
	}

	@Test
	void render_CallsRenderMethod()
	{
		AtomicBoolean b = new AtomicBoolean(false);
		mapView.addRenderer(new MapRenderer()
		{
			@Override
			public void render(@NotNull MapView map, @NotNull MapCanvas canvas, @NotNull Player player)
			{
				b.set(true);
			}
		});

		mapView.render(null);

		assertTrue(b.get());
	}

	@Test
	void render_Contextual_SamePlayer_ReusesCanvas()
	{
		List<MapCanvas> canvases = new ArrayList<>();
		MapRenderer renderer = new MapRenderer(true)
		{
			@Override
			public void render(@NotNull MapView map, @NotNull MapCanvas canvas, @NotNull Player player)
			{
				canvases.add(canvas);
			}
		};
		mapView.addRenderer(renderer);
		PlayerMock player = server.addPlayer();

		mapView.render(player);
		mapView.render(player);

		assertEquals(2, canvases.size());
		assertEquals(canvases.get(0), canvases.get(1));
	}

	@Test
	void render_Contextual_DifferentPlayer_DoesntReusesCanvas()
	{
		List<MapCanvas> canvases = new ArrayList<>();
		MapRenderer renderer = new MapRenderer(true)
		{
			@Override
			public void render(@NotNull MapView map, @NotNull MapCanvas canvas, @NotNull Player player)
			{
				canvases.add(canvas);
			}
		};
		mapView.addRenderer(renderer);
		PlayerMock player1 = server.addPlayer();
		PlayerMock player2 = server.addPlayer();

		mapView.render(player1);
		mapView.render(player2);

		assertEquals(2, canvases.size());
		assertNotEquals(canvases.get(0), canvases.get(1));
	}

	@Test
	void render_NonContextual_DifferentPlayer_ReusesCanvas()
	{
		List<MapCanvas> canvases = new ArrayList<>();
		MapRenderer renderer = new MapRenderer(false)
		{
			@Override
			public void render(@NotNull MapView map, @NotNull MapCanvas canvas, @NotNull Player player)
			{
				canvases.add(canvas);
			}
		};
		mapView.addRenderer(renderer);
		PlayerMock player1 = server.addPlayer();
		PlayerMock player2 = server.addPlayer();

		mapView.render(player1);
		mapView.render(player2);

		assertEquals(2, canvases.size());
		assertEquals(canvases.get(0), canvases.get(1));
	}

	@Test
	void isLocked_Constructor_False()
	{
		assertFalse(mapView.isLocked());
	}

	@Test
	void setLocked_Sets()
	{
		mapView.setLocked(true);

		assertTrue(mapView.isLocked());
	}

}
