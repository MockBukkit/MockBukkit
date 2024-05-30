package be.seeseemelk.mockbukkit.entity;

import be.seeseemelk.mockbukkit.MockBukkitExtension;
import be.seeseemelk.mockbukkit.MockBukkitInject;
import be.seeseemelk.mockbukkit.ServerMock;
import org.bukkit.Location;
import org.bukkit.entity.Dolphin;
import org.bukkit.entity.EntityType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockBukkitExtension.class)
class DolphinMockTest
{

	@MockBukkitInject
	private ServerMock server;
	private Dolphin dolphin;

	@BeforeEach
	void setUp()
	{
		dolphin = new DolphinMock(server, UUID.randomUUID());
	}

	@Test
	void testGetType()
	{
		assertEquals(EntityType.DOLPHIN, dolphin.getType());
	}

	@Test
	void testGetMoistness_Default()
	{
		assertEquals(2400, dolphin.getMoistness());
	}

	@Test
	void testSetMoistness()
	{
		dolphin.setMoistness(100);
		assertEquals(100, dolphin.getMoistness());
	}

	@Test
	void testGotFish_Default()
	{
		assertFalse(dolphin.hasFish());
	}

	@Test
	void testSetGotFish()
	{
		dolphin.setHasFish(true);
		assertTrue(dolphin.hasFish());
	}

	@Test
	void testGetTreasureLocation_Default()
	{
		assertEquals(new Location(null, 0, 0, 0), dolphin.getTreasureLocation());
	}

	@Test
	void testSetTreasureLocation()
	{
		Location location = new Location(null, 1, 2, 3);
		dolphin.setTreasureLocation(location);
		assertEquals(location, dolphin.getTreasureLocation());
	}

	@Test
	void testSetTreasureLocation_Null_ThrowsException()
	{
		IllegalArgumentException illegalArgumentException = assertThrows(IllegalArgumentException.class, () -> {
			dolphin.setTreasureLocation(null);
		});

		assertEquals("Location can't be null.", illegalArgumentException.getMessage());
	}

}
