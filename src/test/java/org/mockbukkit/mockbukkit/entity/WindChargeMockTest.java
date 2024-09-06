package org.mockbukkit.mockbukkit.entity;

import org.mockbukkit.mockbukkit.MockBukkitExtension;
import org.mockbukkit.mockbukkit.MockBukkitInject;
import org.mockbukkit.mockbukkit.ServerMock;
import org.bukkit.entity.EntityType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockBukkitExtension.class)
class WindChargeMockTest
{
	@MockBukkitInject
	private ServerMock server;
	private WindChargeMock windCharge;

	@BeforeEach
	void prepare()
	{
		this.windCharge = new WindChargeMock(server, UUID.randomUUID());
	}

	@Test
	void getType()
	{
		assertEquals(EntityType.WIND_CHARGE, windCharge.getType());
	}

	@Test
	void explode_WhenEntityIsNotInWorld()
	{
		assertDoesNotThrow(() -> windCharge.explode());
	}
}
