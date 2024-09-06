package org.mockbukkit.mockbukkit.entity;

import org.mockbukkit.mockbukkit.MockBukkit;
import org.mockbukkit.mockbukkit.ServerMock;
import org.bukkit.entity.EntityType;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CowMockTest
{

	private ServerMock server;
	private CowMock cow;

	@BeforeEach
	void setUp()
	{
		server = MockBukkit.mock();
		cow = new CowMock(server, UUID.randomUUID());
	}

	@AfterEach
	void tearDown()
	{
		MockBukkit.unmock();
	}

	@Test
	void testGetType()
	{
		assertEquals(EntityType.COW, cow.getType());
	}

	@Test
	void getEyeHeight_GivenDefaultCow()
	{
		assertEquals(1.3D, cow.getEyeHeight());
	}

	@Test
	void getEyeHeight_GivenBabyCow()
	{
		cow.setBaby();
		assertEquals(0.65D, cow.getEyeHeight());
	}

}
