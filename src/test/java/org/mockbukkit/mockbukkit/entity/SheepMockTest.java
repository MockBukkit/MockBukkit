package org.mockbukkit.mockbukkit.entity;

import org.mockbukkit.mockbukkit.MockBukkit;
import org.mockbukkit.mockbukkit.ServerMock;
import org.bukkit.DyeColor;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

class SheepMockTest
{

	private ServerMock server;
	private SheepMock sheep;

	@BeforeEach
	void setUp()
	{
		server = MockBukkit.mock();
		sheep = new SheepMock(server, UUID.randomUUID());
	}

	@AfterEach
	void tearDown()
	{
		MockBukkit.unmock();
	}

	@Test
	void testGetSheared()
	{
		assertFalse(sheep.isSheared());
	}

	@Test
	void testSetSheared()
	{
		sheep.setSheared(true);
		assertTrue(sheep.isSheared());
	}

	@Test
	void testGetColor()
	{
		assertSame(sheep.getColor(), DyeColor.WHITE);
	}

	@Test
	void testSetColor()
	{
		sheep.setColor(DyeColor.BLUE);
		assertSame(sheep.getColor(), DyeColor.BLUE);
	}

}
