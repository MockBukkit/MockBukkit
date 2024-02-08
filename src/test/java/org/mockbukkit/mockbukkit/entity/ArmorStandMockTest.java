package org.mockbukkit.mockbukkit.entity;

import org.mockbukkit.mockbukkit.MockBukkit;
import org.mockbukkit.mockbukkit.ServerMock;
import org.mockbukkit.mockbukkit.world.WorldMock;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.EntityType;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.EulerAngle;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Set;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ArmorStandMockTest
{

	private ServerMock server;
	private World world;

	private ArmorStandMock armorStand;

	@BeforeEach
	void setUp()
	{
		server = MockBukkit.mock();
		world = new WorldMock();
		armorStand = new ArmorStandMock(server, UUID.randomUUID());
	}

	@AfterEach
	void tearDown()
	{
		MockBukkit.unmock();
	}

	@Test
	void testEntityType()
	{
		assertEquals(EntityType.ARMOR_STAND, armorStand.getType());
	}

	@Test
	void testEntitySpawning()
	{
		Location location = new Location(world, 100, 100, 100);
		ArmorStand orb = (ArmorStand) world.spawnEntity(location, EntityType.ARMOR_STAND);

		// Does our entity exist in the correct World?
		assertTrue(world.getEntities().contains(orb));

		// Is it at the right location?
		assertEquals(location, orb.getLocation());
	}

	@Test
	void testHasEquipment()
	{
		assertNotNull(armorStand.getEquipment());
	}

	@Test
	void testArms()
	{
		armorStand.setArms(true);
		assertTrue(armorStand.hasArms());
		armorStand.setArms(false);
		assertFalse(armorStand.hasArms());
	}

	@Test
	void testSmall()
	{
		armorStand.setSmall(true);
		assertTrue(armorStand.isSmall());
		armorStand.setSmall(false);
		assertFalse(armorStand.isSmall());
	}

	@Test
	void testMarker()
	{
		armorStand.setMarker(true);
		assertTrue(armorStand.isMarker());
		armorStand.setMarker(false);
		assertFalse(armorStand.isMarker());
	}

	@Test
	void testBasePlate()
	{
		armorStand.setBasePlate(true);
		assertTrue(armorStand.hasBasePlate());
		armorStand.setBasePlate(false);
		assertFalse(armorStand.hasBasePlate());
	}

	@Test
	void testVisible()
	{
		armorStand.setVisible(true);
		assertTrue(armorStand.isVisible());
		armorStand.setVisible(false);
		assertFalse(armorStand.isVisible());
	}

	@Test
	void testHeadPose()
	{
		armorStand.setHeadPose(new EulerAngle(5, 5, 5));
		assertEquals(armorStand.getHeadPose(), new EulerAngle(5, 5, 5));
	}

	@Test
	void testBodyPose()
	{
		armorStand.setBodyPose(new EulerAngle(5, 5, 5));
		assertEquals(armorStand.getBodyPose(), new EulerAngle(5, 5, 5));
	}

	@Test
	void testLeftArm()
	{
		armorStand.setLeftArmPose(new EulerAngle(5, 5, 5));
		assertEquals(armorStand.getLeftArmPose(), new EulerAngle(5, 5, 5));
	}

	@Test
	void testRightArm()
	{
		armorStand.setRightArmPose(new EulerAngle(5, 5, 5));
		assertEquals(armorStand.getRightArmPose(), new EulerAngle(5, 5, 5));
	}

	@Test
	void testLeftLeg()
	{
		armorStand.setLeftLegPose(new EulerAngle(5, 5, 5));
		assertEquals(armorStand.getLeftLegPose(), new EulerAngle(5, 5, 5));
	}

	@Test
	void testRightLeg()
	{
		armorStand.setRightLegPose(new EulerAngle(5, 5, 5));
		assertEquals(armorStand.getRightLegPose(), new EulerAngle(5, 5, 5));
	}

	@Test
	void testGetBootsDefault()
	{
		assertEquals(Material.AIR, armorStand.getBoots().getType());
	}

	@Test
	void testSetBoots()
	{
		armorStand.setBoots(new ItemStack(Material.IRON_BOOTS));
		assertEquals(Material.IRON_BOOTS, armorStand.getBoots().getType());
	}

	@Test
	void testGetLeggingsDefault()
	{
		assertEquals(Material.AIR, armorStand.getLeggings().getType());
	}

	@Test
	void testSetLeggings()
	{
		armorStand.setLeggings(new ItemStack(Material.IRON_LEGGINGS));
		assertEquals(Material.IRON_LEGGINGS, armorStand.getLeggings().getType());
	}

	@Test
	void testGetChestPlate()
	{
		assertEquals(Material.AIR, armorStand.getChestplate().getType());
	}

	@Test
	void testSetChestPlate()
	{
		armorStand.setChestplate(new ItemStack(Material.IRON_CHESTPLATE));
		assertEquals(Material.IRON_CHESTPLATE, armorStand.getChestplate().getType());
	}

	@Test
	void testGetHelmetDefault()
	{
		assertEquals(Material.AIR, armorStand.getHelmet().getType());
	}

	@Test
	void testSetHelmet()
	{
		armorStand.setHelmet(new ItemStack(Material.IRON_HELMET));
		assertEquals(Material.IRON_HELMET, armorStand.getHelmet().getType());
	}

	@Test
	void testGetItemInHandDefault()
	{
		assertEquals(Material.AIR, armorStand.getItemInHand().getType());
	}

	@Test
	void testSetItemInHand()
	{
		armorStand.setItemInHand(new ItemStack(Material.DIAMOND_SWORD));
		assertEquals(Material.DIAMOND_SWORD, armorStand.getItemInHand().getType());
	}

	@Test
	void testGetItemHand()
	{
		armorStand.setItemInHand(new ItemStack(Material.DIAMOND_SWORD));
		assertEquals(Material.DIAMOND_SWORD, armorStand.getItem(EquipmentSlot.HAND).getType());
	}

	@Test
	void testGetItemOffHand()
	{
		armorStand.getEquipment().setItemInOffHand(new ItemStack(Material.DIAMOND_SWORD));
		assertEquals(Material.DIAMOND_SWORD, armorStand.getItem(EquipmentSlot.OFF_HAND).getType());
	}

	@Test
	void testGetItemBoots()
	{
		armorStand.setBoots(new ItemStack(Material.IRON_BOOTS));
		assertEquals(Material.IRON_BOOTS, armorStand.getItem(EquipmentSlot.FEET).getType());
	}

	@Test
	void testGetItemLeggings()
	{
		armorStand.setLeggings(new ItemStack(Material.IRON_LEGGINGS));
		assertEquals(Material.IRON_LEGGINGS, armorStand.getItem(EquipmentSlot.LEGS).getType());
	}

	@Test
	void testGetItemChestplate()
	{
		armorStand.setChestplate(new ItemStack(Material.IRON_CHESTPLATE));
		assertEquals(Material.IRON_CHESTPLATE, armorStand.getItem(EquipmentSlot.CHEST).getType());
	}

	@Test
	void testGetItemHelmet()
	{
		armorStand.setHelmet(new ItemStack(Material.IRON_HELMET));
		assertEquals(Material.IRON_HELMET, armorStand.getItem(EquipmentSlot.HEAD).getType());
	}

	@Test
	void testGetItemNulLThrows()
	{
		assertThrows(NullPointerException.class, () -> armorStand.getItem(null));
	}

	@Test
	void testSetItemNulLThrows()
	{
		ItemStack item = new ItemStack(Material.DIAMOND_SWORD);
		assertThrows(NullPointerException.class, () -> armorStand.setItem(null, item));
	}

	@Test
	void testSetItemNullSetsAir()
	{
		armorStand.setItem(EquipmentSlot.HAND, null);
		assertEquals(Material.AIR, armorStand.getItem(EquipmentSlot.HAND).getType());
	}

	@Test
	void testSetItemMainHand()
	{
		armorStand.setItem(EquipmentSlot.HAND, new ItemStack(Material.DIAMOND_SWORD));
		assertEquals(Material.DIAMOND_SWORD, armorStand.getItem(EquipmentSlot.HAND).getType());
	}

	@Test
	void testSetItemOffHand()
	{
		armorStand.setItem(EquipmentSlot.OFF_HAND, new ItemStack(Material.DIAMOND_SWORD));
		assertEquals(Material.DIAMOND_SWORD, armorStand.getItem(EquipmentSlot.OFF_HAND).getType());
	}

	@Test
	void testSetItemBoots()
	{
		armorStand.setItem(EquipmentSlot.FEET, new ItemStack(Material.IRON_BOOTS));
		assertEquals(Material.IRON_BOOTS, armorStand.getItem(EquipmentSlot.FEET).getType());
	}

	@Test
	void testSetItemLeggings()
	{
		armorStand.setItem(EquipmentSlot.LEGS, new ItemStack(Material.IRON_LEGGINGS));
		assertEquals(Material.IRON_LEGGINGS, armorStand.getItem(EquipmentSlot.LEGS).getType());
	}

	@Test
	void testSetItemChestplate()
	{
		armorStand.setItem(EquipmentSlot.CHEST, new ItemStack(Material.IRON_CHESTPLATE));
		assertEquals(Material.IRON_CHESTPLATE, armorStand.getItem(EquipmentSlot.CHEST).getType());
	}

	@Test
	void testSetItemHelmet()
	{
		armorStand.setItem(EquipmentSlot.HEAD, new ItemStack(Material.IRON_HELMET));
		assertEquals(Material.IRON_HELMET, armorStand.getItem(EquipmentSlot.HEAD).getType());
	}

	@Test
	void testGetDisabledSlotsDefault()
	{
		assertEquals(0, armorStand.getDisabledSlots().size());
	}

	@Test
	void testSetDisabledSlots()
	{
		armorStand.setDisabledSlots(EquipmentSlot.CHEST, EquipmentSlot.FEET);
		assertEquals(2, armorStand.getDisabledSlots().size());
		assertTrue(armorStand.getDisabledSlots().containsAll(Set.of(EquipmentSlot.CHEST, EquipmentSlot.FEET)));
	}

	@Test
	void testSetDisabledSlotsNullThrows()
	{
		assertThrows(NullPointerException.class, () -> armorStand.setDisabledSlots((EquipmentSlot) null));
	}

	@Test
	void testAddDisabledSlots()
	{
		armorStand.addDisabledSlots(EquipmentSlot.FEET);
		assertEquals(1, armorStand.getDisabledSlots().size());
		armorStand.addDisabledSlots(EquipmentSlot.HAND);
		assertEquals(2, armorStand.getDisabledSlots().size());
		assertTrue(armorStand.getDisabledSlots().containsAll(Set.of(EquipmentSlot.HAND, EquipmentSlot.FEET)));
	}

	@Test
	void testAddDisabledSlotsNullThrows()
	{
		assertThrows(NullPointerException.class, () -> armorStand.addDisabledSlots((EquipmentSlot) null));
	}

	@Test
	void testRemoveDisabledSlots()
	{
		armorStand.setDisabledSlots(EquipmentSlot.HAND, EquipmentSlot.FEET);
		assertTrue(armorStand.getDisabledSlots().containsAll(Set.of(EquipmentSlot.HAND, EquipmentSlot.FEET)));
		armorStand.removeDisabledSlots(EquipmentSlot.HAND);
		assertFalse(armorStand.getDisabledSlots().containsAll(Set.of(EquipmentSlot.HAND, EquipmentSlot.FEET)));
	}

	@Test
	void testRemoveDisabledSlotsNullThrows()
	{
		assertThrows(NullPointerException.class, () -> armorStand.removeDisabledSlots((EquipmentSlot) null));
	}

	@Test
	void testIsSlotDisabled()
	{
		armorStand.addDisabledSlots(EquipmentSlot.FEET);
		assertTrue(armorStand.isSlotDisabled(EquipmentSlot.FEET));
		assertFalse(armorStand.isSlotDisabled(EquipmentSlot.HAND));
	}

	@Test
	void testIsSlotDisabledNullThrows()
	{
		assertThrows(NullPointerException.class, () -> armorStand.isSlotDisabled(null));
	}

}
