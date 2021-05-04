package be.seeseemelk.mockbukkit.entity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.UUID;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.EntityType;
import org.junit.After;
import org.junit.Before;
import org.junit.jupiter.api.Test;

import be.seeseemelk.mockbukkit.MockBukkit;
import be.seeseemelk.mockbukkit.ServerMock;
import be.seeseemelk.mockbukkit.WorldMock;

public class ArmorStandMockTest
{

	private ServerMock server;
	private World world;

	@Before
	public void setUp()
	{
		server = MockBukkit.mock();
		world = new WorldMock();
	}

	@After
	public void tearDown()
	{
		MockBukkit.unmock();
	}

	@Test
	public void testEntityType()
	{
		ArmorStand armorStand = new ArmorStandMock(server, UUID.randomUUID());
		assertEquals(EntityType.ARMOR_STAND, armorStand.getType());
	}

	@Test
	public void testEntitySpawning()
	{
		Location location = new Location(world, 100, 100, 100);
		ArmorStand orb = (ArmorStand) world.spawnEntity(location, EntityType.ARMOR_STAND);

		// Does our entity exist in the correct World?
		assertTrue(world.getEntities().contains(orb));

		// Is it at the right location?
		assertEquals(location, orb.getLocation());
	}

	@Test
	public void testHasEquipment()
	{
		ArmorStand armorStand = new ArmorStandMock(server, UUID.randomUUID());
		assertNotNull(armorStand.getEquipment());
	}

	@Test
	public void testArms()
	{
		ArmorStand armorStand = new ArmorStandMock(server, UUID.randomUUID());

		armorStand.setArms(true);
		assertTrue(armorStand.hasArms());
		armorStand.setArms(false);
		assertFalse(armorStand.hasArms());
	}

	@Test
	public void testSmall()
	{
		ArmorStand armorStand = new ArmorStandMock(server, UUID.randomUUID());

		armorStand.setSmall(true);
		assertTrue(armorStand.isSmall());
		armorStand.setSmall(false);
		assertFalse(armorStand.isSmall());
	}

	@Test
	public void testMarker()
	{
		ArmorStand armorStand = new ArmorStandMock(server, UUID.randomUUID());

		armorStand.setMarker(true);
		assertTrue(armorStand.isMarker());
		armorStand.setMarker(false);
		assertFalse(armorStand.isMarker());
	}

	@Test
	public void testBasePlate()
	{
		ArmorStand armorStand = new ArmorStandMock(server, UUID.randomUUID());

		armorStand.setBasePlate(true);
		assertTrue(armorStand.hasBasePlate());
		armorStand.setBasePlate(false);
		assertFalse(armorStand.hasBasePlate());
	}

	@Test
	public void testVisible()
	{
		ArmorStand armorStand = new ArmorStandMock(server, UUID.randomUUID());

		armorStand.setVisible(true);
		assertTrue(armorStand.isVisible());
		armorStand.setVisible(false);
		assertFalse(armorStand.isVisible());
	}
}
