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
class BreezeWindChargeMockTest
{
	@MockBukkitInject
	private ServerMock server;
	private BreezeWindChargeMock breezeCharge;

	@BeforeEach
	void prepare()
	{
		this.breezeCharge = new BreezeWindChargeMock(server, UUID.randomUUID());
	}

	@Test
	void getType()
	{
		assertEquals(EntityType.BREEZE_WIND_CHARGE, breezeCharge.getType());
	}

	@Test
	void explode_WhenEntityIsNotInWorld()
	{
		assertDoesNotThrow(() -> breezeCharge.explode());
	}

}
