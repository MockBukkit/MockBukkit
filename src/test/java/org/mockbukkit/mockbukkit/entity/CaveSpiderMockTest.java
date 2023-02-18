package org.mockbukkit.mockbukkit.entity;

import org.mockbukkit.mockbukkit.MockBukkit;
import org.mockbukkit.mockbukkit.ServerMock;
import org.bukkit.entity.EntityType;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CaveSpiderMockTest
{

	private ServerMock serverMock;
	private CaveSpiderMock caveSpider;

	@BeforeEach
	void setUp()
	{
		serverMock = MockBukkit.mock();
		caveSpider = new CaveSpiderMock(serverMock, UUID.randomUUID());
	}

	@AfterEach
	void tearDown()
	{
		MockBukkit.unmock();
	}

	@Test
	void testGetType()
	{
		assertEquals(EntityType.CAVE_SPIDER, caveSpider.getType());
	}

}
