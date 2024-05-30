package be.seeseemelk.mockbukkit.inventory;

import be.seeseemelk.mockbukkit.MockBukkit;
import be.seeseemelk.mockbukkit.ServerMock;
import be.seeseemelk.mockbukkit.entity.ArmorStandMock;
import be.seeseemelk.mockbukkit.entity.ZombieMock;
import org.bukkit.Material;
import org.bukkit.entity.ArmorStand;
import org.bukkit.inventory.EntityEquipment;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;

import java.util.Arrays;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNotSame;
import static org.junit.jupiter.api.Assertions.assertThrows;

class EntityEquipmentMockTest
{

	private EntityEquipment equipment;
	private ZombieMock holder;
	private ServerMock server;

	@BeforeEach
	void setUp()
	{
		server = MockBukkit.mock();
		holder = new ZombieMock(server, UUID.randomUUID());
		equipment = holder.getEquipment();
	}

	@AfterEach
	void tearDown()
	{
		MockBukkit.unmock();
	}

	@Test
	void testMainHand()
	{

		assertNotNull(equipment.getItemInMainHand());

		ItemStack item = new ItemStack(Material.DIAMOND);
		equipment.setItemInMainHand(item);

		assertEquals(item, equipment.getItemInMainHand());
		assertNotSame(item, equipment.getItemInMainHand());
		assertEquals(item, equipment.getItem(EquipmentSlot.HAND));
	}

	@Test
	void testOffHand()
	{
		assertNotNull(equipment.getItemInOffHand());

		ItemStack item = new ItemStack(Material.DIAMOND);
		equipment.setItemInOffHand(item);

		assertEquals(item, equipment.getItemInOffHand());
		assertNotSame(item, equipment.getItemInOffHand());
		assertEquals(item, equipment.getItem(EquipmentSlot.OFF_HAND));
	}

	@Test
	void testHelmet()
	{
		assertNotNull(equipment.getHelmet());

		ItemStack item = new ItemStack(Material.DIAMOND);
		equipment.setHelmet(item);

		assertEquals(item, equipment.getHelmet());
		assertNotSame(item, equipment.getHelmet());
		assertEquals(item, equipment.getItem(EquipmentSlot.HEAD));
	}

	@Test
	void testChestplate()
	{
		assertNotNull(equipment.getChestplate());

		ItemStack item = new ItemStack(Material.DIAMOND);
		equipment.setChestplate(item);

		assertEquals(item, equipment.getChestplate());
		assertNotSame(item, equipment.getChestplate());
		assertEquals(item, equipment.getItem(EquipmentSlot.CHEST));
	}

	@Test
	void testLeggings()
	{
		assertNotNull(equipment.getLeggings());

		ItemStack item = new ItemStack(Material.DIAMOND);
		equipment.setLeggings(item);

		assertEquals(item, equipment.getLeggings());
		assertNotSame(item, equipment.getLeggings());
		assertEquals(item, equipment.getItem(EquipmentSlot.LEGS));
	}

	@Test
	void testBoots()
	{
		assertNotNull(equipment.getBoots());

		ItemStack item = new ItemStack(Material.DIAMOND);
		equipment.setBoots(item);

		assertEquals(item, equipment.getBoots());
		assertNotSame(item, equipment.getBoots());
		assertEquals(item, equipment.getItem(EquipmentSlot.FEET));
	}

	@Test
	void testMainHandDropChance()
	{
		equipment.setItemInMainHandDropChance(0.75f);

		assertEquals(0.75f, equipment.getItemInMainHandDropChance());
	}

	@Test
	void testOffHandDropChance()
	{
		equipment.setItemInOffHandDropChance(0.75f);

		assertEquals(0.75f, equipment.getItemInOffHandDropChance());
	}

	@Test
	void testHelmetDropChance()
	{
		equipment.setHelmetDropChance(0.75f);

		assertEquals(0.75f, equipment.getHelmetDropChance());
	}

	@Test
	void testChestplateDropChance()
	{
		equipment.setChestplateDropChance(0.75f);

		assertEquals(0.75f, equipment.getChestplateDropChance());
	}

	@Test
	void testLeggingsDropChance()
	{
		equipment.setLeggingsDropChance(0.75f);

		assertEquals(0.75f, equipment.getLeggingsDropChance());
	}

	@Test
	void testBootsDropChance()
	{
		equipment.setBootsDropChance(0.75f);

		assertEquals(0.75f, equipment.getBootsDropChance());
	}

	@Test
	void setDropChance_NonMob()
	{
		ArmorStand armorStand = new ArmorStandMock(server, UUID.randomUUID());
		EntityEquipment equipment = armorStand.getEquipment();
		assertThrows(IllegalArgumentException.class, () -> {
			equipment.setHelmetDropChance(0.5f);
		});
	}

	@Test
	void testGetArmorContentsDefault()
	{

		assertEquals(4, equipment.getArmorContents().length);
		Arrays.stream(equipment.getArmorContents()).forEach(entry -> {
			assertEquals(Material.AIR, entry.getType());
		});
	}

	@Test
	void testSetArmorContents()
	{
		ItemStack[] contents = new ItemStack[4];
		contents[0] = new ItemStack(Material.DIAMOND);
		contents[1] = new ItemStack(Material.DIAMOND);
		contents[2] = new ItemStack(Material.DIAMOND);
		contents[3] = new ItemStack(Material.DIAMOND);
		equipment.setArmorContents(contents);
		assertArrayEquals(contents, equipment.getArmorContents());
	}

	@Test
	void testSetArmorContentsNullThrows()
	{
		assertThrows(NullPointerException.class, () -> equipment.setArmorContents(null));
	}

	@Test
	void testClear()
	{
		ItemStack[] contents = new ItemStack[4];
		contents[0] = new ItemStack(Material.DIAMOND);
		contents[1] = new ItemStack(Material.DIAMOND);
		contents[2] = new ItemStack(Material.DIAMOND);
		contents[3] = new ItemStack(Material.DIAMOND);
		equipment.setArmorContents(contents);
		equipment.clear();
		ItemStack[] excepted = new ItemStack[4];
		Arrays.fill(excepted, new ItemStack(Material.AIR));
		assertArrayEquals(excepted, equipment.getArmorContents());
	}

	@ParameterizedTest
	@EnumSource(EquipmentSlot.class)
	void testGetItem(EquipmentSlot slot)
	{
		equipment.setItem(slot, null);
		assertEquals(new ItemStack(Material.AIR), equipment.getItem(slot));
		ItemStack item = new ItemStack(Material.DIAMOND);
		equipment.setItem(slot, item);
		assertEquals(item, equipment.getItem(slot));
	}

	@Test
	void testGetItemInHandDefault()
	{
		assertEquals(Material.AIR, equipment.getItemInMainHand().getType());
	}

	@Test
	void testSetItemInHand()
	{
		ItemStack item = new ItemStack(Material.DIAMOND);
		equipment.setItemInHand(item);
		assertEquals(item, equipment.getItemInHand());
	}

	@Test
	void testGetHolder()
	{
		assertEquals(holder, equipment.getHolder());
	}

}
