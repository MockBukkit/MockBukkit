package be.seeseemelk.mockbukkit.entity;

import be.seeseemelk.mockbukkit.MockBukkit;
import be.seeseemelk.mockbukkit.ServerMock;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class PoweredMinecartMockTest
{

	private ServerMock server;
	private PoweredMinecartMock minecartFurnace;

	@BeforeEach
	void setup()
	{
		server = MockBukkit.mock();
		minecartFurnace = new PoweredMinecartMock(server, UUID.randomUUID());
	}

	@AfterEach
	void tearDown()
	{
		MockBukkit.unmock();
	}

	@Test
	void defaultValues()
	{
		assertEquals(0, minecartFurnace.getFuel());
		assertEquals(0, minecartFurnace.getPushX());
		assertEquals(0, minecartFurnace.getPushZ());
	}

	@Test
	void getFuel()
	{
		minecartFurnace.setFuel(20);
		assertEquals(20, minecartFurnace.getFuel());
	}

	@Test
	void setFuel_Negative()
	{
		assertThrows(IllegalArgumentException.class, () -> minecartFurnace.setFuel(-1));
	}

	@Test
	void getPushX()
	{
		minecartFurnace.setPushX(1);
		assertEquals(1, minecartFurnace.getPushX());
	}

	@Test
	void getPushZ()
	{
		minecartFurnace.setPushZ(-1);
		assertEquals(-1, minecartFurnace.getPushZ());
	}

	@Test
	void getTypeTest()
	{
		assertEquals(EntityType.FURNACE_MINECART, minecartFurnace.getType());
	}

	@Test
	void testGetMinecartMaterial()
	{
		assertEquals(minecartFurnace.getMinecartMaterial(), Material.FURNACE_MINECART);
	}

}
