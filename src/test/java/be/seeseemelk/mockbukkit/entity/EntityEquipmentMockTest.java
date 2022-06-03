package be.seeseemelk.mockbukkit.entity;

import be.seeseemelk.mockbukkit.MockBukkit;
import be.seeseemelk.mockbukkit.ServerMock;
import org.bukkit.Material;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Zombie;
import org.bukkit.inventory.EntityEquipment;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

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
		Zombie zombie = new ZombieMock(server, UUID.randomUUID());
		EntityEquipment equipment = zombie.getEquipment();

		assertNotNull(equipment.getItemInMainHand());

		ItemStack item = new ItemStack(Material.DIAMOND);
		equipment.setItemInMainHand(item);

		assertEquals(item, equipment.getItemInMainHand());
		assertEquals(item, equipment.getItem(EquipmentSlot.HAND));
	}

	@Test
	void testOffHand()
	{
		Zombie zombie = new ZombieMock(server, UUID.randomUUID());
		EntityEquipment equipment = zombie.getEquipment();

		assertNotNull(equipment.getItemInOffHand());

		ItemStack item = new ItemStack(Material.DIAMOND);
		equipment.setItemInOffHand(item);

		assertEquals(item, equipment.getItemInOffHand());
		assertEquals(item, equipment.getItem(EquipmentSlot.OFF_HAND));
	}

	@Test
	void testHelmet()
	{
		Zombie zombie = new ZombieMock(server, UUID.randomUUID());
		EntityEquipment equipment = zombie.getEquipment();

		assertNotNull(equipment.getHelmet());

		ItemStack item = new ItemStack(Material.DIAMOND);
		equipment.setHelmet(item);

		assertEquals(item, equipment.getHelmet());
		assertEquals(item, equipment.getItem(EquipmentSlot.HEAD));
	}

	@Test
	void testChestplate()
	{
		Zombie zombie = new ZombieMock(server, UUID.randomUUID());
		EntityEquipment equipment = zombie.getEquipment();

		assertNotNull(equipment.getChestplate());

		ItemStack item = new ItemStack(Material.DIAMOND);
		equipment.setChestplate(item);

		assertEquals(item, equipment.getChestplate());
		assertEquals(item, equipment.getItem(EquipmentSlot.CHEST));
	}

	@Test
	void testLeggings()
	{
		Zombie zombie = new ZombieMock(server, UUID.randomUUID());
		EntityEquipment equipment = zombie.getEquipment();

		assertNotNull(equipment.getLeggings());

		ItemStack item = new ItemStack(Material.DIAMOND);
		equipment.setLeggings(item);

		assertEquals(item, equipment.getLeggings());
		assertEquals(item, equipment.getItem(EquipmentSlot.LEGS));
	}

	@Test
	void testBoots()
	{
		Zombie zombie = new ZombieMock(server, UUID.randomUUID());
		EntityEquipment equipment = zombie.getEquipment();

		assertNotNull(equipment.getBoots());

		ItemStack item = new ItemStack(Material.DIAMOND);
		equipment.setBoots(item);

		assertEquals(item, equipment.getBoots());
		assertEquals(item, equipment.getItem(EquipmentSlot.FEET));
	}

	@Test
	void testMainHandDropChance()
	{
		Zombie zombie = new ZombieMock(server, UUID.randomUUID());
		EntityEquipment equipment = zombie.getEquipment();

		equipment.setItemInMainHandDropChance(0.75f);

		assertEquals(0.75f, equipment.getItemInMainHandDropChance());
	}

	@Test
	void testOffHandDropChance()
	{
		Zombie zombie = new ZombieMock(server, UUID.randomUUID());
		EntityEquipment equipment = zombie.getEquipment();

		equipment.setItemInOffHandDropChance(0.75f);

		assertEquals(0.75f, equipment.getItemInOffHandDropChance());
	}

	@Test
	void testHelmetDropChance()
	{
		Zombie zombie = new ZombieMock(server, UUID.randomUUID());
		EntityEquipment equipment = zombie.getEquipment();

		equipment.setHelmetDropChance(0.75f);

		assertEquals(0.75f, equipment.getHelmetDropChance());
	}

	@Test
	void testChestplateDropChance()
	{
		Zombie zombie = new ZombieMock(server, UUID.randomUUID());
		EntityEquipment equipment = zombie.getEquipment();

		equipment.setChestplateDropChance(0.75f);

		assertEquals(0.75f, equipment.getChestplateDropChance());
	}

	@Test
	void testLeggingsDropChance()
	{
		Zombie zombie = new ZombieMock(server, UUID.randomUUID());
		EntityEquipment equipment = zombie.getEquipment();

		equipment.setLeggingsDropChance(0.75f);

		assertEquals(0.75f, equipment.getLeggingsDropChance());
	}

	@Test
	void testBootsDropChance()
	{
		Zombie zombie = new ZombieMock(server, UUID.randomUUID());
		EntityEquipment equipment = zombie.getEquipment();

		equipment.setBootsDropChance(0.75f);

		assertEquals(0.75f, equipment.getBootsDropChance());
	}

	@Test
	void setDropChance_NonMob()
	{
		ArmorStand armorStand = new ArmorStandMock(server, UUID.randomUUID());
		assertThrows(IllegalArgumentException.class, () ->
		{
			armorStand.getEquipment().setHelmetDropChance(0.5f);
		});
	}

}
