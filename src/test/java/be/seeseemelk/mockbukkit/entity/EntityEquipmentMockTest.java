package be.seeseemelk.mockbukkit.entity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.util.UUID;

import org.bukkit.Material;
import org.bukkit.entity.ArmorStand;
import org.bukkit.inventory.EntityEquipment;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import be.seeseemelk.mockbukkit.MockBukkit;
import be.seeseemelk.mockbukkit.ServerMock;

class EntityEquipmentMockTest
{

	private ServerMock server;

	@BeforeEach
	public void setUp()
	{
		server = MockBukkit.mock();
	}

	@AfterEach
	public void tearDown()
	{
		MockBukkit.unmock();
	}

	@Test
	void testMainHand()
	{
		ArmorStand armorStand = new ArmorStandMock(server, UUID.randomUUID());
		EntityEquipment equipment = armorStand.getEquipment();
		ItemStack item = new ItemStack(Material.DIAMOND);

		assertNull(equipment.getItemInMainHand());
		equipment.setItemInMainHand(item);

		assertEquals(item, equipment.getItemInMainHand());
		assertEquals(item, equipment.getItem(EquipmentSlot.HAND));
	}

	@Test
	void testOffHand()
	{
		ArmorStand armorStand = new ArmorStandMock(server, UUID.randomUUID());
		EntityEquipment equipment = armorStand.getEquipment();
		ItemStack item = new ItemStack(Material.DIAMOND);

		assertNull(equipment.getItemInOffHand());
		equipment.setItemInOffHand(item);

		assertEquals(item, equipment.getItemInOffHand());
		assertEquals(item, equipment.getItem(EquipmentSlot.OFF_HAND));
	}

	@Test
	void testHelmet()
	{
		ArmorStand armorStand = new ArmorStandMock(server, UUID.randomUUID());
		EntityEquipment equipment = armorStand.getEquipment();
		ItemStack item = new ItemStack(Material.DIAMOND);

		assertNull(equipment.getHelmet());
		equipment.setHelmet(item);

		assertEquals(item, equipment.getHelmet());
		assertEquals(item, equipment.getItem(EquipmentSlot.HEAD));
	}

	@Test
	void testChestplate()
	{
		ArmorStand armorStand = new ArmorStandMock(server, UUID.randomUUID());
		EntityEquipment equipment = armorStand.getEquipment();
		ItemStack item = new ItemStack(Material.DIAMOND);

		assertNull(equipment.getChestplate());
		equipment.setChestplate(item);

		assertEquals(item, equipment.getChestplate());
		assertEquals(item, equipment.getItem(EquipmentSlot.CHEST));
	}

	@Test
	void testLeggings()
	{
		ArmorStand armorStand = new ArmorStandMock(server, UUID.randomUUID());
		EntityEquipment equipment = armorStand.getEquipment();
		ItemStack item = new ItemStack(Material.DIAMOND);

		assertNull(equipment.getLeggings());
		equipment.setLeggings(item);

		assertEquals(item, equipment.getLeggings());
		assertEquals(item, equipment.getItem(EquipmentSlot.LEGS));
	}

	@Test
	void testBoots()
	{
		ArmorStand armorStand = new ArmorStandMock(server, UUID.randomUUID());
		EntityEquipment equipment = armorStand.getEquipment();
		ItemStack item = new ItemStack(Material.DIAMOND);

		assertNull(equipment.getBoots());
		equipment.setBoots(item);

		assertEquals(item, equipment.getBoots());
		assertEquals(item, equipment.getItem(EquipmentSlot.FEET));
	}

}
