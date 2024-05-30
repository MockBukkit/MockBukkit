package be.seeseemelk.mockbukkit.entity;

import be.seeseemelk.mockbukkit.MockBukkitExtension;
import be.seeseemelk.mockbukkit.MockBukkitInject;
import be.seeseemelk.mockbukkit.ServerMock;
import org.bukkit.Material;
import org.bukkit.TreeSpecies;
import org.bukkit.entity.Boat;
import org.bukkit.entity.EntityType;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.EnumSource;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.UUID;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(MockBukkitExtension.class)
class BoatMockTest
{

	@MockBukkitInject
	ServerMock serverMock;
	Boat boat;

	@BeforeEach
	void setuo()
	{
		this.boat = new BoatMock(serverMock, UUID.randomUUID());
	}

	@Test
	void testGetType()
	{
		assertEquals(EntityType.BOAT, boat.getType());
	}

	@Test
	void testGetWoodTypeDefault()
	{
		assertEquals(TreeSpecies.GENERIC, boat.getWoodType());
	}

	@ParameterizedTest
	@EnumSource(TreeSpecies.class)
	void testSetWoodType(TreeSpecies species)
	{
		boat.setWoodType(species);
		assertEquals(species, boat.getWoodType());
	}

	@Test
	void testGetBoatTypeDefault()
	{
		assertEquals(Boat.Type.OAK, boat.getBoatType());
	}

	@Test
	void testSetBoatType()
	{
		boat.setBoatType(Boat.Type.BIRCH);
		assertEquals(Boat.Type.BIRCH, boat.getBoatType());
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
		IllegalArgumentException illegalArgumentException = assertThrows(IllegalArgumentException.class, () -> {
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
		IllegalArgumentException illegalArgumentException = assertThrows(IllegalArgumentException.class, () -> {
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
	void testGetBoatMaterial(Boat.Type type, Material material)
	{
		boat.setBoatType(type);
		assertEquals(material, boat.getBoatMaterial());
	}

	public static @NotNull Stream<Arguments> testGetBoatMaterial()
	{
		return Stream.of(Arguments.of(Boat.Type.OAK, Material.OAK_BOAT),
				Arguments.of(Boat.Type.BIRCH, Material.BIRCH_BOAT),
				Arguments.of(Boat.Type.ACACIA, Material.ACACIA_BOAT),
				Arguments.of(Boat.Type.SPRUCE, Material.SPRUCE_BOAT),
				Arguments.of(Boat.Type.JUNGLE, Material.JUNGLE_BOAT),
				Arguments.of(Boat.Type.BAMBOO, Material.BAMBOO_RAFT),
				Arguments.of(Boat.Type.CHERRY, Material.CHERRY_BOAT),
				Arguments.of(Boat.Type.DARK_OAK, Material.DARK_OAK_BOAT),
				Arguments.of(Boat.Type.MANGROVE, Material.MANGROVE_BOAT));
	}
}
