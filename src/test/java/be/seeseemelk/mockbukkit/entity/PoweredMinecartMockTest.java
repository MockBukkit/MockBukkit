package be.seeseemelk.mockbukkit.entity;

import static org.junit.jupiter.api.Assertions.*;

import java.util.UUID;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import be.seeseemelk.mockbukkit.MockBukkit;
import be.seeseemelk.mockbukkit.ServerMock;

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
	public void tearDown()
	{
		MockBukkit.unmock();
	}

	@Test
	public void defaultValues()
	{
		assertEquals(0, minecartFurnace.getFuel());
		assertEquals(0, minecartFurnace.getPushX());
		assertEquals(0, minecartFurnace.getPushZ());
	}

	@Test
	public void getFuel()
	{
		minecartFurnace.setFuel(20);
		assertEquals(20, minecartFurnace.getFuel());
	}

	@Test
	public void setFuel_Negative()
	{
		assertThrows(IllegalArgumentException.class, () -> minecartFurnace.setFuel(-1));
	}

	@Test
	public void getPushX()
	{
		minecartFurnace.setPushX(1);
		assertEquals(1, minecartFurnace.getPushX());
	}

	@Test
	public void getPushZ()
	{
		minecartFurnace.setPushZ(-1);
		assertEquals(-1, minecartFurnace.getPushZ());
	}
}
