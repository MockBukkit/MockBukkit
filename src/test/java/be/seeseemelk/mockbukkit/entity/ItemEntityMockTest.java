package be.seeseemelk.mockbukkit.entity;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

import java.util.UUID;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Item;
import org.bukkit.inventory.ItemStack;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import be.seeseemelk.mockbukkit.MockBukkit;
import be.seeseemelk.mockbukkit.ServerMock;
import be.seeseemelk.mockbukkit.WorldMock;

public class ItemEntityMockTest
{

	private ServerMock server;
	private WorldMock world;

	@Before
	public void setUp()
	{
		server = MockBukkit.mock();
		world = new WorldMock();
	}

	@After
	public void tearDown()
	{
		MockBukkit.unmock();
	}

	@Test
	public void testDropItem()
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
	public void testDropItemNaturally()
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
	public void testDropItemConsumer()
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

	@Test(expected = IllegalArgumentException.class)
	public void testIllegalArgumentForSpawning()
	{
		Location location = new Location(world, 300, 100, 300);
		world.spawnEntity(location, EntityType.DROPPED_ITEM);
	}

	@Test
	public void testEntityType()
	{
		Item item = new ItemEntityMock(server, UUID.randomUUID(), new ItemStack(Material.STONE));
		assertEquals(EntityType.DROPPED_ITEM, item.getType());
	}

	@Test
	public void testPickupDelay()
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
	public void testMaximumPickupDelay()
	{
		int maximum = 32767;
		ItemStack item = new ItemStack(Material.IRON_INGOT);
		Location location = new Location(world, 400, 100, 400);

		Item entity = world.dropItem(location, item);

		entity.setPickupDelay(100000000);
		assertEquals(maximum, entity.getPickupDelay());
	}

	@Test
	public void testSetItemStack()
	{
		ItemStack item = new ItemStack(Material.QUARTZ);
		Location location = new Location(world, 500, 100, 500);

		Item entity = world.dropItem(location, item);

		ItemStack newItem = new ItemStack(Material.ENDER_PEARL);
		entity.setItemStack(newItem);
		assertEquals(newItem, entity.getItemStack());
	}

	@Test(expected = NullPointerException.class)
	public void testSetItemStackNull()
	{
		ItemStack item = new ItemStack(Material.REDSTONE);
		Location location = new Location(world, 600, 100, 600);

		Item entity = world.dropItem(location, item);

		// Spigot really just throws a NPE here, so this is accurate behaviour
		entity.setItemStack(null);
	}
}
