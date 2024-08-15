package be.seeseemelk.mockbukkit.entity;

import be.seeseemelk.mockbukkit.MockBukkit;
import be.seeseemelk.mockbukkit.ServerMock;
import org.bukkit.Sound;
import org.bukkit.entity.EntityType;
import org.bukkit.inventory.Inventory;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertSame;

class PillagerMockTest
{

	private ServerMock server;
	private PillagerMock pillager;

	@BeforeEach
	void setUp()
	{
		server = MockBukkit.mock();
		pillager = new PillagerMock(server, UUID.randomUUID());
	}

	@AfterEach
	void tearDown()
	{
		MockBukkit.unmock();
	}

	@Test
	void getCelebrationSound()
	{
		assertEquals(Sound.ENTITY_PILLAGER_CELEBRATE, pillager.getCelebrationSound());
	}

	@Test
	void getInventory()
	{
		Inventory inventory = pillager.getInventory();

		assertNotNull(inventory);
		assertEquals(5, inventory.getSize());
		assertSame(inventory, pillager.getInventory());
	}

	@Test
	void getType()
	{
		assertEquals(EntityType.PILLAGER, pillager.getType());
	}

	@Test
	void getEyeHeight_GivenDefaultPosition()
	{
		assertEquals(1.6575, pillager.getEyeHeight());
	}

}
