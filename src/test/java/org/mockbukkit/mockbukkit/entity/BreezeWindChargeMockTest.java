package org.mockbukkit.mockbukkit.entity;

import org.bukkit.entity.EntityType;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockbukkit.mockbukkit.MockBukkitExtension;
import org.mockbukkit.mockbukkit.MockBukkitInject;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockBukkitExtension.class)
class BreezeWindChargeMockTest
{

	@MockBukkitInject
	private BreezeWindChargeMock breezeCharge;

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
