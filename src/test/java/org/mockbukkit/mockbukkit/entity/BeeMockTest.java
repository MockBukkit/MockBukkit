package org.mockbukkit.mockbukkit.entity;

import org.mockbukkit.mockbukkit.MockBukkit;
import org.mockbukkit.mockbukkit.ServerMock;
import net.kyori.adventure.util.TriState;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.WorldCreator;
import org.bukkit.entity.EntityType;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class BeeMockTest
{

	private BeeMock bee;

	@BeforeEach
	void setUp()
	{
		ServerMock server = MockBukkit.mock();
		World world = new WorldCreator("world").createWorld();
		bee = new BeeMock(server, UUID.randomUUID());
		bee.setLocation(new Location(world, 0, 0, 0));
	}

	@AfterEach
	void tearDown()
	{
		MockBukkit.unmock();
	}

	@Test
	void testGetType()
	{
		assertEquals(EntityType.BEE, bee.getType());
	}

	@Test
	void testGetHiveDefault()
	{
		assertNull(bee.getHive());
	}

	@Test
	void testSetHive()
	{
		Location location = new Location(bee.getWorld(), 0, 0, 0);
		bee.setHive(location);
		assertEquals(location, bee.getHive());
	}

	@Test
	void testSetHiveNullDoesntThrow()
	{
		bee.setHive(null);
		assertNull(bee.getHive());
	}

	@Test
	void testSetHiveDifferentWorld()
	{
		World world = new WorldCreator("world2").createWorld();
		Location location = new Location(world, 0, 0, 0);
		assertThrows(IllegalArgumentException.class, () -> bee.setHive(location));
	}

	@Test
	void testGetFlowerDefault()
	{
		assertNull(bee.getFlower());
	}

	@Test
	void testSetFlower()
	{
		Location location = new Location(bee.getWorld(), 0, 0, 0);
		bee.setFlower(location);
		assertEquals(location, bee.getFlower());
	}

	@Test
	void testSetFlowerNullDoesntThrow()
	{
		bee.setFlower(null);
		assertNull(bee.getFlower());
	}

	@Test
	void testSetFlowerDifferentWorld()
	{
		World world = new WorldCreator("world2").createWorld();
		Location location = new Location(world, 0, 0, 0);
		assertThrows(IllegalArgumentException.class, () -> bee.setFlower(location));
	}

	@Test
	void testHasNectarDefault()
	{
		assertFalse(bee.hasNectar());
	}

	@Test
	void testSetHasNectar()
	{
		bee.setHasNectar(true);
		assertTrue(bee.hasNectar());
	}

	@Test
	void testHasStungDefault()
	{
		assertFalse(bee.hasStung());
	}

	@Test
	void testSetHasStung()
	{
		bee.setHasStung(true);
		assertTrue(bee.hasStung());
	}

	@Test
	void testGetAngerDefault()
	{
		assertEquals(0, bee.getAnger());
	}

	@Test
	void testSetAnger()
	{
		bee.setAnger(1);
		assertEquals(1, bee.getAnger());
	}

	@Test
	void testGetCannotEnterHiveTicksDefault()
	{
		assertEquals(0, bee.getCannotEnterHiveTicks());
	}

	@Test
	void testSetCannotEnterHiveTicks()
	{
		bee.setCannotEnterHiveTicks(1);
		assertEquals(1, bee.getCannotEnterHiveTicks());
	}

	@Test
	void testGetRollingOverrideDefault()
	{
		assertEquals(TriState.NOT_SET, bee.getRollingOverride());
	}

	@Test
	void testSetRollingOverride()
	{
		bee.setRollingOverride(TriState.TRUE);
		assertEquals(TriState.TRUE, bee.getRollingOverride());
	}

	@Test
	void testSetRollingOverrideNull()
	{
		assertThrows(NullPointerException.class, () -> bee.setRollingOverride(null));
	}

	@Test
	void testIsRollingDefault()
	{
		assertFalse(bee.isRolling());
	}

	@Test
	void testIsRolling()
	{
		bee.setRollingOverride(TriState.TRUE);
		assertTrue(bee.isRolling());
	}

	@Test
	void getEyeHeight_GivenDefault()
	{
		assertEquals(0.3D, bee.getEyeHeight());
	}

	@Test
	void getEyeHeight_WhileSneaking()
	{
		bee.setBaby();
		assertEquals(0.15D, bee.getEyeHeight());
	}

}
