package org.mockbukkit.mockbukkit.entity;

import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.EntityType;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockbukkit.mockbukkit.MockBukkitExtension;
import org.mockbukkit.mockbukkit.MockBukkitInject;
import org.mockbukkit.mockbukkit.ServerMock;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;

@ExtendWith(MockBukkitExtension.class)
class PillagerMockTest
{

	@MockBukkitInject
	private ServerMock server;
	@MockBukkitInject
	private PillagerMock pillager;

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

	@Test
	void finalizeSpawn_ShouldEquipCrossBowInMainHand()
	{
		pillager = new PillagerMock(server, UUID.randomUUID());
		ItemStack crossbow = pillager.getInventory().getItem(EquipmentSlot.HAND.ordinal());
		assertNull(crossbow);

		pillager.finalizeSpawn();

		crossbow = pillager.getInventory().getItem(EquipmentSlot.HAND.ordinal());
		assertNotNull(crossbow);
		assertEquals(1, crossbow.getAmount());
		assertEquals(Material.CROSSBOW, crossbow.getType());
	}

}
