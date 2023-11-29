package be.seeseemelk.mockbukkit.entity;

import be.seeseemelk.mockbukkit.MockBukkitExtension;
import be.seeseemelk.mockbukkit.MockBukkitInject;
import be.seeseemelk.mockbukkit.ServerMock;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Snowball;
import org.bukkit.inventory.ItemStack;
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
		snowball.setItem(new ItemStack(Material.DIAMOND, 5));
		assertEquals(Material.DIAMOND, snowball.getItem().getType());
		assertEquals(1, snowball.getItem().getAmount());
	}

	@Test
	void testSetItemNull()
	{
		assertThrows(IllegalArgumentException.class, () -> snowball.setItem(null));
	}

}
