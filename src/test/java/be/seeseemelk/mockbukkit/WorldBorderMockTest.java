package be.seeseemelk.mockbukkit;

import io.papermc.paper.event.world.border.WorldBorderBoundsChangeEvent;
import io.papermc.paper.event.world.border.WorldBorderCenterChangeEvent;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.WorldBorder;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class WorldBorderMockTest
{

	private ServerMock server;
	private World world;
	private WorldBorder worldBorderMock;

	@BeforeEach
	void setUp()
	{
		server = MockBukkit.mock();
		world = new WorldMock();
		worldBorderMock = world.getWorldBorder();
	}

	@AfterEach
	void tearDown()
	{
		MockBukkit.unmock();
	}

	@Test
	void constructor_NullWorld_ThrowsException()
	{
		assertThrows(NullPointerException.class, () -> new WorldBorderMock(null));
	}

	@Test
	void reset()
	{
		worldBorderMock.reset();

		assertEquals(6.0E7, worldBorderMock.getSize());
		assertEquals(0.2, worldBorderMock.getDamageAmount());
		assertEquals(5.0, worldBorderMock.getDamageBuffer());
		assertEquals(5, worldBorderMock.getWarningDistance());
		assertEquals(15, worldBorderMock.getWarningTime());
		assertEquals(new Location(world, 0, 0, 0), worldBorderMock.getCenter());
	}

	@Test
	void setSize()
	{
		worldBorderMock.setSize(10);

		assertEquals(10, worldBorderMock.getSize());
	}

	@Test
	void setSize_OverTimeGoingDown_CompleteAtFullTime()
	{
		worldBorderMock.setSize(100);
		worldBorderMock.setSize(50, 10);

		server.getScheduler().performTicks(10 * 20);

		assertEquals(50, worldBorderMock.getSize());
	}

	@Test
	void setSize_OverTimeGoingUp_CompleteAtFullTime()
	{
		worldBorderMock.setSize(50);
		worldBorderMock.setSize(100, 10);

		server.getScheduler().performTicks(10 * 20);

		assertEquals(100, worldBorderMock.getSize());
	}

	@Test
	void setSize_OverTime_HalfwayAtHalfTime()
	{
		worldBorderMock.setSize(100);
		worldBorderMock.setSize(50, 10);

		server.getScheduler().performTicks(5 * 20);

		assertEquals(75, worldBorderMock.getSize());
	}

	@Test
	void setSize_LessThanOne_ClampedAtOne()
	{
		worldBorderMock.setSize(0);

		assertEquals(1, worldBorderMock.getSize());
	}

	@Test
	void setSize_GreaterThanMax_ClampedAtMax()
	{
		worldBorderMock.setSize(6.0000001E7);

		assertEquals(6.0E7, worldBorderMock.getSize());
	}

	@Test
	void setSize_Instant_CallsEvent()
	{
		worldBorderMock.setSize(100);

		server.getPluginManager().assertEventFired(WorldBorderBoundsChangeEvent.class,
				(e) -> e.getType() == WorldBorderBoundsChangeEvent.Type.INSTANT_MOVE && e.getNewSize() == 100);
	}

	@Test
	void setSize_Duration_CallsEvent()
	{
		worldBorderMock.setSize(100, 5);

		server.getPluginManager().assertEventFired(WorldBorderBoundsChangeEvent.class,
				(e) -> e.getType() == WorldBorderBoundsChangeEvent.Type.STARTED_MOVE && e.getNewSize() == 100
						&& e.getDuration() == 5000);
	}

	@Test
	void setSize_Event_AppliesSize()
	{
		MockPlugin mockPlugin = MockBukkit.createMockPlugin();
		server.getPluginManager().registerEvents(new Listener()
		{
			@EventHandler
			public void onBorderChange(@NotNull WorldBorderBoundsChangeEvent e)
			{
				e.setNewSize(50);
			}
		}, mockPlugin);

		worldBorderMock.setSize(100);

		assertEquals(50, worldBorderMock.getSize());
	}

	@Test
	void setSize_Event_AppliesDuration()
	{
		server.getPluginManager().registerEvents(new Listener()
		{
			@EventHandler
			public void onBorderChange(@NotNull WorldBorderBoundsChangeEvent e)
			{
				e.setDuration(5000);
			}
		}, MockBukkit.createMockPlugin());

		worldBorderMock.setSize(100, 10);
		server.getScheduler().performTicks(5 * 20);

		assertEquals(100, worldBorderMock.getSize());
	}

	@Test
	void setSize_CanceledEvent_DoesntApply()
	{
		server.getPluginManager().registerEvents(new Listener()
		{
			@EventHandler
			public void onBorderChange(@NotNull WorldBorderBoundsChangeEvent e)
			{
				e.setCancelled(true);
			}
		}, MockBukkit.createMockPlugin());

		worldBorderMock.setSize(100);

		assertEquals(6.0E7, worldBorderMock.getSize());
	}

	@Test
	void setCenterLocation()
	{
		worldBorderMock.setCenter(new Location(null, 10, 0, 10));

		assertEquals(new Location(world, 10, 0, 10), worldBorderMock.getCenter());
	}

	@Test
	void setCenterXZ()
	{
		worldBorderMock.setCenter(10, 10);

		assertEquals(new Location(world, 10, 0, 10), worldBorderMock.getCenter());
	}

	@Test
	void setCenter_CallsEvent()
	{
		worldBorderMock.setCenter(10, 12);

		server.getPluginManager().assertEventFired(WorldBorderCenterChangeEvent.class,
				(e) -> e.getNewCenter().getX() == 10 && e.getNewCenter().getZ() == 12);
	}

	@Test
	void setCenter_CanceledEvent_DoesntApply()
	{
		server.getPluginManager().registerEvents(new Listener()
		{
			@EventHandler
			public void onBorderChange(@NotNull WorldBorderCenterChangeEvent e)
			{
				e.setCancelled(true);
			}
		}, MockBukkit.createMockPlugin());

		worldBorderMock.setCenter(10, 10);

		assertEquals(0, worldBorderMock.getCenter().getBlockX());
		assertEquals(0, worldBorderMock.getCenter().getBlockZ());
	}

	@Test
	void setDamageBuffer()
	{
		worldBorderMock.setDamageBuffer(10.0);

		assertEquals(10.0, worldBorderMock.getDamageBuffer());
	}

	@Test
	void setDamageAmount()
	{
		worldBorderMock.setDamageAmount(1);

		assertEquals(1, worldBorderMock.getDamageAmount());
	}

	@Test
	void setWarningTime()
	{
		worldBorderMock.setWarningTime(10);

		assertEquals(10, worldBorderMock.getWarningTime());
	}

	@Test
	void setWarningDistance()
	{
		worldBorderMock.setWarningDistance(10);

		assertEquals(10, worldBorderMock.getWarningDistance());
	}

	@Test
	void isInside_Null_ExceptionThrown()
	{
		assertThrows(NullPointerException.class, () -> {
			worldBorderMock.isInside(null);
		});
	}

	@Test
	void isInside_InsideLocation_True()
	{
		worldBorderMock.setSize(100);

		assertTrue(worldBorderMock.isInside(new Location(world, 0, 0, 0)));
	}

	@Test
	void isInside_InsideLocationWrongWorld_False()
	{
		worldBorderMock.setSize(100);

		assertFalse(worldBorderMock.isInside(new Location(new WorldMock(), 0, 0, 0)));
	}

	@Test
	void isInside_OutsideLocation_False()
	{
		worldBorderMock.setSize(100);

		assertFalse(worldBorderMock.isInside(new Location(world, 101, 0, 101)));
	}

	@Test
	void testGetWorld()
	{
		assertEquals(world, worldBorderMock.getWorld());
	}

}
