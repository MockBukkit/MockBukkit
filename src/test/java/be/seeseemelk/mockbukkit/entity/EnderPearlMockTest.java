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
		pearl.setItem(new ItemStack(Material.APPLE));
		assertEquals(Material.APPLE, pearl.getItem().getType());
	}

	@Test
	void testSetItem_Null()
	{
		NullPointerException nullPointerException = assertThrows(NullPointerException.class, () ->
		{
			pearl.setItem(null);
		});

		assertEquals("Item cannot be null", nullPointerException.getMessage());
	}

	@Test
	void getType()
	{
		assertEquals(EntityType.ENDER_PEARL, pearl.getType());
	}
}
