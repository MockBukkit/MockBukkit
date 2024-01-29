package be.seeseemelk.mockbukkit.entity;

import be.seeseemelk.mockbukkit.MockBukkit;
import be.seeseemelk.mockbukkit.ServerMock;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.inventory.ItemStack;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class ThrownExpBottleMockTest
{

	private ThrownExpBottleMock bottle;

	@BeforeEach
	void setUp()
	{
		ServerMock server = MockBukkit.mock();
		bottle = new ThrownExpBottleMock(server, UUID.randomUUID());
	}

	@AfterEach
	void tearDown()
	{
		MockBukkit.unmock();
	}

	@Test
	void testGetItem()
	{
		assertEquals(Material.EXPERIENCE_BOTTLE, bottle.getItem().getType());
	}

	@Test
	void testSetItem()
	{
		bottle.setItem(new ItemStack(Material.DIAMOND, 32));

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
		assertEquals(EntityType.THROWN_EXP_BOTTLE, bottle.getType());
	}

}
