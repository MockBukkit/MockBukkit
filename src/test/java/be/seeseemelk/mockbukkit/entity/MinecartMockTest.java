package be.seeseemelk.mockbukkit.entity;

import be.seeseemelk.mockbukkit.MockBukkit;
import be.seeseemelk.mockbukkit.ServerMock;
import be.seeseemelk.mockbukkit.TestPlugin;
import be.seeseemelk.mockbukkit.block.data.BlockDataMock;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.vehicle.VehicleEnterEvent;
import org.bukkit.event.vehicle.VehicleExitEvent;
import org.bukkit.material.MaterialData;
import org.bukkit.util.Vector;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.spigotmc.event.entity.EntityDismountEvent;
import org.spigotmc.event.entity.EntityMountEvent;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNotSame;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

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
		assertEquals(new Vector(0.949999988079071, 0.949999988079071, 0.949999988079071),
				minecart.getFlyingVelocityMod());
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
		server.getPluginManager().assertEventFired(VehicleEnterEvent.class,
				event -> event.getVehicle() == minecart && event.getEntered() == mock);
		server.getPluginManager().assertEventFired(EntityMountEvent.class,
				event -> event.getMount() == minecart && event.getEntity() == mock);
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
		server.getPluginManager().assertEventNotFired(EntityMountEvent.class);
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
		server.getPluginManager().assertEventNotFired(EntityDismountEvent.class);
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
