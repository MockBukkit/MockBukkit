package org.mockbukkit.mockbukkit.entity;

import org.bukkit.entity.EntityType;
import org.junit.jupiter.api.Test;
import org.mockbukkit.mockbukkit.MockBukkitInject;
import org.mockbukkit.mockbukkit.ServerMock;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

public class MarkerMockTest
{

	@MockBukkitInject
	private ServerMock server;
	@MockBukkitInject
	private MarkerMock marker;

	@Test
	void testType()
	{
		assertEquals(EntityType.MARKER, marker.getType());
	}

	@Test
	void testCannotAddPassenger()
	{
		assertFalse(marker.addPassenger(new SimpleEntityMock(server)));
		assertEquals(0, marker.getPassengers().size());
	}

}
