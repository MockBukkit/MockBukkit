package org.mockbukkit.mockbukkit.entity;

import org.mockbukkit.mockbukkit.MockBukkit;
import org.mockbukkit.mockbukkit.ServerMock;
import org.mockbukkit.mockbukkit.world.WorldMock;
import org.mockbukkit.mockbukkit.entity.data.EntitySubType;
import org.mockbukkit.mockbukkit.inventory.ItemStackMock;
import io.papermc.paper.math.Rotations;
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
	private static final double ACCEPTABLE_ERROR_DELTA = 0.0000000001;

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
		armorStand.setBoots(new ItemStackMock(Material.IRON_BOOTS));
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
		armorStand.setLeggings(new ItemStackMock(Material.IRON_LEGGINGS));
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
		armorStand.setChestplate(new ItemStackMock(Material.IRON_CHESTPLATE));
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
		armorStand.setHelmet(new ItemStackMock(Material.IRON_HELMET));
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
		armorStand.setItemInHand(new ItemStackMock(Material.DIAMOND_SWORD));
		assertEquals(Material.DIAMOND_SWORD, armorStand.getItemInHand().getType());
	}

	@Test
	void testGetItemHand()
	{
		armorStand.setItemInHand(new ItemStackMock(Material.DIAMOND_SWORD));
		assertEquals(Material.DIAMOND_SWORD, armorStand.getItem(EquipmentSlot.HAND).getType());
	}

	@Test
	void testGetItemOffHand()
	{
		armorStand.getEquipment().setItemInOffHand(new ItemStackMock(Material.DIAMOND_SWORD));
		assertEquals(Material.DIAMOND_SWORD, armorStand.getItem(EquipmentSlot.OFF_HAND).getType());
	}

	@Test
	void testGetItemBoots()
	{
		armorStand.setBoots(new ItemStackMock(Material.IRON_BOOTS));
		assertEquals(Material.IRON_BOOTS, armorStand.getItem(EquipmentSlot.FEET).getType());
	}

	@Test
	void testGetItemLeggings()
	{
		armorStand.setLeggings(new ItemStackMock(Material.IRON_LEGGINGS));
		assertEquals(Material.IRON_LEGGINGS, armorStand.getItem(EquipmentSlot.LEGS).getType());
	}

	@Test
	void testGetItemChestplate()
	{
		armorStand.setChestplate(new ItemStackMock(Material.IRON_CHESTPLATE));
		assertEquals(Material.IRON_CHESTPLATE, armorStand.getItem(EquipmentSlot.CHEST).getType());
	}

	@Test
	void testGetItemHelmet()
	{
		armorStand.setHelmet(new ItemStackMock(Material.IRON_HELMET));
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
		ItemStack item = new ItemStackMock(Material.DIAMOND_SWORD);
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
		armorStand.setItem(EquipmentSlot.HAND, new ItemStackMock(Material.DIAMOND_SWORD));
		assertEquals(Material.DIAMOND_SWORD, armorStand.getItem(EquipmentSlot.HAND).getType());
	}

	@Test
	void testSetItemOffHand()
	{
		armorStand.setItem(EquipmentSlot.OFF_HAND, new ItemStackMock(Material.DIAMOND_SWORD));
		assertEquals(Material.DIAMOND_SWORD, armorStand.getItem(EquipmentSlot.OFF_HAND).getType());
	}

	@Test
	void testSetItemBoots()
	{
		armorStand.setItem(EquipmentSlot.FEET, new ItemStackMock(Material.IRON_BOOTS));
		assertEquals(Material.IRON_BOOTS, armorStand.getItem(EquipmentSlot.FEET).getType());
	}

	@Test
	void testSetItemLeggings()
	{
		armorStand.setItem(EquipmentSlot.LEGS, new ItemStackMock(Material.IRON_LEGGINGS));
		assertEquals(Material.IRON_LEGGINGS, armorStand.getItem(EquipmentSlot.LEGS).getType());
	}

	@Test
	void testSetItemChestplate()
	{
		armorStand.setItem(EquipmentSlot.CHEST, new ItemStackMock(Material.IRON_CHESTPLATE));
		assertEquals(Material.IRON_CHESTPLATE, armorStand.getItem(EquipmentSlot.CHEST).getType());
	}

	@Test
	void testSetItemHelmet()
	{
		armorStand.setItem(EquipmentSlot.HEAD, new ItemStackMock(Material.IRON_HELMET));
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

	@Test
	void getHeight_GivenNormalArmorStand()
	{
		assertEquals(1.975D, armorStand.getHeight());
	}

	@Test
	void getHeight_GivenSmallArmorStand()
	{
		armorStand.setSmall(true);
		assertEquals(0.9875D, armorStand.getHeight());
	}

	@Test
	void getEyeHeight_GivenNormalArmorStand()
	{
		assertEquals(1.7775D, armorStand.getEyeHeight());
	}

	@Test
	void getEyeHeight_GivenSmallArmorStand()
	{
		armorStand.setSmall(true);
		assertEquals(0.88875D, armorStand.getEyeHeight());
	}

	@Test
	void getSubType_GivenNormalArmorStand()
	{
		EntitySubType actual = armorStand.getSubType();
		assertEquals(EntitySubType.DEFAULT, actual);
	}

	@Test
	void getSubType_GivenSmallArmorStand()
	{
		armorStand.setSmall(true);
		EntitySubType actual = armorStand.getSubType();
		assertEquals(EntitySubType.SMALL, actual);
	}

	@Test
	void getBodyRotations_GivenDefaultValue()
	{
		Rotations actual = armorStand.getBodyRotations();
		assertEquals(0.0, actual.x());
		assertEquals(0.0, actual.y());
		assertEquals(0.0, actual.z());

		EulerAngle actualPose = armorStand.getBodyPose();
		assertEquals(0.0, actualPose.getX(), ACCEPTABLE_ERROR_DELTA);
		assertEquals(0.0, actualPose.getY(), ACCEPTABLE_ERROR_DELTA);
		assertEquals(0.0, actualPose.getZ(), ACCEPTABLE_ERROR_DELTA);
	}

	@Test
	void getBodyRotations_GivenNewRotation()
	{
		armorStand.setBodyRotations(Rotations.ofDegrees(45, 0, 30));
		Rotations actual = armorStand.getBodyRotations();
		assertEquals(45, actual.x(), ACCEPTABLE_ERROR_DELTA);
		assertEquals(0, actual.y(), ACCEPTABLE_ERROR_DELTA);
		assertEquals(30, actual.z(), ACCEPTABLE_ERROR_DELTA);

		EulerAngle actualPose = armorStand.getBodyPose();
		assertEquals(0.7853981633974483, actualPose.getX(), ACCEPTABLE_ERROR_DELTA);
		assertEquals(0.0, actualPose.getY(), ACCEPTABLE_ERROR_DELTA);
		assertEquals(0.5235987755982988, actualPose.getZ(), ACCEPTABLE_ERROR_DELTA);
	}

	@Test
	void getLeftArmRotations_GivenDefaultValue()
	{
		Rotations actual = armorStand.getLeftArmRotations();
		assertEquals(-10.0, actual.x());
		assertEquals(0.0, actual.y());
		assertEquals(-10.0, actual.z());

		EulerAngle actualPose = armorStand.getLeftArmPose();
		assertEquals(-0.17453292519943295, actualPose.getX(), ACCEPTABLE_ERROR_DELTA);
		assertEquals(0.0, actualPose.getY(), ACCEPTABLE_ERROR_DELTA);
		assertEquals(-0.17453292519943295, actualPose.getZ(), ACCEPTABLE_ERROR_DELTA);
	}

	@Test
	void getLeftArmRotations_GivenNewRotation()
	{
		armorStand.setLeftArmRotations(Rotations.ofDegrees(45, 0, 30));
		Rotations actual = armorStand.getLeftArmRotations();
		assertEquals(45, actual.x(), ACCEPTABLE_ERROR_DELTA);
		assertEquals(0, actual.y(), ACCEPTABLE_ERROR_DELTA);
		assertEquals(30, actual.z(), ACCEPTABLE_ERROR_DELTA);

		EulerAngle actualPose = armorStand.getLeftArmPose();
		assertEquals(0.7853981633974483, actualPose.getX(), ACCEPTABLE_ERROR_DELTA);
		assertEquals(0.0, actualPose.getY(), ACCEPTABLE_ERROR_DELTA);
		assertEquals(0.5235987755982988, actualPose.getZ(), ACCEPTABLE_ERROR_DELTA);
	}

	@Test
	void getRightArmRotations_GivenDefaultValue()
	{
		Rotations actual = armorStand.getRightArmRotations();
		assertEquals(-14.999999999999998, actual.x());
		assertEquals(0.0, actual.y());
		assertEquals(10.0, actual.z());

		EulerAngle actualPose = armorStand.getRightArmPose();
		assertEquals(-0.2617993877991494, actualPose.getX(), ACCEPTABLE_ERROR_DELTA);
		assertEquals(0.0, actualPose.getY(), ACCEPTABLE_ERROR_DELTA);
		assertEquals(0.17453292519943295, actualPose.getZ(), ACCEPTABLE_ERROR_DELTA);
	}

	@Test
	void getRightArmRotations_GivenNewRotation()
	{
		armorStand.setRightArmRotations(Rotations.ofDegrees(45, 0, 30));
		Rotations actual = armorStand.getRightArmRotations();
		assertEquals(45, actual.x(), ACCEPTABLE_ERROR_DELTA);
		assertEquals(0, actual.y(), ACCEPTABLE_ERROR_DELTA);
		assertEquals(30, actual.z(), ACCEPTABLE_ERROR_DELTA);

		EulerAngle actualPose = armorStand.getRightArmPose();
		assertEquals(0.7853981633974483, actualPose.getX(), ACCEPTABLE_ERROR_DELTA);
		assertEquals(0.0, actualPose.getY(), ACCEPTABLE_ERROR_DELTA);
		assertEquals(0.5235987755982988, actualPose.getZ(), ACCEPTABLE_ERROR_DELTA);
	}

	@Test
	void getLeftLegRotations_GivenDefaultValue()
	{
		Rotations actual = armorStand.getLeftLegRotations();
		assertEquals(-1, actual.x());
		assertEquals(0, actual.y());
		assertEquals(-1, actual.z());

		EulerAngle actualPose = armorStand.getLeftLegPose();
		assertEquals(-0.017453292519943295, actualPose.getX(), ACCEPTABLE_ERROR_DELTA);
		assertEquals(0.0, actualPose.getY(), ACCEPTABLE_ERROR_DELTA);
		assertEquals(-0.017453292519943295, actualPose.getZ(), ACCEPTABLE_ERROR_DELTA);
	}

	@Test
	void getLeftLegRotations_GivenNewRotation()
	{
		armorStand.setLeftLegRotations(Rotations.ofDegrees(45, 0, 30));
		Rotations actual = armorStand.getLeftLegRotations();
		assertEquals(45, actual.x(), ACCEPTABLE_ERROR_DELTA);
		assertEquals(0, actual.y(), ACCEPTABLE_ERROR_DELTA);
		assertEquals(30, actual.z(), ACCEPTABLE_ERROR_DELTA);

		EulerAngle actualPose = armorStand.getLeftLegPose();
		assertEquals(0.7853981633974483, actualPose.getX(), ACCEPTABLE_ERROR_DELTA);
		assertEquals(0.0, actualPose.getY(), ACCEPTABLE_ERROR_DELTA);
		assertEquals(0.5235987755982988, actualPose.getZ(), ACCEPTABLE_ERROR_DELTA);
	}

	@Test
	void getRightLegRotations_GivenDefaultValue()
	{
		Rotations actual = armorStand.getRightLegRotations();
		assertEquals(1, actual.x());
		assertEquals(0, actual.y());
		assertEquals(1, actual.z());

		EulerAngle actualPose = armorStand.getRightLegPose();
		assertEquals(0.017453292519943295, actualPose.getX(), ACCEPTABLE_ERROR_DELTA);
		assertEquals(0.0, actualPose.getY(), ACCEPTABLE_ERROR_DELTA);
		assertEquals(0.017453292519943295, actualPose.getZ(), ACCEPTABLE_ERROR_DELTA);
	}

	@Test
	void getRightLegRotations_GivenNewRotation()
	{
		armorStand.setRightLegRotations(Rotations.ofDegrees(45, 0, 30));
		Rotations actual = armorStand.getRightLegRotations();
		assertEquals(45, actual.x(), ACCEPTABLE_ERROR_DELTA);
		assertEquals(0, actual.y(), ACCEPTABLE_ERROR_DELTA);
		assertEquals(30, actual.z(), ACCEPTABLE_ERROR_DELTA);

		EulerAngle actualPose = armorStand.getRightLegPose();
		assertEquals(0.7853981633974483, actualPose.getX(), ACCEPTABLE_ERROR_DELTA);
		assertEquals(0.0, actualPose.getY(), ACCEPTABLE_ERROR_DELTA);
		assertEquals(0.5235987755982988, actualPose.getZ(), ACCEPTABLE_ERROR_DELTA);
	}

	@Test
	void getHeadRotations_GivenDefaultValue()
	{
		Rotations actual = armorStand.getHeadRotations();
		assertEquals(0, actual.x());
		assertEquals(0, actual.y());
		assertEquals(0, actual.z());

		EulerAngle actualPose = armorStand.getHeadPose();
		assertEquals(0.0, actualPose.getX(), ACCEPTABLE_ERROR_DELTA);
		assertEquals(0.0, actualPose.getY(), ACCEPTABLE_ERROR_DELTA);
		assertEquals(0.0, actualPose.getZ(), ACCEPTABLE_ERROR_DELTA);
	}

	@Test
	void getHeadRotations_GivenNewRotation()
	{
		armorStand.setHeadRotations(Rotations.ofDegrees(45, 0, 30));
		Rotations actual = armorStand.getHeadRotations();
		assertEquals(45, actual.x(), ACCEPTABLE_ERROR_DELTA);
		assertEquals(0, actual.y(), ACCEPTABLE_ERROR_DELTA);
		assertEquals(30, actual.z(), ACCEPTABLE_ERROR_DELTA);

		EulerAngle actualPose = armorStand.getHeadPose();
		assertEquals(0.7853981633974483, actualPose.getX(), ACCEPTABLE_ERROR_DELTA);
		assertEquals(0.0, actualPose.getY(), ACCEPTABLE_ERROR_DELTA);
		assertEquals(0.5235987755982988, actualPose.getZ(), ACCEPTABLE_ERROR_DELTA);
	}

	@Test
	void canMove_GivenDefaultValue()
	{
		boolean actual = armorStand.canMove();
		assertTrue(actual);
	}

	@Test
	void canMove_GivenChangedValue()
	{
		armorStand.setCanMove(false);
		boolean actual = armorStand.canMove();
		assertFalse(actual);
	}

	@Test
	void canTick_GivenDefaultValue()
	{
		boolean actual = armorStand.canTick();
		assertTrue(actual);
	}

	@Test
	void canTick_GivenChangedValue()
	{
		armorStand.setCanTick(false);
		boolean actual = armorStand.canTick();
		assertFalse(actual);
	}

}
