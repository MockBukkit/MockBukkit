package org.mockbukkit.mockbukkit.entity;

import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockbukkit.mockbukkit.MockBukkitExtension;
import org.mockbukkit.mockbukkit.MockBukkitInject;
import org.mockbukkit.mockbukkit.inventory.ItemStackMock;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockBukkitExtension.class)
class ThrownExpBottleMockTest
{

	@MockBukkitInject
	private ThrownExpBottleMock bottle;

	@Test
	void testGetItem()
	{
		assertEquals(Material.EXPERIENCE_BOTTLE, bottle.getItem().getType());
	}

	@Test
	void testSetItem()
	{
		bottle.setItem(new ItemStackMock(Material.DIAMOND, 32));

		assertEquals(Material.DIAMOND, bottle.getItem().getType());
		assertEquals(1, bottle.getItem().getAmount());
	}

	@Test
	void testSetItemNull()
	{
		assertThrows(IllegalArgumentException.class, () -> bottle.setItem(null));
	}

	@Test
	void testGetType()
	{
		assertEquals(EntityType.EXPERIENCE_BOTTLE, bottle.getType());
	}

}
