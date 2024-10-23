package org.mockbukkit.mockbukkit.entity;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDismountEvent;
import org.bukkit.event.entity.EntityMountEvent;
import org.bukkit.event.vehicle.VehicleEnterEvent;
import org.bukkit.event.vehicle.VehicleExitEvent;
import org.bukkit.material.MaterialData;
import org.bukkit.util.BoundingBox;
import org.bukkit.util.Vector;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockbukkit.mockbukkit.MockBukkit;
import org.mockbukkit.mockbukkit.ServerMock;
import org.mockbukkit.mockbukkit.plugin.TestPlugin;
import org.mockbukkit.mockbukkit.block.data.BlockDataMock;

import java.util.UUID;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNotSame;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockbukkit.mockbukkit.matcher.plugin.PluginManagerFiredEventClassMatcher.hasNotFiredEventInstance;
import static org.mockbukkit.mockbukkit.matcher.plugin.PluginManagerFiredEventFilterMatcher.hasFiredFilteredEvent;

class MinecartMockTest
{

	private ServerMock server;
	private MockMinecart minecart;

	@BeforeEach
	void setUp()
	{
		server = MockBukkit.mock();
		minecart = new MockMinecart(server, UUID.fromString("1b8486fe-59f7-4c97-8e7f-ec1c05a366c9"));
	}

	@AfterEach
	void tearDown()
	{
		MockBukkit.unmock();
	}

	@Test
	void constructor_DefaultValues()
	{
		assertEquals(0, minecart.getDamage());
		assertEquals(0.4, minecart.getMaxSpeed());
		assertTrue(minecart.isSlowWhenEmpty());
		assertEquals(new Vector(0.949999988079071, 0.949999988079071, 0.949999988079071), minecart.getFlyingVelocityMod());
		assertEquals(new Vector(0.5, 0.5, 0.5), minecart.getDerailedVelocityMod());
		assertNull(minecart.getDisplayBlockData());
		assertEquals(0, minecart.getDisplayBlockOffset());
	}

	@Test
	void setFlyingVelocityMod_ClonesValue()
	{
		Vector flyingVelocityMod = new Vector(1, 2, 3);

		minecart.setFlyingVelocityMod(flyingVelocityMod);

		assertEquals(flyingVelocityMod, minecart.getFlyingVelocityMod());
		assertNotSame(flyingVelocityMod, minecart.getFlyingVelocityMod());
	}

	@Test
	void setDerailedVelocityMod_ClonesValue()
	{
		Vector derailedVelocityMod = new Vector(1, 2, 3);

		minecart.setDerailedVelocityMod(derailedVelocityMod);

		assertEquals(derailedVelocityMod, minecart.getDerailedVelocityMod());
		assertNotSame(derailedVelocityMod, minecart.getDerailedVelocityMod());
	}

	@Test
	void setDisplayBlock()
	{
		minecart.setDisplayBlock(new MaterialData(Material.STONE));

		assertEquals(Material.STONE, minecart.getDisplayBlock().getItemType());
	}

	@Test
	void setDisplayBlock_Null_SetsToAir()
	{
		minecart.setDisplayBlock(null);

		assertNotNull(minecart.getDisplayBlock());
		assertEquals(Material.AIR, minecart.getDisplayBlock().getItemType());
	}

	@Test
	void setDisplayBlockData()
	{
		minecart.setDisplayBlockData(new BlockDataMock(Material.STONE));

		assertEquals(Material.STONE, minecart.getDisplayBlockData().getMaterial());
	}

	@Test
	void setDisplayBlockData_Null_SetsToAir()
	{
		minecart.setDisplayBlockData(null);

		assertNotNull(minecart.getDisplayBlockData());
		assertEquals(Material.AIR, minecart.getDisplayBlockData().getMaterial());
	}

	@Test
	void addPassenger()
	{
		MobMock mock = new SimpleMobMock(server); // A LivingEntity is needed here
		assertTrue(minecart.addPassenger(mock));
		assertThat(server.getPluginManager(), hasFiredFilteredEvent(VehicleEnterEvent.class, event -> event.getVehicle() == minecart && event.getEntered() == mock));
		assertThat(server.getPluginManager(), hasFiredFilteredEvent(EntityMountEvent.class, event -> event.getMount() == minecart && event.getEntity() == mock));
	}

	@Test
	void addPassenger_CancelVehicleEvent()
	{
		TestPlugin plugin = MockBukkit.load(TestPlugin.class);
		MobMock mock = new SimpleMobMock(server);
		server.getPluginManager().registerEvents(new Listener()
		{
			@EventHandler
			public void onMount(@NotNull VehicleEnterEvent event)
			{
				event.setCancelled(true);
			}
		}, plugin);
		assertFalse(minecart.addPassenger(mock));
		assertTrue(minecart.isEmpty());
		assertThat(server.getPluginManager(), hasNotFiredEventInstance(EntityMountEvent.class));
	}

	@Test
	void removePassenger_CancelVehicleEvent()
	{
		TestPlugin plugin = MockBukkit.load(TestPlugin.class);
		MobMock mock = new SimpleMobMock(server);
		minecart.addPassenger(mock);
		server.getPluginManager().registerEvents(new Listener()
		{
			@EventHandler
			public void onMount(@NotNull VehicleExitEvent event)
			{
				event.setCancelled(true);
			}
		}, plugin);
		assertTrue(minecart.removePassenger(mock));
		assertFalse(minecart.isEmpty());
		assertThat(server.getPluginManager(), hasNotFiredEventInstance(EntityDismountEvent.class));
	}

	@Test
	void testSetDamage()
	{
		minecart.setDamage(0.5);
		assertEquals(0.5, minecart.getDamage());
	}

	@Test
	void testSetMaxSpeed()
	{
		minecart.setMaxSpeed(0.5);
		assertEquals(0.5, minecart.getMaxSpeed());
	}

	@Test
	void testSetSlowWhenEmpty()
	{
		minecart.setSlowWhenEmpty(false);
		assertFalse(minecart.isSlowWhenEmpty());
	}

	@Test
	void testSetDisplayBlockOffset()
	{
		minecart.setDisplayBlockOffset(1);
		assertEquals(1, minecart.getDisplayBlockOffset());
	}

	@Test
	void getType()
	{
		assertEquals(EntityType.MINECART, minecart.getType());
	}

	@Test
	void getBoundingBox_GivenDefaultLocation()
	{
		BoundingBox actual = minecart.getBoundingBox();
		assertNotNull(actual);

		assertEquals(-0.49, actual.getMinX());
		assertEquals(0, actual.getMinY());
		assertEquals(-0.49, actual.getMinZ());

		assertEquals(0.49, actual.getMaxX());
		assertEquals(0.7, actual.getMaxY());
		assertEquals(0.49, actual.getMaxZ());
	}

	@Test
	void getBoundingBox_GivenCustomLocation()
	{
		minecart.teleport(new Location(minecart.getWorld(), 10, 5, 10));

		BoundingBox actual = minecart.getBoundingBox();
		assertNotNull(actual);

		assertEquals(9.51, actual.getMinX());
		assertEquals(5, actual.getMinY());
		assertEquals(9.51, actual.getMinZ());

		assertEquals(10.49, actual.getMaxX());
		assertEquals(5.7, actual.getMaxY());
		assertEquals(10.49, actual.getMaxZ());
	}

	private static class MockMinecart extends MinecartMock
	{

		protected MockMinecart(@NotNull ServerMock server, @NotNull UUID uuid)
		{
			super(server, uuid);
		}

		@Override
		public @NotNull Material getMinecartMaterial()
		{
			return Material.MINECART;
		}

	}

}
