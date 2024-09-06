package org.mockbukkit.mockbukkit.entity;

import org.mockbukkit.mockbukkit.MockBukkit;
import org.mockbukkit.mockbukkit.ServerMock;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.util.BoundingBox;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

class PoweredMinecartMockTest
{

	private ServerMock server;
	private PoweredMinecartMock minecartFurnace;

	@BeforeEach
	void setup()
	{
		server = MockBukkit.mock();
		minecartFurnace = new PoweredMinecartMock(server, UUID.randomUUID());
	}

	@AfterEach
	void tearDown()
	{
		MockBukkit.unmock();
	}

	@Test
	void defaultValues()
	{
		assertEquals(0, minecartFurnace.getFuel());
		assertEquals(0, minecartFurnace.getPushX());
		assertEquals(0, minecartFurnace.getPushZ());
	}

	@Test
	void getFuel()
	{
		minecartFurnace.setFuel(20);
		assertEquals(20, minecartFurnace.getFuel());
	}

	@Test
	void setFuel_Negative()
	{
		assertThrows(IllegalArgumentException.class, () -> minecartFurnace.setFuel(-1));
	}

	@Test
	void getPushX()
	{
		minecartFurnace.setPushX(1);
		assertEquals(1, minecartFurnace.getPushX());
	}

	@Test
	void getPushZ()
	{
		minecartFurnace.setPushZ(-1);
		assertEquals(-1, minecartFurnace.getPushZ());
	}

	@Test
	void getTypeTest()
	{
		assertEquals(EntityType.FURNACE_MINECART, minecartFurnace.getType());
	}

	@Test
	void testGetMinecartMaterial()
	{
		assertEquals(minecartFurnace.getMinecartMaterial(), Material.FURNACE_MINECART);
	}

	@Test
	void getBoundingBox_GivenDefaultLocation()
	{
		BoundingBox actual = minecartFurnace.getBoundingBox();
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
		minecartFurnace.teleport(new Location(minecartFurnace.getWorld(), 10, 5, 10));

		BoundingBox actual = minecartFurnace.getBoundingBox();
		assertNotNull(actual);

		assertEquals(9.51, actual.getMinX());
		assertEquals(5, actual.getMinY());
		assertEquals(9.51, actual.getMinZ());

		assertEquals(10.49, actual.getMaxX());
		assertEquals(5.7, actual.getMaxY());
		assertEquals(10.49, actual.getMaxZ());
	}

}
