package be.seeseemelk.mockbukkit.entity;

import be.seeseemelk.mockbukkit.MockBukkitExtension;
import be.seeseemelk.mockbukkit.MockBukkitInject;
import be.seeseemelk.mockbukkit.ServerMock;
import org.bukkit.Material;
import org.bukkit.entity.EnderPearl;
import org.bukkit.entity.EntityType;
import org.bukkit.inventory.ItemStack;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotSame;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockBukkitExtension.class)
class EnderPearlMockTest
{

	@MockBukkitInject
	ServerMock server;
	EnderPearl pearl;

	@BeforeEach
	void setUp()
	{
		pearl = new EnderPearlMock(server, UUID.randomUUID());
	}

	@Test
	void getItem_DefaultIsEnderPearl()
	{
		assertEquals(Material.ENDER_PEARL, pearl.getItem().getType());
	}

	@Test
	void testSetItem()
	{
		ItemStack item = new ItemStack(Material.APPLE);
		pearl.setItem(item);
		assertEquals(Material.APPLE, pearl.getItem().getType());
		assertEquals(1, pearl.getItem().getAmount());
		assertNotSame(item, pearl.getItem());
		assertEquals(item, pearl.getItem());
	}

	@Test
	void testSetItem_AlwaysOne()
	{
		ItemStack item = new ItemStack(Material.APPLE, 5);
		pearl.setItem(item);
		assertEquals(1, pearl.getItem().getAmount());
	}

	@Test
	void testSetItem_Null()
	{
		NullPointerException nullPointerException = assertThrows(NullPointerException.class, () -> {
			pearl.setItem(null);
		});

		assertEquals("Item cannot be null", nullPointerException.getMessage());
	}

	@Test
	void testGetItem_ReturnsCopy()
	{
		ItemStack item = new ItemStack(Material.APPLE);
		pearl.setItem(item);
		assertNotSame(item, pearl.getItem());
		assertEquals(item, pearl.getItem());
	}

	@Test
	void getType()
	{
		assertEquals(EntityType.ENDER_PEARL, pearl.getType());
	}

}
