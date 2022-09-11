package be.seeseemelk.mockbukkit.entity;

import be.seeseemelk.mockbukkit.MockBukkit;
import be.seeseemelk.mockbukkit.ServerMock;
import org.bukkit.Material;
import org.bukkit.Server;
import org.bukkit.entity.EntityType;
import org.bukkit.inventory.ItemStack;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class EggMockTest
{

	private EggMock egg;

	@BeforeEach
	void setUp()
	{
		ServerMock server = MockBukkit.mock();
		egg = new EggMock(server, UUID.randomUUID());
	}

	@AfterEach
	void tearDown()
	{
		MockBukkit.unmock();
	}

	@Test
	void testGetType()
	{
		assertEquals(EntityType.EGG, egg.getType());
	}

	@Test
	void testGetItemDefault()
	{
		assertEquals(new ItemStack(Material.EGG), egg.getItem());
	}

	@Test
	void testSetItem()
	{
		ItemStack item = new ItemStack(Material.DIAMOND);
		egg.setItem(item);
		assertEquals(egg.getItem().getType(), Material.DIAMOND);
	}

	@Test
	void testSetItemNull()
	{
		assertThrows(NullPointerException.class, () -> egg.setItem(null));
	}

}
