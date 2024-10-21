package org.mockbukkit.mockbukkit.entity;

import org.mockbukkit.mockbukkit.MockBukkitExtension;
import org.mockbukkit.mockbukkit.MockBukkitInject;
import org.mockbukkit.mockbukkit.ServerMock;
import org.mockbukkit.mockbukkit.inventory.HopperInventoryMock;
import org.mockbukkit.mockbukkit.inventory.ItemStackMock;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.minecart.HopperMinecart;
import org.bukkit.util.BoundingBox;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(MockBukkitExtension.class)
class HopperMinecartMockTest
{

	@MockBukkitInject
	private ServerMock server;

	private HopperMinecart minecart;

	@BeforeEach
	void setUp() throws Exception
	{
		minecart = new HopperMinecartMock(server, UUID.randomUUID());
	}

	@Test
	void testIsEnabledDefault()
	{
		assertTrue(minecart.isEnabled());
	}

	@Test
	void testSetEnabled()
	{
		minecart.setEnabled(false);
		assertFalse(minecart.isEnabled());
	}

	@Test
	void testGetMinecartMaterial()
	{
		assertEquals(minecart.getMinecartMaterial(), Material.HOPPER_MINECART);
	}

	@Test
	void testGetEntity()
	{
		assertEquals(minecart.getEntity(), minecart);
	}

	@Test
	void testGetEntityType()
	{
		assertEquals(EntityType.HOPPER_MINECART, minecart.getType());
	}

	@Test
	void testGetInventory()
	{
		assertTrue(minecart.getInventory() instanceof HopperInventoryMock);
		minecart.getInventory().setItem(0, new ItemStackMock(Material.DIRT));
		assertEquals(Material.DIRT, minecart.getInventory().getItem(0).getType());
	}

	@Test
	void testGetPickupCooldownThrows()
	{
		UnsupportedOperationException unsupportedOperationException = assertThrows(UnsupportedOperationException.class,
				() -> minecart.getPickupCooldown());

		assertEquals("Hopper minecarts don't have cooldowns", unsupportedOperationException.getMessage());
	}

	@Test
	void testSetPickupCooldownThrows()
	{
		UnsupportedOperationException unsupportedOperationException = assertThrows(UnsupportedOperationException.class,
				() -> minecart.setPickupCooldown(1));

		assertEquals("Hopper minecarts don't have cooldowns", unsupportedOperationException.getMessage());
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

}
