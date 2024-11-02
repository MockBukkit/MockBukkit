package org.mockbukkit.mockbukkit.entity;

import org.mockbukkit.mockbukkit.MockBukkit;
import org.mockbukkit.mockbukkit.ServerMock;
import org.mockbukkit.mockbukkit.inventory.ItemStackMock;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.SizedFireball;
import org.bukkit.inventory.ItemStack;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class SizedFireballMockTest
{

	private SizedFireball fireball;

	@BeforeEach
	void setUp()
	{
		ServerMock server = MockBukkit.mock();
		fireball = new SizedFireballMock(server, UUID.randomUUID());
	}

	@AfterEach
	void tearDown()
	{
		MockBukkit.unmock();
	}

	@Test
	void testGetDisplayItem()
	{
		ItemStack item = new ItemStackMock(Material.FIRE_CHARGE);
		assertEquals(item, fireball.getDisplayItem());
	}

	@Test
	void testSetDisplayItem()
	{
		ItemStack item = new ItemStackMock(Material.FIRE_CHARGE);
		fireball.setDisplayItem(item);
		assertEquals(item, fireball.getDisplayItem());
	}

	@Test
	void testSetDisplayItem_Null()
	{
		assertThrows(NullPointerException.class, () -> fireball.setDisplayItem(null));
	}

	@Test
	void testGetType()
	{
		assertEquals(EntityType.FIREBALL, fireball.getType());
	}

}
