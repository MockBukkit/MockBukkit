package org.mockbukkit.mockbukkit.entity;

import org.mockbukkit.mockbukkit.MockBukkitExtension;
import org.mockbukkit.mockbukkit.MockBukkitInject;
import org.mockbukkit.mockbukkit.ServerMock;
import org.mockbukkit.mockbukkit.inventory.ItemStackMock;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Snowball;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockBukkitExtension.class)
class SnowballMockTest
{

	@MockBukkitInject
	private ServerMock server;
	private Snowball snowball;

	@BeforeEach
	void setUp()
	{
		snowball = new SnowballMock(server, UUID.randomUUID());
	}

	@Test
	void testGetType()
	{
		assertEquals(EntityType.SNOWBALL, snowball.getType());
	}

	@Test
	void testGetItem()
	{
		assertEquals(Material.SNOWBALL, snowball.getItem().getType());
		assertEquals(1, snowball.getItem().getAmount());
	}

	@Test
	void testSetItem()
	{
		snowball.setItem(new ItemStackMock(Material.DIAMOND, 5));
		assertEquals(Material.DIAMOND, snowball.getItem().getType());
		assertEquals(1, snowball.getItem().getAmount());
	}

	@Test
	void testSetItemNull()
	{
		assertThrows(IllegalArgumentException.class, () -> snowball.setItem(null));
	}

}
