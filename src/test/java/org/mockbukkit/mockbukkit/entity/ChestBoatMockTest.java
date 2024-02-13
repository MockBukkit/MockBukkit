package org.mockbukkit.mockbukkit.entity;

import org.bukkit.entity.EntityType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockbukkit.mockbukkit.MockBukkitExtension;
import org.mockbukkit.mockbukkit.MockBukkitInject;
import org.mockbukkit.mockbukkit.ServerMock;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(MockBukkitExtension.class)
class ChestBoatMockTest
{

	@MockBukkitInject
	private ServerMock server;
	private ChestBoatMock boat;

	@BeforeEach
	void setUp() throws Exception
	{
		boat = new ChestBoatMock(server, UUID.randomUUID());
	}

	@Test
	void testGetInventory()
	{
		assertNotNull(boat.getInventory());
		assertEquals(27, boat.getInventory().getSize());
		assertEquals(boat, boat.getInventory().getHolder());
	}

	@Test
	void testGetEntity()
	{
		assertEquals(boat, boat.getEntity());
	}

	@Test
	void testGetEntityType()
	{
		assertEquals(EntityType.CHEST_BOAT, boat.getType());
	}

}
