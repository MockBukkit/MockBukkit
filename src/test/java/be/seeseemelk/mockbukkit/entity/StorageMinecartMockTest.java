package be.seeseemelk.mockbukkit.entity;

import be.seeseemelk.mockbukkit.MockBukkitExtension;
import be.seeseemelk.mockbukkit.MockBukkitInject;
import be.seeseemelk.mockbukkit.ServerMock;
import be.seeseemelk.mockbukkit.inventory.ItemStackMock;
import org.bukkit.Material;
import org.bukkit.entity.minecart.StorageMinecart;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.util.UUID;

import static org.bukkit.entity.EntityType.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockBukkitExtension.class)
class StorageMinecartMockTest
{

	@MockBukkitInject
	ServerMock server;
	StorageMinecart minecart;

	@BeforeEach
	void setUp() throws Exception
	{
		minecart = new StorageMinecartMock(server, UUID.randomUUID());
	}

	@Test
	void testGetInventory()
	{
		minecart.getInventory().setItem(0, new ItemStackMock(Material.DIRT));
		assertEquals(Material.DIRT, minecart.getInventory().getItem(0).getType());
	}

	@Test
	void testGetMinecartMaterial()
	{
		assertEquals(minecart.getMinecartMaterial(), Material.CHEST_MINECART);
	}

	@Test
	void testGetEntity()
	{
		assertEquals(minecart, minecart.getEntity());
	}

	@Test
	void testGetType()
	{
		assertEquals(minecart.getType(), CHEST_MINECART);
	}

}
