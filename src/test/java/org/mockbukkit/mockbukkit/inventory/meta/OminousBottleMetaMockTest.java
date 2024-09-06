package org.mockbukkit.mockbukkit.inventory.meta;

import org.mockbukkit.mockbukkit.MockBukkitExtension;
import org.mockbukkit.mockbukkit.MockBukkitInject;
import org.bukkit.Server;
import org.bukkit.inventory.meta.OminousBottleMeta;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(MockBukkitExtension.class)
class OminousBottleMetaMockTest
{

	@MockBukkitInject
	private Server server;
	private OminousBottleMeta meta;
	private int amplifier = 1;

	@BeforeEach
	void setUp()
	{
		meta = new OminousBottleMetaMock();
	}

	@Test
	void hasAmplifier_default()
	{
		assertFalse(meta.hasAmplifier());
	}

	@Test
	void setAmplifier_valid()
	{
		meta.setAmplifier(amplifier);
		assertEquals(amplifier, meta.getAmplifier());
		assertTrue(meta.hasAmplifier());
	}

	@Test
	void setAmplifier_toSmall()
	{
		assertThrows(IllegalArgumentException.class, () -> meta.setAmplifier(-1));
	}

	@Test
	void setAmplifier_toBig()
	{
		assertThrows(IllegalArgumentException.class, () -> meta.setAmplifier(100));
	}

	@Test
	void getAmplifier_invalid()
	{
		assertThrows(IllegalStateException.class, () -> meta.getAmplifier());
	}

	@Test
	void testClone()
	{
		OminousBottleMetaMock ominousMeta = new OminousBottleMetaMock();
		ominousMeta.setAmplifier(amplifier);

		OminousBottleMetaMock clone = ominousMeta.clone();

		assertEquals(ominousMeta, clone);
		assertEquals(ominousMeta.getAmplifier(), clone.getAmplifier());
	}


}
