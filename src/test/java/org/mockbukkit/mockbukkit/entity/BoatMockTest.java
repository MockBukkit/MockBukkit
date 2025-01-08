package org.mockbukkit.mockbukkit.entity;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.TreeSpecies;
import org.bukkit.entity.Boat;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.EnumSource;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockbukkit.mockbukkit.MockBukkitExtension;
import org.mockbukkit.mockbukkit.MockBukkitInject;
import org.mockbukkit.mockbukkit.ServerMock;
import org.mockbukkit.mockbukkit.entity.boat.OakBoatMock;
import org.mockbukkit.mockbukkit.world.WorldMock;

import java.util.UUID;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(MockBukkitExtension.class)
class BoatMockTest
{

	@MockBukkitInject
	ServerMock serverMock;
	Boat boat;

	@BeforeEach
	void setup()
	{
		this.boat = new OakBoatMock(serverMock, UUID.randomUUID());
	}

	@Test
	void testGetType()
	{
		assertEquals(EntityType.OAK_BOAT, boat.getType());
	}

	@Test
	void testGetWoodTypeDefault()
	{
		assertEquals(TreeSpecies.GENERIC, boat.getWoodType());
	}

	@ParameterizedTest
	@EnumSource(TreeSpecies.class)
	void setWoodType_isNoLongerSupported(TreeSpecies species)
	{
		UnsupportedOperationException e = assertThrows(UnsupportedOperationException.class, () -> boat.setWoodType(species));
		assertEquals("Not supported - you must spawn a new entity to change boat type.", e.getMessage());
	}

	@Test
	void testGetBoatTypeDefault()
	{
		assertEquals(Boat.Type.OAK, boat.getBoatType());
	}

	@ParameterizedTest
	@EnumSource(Boat.Type.class)
	void setBoatType_isNoLongerSupported(Boat.Type type)
	{
		UnsupportedOperationException e = assertThrows(UnsupportedOperationException.class, () -> boat.setBoatType(type));
		assertEquals("Not supported - you must spawn a new entity to change boat type.", e.getMessage());
	}

	@Test
	void testGetMaxSpeedDefault()
	{
		assertEquals(0.4D, boat.getMaxSpeed());
	}

	@Test
	void testSetMaxSpeed()
	{
		boat.setMaxSpeed(2D);
		assertEquals(2D, boat.getMaxSpeed());
	}

	@Test
	void testSetMaxSpeedToLow()
	{
		IllegalArgumentException illegalArgumentException = assertThrows(IllegalArgumentException.class, () ->
		{
			boat.setMaxSpeed(-1D);
		});

		assertEquals("Speed cannot be negative", illegalArgumentException.getMessage());
	}

	@Test
	void testGetOccupiedDecelerationDefault()
	{
		assertEquals(0.2D, boat.getOccupiedDeceleration());
	}

	@Test
	void testSetOccupiedDeceleration()
	{
		boat.setOccupiedDeceleration(0.5D);
		assertEquals(0.5D, boat.getOccupiedDeceleration());
	}

	@Test
	void testSetOccupiedDecelerationToLow()
	{
		IllegalArgumentException illegalArgumentException = assertThrows(IllegalArgumentException.class, () ->
		{
			boat.setOccupiedDeceleration(-1D);
		});

		assertEquals("Rate cannot be negative", illegalArgumentException.getMessage());
	}

	@Test
	void testGetUnoccupiedDecelerationDefault()
	{
		assertEquals(-1D, boat.getUnoccupiedDeceleration());
	}

	@Test
	void testSetUnoccupiedDeceleration()
	{
		boat.setUnoccupiedDeceleration(2D);
		assertEquals(2D, boat.getUnoccupiedDeceleration());
	}

	@Test
	void testGetWorkOnLandDefault()
	{
		assertFalse(boat.getWorkOnLand());
	}

	@Test
	void testSetWorkOnLand()
	{
		boat.setWorkOnLand(true);
		assertTrue(boat.getWorkOnLand());
	}

	@ParameterizedTest
	@MethodSource
	void testGetBoatMaterial(EntityType entityType, Material material)
	{
		WorldMock world = serverMock.addSimpleWorld("test");
		Entity actualEntity = world.spawnEntity(new Location(world, 0, 0, 0), entityType);

		BoatMock actualBoat = assertInstanceOf(BoatMock.class, actualEntity);
		assertEquals(material, actualBoat.getBoatMaterial());
	}

	public static @NotNull Stream<Arguments> testGetBoatMaterial()
	{
		return Stream.of(
				// Normal boat
				Arguments.of(EntityType.OAK_BOAT, Material.OAK_BOAT),
				Arguments.of(EntityType.BIRCH_BOAT, Material.BIRCH_BOAT),
				Arguments.of(EntityType.ACACIA_BOAT, Material.ACACIA_BOAT),
				Arguments.of(EntityType.SPRUCE_BOAT, Material.SPRUCE_BOAT),
				Arguments.of(EntityType.JUNGLE_BOAT, Material.JUNGLE_BOAT),
				Arguments.of(EntityType.BAMBOO_RAFT, Material.BAMBOO_RAFT),
				Arguments.of(EntityType.CHERRY_BOAT, Material.CHERRY_BOAT),
				Arguments.of(EntityType.DARK_OAK_BOAT, Material.DARK_OAK_BOAT),
				Arguments.of(EntityType.MANGROVE_BOAT, Material.MANGROVE_BOAT),
				// Chest boat
				Arguments.of(EntityType.OAK_CHEST_BOAT, Material.OAK_CHEST_BOAT),
				Arguments.of(EntityType.BIRCH_CHEST_BOAT, Material.BIRCH_CHEST_BOAT),
				Arguments.of(EntityType.ACACIA_CHEST_BOAT, Material.ACACIA_CHEST_BOAT),
				Arguments.of(EntityType.SPRUCE_CHEST_BOAT, Material.SPRUCE_CHEST_BOAT),
				Arguments.of(EntityType.JUNGLE_CHEST_BOAT, Material.JUNGLE_CHEST_BOAT),
				Arguments.of(EntityType.BAMBOO_CHEST_RAFT, Material.BAMBOO_CHEST_RAFT),
				Arguments.of(EntityType.CHERRY_CHEST_BOAT, Material.CHERRY_CHEST_BOAT),
				Arguments.of(EntityType.DARK_OAK_CHEST_BOAT, Material.DARK_OAK_CHEST_BOAT),
				Arguments.of(EntityType.MANGROVE_CHEST_BOAT, Material.MANGROVE_CHEST_BOAT)
		);
	}

}
