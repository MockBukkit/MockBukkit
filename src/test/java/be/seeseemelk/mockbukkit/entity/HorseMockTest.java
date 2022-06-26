package be.seeseemelk.mockbukkit.entity;

import be.seeseemelk.mockbukkit.MockBukkit;
import be.seeseemelk.mockbukkit.ServerMock;
import org.bukkit.entity.Horse.Color;
import org.bukkit.entity.Horse.Style;
import org.bukkit.inventory.HorseInventory;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertTrue;

class HorseMockTest
{

	private ServerMock server;
	private HorseMock horse;

	@BeforeEach
	void setUp()
	{
		server = MockBukkit.mock();
		horse = new HorseMock(server, UUID.randomUUID());
	}

	@AfterEach
	void tearDown()
	{
		MockBukkit.unmock();
	}

	@Test
	void testTamed()
	{
		assertFalse(horse.isTamed());
		horse.setTamed(true);
		assertTrue(horse.isTamed());
	}

	@Test
	void testColor()
	{
		assertEquals(Color.WHITE, horse.getColor());
		horse.setColor(Color.CHESTNUT);
		assertEquals(Color.CHESTNUT, horse.getColor());
	}

	@Test
	void testStyle()
	{
		assertEquals(Style.BLACK_DOTS, horse.getStyle());
		horse.setStyle(Style.WHITE_DOTS);
		assertEquals(Style.WHITE_DOTS, horse.getStyle());
	}

	@Test
	void testInventory()
	{
		HorseInventory inventory = horse.getInventory();
		assertInstanceOf(HorseInventory.class, inventory);
		assertTrue(inventory.isEmpty());
	}

}
