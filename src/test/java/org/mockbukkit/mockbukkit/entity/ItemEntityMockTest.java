package org.mockbukkit.mockbukkit.entity;

import org.mockbukkit.mockbukkit.MockBukkit;
import org.mockbukkit.mockbukkit.ServerMock;
import org.mockbukkit.mockbukkit.world.WorldMock;
import net.kyori.adventure.util.TriState;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Item;
import org.bukkit.inventory.ItemStack;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class ItemEntityMockTest
{

	private ServerMock server;
	private WorldMock world;

	@BeforeEach
	void setUp()
	{
		server = MockBukkit.mock();
		world = new WorldMock();
	}

	@AfterEach
	void tearDown()
	{
		MockBukkit.unmock();
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

	@Test
	void testGetFrictionStateDefault()
	{
		ItemStack item = new ItemStack(Material.QUARTZ);
		Location location = new Location(world, 500, 100, 500);

		Item entity = world.dropItem(location, item);

		assertEquals(TriState.NOT_SET, entity.getFrictionState());
	}

	@Test
	void testSetFrictionState()
	{
		ItemStack item = new ItemStack(Material.QUARTZ);
		Location location = new Location(world, 500, 100, 500);

		Item entity = world.dropItem(location, item);

		entity.setFrictionState(TriState.TRUE);
		assertEquals(TriState.TRUE, entity.getFrictionState());
	}

	@Test
	void testSetFrictionStateNull()
	{
		ItemStack item = new ItemStack(Material.QUARTZ);
		Location location = new Location(world, 500, 100, 500);

		Item entity = world.dropItem(location, item);

		assertThrows(NullPointerException.class, () -> entity.setFrictionState(null));
	}

}
