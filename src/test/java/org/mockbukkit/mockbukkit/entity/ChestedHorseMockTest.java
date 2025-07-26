package org.mockbukkit.mockbukkit.entity;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockbukkit.mockbukkit.MockBukkitExtension;
import org.mockbukkit.mockbukkit.MockBukkitInject;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(MockBukkitExtension.class)
class ChestedHorseMockTest
{

	@MockBukkitInject
	private MuleMock chestedHorse;

	@Test
	void testIsCarryingChestDefault()
	{
		assertFalse(chestedHorse.isCarryingChest());
	}

	@Test
	void testIsCarryingChest()
	{
		chestedHorse.setCarryingChest(true);
		assertTrue(chestedHorse.isCarryingChest());
	}

	@Test
	void testSetCarryingChestOnAlreadyTrue()
	{
		chestedHorse.setCarryingChest(true);
		chestedHorse.setCarryingChest(true);
		assertTrue(chestedHorse.isCarryingChest());
	}

}
