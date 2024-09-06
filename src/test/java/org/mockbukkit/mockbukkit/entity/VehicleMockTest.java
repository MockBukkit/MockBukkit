package org.mockbukkit.mockbukkit.entity;

import org.mockbukkit.mockbukkit.MockBukkitExtension;
import org.mockbukkit.mockbukkit.MockBukkitInject;
import org.mockbukkit.mockbukkit.ServerMock;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.util.UUID;

@ExtendWith(MockBukkitExtension.class)
class VehicleMockTest
{

	@MockBukkitInject
	private ServerMock server;
	private VehicleMock vehicle;

	@BeforeEach
	void setUp()
	{
		vehicle = new RideableMinecartMock(server, UUID.randomUUID());
	}

	@Test
	void testToString()
	{
		Assertions.assertEquals("VehicleMock{passenger=null}", vehicle.toString());
	}

}
