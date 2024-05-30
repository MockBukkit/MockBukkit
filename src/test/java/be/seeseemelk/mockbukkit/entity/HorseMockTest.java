package be.seeseemelk.mockbukkit.entity;

import be.seeseemelk.mockbukkit.MockBukkit;
import be.seeseemelk.mockbukkit.ServerMock;
import org.bukkit.entity.Horse;
import org.bukkit.entity.Horse.Color;
import org.bukkit.entity.Horse.Style;
import org.bukkit.inventory.HorseInventory;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertThrowsExactly;
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
	void testIsCarryingChest()
	{
		assertFalse(horse.isCarryingChest());
	}

	@Test
	void testSetCarryingChest_ThrowsException()
	{
		assertThrowsExactly(UnsupportedOperationException.class, () -> horse.setCarryingChest(true));
	}

	@Test
	void testInventory()
	{
		HorseInventory inventory = horse.getInventory();
		assertInstanceOf(HorseInventory.class, inventory);
		assertTrue(inventory.isEmpty());
	}

	@Test
	void testGetVariant()
	{
		assertEquals(Horse.Variant.HORSE, horse.getVariant());
	}

	@Test
	void testGetColorNull() throws NoSuchFieldException, IllegalAccessException
	{
		Field color = horse.getClass().getDeclaredField("color");
		color.setAccessible(true);
		color.set(horse, null);
		IllegalStateException illegalStateException = assertThrowsExactly(IllegalStateException.class, horse::getColor);
		assertEquals("No color has been set", illegalStateException.getMessage());
	}

	@Test
	void testGetStyleNull() throws NoSuchFieldException, IllegalAccessException
	{
		Field style = horse.getClass().getDeclaredField("style");
		style.setAccessible(true);
		style.set(horse, null);
		IllegalStateException illegalStateException = assertThrowsExactly(IllegalStateException.class, horse::getStyle);
		assertEquals("No style has been set", illegalStateException.getMessage());
	}

	@Test
	void testGetInventoryNull() throws NoSuchFieldException, IllegalAccessException
	{
		Field inventory = horse.getClass().getDeclaredField("inventory");
		inventory.setAccessible(true);
		inventory.set(horse, null);
		IllegalStateException illegalStateException = assertThrowsExactly(IllegalStateException.class,
				horse::getInventory);
		assertEquals("No inventory has been set", illegalStateException.getMessage());
	}
}
