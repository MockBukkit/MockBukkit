package be.seeseemelk.mockbukkit.entity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.UUID;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Item;
import org.bukkit.inventory.ItemStack;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import be.seeseemelk.mockbukkit.MockBukkit;
import be.seeseemelk.mockbukkit.ServerMock;
import be.seeseemelk.mockbukkit.WorldMock;

class ItemEntityMockTest
{

	private ServerMock server;
	private WorldMock world;

	@BeforeEach
	public void setUp()
	{
		server = MockBukkit.mock();
		world = new WorldMock();
	}

	@AfterEach
	public void tearDown()
	{
		MockBukkit.unmock();
	}

	@Test
	void testDropItem()
	{
		ItemStack item = new ItemStack(Material.DIAMOND);
		Location location = new Location(world, 100, 100, 100);

		Item entity = world.dropItem(location, item);

		// Is this the same Item we wanted to drop?
		assertEquals(item, entity.getItemStack());

		// Does our Item exist in the correct World?
		assertTrue(world.getEntities().contains(entity));

		// Is it at the right location?
		assertEquals(location, entity.getLocation());
	}

	@Test
	void testDropItemNaturally()
	{
		ItemStack item = new ItemStack(Material.EMERALD);
		Location location = new Location(world, 200, 100, 200);

		Item entity = world.dropItemNaturally(location, item);

		// Is this the same Item we wanted to drop?
		assertEquals(item, entity.getItemStack());

		// Does our Item exist in the correct World?
		assertTrue(world.getEntities().contains(entity));

		// Has the Location been slightly nudged?
		assertNotEquals(location, entity.getLocation());
	}

	@Test
	void testDropItemConsumer()
	{
		ItemStack item = new ItemStack(Material.BEACON);
		Location location = new Location(world, 200, 50, 500);

		Item entity = world.dropItem(location, item, n ->
		{
			// This consumer should be invoked BEFORE the actually spawned.
			assertFalse(world.getEntities().contains(n));
		});

		assertEquals(item, entity.getItemStack());
		assertTrue(world.getEntities().contains(entity));
	}

	@Test
	void testIllegalArgumentForSpawning()
	{
		Location location = new Location(world, 300, 100, 300);

		assertThrows(IllegalArgumentException.class, () -> world.spawnEntity(location, EntityType.DROPPED_ITEM));
	}

	@Test
	void testEntityType()
	{
		Item item = new ItemEntityMock(server, UUID.randomUUID(), new ItemStack(Material.STONE));
		assertEquals(EntityType.DROPPED_ITEM, item.getType());
	}

	@Test
	void testPickupDelay()
	{
		ItemStack item = new ItemStack(Material.GOLD_INGOT);
		Location location = new Location(world, 300, 100, 300);

		Item entity = world.dropItem(location, item);

		// Default value
		assertEquals(10, entity.getPickupDelay());

		entity.setPickupDelay(50);
		assertEquals(50, entity.getPickupDelay());
	}

	@Test
	void testMaximumPickupDelay()
	{
		int maximum = 32767;
		ItemStack item = new ItemStack(Material.IRON_INGOT);
		Location location = new Location(world, 400, 100, 400);

		Item entity = world.dropItem(location, item);

		entity.setPickupDelay(100000000);
		assertEquals(maximum, entity.getPickupDelay());
	}

	@Test
	void testSetItemStack()
	{
		ItemStack item = new ItemStack(Material.QUARTZ);
		Location location = new Location(world, 500, 100, 500);

		Item entity = world.dropItem(location, item);

		ItemStack newItem = new ItemStack(Material.ENDER_PEARL);
		entity.setItemStack(newItem);
		assertEquals(newItem, entity.getItemStack());
	}

	@Test
	void testSetItemStackNull()
	{
		ItemStack item = new ItemStack(Material.REDSTONE);
		Location location = new Location(world, 600, 100, 600);

		Item entity = world.dropItem(location, item);

		// Spigot really just throws a NPE here, so this is accurate behaviour
		assertThrows(NullPointerException.class, () -> entity.setItemStack(null));
	}
}
