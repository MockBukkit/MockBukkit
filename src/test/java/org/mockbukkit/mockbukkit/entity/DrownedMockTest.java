package org.mockbukkit.mockbukkit.entity;

import org.mockbukkit.mockbukkit.MockBukkit;
import org.mockbukkit.mockbukkit.ServerMock;
import org.bukkit.entity.Drowned;
import org.bukkit.entity.EntityType;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;

class DrownedMockTest
{

	private ServerMock server;
	private DrownedMock drowned;

	@BeforeEach
	void setUp()
	{
		server = MockBukkit.mock();
		drowned = new DrownedMock(server, UUID.randomUUID());
	}

	@AfterEach
	void tearDown()
	{
		MockBukkit.unmock();
	}

	@Test
	void hasCorrectParentClasses()
	{
		assertInstanceOf(Drowned.class, drowned);
		assertInstanceOf(ZombieMock.class, drowned);
		assertInstanceOf(MockRangedEntity.class, drowned);
	}

	@Test
	void getType()
	{
		assertEquals(EntityType.DROWNED, drowned.getType());
	}

	@Test
	void getEyeHeight_GivenDefaultDrowned()
	{
		assertEquals(1.74D, drowned.getEyeHeight());
	}

	@Test
	void getEyeHeight_GivenBabyDrowned()
	{
		drowned.setBaby();
		assertEquals(0.87D, drowned.getEyeHeight());
	}

}
